package com.rong.persist.dao;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.TelCityStatis;
import com.rong.persist.model.TelCollectionStatis;
import com.rong.persist.model.TelStatisJob;

/**
 * 手机数据统计dao
 * @author Wenqiang-Rong
 * @date 2018年5月6日
 */
public class TelStatisJobDao extends BaseDao<TelStatisJob> {
	TelStatisJob dao = new TelStatisJob();
	TelCityStatis telCityStatisDao = new TelCityStatis();
	TelCollectionStatis telCollectionStatisDao = new TelCollectionStatis();
	
	/**
	 * 保存
	 * @param param
	 * @return
	 */
	public Long save(Kv param,Date createTime,Date finishTime){
		TelStatisJob item = new TelStatisJob();
		item.setCreateTime(createTime);
		item.setFinishTime(finishTime);
		item.setParam(param.toJson());
		item.save();
		return item.getId();
	}
	
	/**
	 * 获取采集统计数据
	 * @param jobId
	 * @return
	 */
	public List<TelCollectionStatis> getCollectionStatis(Long jobId) {
		String sql = "select * from tel_collection_statis where job_id = ? order by id asc";
		return telCollectionStatisDao.find(sql,jobId);
	}
	
	/**
	 * 获取城市查询统计数据
	 * @param jobId
	 * @return
	 */
	public List<TelCityStatis> getCityStatis(Long jobId) {
		String sql = "select * from tel_city_statis where job_id = ? order by id asc";
		return telCityStatisDao.find(sql,jobId);
	}
	
	/**
	 * 获取最后一条任务数据
	 * @return
	 */
	public TelStatisJob getLastOne() {
		String sql = "select * from tel_statis_job order by id desc limit 1";
		return dao.findFirst(sql);
	}
	
	
	/**
	 * 获取最后一条任务相同条件的最近一个查询数据
	 * @return
	 */
	public TelStatisJob getLastOneBeforeData() {
		TelStatisJob lastOne = getLastOne();
		String param = lastOne.getParam();
		String sql = "select * from tel_statis_job where param = ? order by id desc limit 1,1";
		return dao.findFirst(sql,param);
	}
	
}
