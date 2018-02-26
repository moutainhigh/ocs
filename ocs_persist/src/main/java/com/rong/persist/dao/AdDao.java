package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Ad;

/**
 * 广告dao
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public class AdDao extends BaseDao<Ad> {

	public static final Ad dao = Ad.dao;

	public static final String FILEDS = "id,create_time,content,user_name";

	public Page<Ad> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Ad.TABLE ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String content = param.getStr("content");
			if (!StringUtils.isNullOrEmpty(content)) {
				where.append(" and user_name like '%" + content + "%'");
			}
			String userName = param.getStr("userName");
			if (!StringUtils.isNullOrEmpty(userName)) {
				where.append(" and user_name = '" + userName + "'");
			}
			String date = param.getStr("date");
			if (!StringUtils.isNullOrEmpty(date)) {
				where.append(" and to_days(create_time) = to_days('"+date+"')");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public Ad findByContent(String content){
		String sql = "select " + FILEDS + " from " + Ad.TABLE + " where content = ? ";
		return dao.findFirst(sql, content);
	}
	
	
	public Ad Rand(){
		String sql = "select " + FILEDS + " from " + Ad.TABLE + " order by RAND() limit 1;";
		return dao.findFirst(sql);
	}
	
	public boolean countCallAutoAdd(long id){
		return Db.update("UPDATE "+Ad.TABLE+" SET count_call = count_call+1 WHERE id = " + id)>0;
	}
}
