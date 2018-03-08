package com.rong.admin.controller;
import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.AccountService;
import com.rong.business.service.AccountServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.util.CommonUtil;
import com.rong.persist.model.Account;

public class UserController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private UserService userService = new UserServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		userService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除用户成功,id:" + id);
	}

	public void resetPwd() {
		Long id = getParaToLong("id");
		String userPwd = getPara("userPwd");
		userService.resetPwd(id, CommonUtil.getMD5(userPwd));
		BaseRenderJson.returnJsonS(this, 1, "还原用户密码成功");
		logger.info("[操作日志]还原用户成功,id：" + id);
	}
	
	public void updateState() {
		Long id = getParaToLong("id");
		Boolean enable = getParaToBoolean("enable");
		userService.setEnable(id, enable);
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]禁用/启用用户成功,id：" + id);
	}

	public void list() {
		int page = getParaToInt("page", 1);
		Boolean state = getParaToBoolean("state");
		String userName = getPara("userName");
		Kv param = Kv.by("userName", userName).set("state", state);
		if(!isAdmin()){
			param.set("agentId", getUser().getId());
		}
		Page<Record> list = userService.getUserList(page, pageSize,param);
		keepPara();
		setAttr("nowDate", new Date());
		setAttr("page", list);
		render("/views/user/list.jsp");
	}
	
	public void loginList() {
		int page = getParaToInt("page", 1);
		String userName = getPara("userName");
		Kv param = Kv.by("userName", userName).set("orderByLoginTime", true);
		if(!isAdmin()){
			param.set("agentId", getUser().getId());
		}
		Page<Record> list = userService.getUserList(page, pageSize,param);
		keepPara();
		setAttr("nowDate", new Date());
		setAttr("page", list);
		render("/views/user/login_list.jsp");
	}
	
	public void editExpirDate() {
		Long id = getParaToLong("id");
		String expirDate = getPara("expirDate");
		userService.editExpirDate(id, expirDate);
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]编辑用户过期时间成功,id：" + id+",expirDate:"+expirDate);
	}
	
	public void editAccount() {
		String userName = getPara("userName");
		String money = getPara("money");
		Account account = accountService.findByUserName(userName);
		account.setAccount(new BigDecimal(money));
		account.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改用户账户成功,userName：" + userName+",account:"+money);
	}
}
