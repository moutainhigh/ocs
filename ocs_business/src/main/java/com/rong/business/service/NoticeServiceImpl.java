package com.rong.business.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.NoticeDao;
import com.rong.persist.model.Notice;

/****
 * @Project_Name:	ocs_business
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		ProjectServiceImpl.java
 * @CreateDate:		2018年2月1日 上午11:49:04
 * @Designer:		Wenqiang-Rong
 * @Desc:			软件广告业务实现类
 * @ModifyHistory:	
 ****/
public class NoticeServiceImpl extends BaseServiceImpl<Notice> implements NoticeService{
	private NoticeDao dao = new NoticeDao();

	@Override
	public Page<Notice> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public boolean save(String content, String softwareCode, String softwareName, Boolean enable) {
		return dao.save(content, softwareCode, softwareName, enable);
	}

	@Override
	public Notice findBySoftwareCode(String softwareCode) {
		return dao.findBySoftwareCode(softwareCode);
	}
	

}


