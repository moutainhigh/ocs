package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.ReportInterfaceCall;

/**
 * 接口调用报表dao
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public class ReportInterfaceCallDao extends BaseDao<ReportInterfaceCall> {

	public static final ReportInterfaceCall dao = ReportInterfaceCall.dao;

	public static final String FILEDS = "id,create_time,report_date,project_name,project_price,count_success_call,count_fail_call";

	public Page<ReportInterfaceCall> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + ReportInterfaceCall.TABLE ;
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
	
	public Long sumCall7days(){
		String sql = "select sum(count_success_call)+sum(count_fail_call) sumCall from "+ReportInterfaceCall.TABLE+" where TO_DAYS(NOW()) - TO_DAYS(report_date) <= 7";
		Record item = Db.findFirst(sql);
		if(item!=null){
			return item.getLong("sumCall");
		}
		return 0L;
	}
	
	public Long sumCall(){
		String sql = "select sum(count_success_call)+sum(count_fail_call) sumCall from "+ReportInterfaceCall.TABLE;
		Record item = Db.findFirst(sql);
		if(item!=null){
			return item.getLong("sumCall");
		}
		return 0L;
	}
}
