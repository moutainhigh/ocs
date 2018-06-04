package com.rong.business.service.tel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.dao.TelStatisJobDao;
import com.rong.persist.model.Tel;
import com.rong.persist.model.TelCityStatis;
import com.rong.persist.model.TelCollectionStatis;
import com.rong.persist.model.TelStatisJob;

/**
 * 手机号码统计业务实现类
 * @author Wenqiang-Rong
 * @date 2018年5月5日
 */
public class TelStatisServiceImpl extends BaseServiceImpl<Tel> implements TelStatisService{
	private TelStatisDao dao = new TelStatisDao();
	private TelStatisJobDao jobDao = new TelStatisJobDao();

	@Override
	public Map<String, List<Record>> statisCollection() {
		return dao.statisCollection();
	}

	@Override
	public Map<String, Long> statisByCity(Kv param) {
		return dao.statisByCity(param);
	}

	@Override
	public Long save(Kv param, Date createTime, Date finishTime) {
		return jobDao.save(param, createTime, finishTime);
	}

	@Override
	public List<TelCollectionStatis> getCollectionStatis(Long jobId) {
		return jobDao.getCollectionStatis(jobId);
	}

	@Override
	public List<TelCityStatis> getCityStatis(Long jobId) {
		return jobDao.getCityStatis(jobId);
	}

	@Override
	public TelStatisJob getLastOne() {
		return jobDao.getLastOne();
	}

	@Override
	public List<Tel> getAllTel(String col, int limit, Kv param) {
		return dao.getAllTel(col, limit, param);
	}

	@Override
	public TelStatisJob getLastOneBeforeData(){
		return jobDao.getLastOneBeforeData();
	}
	
	@Override
	public TelStatisJob getBeforeData(Long id){
		return jobDao.getBeforeData(id);
	}

	@Override
	public Page<TelStatisJob> pageTelStaisJob(int pageNumber, int pageSize, Kv param) {
		return jobDao.pageTelStaisJob(pageNumber, pageSize, param);
	}

	@Override
	public TelStatisJob getById(Long id) {
		return jobDao.findById(id);
	}

}


