package com.rong.admin.controller;
import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.RechargeService;
import com.rong.business.service.RechargeServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.Recharge;

public class RechargeController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private RechargeService rechargeService = new RechargeServiceImpl();
	
	/**
	 * 列表
	 */
	public void list() {
		int page = getParaToInt("page", 1);
		String userName = getPara("userName");
		Boolean useState = getParaToBoolean("useState");
		String date = getPara("date");
		Kv param = Kv.by("userName", userName).set("useState", useState).set("date",date);
		if(!isAdmin()){
			param.set("agentId", getUser().getId());
		}
		Page<Recharge> list = rechargeService.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/recharge/list.jsp");
	}
	
	/**
	 * 手动充值
	 */
	public void save() {
		String userName = getPara("userName");
		String money = getPara("money");
		Integer type = getParaToInt("type");
		String orderCode = getPara("orderCode");
		String remark = getPara("remark");
		rechargeService.save(userName, type, new BigDecimal(money), orderCode, remark,null);
		BaseRenderJson.returnJsonS(this, 1, "手动充值成功");
		logger.info("[操作日志]手动充值成功,"+userName+"充值金额："+money);
	}

}
