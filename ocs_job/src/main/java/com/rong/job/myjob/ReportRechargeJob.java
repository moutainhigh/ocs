package com.rong.job.myjob;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.model.ReportRecharge;

/**
 * 统计充值数据
 * 
 * @author Wenqiang-Rong
 * @date 2018年2月24日
 */
public class ReportRechargeJob implements Job {
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始统计充值数据");
		try {
			String sql = "select sum(money) sumMoney,count(*) rechargeCount from ocs_recharge where TO_DAYS(NOW()) - TO_DAYS(create_time) = 1";
			Record item = Db.findFirst(sql);
			if(item!=null){
				ReportRecharge model = new ReportRecharge();
				model.setCreateTime(new Date());
				model.setReportDate(DateTimeUtil.nextDay(1));
				model.setCountRecharge(item.getInt("rechargeCount")==null?0:item.getInt("rechargeCount"));
				model.setSumRechargeMoney(item.getBigDecimal("sumMoney")==null?BigDecimal.ZERO:item.getBigDecimal("sumMoney"));
				model.save();
				logger.info("成功统计充值数据");
			}
			logger.info("统计充值数据:无");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error:统计充值数据异常" + e);
		}
	}
}
