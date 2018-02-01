package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.QqService;
import com.rong.business.service.QqServiceImpl;
import com.rong.persist.model.Qq;

public class QqController extends BaseController{
	private QqService qqService = new QqServiceImpl();
	
	/**
	 * 列表
	 */
	public void list() {
		int page = getParaToInt("page", 1);
		String date = getPara("date");
		Kv param = Kv.by("date",date);
		Page<Qq> list = qqService.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/qq/list.jsp");
	}
}
