package com.rong.persist.test;

import com.rong.common.util.GsonUtil;
import com.rong.persist.dao.TelStatisDao;

public class telStatisDaoTest {
	public static void main(String[] args) throws Exception {
		// 1.初始化数据库
		JdbcInit.init();
		TelStatisDao dao = new TelStatisDao();
//		System.out.println(GsonUtil.toJson(dao.statisCollection()));
		System.out.println(GsonUtil.toJson(dao.statisByCity(null)));
		
	}
}
