package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.AdTaskService;
import com.rong.business.service.AdTaskServiceImpl;
import com.rong.persist.model.AdTask;
import com.rong.persist.model.AdTaskDetail;

public class AdTaskController extends BaseController{
	private AdTaskService service = new AdTaskServiceImpl();
	
	public void list() {
		int page = getParaToInt("page", 1);
		String userName = getPara("userName");
		String orderCode = getPara("orderCode");
		String state = getPara("state");
		Kv param = Kv.by("userName",userName).set("orderCode",orderCode).set("state",state);
		Page<AdTask> list = service.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/adtask/list.jsp");
	}
	
	public void adDetailList() {
		int page = getParaToInt("page", 1);
		String orderCode = getPara("orderCode");
		Page<AdTaskDetail> list = service.pageDetail(page, page, orderCode);
		keepPara();
		setAttr("page", list);
		render("/views/adtask/detail_list.jsp");
	}
}
