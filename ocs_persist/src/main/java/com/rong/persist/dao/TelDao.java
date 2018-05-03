package com.rong.persist.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.bean.MyPage;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Tel;

/**
 * 手机dao
 * @author Wenqiang-Rong
 * @date 2018年4月20日
 */
public class TelDao extends BaseDao<Tel> {

	public static final Tel dao = Tel.dao;

	public static final String FILEDS = "id,create_time,tel,tel_province,tel_city,tel_area_code,tel_operator,platform_collection,alipay_name,qq_nickname,sex,TIMESTAMPDIFF(YEAR, age, CURDATE()) age,addr,col1,col2,col3,col4,col5";

	public MyPage page(int limit, int offset, String tel,Kv param) {
		String tableName;
		if (!StringUtils.isNullOrEmpty(tel) && tel.length()>=4) {
			tableName = getTableName(tel);
			if(tel.length()==11){
				List<Tel> returnList = new ArrayList<Tel>(limit); 
				returnList.add(this.findTel(tel));
				return new MyPage(limit, offset, 1, returnList);
			}
		}else{
			tableName = randTableName();
		}
		String select = "select t1.* from " + tableName +" AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `"+tableName+"`)-"
				+ "(SELECT MIN(id) FROM `"+tableName+"`))+(SELECT MIN(id) FROM `"+tableName+"`)) AS id) AS t2 ";
		StringBuffer where = createParam(new StringBuffer(" where t1.id >= t2.id"),tel, param);
		String orderBy = " order by t1.id asc";
		String page = " limit "+(limit/10)+" offset "+offset;
		select = select + where + orderBy + page;
		List<Tel> returnList = new ArrayList<Tel>(limit); 
		for (int i = 0; i < 10; i++) {
			List<Tel> list = dao.find(select);
			returnList.addAll(list);
		}
		return new MyPage(limit, offset, count(tableName, createParam(new StringBuffer(" where 1=1"),tel, param).toString()), returnList);
	}

	private StringBuffer createParam(StringBuffer where,String tel, Kv param) {
		if(param!=null){
			if (!StringUtils.isNullOrEmpty(tel)) {
				if(tel.length()==11){
					where.append(" and tel = '" + tel + "'");
				}else if(tel.length()==7){
					where.append(" and tel like '" + tel + "%'");
				}
			}
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
					where.append(" and col1 is null");
				}else{
					where.append(" and col1 like '%" + platform + "%'");
				}
			}
			String unplatform = param.getStr("unplatform");
			if (!StringUtils.isNullOrEmpty(unplatform)) {
				where.append(" and col1 not like '%" + unplatform + "%'");
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
				if(age.contains("-")){
					String [] ageArr = age.split("-");
					where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) >= " + ageArr[0]);
					where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) <= " + ageArr[1]);
				}else{
					where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) = " + age);
				}
			}
			String alipayName = param.getStr("alipayName");
			if (!StringUtils.isNullOrEmpty(alipayName)) {
				where.append(" and alipay_name = '" + alipayName + "'");
			}
			String qqNickName = param.getStr("qqNickName");
			if (!StringUtils.isNullOrEmpty(qqNickName)) {
				where.append(" and qq_nickname = '" + qqNickName + "'");
			}
			String addr = param.getStr("addr");
			if (!StringUtils.isNullOrEmpty(addr)) {
				where.append(" and addr = '" + addr + "'");
			}
			String register = param.getStr("register");
			if (!StringUtils.isNullOrEmpty(register)) {
				if("1".equals(register)){
					where.append(" and col2 = '" + register + "'");
				}else{
					where.append(" and col2 is null ");
				}
			}
			
		}
		return where;
	}
	
	public int count(String tableName,String where) {
		String select = "explain select count(*) from " + tableName ;
		select = select + where;
		Record record = Db.use("tel").findFirst(select);
		if(record==null){
			return 0;
		}
		return record.getInt("rows")==null?0:record.getInt("rows");
	}
	
	
	@SuppressWarnings("rawtypes")
	public List list(int limit, int offset, String tel,Kv param) {
		String tableName;
		if (!StringUtils.isNullOrEmpty(tel) && tel.length()>=4) {
			tableName = getTableName(tel);
			if(tel.length()==11){
				return  Db.use("tel").find("select * from " + tableName +" where tel = ?",tel);
			}
		}else{
			tableName = randTableName();
		}
		String select = "select t1.tel from " + tableName +" AS t1 JOIN (SELECT ROUND(RAND() * ((SELECT MAX(id) FROM `"+tableName+"`)-"
				+ "(SELECT MIN(id) FROM `"+tableName+"`))+(SELECT MIN(id) FROM `"+tableName+"`)) AS id) AS t2 ";
		StringBuffer where = createParam(new StringBuffer(" where t1.id >= t2.id"),tel, param);
		String orderBy = " order by t1.id asc";
		String page = " limit 1 offset "+offset;
		select = select + where + orderBy + page;
		List<Record> returnList = new ArrayList<Record>(limit); 
		for (int i = 0; i < limit; i++) {
			List<Record> list = Db.use("tel").find(select);
			returnList.addAll(list);
		}
		return returnList;
	}
	
	public boolean updateTel(String tel,String platform,String alipayName,String qqNickName,String sex,Date age,String addr,String register){
		String tableName = getTableName(tel);
		Tel item = findTel(tel);
		// 使用备用字段col1保存采集平台，多平台采集采用，隔开
		if(item.getCol1()!=null){
			if(!item.getCol1().contains(platform)){
				item.setCol1(item.getCol1()+","+platform);
			}
		}else{
			item.setCol1(platform);
		}
		item.setAlipayName(alipayName);
		item.setQqNickname(qqNickName);
		item.setSex(sex);
		item.setAge(age);
		item.setAddr(addr);
		item.setCol2(register);
		return Db.use("tel").update(tableName, item.toRecord());
	}
	
	public Tel findTel(String tel){
		String tableName = getTableName(tel);
		String sql = "select " + FILEDS + " from " + tableName + " where tel = ?";
		return dao.findFirst(sql,tel);
	}
	
	public static boolean existTableName(String tableName){
		String sql = "SELECT table_name FROM information_schema. TABLES WHERE table_schema = 'tel_db' and table_name = '"+tableName+"'";
		Record table = Db.use("tel").findFirst(sql);
		if(table==null){
			return false;
		}
		return true;
	}
	
	public static String randTableName(){
		String sql = "SELECT table_name tableName FROM information_schema. TABLES WHERE table_schema = 'tel_db' order by rand() limit 1";
		Record table = Db.use("tel").findFirst(sql);
		if(table!=null){
			return table.getStr("tableName");
		}
		return null;
	}
	
	public static String getTableName(String tel){
		return "tel_"+tel.substring(0,4);
	}
	
}
