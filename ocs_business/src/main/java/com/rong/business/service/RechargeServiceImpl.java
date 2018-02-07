package com.rong.business.service;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.util.NumberUtil;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.dao.RechargeDao;
import com.rong.persist.dao.RechargeSetDao;
import com.rong.persist.dao.SystemAdminDao;
import com.rong.persist.dao.SystemConfigDao;
import com.rong.persist.model.Recharge;
import com.rong.persist.model.SystemConfig;

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
	private RechargeSetDao rechargeSetDao = new RechargeSetDao();
	private SystemAdminDao adminDao = new SystemAdminDao();
	private SystemConfigDao configDao = new SystemConfigDao();
	
	@Override
	public boolean save(String userName,int type,BigDecimal money,String orderCode,String remark,Long agentId) {
		//校验agentId是否真实存在
		if (agentId != null && adminDao.findById(agentId)==null) {
			throw new CommonException(MyErrorCodeConfig.AGENT_NOT_EXIST, "代理用户不存在");
		}
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
		recharge.setGiveMoney(rechargeSetDao.giveMoney(money.intValue()));
		recharge.setAgentId(agentId);
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

	@Override
	public Recharge findByOrderCode(String orderCode) {
		return dao.findByOrderCode(orderCode);
	}
	
	@Override
	public Recharge findByOrderCodeNotReg(String orderCode) {
		BigDecimal regMoney = new BigDecimal(688);
		SystemConfig systemConfig = configDao.getByKey("reg.money");
		if(systemConfig!=null){
			regMoney = new BigDecimal(systemConfig.getValue());
		}
		Recharge recharge = dao.findByOrderCodeNotReg(orderCode);
		if(recharge!=null && recharge.getMoney().compareTo(regMoney)==0){
			return recharge;
		}
		return null;
	}

}


