package com.rong.business.service;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.ReportInterfaceCallDao;
import com.rong.persist.model.ReportInterfaceCall;

/**
 * 接口调用报表实现类
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public class ReportInterfaceCallServiceImpl extends BaseServiceImpl<ReportInterfaceCall> implements ReportInterfaceCallService{
	private ReportInterfaceCallDao dao = new ReportInterfaceCallDao();

	@Override
	public Page<ReportInterfaceCall> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}
	
	@Override
	public Long sumCall() {
		return dao.sumCall();
	}

	@Override
	public Long sumCall7day() {
		return dao.sumCall7days();
	}

	@Override
	public List<Record> list() {
		return dao.list();
	}
	
	


}


