package com.rong.api.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.rong.api.jna.WebDll;
import com.rong.business.service.AccountService;
import com.rong.business.service.AccountServiceImpl;
import com.rong.business.service.InterfaceCallService;
import com.rong.business.service.InterfaceCallServiceImpl;
import com.rong.business.service.ProjectService;
import com.rong.business.service.ProjectServiceImpl;
import com.rong.business.service.QqService;
import com.rong.business.service.QqServiceImpl;
import com.rong.business.service.RechargeService;
import com.rong.business.service.RechargeServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.business.service.UserTokenService;
import com.rong.business.service.UserTokenServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.util.CommonUtil;
import com.rong.common.util.RequestUtils;
import com.rong.common.util.StringUtils;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.dao.SystemConfigDao;
import com.rong.persist.model.Account;
import com.rong.persist.model.Project;
import com.rong.persist.model.Qq;
import com.rong.persist.model.Recharge;
import com.rong.persist.model.SystemConfig;
import com.rong.persist.model.User;

/**
 * 用户接口
 * 
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class UserController extends Controller {
	private final Log logger = Log.getLog(this.getClass());
	private UserService userService = new UserServiceImpl();
	private UserTokenService userTokenService = new UserTokenServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	private InterfaceCallService interfaceCallService = new InterfaceCallServiceImpl();
	private ProjectService projectService = new ProjectServiceImpl();
	private QqService qqService = new QqServiceImpl();
	private RechargeService rechargeService = new RechargeServiceImpl();

	/**
	 * 用户注册
	 */
	public void reg() {
		String userName = getPara("userName");
		String userPwd = getPara("userPwd");
		String orderCode = getPara("orderCode");// 订单号
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("userPwd", userPwd);
		paramMap.put("orderCode", orderCode);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		// 校验用户名：5-11位数字
		if (!userName.matches(MyConst.REG_USER_NAME)) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_NAME_ERROR, "用户名只允许5-11位数字");
			return;
		}
		// 校验该用户名是否已被注册
		if (userService.findByUserName(userName) != null) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_EXIST, "用户名已被使用，请重新填写");
			return;
		}
		// 校验订单号是否已被使用（是否有效,金额是否为688）
		Recharge recharge = rechargeService.findByOrderCodeNotReg(orderCode);
		if(recharge==null){
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REG_ORDERCODE_ERROR, "无效的订单号");
			return;
		}
		User user = new User();
		user.setUserName(userName);
		user.setUserPwd(CommonUtil.getMD5(userPwd));
		user.setCreateTime(new Date());
		user.setState(true);
		user.setAgentId(recharge.getAgentId());
		try {
			// 注册信息保存
			user.save();
			// token处理
			String token = userTokenService.saveToken(user);
			// 生成相应的账户信息
			accountService.save(userName);
			// 更新充值记录中的订单号状态为已注册使用
			recharge.setRegState(true);
			recharge.update();
			// 组织返回信息
			Record returnObj = new Record();
			returnObj.set("userName", userName);
			returnObj.set("token", token);
			BaseRenderJson.baseRenderObj.returnObj(this, returnObj, MyErrorCodeConfig.REQUEST_SUCCESS, "注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.ERROR_FAIL, "注册异常");
			logger.error("" + e);
		}
	}

	/**
	 * 登录
	 */
	public void login() {
		String userName = getPara("userName");
		String userPwd = getPara("userPwd");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userName", userName);
		paraMap.put("userPwd", userPwd);
		if (CommonValidatorUtils.requiredValidate(paraMap, this)) {
			return;
		}
		User user = checkUserNameAndPwd(userName, userPwd);
		if(user == null){
			return;
		}
		// 更新用户登录ip和登录时间
		user.setLoginTime(new Date());
		user.setLoginIp(RequestUtils.getRequestIpAddress(this.getRequest()));
		user.update();
		// 旧的TOKEN失效 删除掉旧的token
		userTokenService.delByUserName(userName);
		String token = userTokenService.saveToken(user);// 保存新的token信息
		Record returnObj = new Record();
		returnObj.set("userName", userName);
		returnObj.set("token", token);
		BaseRenderJson.baseRenderObj.returnObj(this, returnObj, MyErrorCodeConfig.REQUEST_SUCCESS, "登录成功");
	}
	
	/**
	 * 查询余额
	 */
	public void account() {
		String userName = getPara("userName");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userName", userName);
		if (CommonValidatorUtils.requiredValidate(paraMap, this)) {
			return;
		}
		Account item = accountService.findByUserName(userName);
		Record returnObj = new Record();
		returnObj.set("account", item.getAccount());
		BaseRenderJson.baseRenderObj.returnObj(this, returnObj, MyErrorCodeConfig.REQUEST_SUCCESS, "查询成功");
	}
	
	/**
	 * 刷新白名单配置
	 */
	public void refreshConf() {
		SystemConfig config = new SystemConfigDao().getByKey("apiAuthIp");
		if(config!=null){
			MyConst.apiAuthIp = config.getValue();
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "刷新成功");
			return;
		}
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_FAIL, "刷新失败");
	}
	
	/**
	 * 接口调用计费
	 */
	public void consum(){
		String userName = getPara("userName");
		Long projectId = getParaToLong("projectId");
		String data = getPara("data");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userName", userName);
		paraMap.put("projectId", projectId);
		paraMap.put("data", data);
		if (CommonValidatorUtils.requiredValidate(paraMap, this)) {
			return;
		}
		String token = consumBusiness(userName, projectId,true,data);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,token ,"计费成功");
	}
	
	/**
	 * 接口调用计费-qq项目
	 */
	public void consumqq(){
		String userName = getPara("userName");
		String qq = getPara("qq");
		String qqPwd = getPara("qqPwd");
		String data = getPara("data");
		Long projectId = getParaToLong("projectId");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userName", userName);
		paraMap.put("qq", qq);
		paraMap.put("qqPwd", qqPwd);
		paraMap.put("projectId", projectId);
		paraMap.put("data", data);
		if (CommonValidatorUtils.requiredValidate(paraMap, this)) {
			return;
		}
		String token = "";
		Qq qqFind = qqService.findByQq(qq);
		// 校验qq是否存在，存在则直接取数据库token
		if(qqFind!=null){
			if(qqFind.getPwd().equals(qqPwd)){
				consumBusiness(userName, projectId,false,data);
			}else{
				token = consumBusiness(userName, projectId,true,data);
				//更新token
				qqFind.setToken(token);
				qqFind.setPwd(qqPwd);
				qqFind.update();
			}
			BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,qqFind.getToken() ,"计费成功");
		}else{
			// 保存qq
			token = consumBusiness(userName, projectId,true,data);
			qqService.save(qq, qqPwd, token);
			BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,token ,"计费成功");
		}
	}

	/**
	 * 私有的消费计费逻辑
	 * @param userName
	 * @param userPwd
	 * @param projectId
	 */
	private String consumBusiness(String userName,Long projectId,boolean hasGetToken,String data) {
		// 1.校验用户是否合法
		User user = checkUserState(userName);
		if(user == null){
			return null;
		}
		// 1.1校验项目是否存在
		Project project = projectService.findById(projectId);
		if(project==null){//项目不存在
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.PROJECT_NOT_EXIST, "项目不存在");
			return null;
		}
		// 2.校验余额是否足够
		Account userAccount = accountService.findByUserName(userName);
		if(BigDecimal.ZERO.compareTo(userAccount.getAccount())==1){
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.ACCOUNT_NOT_ENOUGH, "余额不足，请及时充值");
			return null;
		}
		// 3.调用Dll获取加密串
		try {
			data = new String(Base64.getDecoder().decode(data),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CommonException(MyErrorCodeConfig.ERROR_BAD_REQUEST, "data参数base64解密失败");
		}
		String returnStr = "";
		if(hasGetToken){
			returnStr = WebDll.Instance.enc(data, data.length());
			if(StringUtils.isNullOrEmpty(returnStr)){
				interfaceCallService.save(userName, false, projectId,project.getProjectName(),"调用DLL失败");
				BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.DLL_ERROR, "调用DLL失败");
				return null;
			}
		}
		// 4.计费
		interfaceCallService.consumed(projectId,project.getProjectName(),project.getPrice(), userName, userAccount.getAccount());
		try {
			returnStr = Base64.getEncoder().encodeToString(returnStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CommonException(MyErrorCodeConfig.ERROR_BAD_REQUEST, "returnToken参数base64加密失败");
		}
		return returnStr;
	}

	/** 校验登录用户名密码是否正确  */
	private User checkUserNameAndPwd(String userName, String userPwd) {
		User temp = checkUserState(userName);
		User user = userService.findByUserNameAndPwd(userName, userPwd);
		if (user == null) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_LOGIN_ERROR, "用户名或者密码错误");
			return null;
		}
		return temp;
	}

	/** 校验登录用户是否正常  */
	private User checkUserState(String userName) {
		// 查询登陆信息
		User temp = userService.findByUserName(userName);
		if (temp != null) {
			// 校验用户是否被禁用
			if (!temp.getState()) {
				BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_DISABLE,
						"系统发现您的账号异常，目前账号已被锁定，如有疑问，请联系客服咨询");
				return null;
			}
		} else {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_NOT_EXIST, "该账号不存在");
			return null;
		}
		return temp;
	}
}
