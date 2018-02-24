package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.User;

/**
 * 清理过期用户
 * @author Wenqiang-Rong
 * @date 2018年2月24日
 */
public class UserExpirClearJob implements Job{
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始清理过期用户");
		try{
			String sql = "update "+User.TABLE +" set state = 0 where TO_DAYS(NOW()) - TO_DAYS(expir_date) >= 0 ";
			Db.update(sql);
			logger.info("成功清理过期用户");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error:清理过期用户出现异常"+e);
		}		
	}
}
