package com.rong.business.service;

import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AdTaskDao;
import com.rong.persist.dao.AdTaskDetailDao;
import com.rong.persist.model.AdTask;
import com.rong.persist.model.AdTaskDetail;

/**
 * 广告任务业务实现类
 * @author Wenqiang-Rong
 * @date 2018年4月3日
 */
public class AdTaskServiceImpl extends BaseServiceImpl<AdTask> implements AdTaskService{
	private AdTaskDao dao = new AdTaskDao();
	private AdTaskDetailDao adTaskDetailDao = new AdTaskDetailDao();
	@Override
	public Page<AdTask> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}
	@Override
	public AdTask findByOrderCode(String orderCode) {
		return dao.findByOrderCode(orderCode);
	}
	@Override
	public AdTask rand() {
		return dao.Rand();
	}
	@Override
	public boolean save(String userName, String content, Long projectId, Boolean back, BigDecimal moeny,
			Integer countCall) {
		return dao.save(userName, content, projectId, back, moeny, countCall);
	}
	@Override
	public Page<AdTaskDetail> pageDetail(int pageNumber, int pageSize, String orderCode) {
		return adTaskDetailDao.page(pageNumber, pageSize, orderCode);
	}
	@Override
	public boolean saveDetail(String orderCode,String qqGroupName, String qqGroupNo, String qq) {
		return adTaskDetailDao.save(orderCode,qqGroupName, qqGroupNo, qq);
	}


}


