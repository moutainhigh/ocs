package com.rong.persist.dao;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Record> list(){
		// 今天
		String sql = "select project_name ,count_success_call success,count_fail_call fail " + "from "
				+ ReportInterfaceCall.TABLE + " where TO_DAYS(NOW()) - TO_DAYS(report_date) = 0";
		List<Record> list = Db.find(sql);
		// 昨天
		String sql1 = "select project_name ,count_success_call success,count_fail_call fail " + "from "
				+ ReportInterfaceCall.TABLE + " where TO_DAYS(NOW()) - TO_DAYS(report_date) = 1";
		List<Record> list1 = Db.find(sql1);
		// 前天
		String sql2 = "select project_name ,count_success_call success,count_fail_call fail " + "from "
				+ ReportInterfaceCall.TABLE + " where TO_DAYS(NOW()) - TO_DAYS(report_date) = 2";
		List<Record> list2 = Db.find(sql2);
		// 总计
		String sql3 = "select project_name ,sum(count_success_call) success,sum(count_fail_call) fail from "
				+ ReportInterfaceCall.TABLE +" group by project_name";
		List<Record> list3 = Db.find(sql3);
		
		List<Record> returnList = new ArrayList<Record>();
		for (int i = 0;i<list3.size();i++) {
			Record model = new Record();
			String allProjectName = list3.get(i).get("project_name");
			model.set("projectName", list3.get(i).get("project_name"));
			model.set("allSuccess", list3.get(i).get("success"));
			model.set("allFail", list3.get(i).get("fail"));
			for (Record record : list2) {
				if(allProjectName.equals(record.get("project_name"))){
					model.set("beforeYesterDaySuccess", record.get("success"));
					model.set("beforeYesterDayFail", record.get("fail"));
					break;
				}
			}
			for (Record record : list1) {
				if(allProjectName.equals(record.get("project_name"))){
					model.set("yesterDaySuccess", record.get("success"));
					model.set("yesterDayFail", record.get("fail"));
					break;
				}
			}
			for (Record record : list) {
				if(allProjectName.equals(record.get("project_name"))){
					model.set("todaySuccess", record.get("success"));
					model.set("todayFail", record.get("fail"));
					break;
				}
			}
			returnList.add(model);
		}
		return returnList;
	}
}
