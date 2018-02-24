package com.rong.job.myjob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.model.ReportInterfaceCall;

/**
 * 统计接口调用数据
 * 
 * @author Wenqiang-Rong
 * @date 2018年2月24日
 */
public class ReportInterfaceCallJob implements Job {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始统计接口调用数据");
		try {
			String sql = "select i.project_name ,p.price,count(if(call_success=1,true,null)) success,count(if(call_success=0,true,null)) fail "
					+ "from ocs_interface_call i , ocs_project p "
					+ "where i.project_name = p.project_name and TO_DAYS(NOW()) - TO_DAYS(i.create_time) = 1 group by i.project_name";
			List<Record> list = Db.find(sql);
			if(list!=null && list.size()>0){
				List<ReportInterfaceCall> saveList = new ArrayList<>(list.size());
				for (Record item : list) {
					ReportInterfaceCall model = new ReportInterfaceCall();
					model.setCreateTime(new Date());
					model.setReportDate(DateTimeUtil.nextDay(1));
					model.setProjectName(item.getStr("project_name"));
					model.setProjectPrice(item.getBigDecimal("price"));
					model.setCountSuccessCall(item.getInt("success"));
					model.setCountFailCall(item.getInt("fail"));
					saveList.add(model);
				}
				Db.batchSave(saveList, 100);
				logger.info("成功统计接口调用数据："+list.size());
			}
			logger.info("成功统计接口调用数据:无");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error:统计接口调用数据异常" + e);
		}
	}
}
