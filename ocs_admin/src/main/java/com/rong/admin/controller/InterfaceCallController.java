package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.InterfaceCallService;
import com.rong.business.service.InterfaceCallServiceImpl;
import com.rong.persist.model.InterfaceCall;

public class InterfaceCallController extends BaseController{
	private InterfaceCallService interfaceCallService = new InterfaceCallServiceImpl();
	
	/**
	 * 列表
	 */
	public void list() {
		int page = getParaToInt("page", 1);
		String date = getPara("date");
		Integer projectId = getParaToInt("projectId");
		String userName = getPara("userName");
		Kv param = Kv.by("date",date).set("projectId",projectId).set("userName",userName);
		Page<InterfaceCall> list = interfaceCallService.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/apicall/list.jsp");
	}
}
