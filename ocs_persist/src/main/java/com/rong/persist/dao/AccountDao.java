package com.rong.persist.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.util.NumberUtil;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Account;
import com.rong.persist.model.Recharge;

/**
 * 用户账户dao
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class AccountDao extends BaseDao<Account> {
	public static final Account dao = Account.dao;
	public static final String FILEDS = "id,create_time,update_time,user_name,account,consumed_sum,last_consumed_time";
	
	public Account findByUserName(String userName){
		String sql = "select " + FILEDS + " from " + Account.TABLE + " where user_name = ?";
		return dao.findFirst(sql, userName);
	}
	
	public boolean recharge(String userName,BigDecimal money){
		//money<0
		if(BigDecimal.ZERO.compareTo(money)==1){
			throw new CommonException(MyErrorCodeConfig.MONEY_ERROR, "充值金额不能小于0");
		}
		String sql = "update " + Account.TABLE +" set account = account + ?,update_time = now() where user_name = ?";
		return Db.update(sql, money,userName)>0;
	}
	
	public boolean consumed(String userName,BigDecimal account,BigDecimal money){
		if(account.compareTo(money)==-1){//账户小于消费金额
			throw new CommonException(MyErrorCodeConfig.ACCOUNT_NOT_ENOUGH, "余额不足");
		}
		String sql = "update " + Account.TABLE +" set account = account - ?,consumed_sum = consumed_sum + ?,update_time = now(),last_consumed_time = now() where user_name = ?";
		return Db.update(sql, money,money,userName)>0;
	}
	
	public boolean updateUserAccount(String userName, BigDecimal money) {
		Account account = findByUserName(userName);
		BigDecimal oldMoney = new BigDecimal("0");
		if(account==null){
			// 生成相应的账户信息
			account = new Account();
			account.setCreateTime(new Date());
			account.setUserName(userName);
			account.setAccount(money);
			account.save();
		}else{
			oldMoney = account.getAccount();
			account.setAccount(money);
			account.update();
		}
		Recharge recharge = new Recharge();
		recharge.setUserName(userName);
		recharge.setCreateTime(new Date());
		recharge.setMoney(money.subtract(oldMoney));
		recharge.setType(3);
		recharge.setRechargeCode(NumberUtil.createOrderCode(3));
		recharge.setGiveMoney(0);
		recharge.setUseState(true);
		recharge.setRemark("手动将账户余额["+oldMoney+"]调整为："+money);
		recharge.save();
		return true;
	}
}
