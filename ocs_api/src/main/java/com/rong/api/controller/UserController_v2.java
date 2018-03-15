package com.rong.api.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
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
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.business.service.UserTokenService;
import com.rong.business.service.UserTokenServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.util.CommonUtil;
import com.rong.common.util.StringUtils;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.Account;
import com.rong.persist.model.InterfaceCall;
import com.rong.persist.model.Project;
import com.rong.persist.model.Qq;
import com.rong.persist.model.User;

/**
 * 用户接口2.0版本
 * 只保留修改后的接口
 * @version 2.0
 * @author Wenqiang-Rong
 * @date 2018年2月8日
 */
public class UserController_v2 extends Controller {
	private UserService userService = new UserServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	private InterfaceCallService interfaceCallService = new InterfaceCallServiceImpl();
	private ProjectService projectService = new ProjectServiceImpl();
	private QqService qqService = new QqServiceImpl();
	private UserTokenService userTokenService = new UserTokenServiceImpl();
	
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
		Record returnObj = consumBusiness(userName, projectId,true,data);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,returnObj ,"计费成功");
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
		Qq qqFind = qqService.findByQqAndUserName(qq,userName);
		Record returnObj = null;
		// 校验qq是否存在，存在则直接取数据库token
		if(qqFind!=null){
			if(qqFind.getPwd().equals(qqPwd)){
				returnObj = consumBusiness(userName, projectId,false,data);
				returnObj.set("token", qqFind.getToken());
			}else{
				returnObj = consumBusiness(userName, projectId,true,data);
				//更新token
				qqFind.setToken(returnObj.getStr("token"));
				qqFind.setPwd(qqPwd);
				qqFind.update();
			}
			BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,returnObj ,"计费成功");
		}else{
			// 保存qq
			returnObj = consumBusiness(userName, projectId,true,data);
			qqService.save(qq, qqPwd, returnObj.getStr("token"),userName);
			BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,returnObj ,"计费成功");
		}
	}

	/**
	 * 私有的消费计费逻辑
	 * @param userName
	 * @param userPwd
	 * @param projectId
	 */
	private Record consumBusiness(String userName,Long projectId,boolean hasGetToken,String data) {
		// 1.校验用户是否合法
		User user = checkUserState(userName);
		if(user == null){
			return null;
		}
		// 1.1校验项目是否存在
		Project project = projectService.findById(projectId);
		if(project==null){//项目不存在
			throw new CommonException(MyErrorCodeConfig.PROJECT_NOT_EXIST, "项目不存在");
		}
		// 2.校验余额是否足够
		Account userAccount = accountService.findByUserName(userName);
		if(BigDecimal.ZERO.compareTo(userAccount.getAccount())==1){
			throw new CommonException(MyErrorCodeConfig.ACCOUNT_NOT_ENOUGH, "余额不足，请及时充值");
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
				throw new CommonException(MyErrorCodeConfig.DLL_ERROR, "调用DLL失败");
			}
		}
		// 4.计费
		Long id = interfaceCallService.consumed(projectId,project.getProjectName(),project.getPrice(), userName, userAccount.getAccount());
		try {
			returnStr = Base64.getEncoder().encodeToString(returnStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CommonException(MyErrorCodeConfig.ERROR_BAD_REQUEST, "returnToken参数base64加密失败");
		}
		// 5.组织返回格式
		Record returnObj = new Record();
		returnObj.set("token", returnStr).set("id", id);
		return returnObj;
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
	
	/**
	 * 确认客户端是否调用成功
	 */
	public void callbackSuccess() {
		Long id = getParaToLong("id");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("id", id);
		if (CommonValidatorUtils.requiredValidate(paraMap, this)) {
			return;
		}
		InterfaceCall item = interfaceCallService.findById(id);
		if(item==null){
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_FAIL, "确认失败，id不存在");
			return;
		}
		item.setCallbackSuccess(true);
		item.update();
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "确认成功");
	}
	
	/**
	 * 用户注册，校验规则只要用户名不重复，且长度不超过11位即可
	 */
	public void reg() {
		String userName = getPara("userName");
		String userPwd = getPara("userPwd");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("userPwd", userPwd);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		// 校验用户名：不超过11长度
		if (userName.length()>11) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_NAME_ERROR, "用户名只允许11位长度字符串");
			return;
		}
		// 校验该用户名是否已被注册
		if (userService.findByUserName(userName) != null) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.USER_EXIST, "用户名已被使用，请重新填写");
			return;
		}
		User user = new User();
		user.setUserName(userName);
		user.setUserPwd(CommonUtil.getMD5(userPwd));
		user.setCreateTime(new Date());
		user.setState(true);
		try {
			// 注册信息保存
			user.save();
			// token处理
			String token = userTokenService.saveToken(user);
			// 生成相应的账户信息
			accountService.save(userName);
			// 组织返回信息
			Record returnObj = new Record();
			returnObj.set("userName", userName);
			returnObj.set("token", token);
			BaseRenderJson.baseRenderObj.returnObj(this, returnObj, MyErrorCodeConfig.REQUEST_SUCCESS, "注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.ERROR_FAIL, "注册异常");
		}
	}
}
