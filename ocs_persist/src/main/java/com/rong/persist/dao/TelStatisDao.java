package com.rong.persist.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Tel;

/**
 * 手机数据统计dao
 * @author Wenqiang-Rong
 * @date 2018年5月5日
 */
public class TelStatisDao extends BaseDao<Tel> {
	
	public static final Tel dao = Tel.dao;
	public static final TelDao telDao = new TelDao();
	/**
	 * 采集平台类型
	 * @author rongwq
	 *
	 */
	public static class platform{
		/**
		 * 支付宝
		 */
		public static final int ALIPAY = 1;
		/**
		 * QQ
		 */
		public static final int QQ = 2;
		/**
		 * 陆金所
		 */
		public static final int LUJINSUO = 3;
		public static final Integer [] LIST = new Integer[]{ALIPAY,QQ,LUJINSUO};
	}
	
	/**
	 * 统计采集，未采集平台
	 * 结果数据格式：
	 * {"collectioned":[{"columns":{"item":1,"count":0}},
	 * {"columns":{"item":2,"count":0}},
	 * {"columns":{"item":3,"count":0}}],
	 * "unCollectioned":[{"columns":{"item":1,"count":99990}},
	 * {"columns":{"item":2,"count":99990}},
	 * {"columns":{"item":3,"count":99990}}]}
	 * @return
	 */
	public Map<String,List<Record>> statisCollection(){
		List<String> allTable = getAllTable();
		// 已采集集合
		List<Record> returnList = new ArrayList<>();
		// 未采集集合
		List<Record> returnUnList = new ArrayList<>();
		// 已采集数据
		Record collectioned ;
		// 未采集数据
		Record unCollectioned ;
		// 获取所有表的采集数据
		List<List<Record>> allTableData = new ArrayList<>();
		for (String table : allTable) {
			List<Record> data = countCollection(table);
			allTableData.add(data);
		}
		// 组织数据
		for (Integer item : platform.LIST) {
			collectioned = new Record().set("item", item); 
			unCollectioned = new Record().set("item", item); 
			for (List<Record> list : allTableData) {
				for (Record record : list) {
					int count = collectioned.getInt("count")==null?0:collectioned.getInt("count");
					int unCount = unCollectioned.getInt("count")==null?0:unCollectioned.getInt("count");
					if(record.getStr("platform").equals(String.valueOf(item))) {
						collectioned.set("count", count+record.getInt("count")); 
						unCollectioned.set("count", unCount+record.getInt("unCount")); 
					}
				}
			}
			returnList.add(collectioned);
			returnUnList.add(unCollectioned);
		}
		Map<String,List<Record>> returnMap = new HashMap<>();
		returnMap.put("collectioned", returnList);
		returnMap.put("unCollectioned", returnUnList);
		return returnMap;
	}
	
	
	/**
	 * 统计省份城市
	 * @return
	 */
	public Map<String,Long> statisByCity(Kv param){
		List<String> allTable = getAllTable();
		String where = createParam(param);
		Map<String,Long> returnMap = new HashMap<>();
		long sum = 0;
		for (String tableName : allTable) {
			String select = "select tel_province province,count(*) telCount from "+tableName+ where+" group by tel_province ";
			List<Record> list = Db.use("tel").find(select);
			for (Record record : list) {
				sum+= record.getLong("telCount");
				String province = record.getStr("province");
				if(returnMap.containsKey(province)) {
					returnMap.put(province, returnMap.get(province)+record.getLong("telCount"));
				}else {
					returnMap.put(province, record.getLong("telCount"));
				}
			}
		}
		returnMap.put("sum", sum);
		return returnMap;
	}
	
	/**
	 * 获取表数据量:采集，未采集
	 * @param tableName
	 * @param where
	 * @return
	 */
	private List<Record> countCollection(String tableName) {
		// 获取表数据
		int tableCount = count(tableName, null);
		List<Record> returnList = new ArrayList<>(2);
		for (Integer item : platform.LIST) {
			Record record = new Record();
			String where = " where platform_collection like '%" + item + "%'";
			int count = count(tableName, where);
			// 采集数据
			// 平台
			record.set("platform", item);
			// 已采集
			record.set("count", count);
			// 未采集
			record.set("unCount", tableCount-count);
			returnList.add(record);
		}
		return returnList;
	}
	
	
	/**
	 * 获取表数据量
	 * @param tableName
	 * @param where
	 * @return
	 */
	public int count(String tableName,String where) {
		String select = "select count(*) rows from " + tableName ;
		if(where!=null) {
			select = select + where;
		}
		Record record = Db.use("tel").findFirst(select);
		if(record==null){
			return 0;
		}
		return record.getInt("rows")==null?0:record.getInt("rows");
	}
	
	/**
	 * 获取所有表总采集数据量
	 * @param tableName
	 * @param where
	 * @return
	 */
	public long getAllTableCount() {
		String select = "select sum(table_rows) rows from information_schema.tables "+
				"where TABLE_SCHEMA = 'tel_db' and LENGTH(table_name) = 9 and table_rows>0" ;
		Record record = Db.use("tel").findFirst(select);
		if(record==null){
			return 0;
		}
		return record.getLong("rows")==null?0:record.getLong("rows");
	}
	
	/**
	 * 获取所有表
	 * @return
	 */
	public List<String> getAllTable(){
		String sql = "SELECT table_name tableName FROM information_schema. TABLES WHERE table_schema = 'tel_db' "
				+ "and LENGTH(table_name) = 9 and table_rows>0";
		List<Record> tableList = Db.use("tel").find(sql);
		List<String> returnList = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(tableList)){
			for (Record table : tableList) {
				returnList.add(table.getStr("tableName"));
			}
		}
		return returnList;
	}
	
	/**
	 * 组织参数
	 * @param where
	 * @param tel
	 * @param param
	 * @return
	 */
	private String createParam(Kv param) {
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			// 是否采集 true采集，false未采集
			boolean collectionType = param.getBoolean("collectionType");
			// 平台 支付宝 qq 陆金所
			String platform = param.getStr("platform");
			if (collectionType) {
				where.append(" and platform_collection like '%" + platform + "%'");
			}
			// 运营商
			String operator = param.getStr("operator");
			if (!StringUtils.isNullOrEmpty(operator)) {
				where.append(" and tel_operator = '" + operator + "'");
			}
			// 性别
			String sex = param.getStr("sex");
			if (!StringUtils.isNullOrEmpty(sex)) {
				where.append(" and sex = '" + sex + "'");
			}
			// 年龄
			String age = param.getStr("age");
			if (!StringUtils.isNullOrEmpty(age)) {
				if(age.contains("-")){
					String [] ageArr = age.split("-");
					where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) >= " + ageArr[0]);
					where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) <= " + ageArr[1]);
				}else{
					where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) = " + age);
				}
			}
			// 注册情况
			String register = param.getStr("register");
			if (!StringUtils.isNullOrEmpty(register)) {
				if("1".equals(register)){
					where.append(" and register = '" + register + "'");
				}else{
					where.append(" and (register = '' or register = '0') ");
				}
			}
			
		}
		return where.toString();
	}
	
	public static String getTableName(String tel){
		return "tel_"+tel.substring(0,5);
	}
	
	public Tel findTel(String tel){
		String tableName = getTableName(tel);
		String sql = "select * from " + tableName + " where tel = ?";
		return dao.findFirst(sql,tel);
	}
	
	public boolean saveOrUpdateTel(String tel,String platform,String alipayName,String qqNickName,String sex,Date age,String addr,String register){
		String tableName = getTableName(tel);
		Tel item = findTel(tel);
		if(item!=null){
			if(item.getPlatformCollection()!=null){
				if(!item.getPlatformCollection().contains(platform)){
					item.setPlatformCollection(item.getPlatformCollection()+","+platform);
				}
			}else{
				item.setPlatformCollection(platform);
			}
			item.setAlipayName(alipayName);
			item.setQqNickname(qqNickName);
			item.setSex(sex);
			item.setAge(age);
			item.setAddr(addr);
			item.setRegister(register);
			item.remove("ageStr");
			return Db.use("tel").update(tableName, item.toRecord());
		}else{
			
			item = telDao.findTel(tel);
			if(!StringUtils.isNullOrEmpty(item.getCol1())){
				if(!item.getCol1().contains(platform)){
					item.setPlatformCollection(item.getCol1()+","+platform);
				}else{
					item.setPlatformCollection(item.getCol1());
				}
			}else{
				item.setPlatformCollection(platform);
			}
			item.setAlipayName(alipayName);
			item.setQqNickname(qqNickName);
			item.setSex(sex);
			item.setAge(age);
			item.setAddr(addr);
			item.setRegister(register);
			item.remove("ageStr");
			item.remove("create_time");
			item.remove("col1");
			item.remove("col2");
			return Db.use("tel").save(tableName, item.toRecord());
		}
	}
	
	
}
