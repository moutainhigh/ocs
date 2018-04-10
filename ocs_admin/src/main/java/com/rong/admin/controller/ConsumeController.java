package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.ConsumeService;
import com.rong.business.service.ConsumeServiceImpl;
import com.rong.persist.model.Consume;

public class ConsumeController extends BaseController{
	private ConsumeService service = new ConsumeServiceImpl();
	
	/**
	 * 列表
	 */
	public void list() {
		int page = getParaToInt("page", 1);
		String date = getPara("date");
		String projectName = getPara("projectName");
		String userName = getPara("userName");
		Kv param = Kv.by("date",date).set("projectName",projectName).set("userName",userName);
		Page<Consume> list = service.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/consume/list.jsp");
	}
}
