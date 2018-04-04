package com.rong.job.myjob;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.dao.AdTaskDao;
import com.rong.persist.dao.AdTaskDetailDao;
import com.rong.persist.model.AdTask;
import com.rong.persist.model.AdTaskDetail;

public class AdTaskJob implements Job{
	private final Logger logger = Logger.getLogger(this.getClass());
	AdTaskDao adTaskDao = new AdTaskDao();
	AdTaskDetailDao adTaskDetailDao = new AdTaskDetailDao();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		checkAdTaskState();
	}
	
	
	/**
	 * 检测执行中状态的广告任务是否完成，超过或者等于98%则设置为执行完成
	 * 检测执行中状态的广告任务是否异常，异常则变为待执行
	 * 注：超过2分钟未有新记录产生的执行中的任务则为异常
	 */
	private void checkAdTaskState(){
		try{
			// 查询所在在执行中的任务
			List<AdTask> AdTaskList = adTaskDao.find("select * from "+ AdTask.TABLE + " where state = 2");
			int finish = 0;
			int error = 0;
			for (AdTask adTask : AdTaskList) {
				// 检测执行中状态的广告任务是否完成，超过或者等于98%则设置为执行完成
				Record item = Db.findFirst("select count(*) count from " + AdTaskDetail.TABLE+ " where order_code = ?",adTask.getOrderCode());
				Integer count = item.getInt("count");
				if(count!=null && adTask.getCountCall()!=null){
					adTask.setCountCalled(count);
					float val = (float)count/adTask.getCountCall();
					if(val>=0.98){
						finish++;	
						adTask.setState(3);
						adTask.update();
					}else{
						// 最后一条执行详情记录，是否已经超过2分钟，超过则异常
						AdTaskDetail adTaskDetail = adTaskDetailDao.findFirst("select create_time from " + AdTaskDetail.TABLE + " where order_code = ? order by create_time desc limit 1",adTask.getOrderCode());
						long timeVal = DateTimeUtil.getBetweenMinute(adTaskDetail.getCreateTime(), new Date());
						if(timeVal>2){
							error++;
							adTask.setState(1);
							adTask.update();
						}
						
					}
				}
			}
			logger.info("检测广告词任务完成情况，执行中总计："+AdTaskList.size()+",已完成："+finish+",异常："+error);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error:检测广告任务异常"+e);
		}		
	}
}
