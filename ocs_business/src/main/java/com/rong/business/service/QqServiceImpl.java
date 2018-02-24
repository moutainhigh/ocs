package com.rong.business.service;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqDao;
import com.rong.persist.model.Qq;

/****
 * @Project_Name:	ocs_business
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		QqServiceImpl.java
 * @CreateDate:		2018年2月1日 下午9:23:49
 * @Designer:		Wenqiang-Rong
 * @Desc:			qq数据管理
 * @ModifyHistory:	
 ****/

public class QqServiceImpl extends BaseServiceImpl<Qq> implements QqService{
	private QqDao dao = new QqDao();
	
	@Override
	public Page<Qq> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public boolean save(String qq, String pwd, String token,String userName) {
		Qq model = new Qq();
		model.setCreateTime(new Date());
		model.setQq(qq);
		model.setPwd(pwd);
		model.setToken(token);
		model.setUserName(userName);
		return dao.save(model);
	}

	@Override
	public Qq findByQqAndUserName(String qq, String userName) {
		return dao.findByQqAndUserName(qq, userName);
	}

	@Override
	public boolean saveOrUpdate(String qq, String userName, String data) {
		Qq item = dao.findByQqAndUserName(qq, userName);
		if (item == null) {
			Qq model = new Qq();
			model.setCreateTime(new Date());
			model.setQq(qq);
			model.setData(data);
			model.setUserName(userName);
			return dao.save(model);
		}
		item.setData(data);
		return dao.update(item);
	}

}


