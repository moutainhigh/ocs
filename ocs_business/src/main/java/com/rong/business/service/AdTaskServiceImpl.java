package com.rong.business.service;

import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.dao.AdTaskDao;
import com.rong.persist.dao.AdTaskDetailDao;
import com.rong.persist.dao.ConsumeDao;
import com.rong.persist.dao.ProjectDao;
import com.rong.persist.dao.UserDao;
import com.rong.persist.model.AdTask;
import com.rong.persist.model.AdTaskDetail;
import com.rong.persist.model.Project;
import com.rong.persist.model.User;

/**
 * 广告任务业务实现类
 * @author Wenqiang-Rong
 * @date 2018年4月3日
 */
public class AdTaskServiceImpl extends BaseServiceImpl<AdTask> implements AdTaskService{
	private AdTaskDao dao = new AdTaskDao();
	private AdTaskDetailDao adTaskDetailDao = new AdTaskDetailDao();
	private AccountDao accountDao = new AccountDao();
	private ConsumeDao consumeDao = new ConsumeDao();
	private ProjectDao projectDao = new ProjectDao();
	@Override
	public Page<AdTask> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}
	@Override
	public AdTask findByOrderCode(String orderCode) {
		return dao.findByOrderCode(orderCode);
	}
	@Override
	public AdTask getFirst() {
		return dao.getFirst();
	}
	@Override
	public String save(String userName, String content, Long projectId, Boolean back, BigDecimal moeny,
			Integer countCall) {
		Project pro = projectDao.findById(projectId);
		BigDecimal allMoney = pro.getPrice().multiply(new BigDecimal(countCall));
		// 扣除金额
		accountDao.consumed(userName, accountDao.findByUserName(userName).getAccount(), allMoney);
		// 保存定时任务
		String orderCode = dao.save(userName, content, projectId, back, moeny, countCall);
		// 保存消费记录
		consumeDao.save(allMoney, userName, projectId, orderCode);
		return orderCode;
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


