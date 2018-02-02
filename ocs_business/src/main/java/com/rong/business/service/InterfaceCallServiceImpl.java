package com.rong.business.service;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.dao.InterfaceCallDao;
import com.rong.persist.model.Consume;
import com.rong.persist.model.InterfaceCall;

/****
 * @Project_Name:	ocs_business
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		ProjectServiceImpl.java
 * @CreateDate:		2018年2月1日 上午11:49:04
 * @Designer:		Wenqiang-Rong
 * @Desc:			
 * @ModifyHistory:	
 ****/
public class InterfaceCallServiceImpl extends BaseServiceImpl<InterfaceCall> implements InterfaceCallService{
	private InterfaceCallDao dao = new InterfaceCallDao();
	private AccountDao accountDao = new AccountDao();
	
	@Override
	public Page<InterfaceCall> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public int countByProjectId(long projectId) {
		return dao.countByProjectId(projectId);
	}
	@Override
	public boolean save(String userName,boolean callSuccess,long projectId,String projectName,String remark){
		return dao.save(userName, callSuccess, projectId, projectName, remark);
	}

	@Override
	public boolean consumed(long projectId,String projectName,BigDecimal projectPrice,String userName,BigDecimal account){
		// 1.保存调用记录
		boolean result = dao.save(userName, true, projectId, projectName, null);
		// 2.更新余额
		if(result){
			// 2.1.保存消费记录
			Consume consume = new Consume();
			consume.setCreateTime(new Date());
			consume.setMoney(projectPrice);
			consume.setProjectId(projectId);
			consume.setProjectName(projectName);
			consume.setUserName(userName);
			consume.save();
			return accountDao.consumed(userName, account, projectPrice);
		}
		return false;
	}

}


