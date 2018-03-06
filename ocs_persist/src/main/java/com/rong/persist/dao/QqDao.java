package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Qq;

/**
 * QQdao
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class QqDao extends BaseDao<Qq> {

	public static final Qq dao = Qq.dao;

	public static final String FILEDS = "id,create_time,update_time,qq,pwd,token,data,user_name";

	public Page<Qq> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Qq.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String date = param.getStr("date");
			if (!StringUtils.isNullOrEmpty(date)) {
				where.append(" and to_days(create_time) = to_days('"+date+"')");
			}
			String qq = param.getStr("qq");
			if (!StringUtils.isNullOrEmpty(qq)) {
				where.append(" and qq = '"+qq+"'");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public Qq findByQqAndUserName(String qq,String userName){
		String sql = "select " + FILEDS + " from " + Qq.TABLE + " where qq = ? and user_name = ?";
		return dao.findFirst(sql, qq,userName);
	}
}
