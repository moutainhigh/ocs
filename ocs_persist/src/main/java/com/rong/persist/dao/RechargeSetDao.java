package com.rong.persist.dao;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.RechargeSet;

/**
 * 充值赠送设置dao
 * @author Wenqiang-Rong
 * @date 2018年2月3日
 */
public class RechargeSetDao extends BaseDao<RechargeSet> {
	public static final RechargeSet dao = RechargeSet.dao;
	public static final String FILEDS = "id,create_time,update_time,recharge_money,give,give_remark,state";
	
	public Page<RechargeSet> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + RechargeSet.TABLE;
		String orderBy = " order by id asc";
		sqlExceptSelect = sqlExceptSelect + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public boolean save(int rechargeMoney,int give,String remark){
		RechargeSet item = new RechargeSet();
		item.setCreateTime(new Date());
		item.setState(true);
		item.setRechargeMoney(rechargeMoney);
		item.setGive(give);
		item.setGiveRemark(remark);
		return item.save();
	}
	
	public boolean updateState(long id,boolean state){
		RechargeSet item = findById(id);
		item.setUpdateTime(new Date());
		item.setState(state);
		return item.update();
	}
	
	public int giveMoney(int money){
		String sql = "select give from " + RechargeSet.TABLE + " where recharge_money <= ? order by recharge_money desc limit 1";
		RechargeSet item = dao.findFirst(sql, money);
		if(item!=null){
			return item.getGive();
		}
		return 0;
	}
}
