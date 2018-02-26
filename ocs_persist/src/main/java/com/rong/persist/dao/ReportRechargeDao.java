package com.rong.persist.dao;

import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.ReportRecharge;

/**
 * 充值报表dao
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public class ReportRechargeDao extends BaseDao<ReportRecharge> {

	public static final ReportRecharge dao = ReportRecharge.dao;

	public static final String FILEDS = "id,create_time,report_date,sum_recharge_money,count_recharge";

	public Page<ReportRecharge> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + ReportRecharge.TABLE ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			//报表日期
			String date = param.getStr("date");
			if (!StringUtils.isNullOrEmpty(date)) {
				where.append(" and to_days(report_date) = to_days('"+date+"')");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public BigDecimal sumMomey7days(){
		String sql = "select sum(sum_recharge_money) sumMoney from "+ReportRecharge.TABLE+" where TO_DAYS(NOW()) - TO_DAYS(report_date) <= 7";
		Record item = Db.findFirst(sql);
		if(item!=null){
			return item.getBigDecimal("sumMoney");
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal sumMomey(){
		String sql = "select sum(sum_recharge_money) sumMoney from "+ReportRecharge.TABLE;
		Record item = Db.findFirst(sql);
		if(item!=null){
			return item.getBigDecimal("sumMoney");
		}
		return BigDecimal.ZERO;
	}
}
