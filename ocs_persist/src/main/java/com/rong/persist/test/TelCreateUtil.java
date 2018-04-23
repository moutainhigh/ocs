package com.rong.persist.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;

public class TelCreateUtil {
	// 手机号码文件
	public static String TEL_FILE = "E:\\test.txt";
	// 开始行数
	public static int START_LINE = 1;
	// 每次提交数量
	public static int BATCHSIZE = 10000;
	// 号码段集合
	public static List<Tel> telList = new ArrayList<Tel>();
	//号码段set-看数据是否相同
	public static Set<String> telSet = new HashSet<>();
	// 号码段队列
	public static BlockingQueue<Tel> telQueue ;
	// 表集合
	public static Set<String> tableNameList = new HashSet<String>();
	
	/**
	 * 创建所有表
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 */
	private static void createAllTable() {
		long startTime = System.currentTimeMillis();
		int createTableCount = 0;
		for (Tel tel : telList) {
			String tableName = "tel_"+tel.getTel().substring(0,4);
			boolean result = createTable(tableName);
			if(result){
				tableNameList.add(tableName);
				createTableCount++;
			}
		}
		System.out.println("总计创建表数量："+createTableCount);
		long endTime = System.currentTimeMillis();
		System.out.println("2.创建表耗时："+formatTime(endTime-startTime));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 将所有数据放入内存中
	 */
	private static void readerAllLine(int startLine,String fileName) {
		//读取所有号码段耗时
		long startTime = System.currentTimeMillis();
		File file = new File(fileName);
		InputStreamReader read = null;    
		BufferedReader reader = null;
		try {
			read = new InputStreamReader(new FileInputStream(file),"gbk");   
			reader = new BufferedReader(read);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			// 格式："江苏","南京市","025","中国联通","1300006","210000"
			while ((tempString = reader.readLine()) != null) {
				//业务逻辑
				if(line>=startLine){
					String [] telArr = tempString.split(",");
					Tel tel = new Tel();
					tel.setTel_province(telArr[0].substring(1, telArr[0].length()-1));
					tel.setTel_city(telArr[1].substring(1, telArr[1].length()-1));
					tel.setTel_area_code(telArr[2].substring(1, telArr[2].length()-1));
					tel.setTel_operator(telArr[3].substring(1, telArr[3].length()-1));
					tel.setTel(telArr[4].substring(1, telArr[4].length()-1));
					telList.add(tel);
					telSet.add(tel.getTel());
				}
				line++;
			}
			//加入队列
			addQueue();
			System.out.println("总数据："+telList.size());
			reader.close();
			long endTime = System.currentTimeMillis();
			System.out.println("1.读取所有号码段耗时："+formatTime(endTime-startTime));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {

				}
			}
		}
	}
	
	/**
	 * 将号码段加入队列
	 */
	private static void addQueue(){
		telQueue = new ArrayBlockingQueue<Tel>(telList.size());
		telQueue.addAll(telList);
	}
	
	/**
	 * 创建所有表,存在则不创建，不存在则创建
	 * @param tableName
	 */
	private static boolean createTable(String tableName){
		boolean exist = existTableName(tableName);
		if(!exist){
			String sql = "CREATE TABLE `"+tableName+"` ("+
  "`id` bigint(20) NOT NULL AUTO_INCREMENT,"+
  "`tel` char(11) NOT NULL COMMENT '手机号码',"+
  "`tel_province` varchar(10) DEFAULT NULL COMMENT '手机归属地：省份',"+
  "`tel_city` varchar(10) DEFAULT NULL COMMENT '手机归属地：城市',"+
  "`tel_area_code` varchar(4) DEFAULT NULL COMMENT '区号',"+
  "`tel_operator` varchar(4) DEFAULT NULL COMMENT '运营商',"+
  "`platform_collection` char(1) DEFAULT NULL COMMENT '采集平台 1.支付宝 2.QQ',"+
  "`alipay_name` varchar(50) DEFAULT NULL COMMENT '支付宝名称（（姓名、或者企业名称）',"+
  "`qq_nickname` varchar(50) DEFAULT NULL COMMENT 'qq昵称',"+
  "`sex` char(1) NOT NULL DEFAULT '0' COMMENT '0-保密 1男 2女',"+
  "`age` date DEFAULT NULL COMMENT '出身日期，前端采用时间进行计算得出实际年龄',"+
  "`addr` varchar(100) DEFAULT NULL COMMENT '地址',"+
  "`col1` varchar(100) DEFAULT NULL COMMENT '备用字段',"+
  "`col2` varchar(100) DEFAULT NULL COMMENT '备用字段',"+
  "`col3` varchar(100) DEFAULT NULL COMMENT '备用字段',"+
  "`col4` varchar(100) DEFAULT NULL COMMENT '备用字段',"+
  "`col5` varchar(100) DEFAULT NULL COMMENT '备用字段',"+
  "`create_time` datetime NOT NULL,"+
  "PRIMARY KEY (`id`),"+
  "UNIQUE KEY `tel_uniq` (`tel`) USING HASH COMMENT '手机号码唯一索引',"+
  "KEY `tel_province_normal` (`tel_province`) USING HASH,"+
  "KEY `tel_city_normal` (`tel_city`) USING HASH,"+
  "KEY `tel_area_code_normal` (`tel_area_code`) USING HASH,"+
  "KEY `platform_collection_normal` (`platform_collection`) USING HASH"+
  ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手机号码表';";
			Db.update(sql);
			tableNameList.add(tableName);
			return true;
		}
		return false;
	}
	
	/**
	 * 表是否存在
	 * @param tableName
	 * @return
	 */
	private static boolean existTableName(String tableName){
		if(tableNameList.contains(tableName)){
			return true;
		}
		String sql = "SELECT table_name FROM information_schema. TABLES WHERE table_schema = 'tel_db' and table_name = '"+tableName+"'";
		Record table = Db.findFirst(sql);
		if(table==null){
			return false;
		}
		tableNameList.add(tableName);
		return true;
	}
	
	/**
	 * 已知号码段，生成剩余手机号码
	 * 如：号码段为1892245 ，第一个号码为：18922450001
	 * 生成9999个手机号码，并保存
	 * @param tel
	 * @param province
	 * @param city
	 * @param areaCode
	 * @param operator
	 */
	public static synchronized void createTel(Tel tel){
		String tableName = "tel_"+tel.getTel().substring(0,4);
		List<Record> recordList = new ArrayList<Record>();
		for (int i=1;i<10000;i++) {
			String val = "";
			if(i<10){
				val = "000"+i;
			}else if(i>=10 &&i<100){
				val = "00"+i;
			}else if(i>=100 &&i<1000){
				val = "0"+i;
			}else{
				val = String.valueOf(i);
			}
			Record record = new Record();
			record.set("create_time", new Date());
			record.set("tel", tel.getTel()+val);
			record.set("tel_province", tel.getTel_province());
			record.set("tel_city", tel.getTel_city());
			record.set("tel_area_code", tel.getTel_area_code());
			record.set("tel_operator", tel.getTel_operator());
			recordList.add(record);
		}
		Db.batchSave(tableName, recordList, BATCHSIZE);
	}
	
	
	/**
	 * 使用多线程执行生成手机号码并保存到数据库
	 */
	public static void createAllTel() {
		ExecutorService pool = Executors.newFixedThreadPool(100);
		for (int i = 0; i < telList.size(); i++) {
			pool.submit(new TelCreateTask(i));
		}
		pool.shutdown();
	}
	
	/*
	 * 毫秒转化分钟+秒
	 */
	public static String formatTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strMinute + " 分钟 " + strSecond + " 秒";
	}  
	
	/**
	 * 测试库表数据
	 * @param limit
	 * @param offset
	 * @param param
	 * @return
	 */
	public static int count(int limit, int offset,Kv param) {
		String tableName = "tel_1300";
		String select = "explain select count(*) from " + tableName ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String province = param.getStr("province");
			if (!StringUtils.isNullOrEmpty(province)) {
				where.append(" and tel_province = '" + province + "'");
			}
			String city = param.getStr("city");
			if (!StringUtils.isNullOrEmpty(city)) {
				where.append(" and tel_city = '" + city + "'");
			}
			String platform = param.getStr("platform");
			if (!StringUtils.isNullOrEmpty(platform)) {
				if("0".equals(platform)){
					where.append(" and platform_collection is null");
				}else{
					where.append(" and platform_collection = '" + platform + "'");
				}
			}
			String operator = param.getStr("operator");
			if (!StringUtils.isNullOrEmpty(operator)) {
				where.append(" and tel_operator = '" + operator + "'");
			}
			String areaCode = param.getStr("areaCode");
			if (!StringUtils.isNullOrEmpty(areaCode)) {
				where.append(" and tel_area_code = '" + areaCode + "'");
			}
			String sex = param.getStr("sex");
			if (!StringUtils.isNullOrEmpty(sex)) {
				where.append(" and sex = '" + sex + "'");
			}
			String age = param.getStr("age");
			if (!StringUtils.isNullOrEmpty(age)) {
				where.append(" and TIMESTAMPDIFF(YEAR, sex, CURDATE()) = " + age);
			}
		}
		select = select + where;
		Record record = Db.findFirst(select);
		return record.getInt("rows");
	}
	
	
	public static void main(String[] args) throws Exception {
		
		
		// 1.初始化数据库
		JdbcInit.init();
		// 2.读取所有号码段数据
		readerAllLine(START_LINE, TEL_FILE);
		// 3.创建所有表-仅需要运行一次
		createAllTable();
		// 4.往表中插入数据
		createAllTel();
	}
}
