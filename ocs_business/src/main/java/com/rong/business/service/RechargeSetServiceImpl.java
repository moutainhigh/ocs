package com.rong.business.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.RechargeSetDao;
import com.rong.persist.model.RechargeSet;

/****
 * @Project_Name:	ocs_business
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		RechargeServiceImpl.java
 * @CreateDate:		2018年2月1日 下午3:36:20
 * @Designer:		Wenqiang-Rong
 * @Desc:			充值设置
 * @ModifyHistory:	
 ****/
public class RechargeSetServiceImpl extends BaseServiceImpl<RechargeSet> implements RechargeSetService {
	private RechargeSetDao dao = new RechargeSetDao();
	
	@Override
	public Page<RechargeSet> page(int page, int pageSize) {
		return dao.page(page, pageSize, null);
	}

	@Override
	public boolean setEnable(long id, boolean isEnable) {
		return dao.updateState(id, isEnable);
	}

	@Override
	public boolean save(int rechargeMoney, int give, String remark) {
		return dao.save(rechargeMoney, give, remark);
	}

	@Override
	public int giveMoney(int money) {
		return dao.giveMoney(money);
	}

}


