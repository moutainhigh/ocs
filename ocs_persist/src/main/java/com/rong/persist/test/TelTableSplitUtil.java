package com.rong.persist.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class TelTableSplitUtil {
	// 库表队列
	public static BlockingQueue<String> tableQueue = new ArrayBlockingQueue<String>(400) ;
	// 表集合
	public static Set<String> tableNameList = new HashSet<String>();
	
	/**
	 * 创建所有表,存在则不创建，不存在则创建,大概4000张表
	 * 
	 * @param tableName
	 */
	private static boolean createTable(String tableName) {
		boolean exist = TelCreateUtil.existTableName(tableName);
		if(!exist){
		String sql = "CREATE TABLE `" + tableName + "` (" + "`id` bigint(20) NOT NULL AUTO_INCREMENT,"
				+ "`tel` char(11) NOT NULL COMMENT '手机号码',"
				+ "`tel_province` varchar(10) DEFAULT NULL COMMENT '手机归属地：省份',"
				+ "`tel_city` varchar(10) DEFAULT NULL COMMENT '手机归属地：城市',"
				+ "`tel_area_code` varchar(4) DEFAULT NULL COMMENT '区号',"
				+ "`tel_operator` varchar(4) DEFAULT NULL COMMENT '运营商',"
				+ "`platform_collection` varchar(100) DEFAULT '0' COMMENT '多个采用,隔开;采集平台 1.支付宝 2.QQ 3.陆金所',"
				+ "`alipay_name` varchar(50) DEFAULT '' COMMENT '支付宝名称（（姓名、或者企业名称）',"
				+ "`qq_nickname` varchar(50) DEFAULT '' COMMENT 'qq昵称',"
				+ "`sex` char(1) NOT NULL DEFAULT '0' COMMENT '0-保密 1男 2女',"
				+ "`age` date DEFAULT NULL COMMENT '出身日期，前端采用时间进行计算得出实际年龄',"
				+ "`addr` varchar(100) DEFAULT '' COMMENT '地址',"
				+ "`register` char(1) DEFAULT '0' COMMENT '是否注册1注册，0未注册 ',"
				+ "`col1` varchar(100) DEFAULT '' COMMENT '备用字段'," + "`col2` varchar(100) DEFAULT '' COMMENT '备用字段',"
				+ "`col3` varchar(100) DEFAULT '' COMMENT '备用字段'," + "`col4` varchar(100) DEFAULT '' COMMENT '备用字段',"
				+ "`col5` varchar(100) DEFAULT '' COMMENT '备用字段',"
				+ "PRIMARY KEY (`id`)," + "UNIQUE KEY `tel_uniq` (`tel`) USING HASH COMMENT '手机号码唯一索引',"
				+ "KEY `tel_province_normal` (`tel_province`) USING HASH,"
				+ "KEY `tel_city_normal` (`tel_city`) USING HASH,"
				+ "KEY `tel_area_code_normal` (`tel_area_code`) USING HASH,"
				+ "KEY `alipay_name_normal` (`alipay_name`) USING HASH,"
				+ "KEY `qq_nickname_normal` (`qq_nickname`) USING HASH," + "KEY `sex_normal` (`sex`) USING HASH,"
				+ "KEY `addr_normal` (`addr`) USING HASH,"
				+ "KEY `platform_collection_normal` (`platform_collection`) USING HASH"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机号码表';";
		Db.update(sql);
		}
		return true;
	}

	public static List<String> getAllTable() {
		String sql = "SELECT table_name tableName FROM information_schema. TABLES WHERE table_schema = 'tel_db' and LENGTH(table_name) = 8";
		List<Record> tableList = Db.use("tel").find(sql);
		List<String> returnList = new ArrayList<String>();
		if (CollectionUtils.isNotEmpty(tableList)) {
			for (Record table : tableList) {
				returnList.add(table.getStr("tableName"));
			}
			tableNameList.addAll(returnList);
			// 放入队列
			tableQueue.addAll(returnList);
			return returnList;
		}
		return null;
	}
	
	public static List<String> getAllTableForTel5() {
		String sql = "SELECT table_name tableName FROM information_schema. TABLES WHERE table_schema = 'tel_db' and LENGTH(table_name) = 9 and table_rows>0";
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
	
	public static void clearData(){
		List<String> tables = getAllTableForTel5();
		for (String table : tables) {
			Db.update("delete from " + table + " where id>0");
		}
	}
	
	/**
	 * 每张表扩展10个
	 */
	private static void createAllTable(){
		long startTime = System.currentTimeMillis();
		for (String table : tableNameList) {
			for(int i =0;i<10;i++){
				createTable(table+i);
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("建表耗时："+TelCreateUtil.formatTime(endTime-startTime));
	}
	
	/**
	 * 复制原有数据
	 */
	private static void addData(){
		ExecutorService pool = Executors.newFixedThreadPool(200);
		for (int i = 0; i < tableQueue.size(); i++) {
			pool.submit(new TelTableSplitTask(i));
		}
		pool.shutdown();
	}

	public static void main(String[] args) throws Exception {

		// 1.初始化数据库
		JdbcInit.init();
//		clearData();//清理数据使用
		getAllTable();
		//2.创建所有表-仅需要运行一次
//		createAllTable();
		// 3.往表中插入数据-仅将有效的数据存储即可
		addData();
	}
}
