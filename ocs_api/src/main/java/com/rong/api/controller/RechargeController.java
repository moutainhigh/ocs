package com.rong.api.controller;
import java.math.BigDecimal;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.rong.business.service.RechargeService;
import com.rong.business.service.RechargeServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;

public class RechargeController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private RechargeService rechargeService = new RechargeServiceImpl();
	
	/**
	 * 充值
	 */
	public void saveRecharge() {
		String userName = getPara("userName");
		String money = getPara("money");
		Integer type = getParaToInt("type");
		String orderCode = getPara("orderCode");
		String remark = getPara("remark");
		rechargeService.save(userName, type, new BigDecimal(money), orderCode, remark);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "充值成功");
		logger.info("充值成功,"+userName+"充值金额："+money);
	}

}
