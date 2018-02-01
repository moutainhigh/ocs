package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.InterfaceCall;

/**
 * 接口调用dao
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class InterfaceCallDao extends BaseDao<InterfaceCall> {

	public static final InterfaceCall dao = InterfaceCall.dao;

	public static final String FILEDS = "id,create_time,call_success,remark,project_id,project_name,user_name";

	public Page<InterfaceCall> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + InterfaceCall.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String projectId = param.getStr("projectId");
			if (!StringUtils.isNullOrEmpty(projectId)) {
				where.append(" and project_id = '" + projectId + "'");
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
	
	public int countByProjectId(Long projectId){
		return Db.queryInt("select count(*) from " + InterfaceCall.TABLE + " where project_id = ?",projectId);
	}
}
