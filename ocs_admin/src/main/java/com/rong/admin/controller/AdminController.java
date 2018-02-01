package com.rong.admin.controller;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.exception.CommonException;
import com.rong.common.util.CommonUtil;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.SystemAdmin;
import com.rong.system.service.SystemAdminService;
import com.rong.system.service.SystemAdminServiceImpl;
import com.rong.system.service.SystemRoleService;
import com.rong.system.service.SystemRoleServiceImpl;

public class AdminController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private SystemAdminService adminService = new SystemAdminServiceImpl();
	private SystemRoleService roleService = new SystemRoleServiceImpl();
	/**
	 * 验证码使用
	 */
	public void captcha() {
		renderCaptcha();
	}
	
	/**
	 * 管理后台登录
	 */
	@SuppressWarnings("deprecation")
	public void login() {
	    String username = getPara("userName");
	    String password = getPara("password");
	    //接收文本框值，toUpperCase()忽略验证码大小写
	    /* 验证码
	    String inputRandomCode = getPara("inputRandomCode");
	    boolean loginSuccess = CaptchaRender.validate(this, inputRandomCode.toUpperCase());
	    if(!loginSuccess){//验证码错误
	    	this.redirect("/views/login.jsp?isError=1&username="+username+"&msg="+URLEncoder.encode("验证码错误"));
	    	return;
	    }
	    */
	    try {
	    	SystemAdmin u = adminService.getByUserName(username);
	    	if(u==null){
	    		throw new CommonException("loginError", "用户不存在");
	    	}
	    	password = genPassword(password, u.getStr("salt"));
	        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
	        Subject subject = SecurityUtils.getSubject();
	        // 进行用用户名和密码验证,如果验证不过会throw exception
	        subject.login(token);
	        setSessionAttr(MyConst.SESSION_KEY, u);
	        this.redirect("/views/index.jsp");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        this.redirect("/views/login.jsp?isError=1&username="+username+"&msg="+URLEncoder.encode("用户名或者密码错误"));
	    }
	}
	
	/**
	 * 后台密码加密采用salt
	 * @param password
	 * @param salt
	 * @return
	 */
	private String genPassword(String password, String salt) {
		return CommonUtil.getMD5((password + salt));
	}

	/**
	 * 退出登录
	 */
	public void logout() {
		 Subject subject = SecurityUtils.getSubject();
		 setSessionAttr(MyConst.SESSION_KEY, null);
	     subject.logout();
	     this.redirect("/views/login.jsp");
	}
	
	/**
	 * 去编辑用户
	 */
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			SystemAdmin user = SystemAdmin.dao.findById(id);
			setAttr("user", user);
		}
		keepPara();
		setAttr("roles", roleService.getRolesList());
		render("/views/system/user/edit.jsp");
	}

	/**
	 * 编辑用户接口
	 */
	public void edit() {
		String userName = getPara("userName");
		String password = getPara("password");
		String role = getPara("role");
		Integer id = getParaToInt("id");
		String email = getPara("email");
		String mobile = getPara("mobile");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("password", password);
		paramMap.put("role", role);
		paramMap.put("email", email);
		paramMap.put("mobile", mobile);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		if (adminService.getByUserName(userName)!=null && id==null) {
			BaseRenderJson.returnJsonS(this,0,"账号已经存在");
			return;
		}
		if(id==null){
			SystemAdmin model = new SystemAdmin();
			model.setUserName(userName);
			model.setCreateTime(new Date());
			model.setRole(role);
			String salt = genSalt();
			model.setUserPassword(genPassword(password, salt));
			model.setSalt(salt);
			model.setEmail(email);
			model.setMobile(mobile);
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加新用户"+userName+"成功");
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.error("[操作日志]添加新用户"+userName+"失败");
			}
		}else{
			SystemAdmin admin = SystemAdmin.dao.findById(id);
			admin.setRole(role);
			admin.setEmail(email);
			admin.setMobile(mobile);
			if (admin.update()) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]修改用户"+admin.getUserName()+"成功");
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.error("[操作日志]修改用户"+admin.getUserName()+"失败");
			}
		}
	}

	public void delete() {
		Integer id = getParaToInt("id");
		SystemAdmin u = SystemAdmin.dao.findById(id);
		if(u.delete()){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除用户"+u.getUserName()+"成功,删除用户组中间表id:"+u.getId()+"成功");
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.error("[操作日志]删除用户"+u.getUserName()+"失败,删除用户组中间表id:"+u.getId()+"失败");
		}
		
	}

	public void backPassword() {
		Long id = getParaToLong("id");
		boolean result = adminService.backPassword(id);
		if (result) {
			BaseRenderJson.returnJsonS(this,1,"还原用户密码成功");
			logger.info("[操作日志]还原用户id为："+id+"密码成功");
		} else {
			BaseRenderJson.returnJsonS(this,0,"还原用户密码失敗");
			logger.error("[操作日志]还原用户用户id为："+id+"密码失败");
		}
	}

	public void updatePassword() {
		int id = getParaToInt("id");
		SystemAdmin au = SystemAdmin.dao.findById(id);
		String oldPassword = getPara("oldPassword");
		String password = getPara("password");
		String confirmPassword = getPara("confirmPassword");
		if (!password.equals(confirmPassword)) {
			BaseRenderJson.returnJsonS(this,0,"两次密码输入不一致");
			return;
		}
		if (oldPassword.equals(confirmPassword)) {
			BaseRenderJson.returnJsonS(this,0,"新密码与旧密码一样");
			return;
		}
		String oldEnpassword = genPassword(oldPassword, au.getStr("salt"));
		if (!oldEnpassword.equals(au.getStr("password"))) {
			BaseRenderJson.returnJsonS(this,0,"旧密码输入错误");
			return;
		}
		au.set("password", genPassword(password, au.getStr("salt")));
		if (au.update()) {
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]用户"+au.getUserName()+"更新密码成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.error("[操作日志]用户"+au.getUserName()+"更新密码失败");
		}
	}

	public void userList() {
		int page = getParaToInt("page", 1);
		Page<SystemAdmin> list = adminService.getList(page, pageSize);
		keepPara();
		setAttr("page", list);
		render("/views/system/user/list.jsp");
	}

	private String genSalt() {
		int x = (int) (Math.random() * 10000);
		String salt = String.valueOf(x);
		return salt;
	}
	public void userInfo(){
		SystemAdmin user = getSessionAttr("ADMIN_USER");
		BaseRenderJson.returnViewObjectTmplate(this, "1", user);
	}
	
	public void userInfoToEdit(){
		SystemAdmin user = getSessionAttr("ADMIN_USER");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user", user);
		map.put("roles", roleService.getRolesList());
		BaseRenderJson.returnViewObjectTmplate(this, "1", map);
	}
	
	public void userInfoEdit(){
		SystemAdmin user = getSessionAttr("ADMIN_USER");
		String role = getPara("role");
		String email = getPara("email");
		String mobile = getPara("mobile");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("role", role);
		paramMap.put("email", email);
		paramMap.put("mobile", mobile);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		user.setRole(role);
		user.setEmail(email);
		user.setMobile(mobile);
		if (user.update()) {
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]修改用户"+user.getUserName()+"成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.error("[操作日志]修改用户"+user.getUserName()+"失败");
		}
	}
}
