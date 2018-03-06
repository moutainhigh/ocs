package com.rong.admin.controller;
import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.QqService;
import com.rong.business.service.QqServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.Qq;

public class QqController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private QqService qqService = new QqServiceImpl();
	
	/**
	 * 列表
	 */
	public void list() {
		int page = getParaToInt("page", 1);
		String date = getPara("date");
		String qq = getPara("qq");
		Kv param = Kv.by("date",date).set("qq", qq);
		Page<Qq> list = qqService.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/qq/list.jsp");
	}
	
	public void delete() {
		Long id = getParaToLong("id");
		qqService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功");
	}
	
	public void clear() {
		qqService.clear();
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]清理数据成功");
	}
}
