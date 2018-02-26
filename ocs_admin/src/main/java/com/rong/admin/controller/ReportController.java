package com.rong.admin.controller;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.ReportInterfaceCallService;
import com.rong.business.service.ReportInterfaceCallServiceImpl;
import com.rong.business.service.ReportRechargeService;
import com.rong.business.service.ReportRechargeServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.persist.model.ReportRecharge;

/**
 * 报表管理
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public class ReportController extends BaseController{
	private ReportRechargeService reportRechargeService = new ReportRechargeServiceImpl();
	private ReportInterfaceCallService reportInterfaceCallService = new ReportInterfaceCallServiceImpl();
	/**
	 * 充值报表列表-index
	 */
	public void rechargeIndex() {
		int page = getParaToInt("page", 1);
		String date = getPara("date");
		Kv param = Kv.by("date",date);
		Page<ReportRecharge> list = reportRechargeService.page(page, 7, param);
		keepPara();
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, list.getList(), "查询成功");
	}
	
	
	/**
	 * 充值报表列表
	 */
	public void rechargeList() {
		int page = getParaToInt("page", 1);
		String date = getPara("date");
		Kv param = Kv.by("date",date);
		Page<ReportRecharge> list = reportRechargeService.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		setAttr("sumMoney", reportRechargeService.sumMoney());
		setAttr("sumMoney7day", reportRechargeService.sumMoney7day());
		render("/views/report/report_recharge.jsp");
	}
	
	/**
	 * 调用报表列表
	 */
	public void callList() {
		List<Record> list = reportInterfaceCallService.list();
		keepPara();
		setAttr("list", list);
		render("/views/report/report_call.jsp");
	}
}
