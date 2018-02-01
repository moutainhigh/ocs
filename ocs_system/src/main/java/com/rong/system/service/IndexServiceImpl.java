package com.rong.system.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.model.User;

/**
 * 首页业务实现类
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class IndexServiceImpl implements IndexService{
	@Override
	public Record getIndexUserValue() {
		StringBuffer sql = new StringBuffer("select"); 
		sql.append(" (select count(*) from "+User.TABLE+" u where TO_DAYS(now()) - TO_DAYS(u.registerDate) = 1) registerUserCountYesterday,");
		return Db.findFirst(sql.toString());
	}
}

