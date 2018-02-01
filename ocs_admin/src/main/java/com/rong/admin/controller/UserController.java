package com.rong.admin.controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.common.bean.BaseRenderJson;

public class UserController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private UserService userService = new UserServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		userService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除用户成功,id:" + id);
	}

	public void resetPwd() {
		Long id = getParaToLong("id");
		String userPwd = getPara("userPwd");
		userService.resetPwd(id, userPwd);
		BaseRenderJson.returnJsonS(this, 1, "还原用户密码成功");
		logger.info("[操作日志]还原用户成功,id：" + id);
	}

	public void list() {
		int page = getParaToInt("page", 1);
		Boolean state = getParaToBoolean("state");
		Page<Record> list = userService.getUserList(page, pageSize, state);
		keepPara();
		setAttr("page", list);
		render("/views/user/list.jsp");
	}
}
