package com.rong.business.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.InterfaceCallDao;
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
	
	@Override
	public Page<InterfaceCall> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public int countByProjectId(long projectId) {
		return dao.countByProjectId(projectId);
	}

}


