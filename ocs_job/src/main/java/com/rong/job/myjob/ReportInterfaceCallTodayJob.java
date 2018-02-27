package com.rong.job.myjob;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.model.InterfaceCall;
import com.rong.persist.model.ReportInterfaceCall;

/**
 * 统计接口调用数据-今天
 * 
 * @author Wenqiang-Rong
 * @date 2018年2月27日
 */
public class ReportInterfaceCallTodayJob implements Job {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始统计接口调用数据-今天");
		try {
			String sql = "select i.project_name ,p.price,count(if(call_success=1,true,null)) success,count(if(call_success=0,true,null)) fail "
					+ "from "+InterfaceCall.TABLE+" i , ocs_project p "
					+ "where i.project_name = p.project_name and TO_DAYS(NOW()) - TO_DAYS(i.create_time) = 0 group by i.project_name";
			List<Record> list = Db.find(sql);
			if(list!=null && list.size()>0){
				for (Record item : list) {
					ReportInterfaceCall model = findByDateAndProjectName(item.getStr("project_name"));
					if(model==null){
						model = new ReportInterfaceCall();
						setModel(item, model);
						model.save();
					}else{
						setModel(item, model);
						model.update();
					}
				}
				logger.info("成功统计接口调用数据-今天："+list.size());
				return;
			}
			logger.info("成功统计接口调用数据-今天:无");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error:统计接口调用数据异常-今天" + e);
		}
	}

	private void setModel(Record item, ReportInterfaceCall model) {
		model.setCreateTime(new Date());
		model.setReportDate(new Date());
		model.setProjectName(item.getStr("project_name"));
		model.setProjectPrice(item.getBigDecimal("price"));
		model.setCountSuccessCall(item.getInt("success"));
		model.setCountFailCall(item.getInt("fail"));
	}
	
	private ReportInterfaceCall findByDateAndProjectName(String projectName){
		String sql = "select * from " + ReportInterfaceCall.TABLE +" where project_name = ? and TO_DAYS(NOW()) - TO_DAYS(report_date) = 0";
		return ReportInterfaceCall.dao.findFirst(sql,projectName);
	}
}
