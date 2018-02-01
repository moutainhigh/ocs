package com.rong.business.service;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.NumberUtil;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.dao.RechargeDao;
import com.rong.persist.model.Recharge;

/****
 * @Project_Name:	ocs_business
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		RechargeServiceImpl.java
 * @CreateDate:		2018年2月1日 下午3:36:20
 * @Designer:		Wenqiang-Rong
 * @Desc:			
 * @ModifyHistory:	
 ****/
public class RechargeServiceImpl extends BaseServiceImpl<Recharge> implements RechargeService {
	private RechargeDao dao = new RechargeDao();
	private AccountDao accountDao = new AccountDao();
	
	@Override
	public boolean save(String userName,int type,BigDecimal money,String orderCode,String remark) {
		// 1.保存充值记录
		Recharge recharge = new Recharge();
		recharge.setUserName(userName);
		recharge.setCreateTime(new Date());
		recharge.setMoney(money);
		recharge.setType(type);
		recharge.setOrderCode(orderCode);
		recharge.setRechargeCode(NumberUtil.createOrderCode(type));
		recharge.setUseState(false);
		recharge.setRemark(remark);
		boolean result = recharge.save();
		if(result){
			// 2.充值
			boolean rechargeResult = accountDao.recharge(recharge.getUserName(), recharge.getMoney());
			if(rechargeResult){
				// 3.更新充值状态为成功
				recharge.setUseState(true);
				return recharge.update();
			}
		}
		return false;
	}

	@Override
	public Page<Recharge> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

}


