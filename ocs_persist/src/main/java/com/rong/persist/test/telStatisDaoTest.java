package com.rong.persist.test;

import java.util.List;

import com.rong.common.util.GsonUtil;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.model.Tel;

public class telStatisDaoTest {
	public static void main(String[] args) throws Exception {
		// 1.初始化数据库
		JdbcInit.init();
		TelStatisDao dao = new TelStatisDao();
//		System.out.println(GsonUtil.toJson(dao.statisCollection()));
//		System.out.println(GsonUtil.toJson(dao.statisByCity(null)));
		List<Tel> list = dao.getAllTel("tel", 100, null);
		for (Tel tel : list) {
			Object [] obj = tel._getAttrValues();
			for (Object o : obj) {
				System.out.print(o+"-");
			}
		}
		
	}
}
