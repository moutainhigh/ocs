package com.rong.business.service;

import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.ProjectDao;
import com.rong.persist.model.Project;

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
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService{
	private ProjectDao dao = new ProjectDao();
	
	@Override
	public boolean save(String projectName, BigDecimal price, String remark) {
		return dao.save(projectName, price, remark);
	}

	@Override
	public Page<Project> page(int pageNumber, int pageSize, String name) {
		Kv param = Kv.by("projectName", name);
		return dao.page(pageNumber, pageSize, param);
	}

}


