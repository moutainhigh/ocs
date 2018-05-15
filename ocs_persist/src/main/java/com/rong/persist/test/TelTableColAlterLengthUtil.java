package com.rong.persist.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 表字段长度修改
 * @author Wenqiang-Rong
 * 20180514
 */
public class TelTableColAlterLengthUtil {
	public static List<String> getAllTable() {
		String sql = "SELECT table_name tableName FROM information_schema. TABLES WHERE table_schema = 'tel_db' and LENGTH(table_name) = 8";
		List<Record> tableList = Db.use("tel").find(sql);
		List<String> returnList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(tableList)) {
			for (Record table : tableList) {
				returnList.add(table.getStr("tableName"));
			}
			return returnList;
		}
		return null;
	}
	
	public static List<String> getAllTableForTel5() {
		String sql = "SELECT table_name tableName FROM information_schema. TABLES WHERE table_schema = 'tel_db' and LENGTH(table_name) = 9";
		List<Record> tableList = Db.use("tel").find(sql);
		List<String> returnList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(tableList)) {
			for (Record table : tableList) {
				returnList.add(table.getStr("tableName"));
			}
			return returnList;
		}
		return null;
	}
	
	public static void alertTable(){
		List<String> tables8 = getAllTable();
		for (String table : tables8) {
			Db.update("alter table "+table+" modify column col3 varchar(1000)");
		}
		List<String> tables9 = getAllTableForTel5();
		for (String table : tables9) {
			Db.update("alter table "+table+" modify column col1 varchar(1000)");
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		// 1.初始化数据库
		JdbcInit.init();
		//2.修改字段长度
		alertTable();
	}
}
