package com.rong.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.AccountService;
import com.rong.business.service.AccountServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.business.service.UserTokenService;
import com.rong.business.service.UserTokenServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.CommonUtil;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.dao.SystemConfigDao;
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
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.USER_NAME_ERROR, "用户名只允许5-11位数字");
			return;
		}
		// 校验该用户名是否已被注册
		if (userService.findByUserName(userName) != null) {
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.USER_EXIST, "用户名已被使用，请重新填写");
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
			Record returnObj = new Record();
			returnObj.set("userName", userName);
			returnObj.set("token", token);
			BaseRenderJson.baseRenderObj.returnObj(this, returnObj, MyErrorCodeConfig.REQUEST_SUCCESS, "注册成功");
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.ERROR_FAIL, "注册异常");
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
		// 查询登陆信息
		User temp = userService.findByUserName(userName);
		if (temp != null) {
			// 校验用户是否被禁用
			if (!temp.getState()) {
				BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.USER_DISABLE,
						"系统发现您的账号异常，目前账号已被锁定，如有疑问，请联系客服咨询");
				return;
			}
		} else {
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.USER_NOT_EXIST, "该账号不存在");
			return;
		}
		User user = userService.findByUserNameAndPwd(userName, userPwd);
		if (user == null) {
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.USER_LOGIN_ERROR, "用户名或者密码错误");
			return;
		}
		// 旧的TOKEN失效 删除掉旧的token
		userTokenService.delByUserName(userName);
		String token = userTokenService.saveToken(user);// 保存新的token信息
		Record returnObj = new Record();
		returnObj.set("userName", userName);
		returnObj.set("token", token);
		BaseRenderJson.baseRenderObj.returnObj(this, returnObj, MyErrorCodeConfig.REQUEST_SUCCESS, "登录成功");
	}
	
	public void refreshConf() {
		SystemConfig config = new SystemConfigDao().getByKey("apiAuthIp");
		if(config!=null){
			MyConst.apiAuthIp = config.getValue();
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, "刷新成功");
			return;
		}
		BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.REQUEST_FAIL, "刷新失败");
	}
}
