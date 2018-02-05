package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Recharge;

/**
 * 充值记录dao
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public class RechargeDao extends BaseDao<Recharge> {

	public static final Recharge dao = Recharge.dao;

	public static final String FILEDS = "id,create_time,type,money,recharge_code,order_code,remark,use_state,user_name,give_money,reg_state,agent_id";

	public Page<Recharge> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Recharge.TABLE ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			// 充值状态 1-成功，0-失败
			Boolean useState = param.getBoolean("useState");
			if (useState != null) {
				where.append(" and use_state = " + useState);
			}
			String userName = param.getStr("userName");
			if (!StringUtils.isNullOrEmpty(userName)) {
				where.append(" and user_name = '" + userName + "'");
			}
			String date = param.getStr("date");
			if (!StringUtils.isNullOrEmpty(date)) {
				where.append(" and to_days(create_time) = to_days('"+date+"')");
			}
			// 代理id
			Long agentId = param.getLong("agentId");
			if (agentId != null  && agentId != 0) {
				where.append(" and agent_id = " + agentId + "");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public Recharge findByOrderCodeNotReg(String orderCode){
		String sql = "select " + FILEDS + " from " + Recharge.TABLE + " where order_code = ? and reg_state = false";
		return dao.findFirst(sql, orderCode);
	}
	
	public Recharge findByOrderCode(String orderCode){
		String sql = "select " + FILEDS + " from " + Recharge.TABLE + " where order_code = ? ";
		return dao.findFirst(sql, orderCode);
	}
}
