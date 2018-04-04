package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.Consume;
import com.rong.persist.model.InterfaceCall;

public class ClearJob implements Job{
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		clearTable4InterfaceCall();
		clearTable4Consume();
	}
	
	/**
	 * 因目前数据量过于庞大，每天200W左右调记录，所以采用清理旧数据
	 * 清理接口表数据，只保留最近3天数据
	 */
	private void clearTable4InterfaceCall(){
		logger.info("接口表：开始清理超过3天的数据");
		try{
			String sql = "delete from "+InterfaceCall.TABLE +" where TO_DAYS(NOW()) - TO_DAYS(create_time) >= 3 ";
			int count = Db.update(sql);
			logger.info("接口表：清理超过3天的数据成功，总计："+count);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error:接口表：开始清理超过3天的数据出现异常"+e);
		}		
	}
	
	/**
	 * 因目前数据量过于庞大，每天200W左右调记录，所以采用清理旧数据
	 * 清理消费记录表数据，只保留最近3天数据
	 */
	private void clearTable4Consume(){
		logger.info("接口表：开始清理超过3天的数据");
		try{
			String sql = "delete from "+Consume.TABLE +" where TO_DAYS(NOW()) - TO_DAYS(create_time) >= 3 ";
			int count = Db.update(sql);
			logger.info("接口表：清理超过3天的数据成功，总计："+count);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error:接口表：开始清理超过3天的数据出现异常"+e);
		}		
	}
}
