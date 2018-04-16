package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.UserToken;

public class UsertokenExpirClearJob implements Job{
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始清理过期token");
		try{
			String sql = "update "+UserToken.TABLE +" set is_expir = 0 where TO_DAYS(NOW()) - TO_DAYS(expir_time) >= 0 ";
			Db.update(sql);
			logger.info("成功清理用户过期token");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error:定时清理过期token出现异常"+e);
		}		
	}
}
