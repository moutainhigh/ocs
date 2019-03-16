package com.rong.persist.dao;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Auth;
import com.rong.persist.model.UserAuth;

/**
 * 广告dao
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public class AuthDao extends BaseDao<Auth> {

	public static final Auth dao = Auth.dao;
	public static final UserAuth userAuthdao = UserAuth.dao;

	public static final String FILEDS = "id,create_time,update_time,auth_key,auth_name";
	public static final String FILEDS_USER_AUTH = "id,user_name,auth_key,auth_name";

	public Page<Auth> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Auth.TABLE ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String authKey = param.getStr("authKey");
			if (!StringUtils.isNullOrEmpty(authKey)) {
				where.append(" and auth_key = '" + authKey + "'");
			}
			String authName = param.getStr("authName");
			if (!StringUtils.isNullOrEmpty(authName)) {
				where.append(" and auth_name = '" + authName + "'");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public List<Auth> findByUserName(String userName){
		String sql = "select a.id,a.auth_key,a.auth_name from " + Auth.TABLE_USER_AUTH +" ua,"+Auth.TABLE +" a where ua.auth_id = a.id and ua.user_name = ?";
		return dao.find(sql, userName);
	}
	
	public List<Auth> findByAuthKey(List<String> authKeyList){
		StringBuffer param = new StringBuffer("");
		int i = 0;
		for (String str : authKeyList) {
			if(i!=0){
				param.append(",");
			}
			i++;
			param.append("'").append(str).append("'");
		}
		String sql = "select "+FILEDS+" from " + Auth.TABLE+" where auth_key in ("+param+")";
		return dao.find(sql);
	}
	
	public boolean delAuthByUserName(String userName){
		String sql = "delete from " +  Auth.TABLE_USER_AUTH + " where user_name = ? ";
		return Db.update(sql,userName)>0?true:false;
	}
	
	public Auth hasAuth(String userName,String authKey){
		String sql = "select a.* from " + Auth.TABLE_USER_AUTH +" ua,"+Auth.TABLE +" a where ua.auth_id = a.id and ua.user_name = ? and a.auth_key = ? ";
		return dao.findFirst(sql, userName,authKey);
	}
	
	public List<Auth> findAll(){
		String sql = "select "+FILEDS+" from " + Auth.TABLE;
		return dao.find(sql);
	}
	
}
