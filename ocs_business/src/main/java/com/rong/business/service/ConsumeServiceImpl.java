package com.rong.business.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.ConsumeDao;
import com.rong.persist.model.Consume;

/**
 * 消费流水实现类
 * @author Wenqiang-Rong
 * @date 2018年4月10日
 */
public class ConsumeServiceImpl extends BaseServiceImpl<Consume> implements ConsumeService{
	private ConsumeDao dao = new ConsumeDao();
	
	@Override
	public Page<Consume> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

}


