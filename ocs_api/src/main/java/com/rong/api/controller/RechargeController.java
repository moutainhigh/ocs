package com.rong.api.controller;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.rong.business.service.RechargeService;
import com.rong.business.service.RechargeServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;

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
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("money", money);
		paramMap.put("type", type);
		paramMap.put("orderCode", orderCode);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		// 校验订单号是否已经存在库中
		if (rechargeService.findByOrderCode(orderCode)!=null){
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.RECHARGE_ORDERCODE_ERROR, "订单号已使用");
			return;
		}
		String remark = getPara("remark");
		Long agentId = getParaToLong("agentId");
		rechargeService.save(userName, type, new BigDecimal(money), orderCode, remark,agentId);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "充值成功");
		logger.info("充值成功,"+userName+"充值金额："+money);
	}

}
