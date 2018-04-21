package com.rong.persist.dao;

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

	public static final String FILEDS = "id,create_time,tel,tel_province,tel_city,tel_area_code,tel_operator,platform_collection,alipay_name,qq_nickname,sex,TIMESTAMPDIFF(YEAR, age, CURDATE()) age,addr";

	public MyPage page(int limit, int offset, String tel,Kv param) {
		String tableName;
		if (!StringUtils.isNullOrEmpty(tel) && tel.length()>=4) {
			tableName = getTableName(tel);
		}else{
			tableName = randTableName();
		}
		String select = "select "+FILEDS+" from " + tableName ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			if (!StringUtils.isNullOrEmpty(tel)) {
				if(tel.length()==11){
					where.append(" and tel = '" + tel + "'");
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
				where.append(" and TIMESTAMPDIFF(YEAR, age, CURDATE()) = " + age);
			}
		}
		String orderBy = " order by id asc";
		String page = " limit "+limit+" offset "+offset;
		select = select + where + orderBy + page;
		List<Tel> list = dao.find(select);
		return new MyPage(limit, offset, count(tableName, where.toString()), list);
	}
	
	public int count(String tableName,String where) {
		String select = "explain select count(*) from " + tableName ;
		select = select + where;
		Record record = Db.use("tel").findFirst(select);
		if(record==null){
			return 0;
		}
		return record.getInt("rows");
	}
	
	
	@SuppressWarnings("rawtypes")
	public List list(int limit, int offset, String tel,Kv param) {
		String tableName;
		if (!StringUtils.isNullOrEmpty(tel) && tel.length()>=4) {
			tableName = getTableName(tel);
		}else{
			tableName = randTableName();
		}
		String select = "select tel from " + tableName ;
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
		String orderBy = " order by id asc";
		String page = " limit "+limit+" offset "+offset;
		select = select + where + orderBy + page;
		return Db.use("tel").find(select);
	}
	
	public boolean updateTel(String tel,String platform,String alipayName,String qqNickName,String sex,Date age,String addr){
		String tableName = getTableName(tel);
		Tel item = findTel(tel);
		item.setPlatformCollection(platform);
		item.setAlipayName(alipayName);
		item.setQqNickname(qqNickName);
		item.setSex(sex);
		item.setAge(age);
		item.setAddr(addr);
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
