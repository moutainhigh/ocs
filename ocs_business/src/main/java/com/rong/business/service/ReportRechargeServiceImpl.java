package com.rong.business.service;

import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.ReportRechargeDao;
import com.rong.persist.model.ReportRecharge;

/**
 * 充值报表业务实现类
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public class ReportRechargeServiceImpl extends BaseServiceImpl<ReportRecharge> implements ReportRechargeService{
	private ReportRechargeDao dao = new ReportRechargeDao();

	@Override
	public Page<ReportRecharge> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public BigDecimal sumMoney() {
		return dao.sumMomey();
	}

	@Override
	public BigDecimal sumMoney7day() {
		return dao.sumMomey7days();
	}


}


