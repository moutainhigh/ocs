package com.rong.business.service;
import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.ReportRecharge;

/**
 * 充值报表接口
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public interface ReportRechargeService extends BaseService<ReportRecharge>{
	Page<ReportRecharge> page(int pageNumber, int pageSize, Kv param);
	BigDecimal sumMoney();
	BigDecimal sumMoney7day();
}