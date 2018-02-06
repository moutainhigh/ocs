package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Account;
import com.rong.persist.model.User;

/**
 * 用户dao
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class UserDao extends BaseDao<User> {

	public static final User dao = User.dao;

	public static final String FILEDS = "id,create_time,update_time,state,user_name,user_pwd,login_ip,login_time";

	public Page<Record> page(int pageNumber, int pageSize, Kv param) {
		String select = "select u.agent_id,u.login_ip,u.login_time,u.id,u.user_name,u.state,u.create_time,u.update_time,a.account,a.consumed_sum,a.last_consumed_time";
		String sqlExceptSelect = "from " + User.TABLE + " u ," + Account.TABLE +" a";
		StringBuffer where = new StringBuffer(" where u.user_name = a.user_name");
		if(param!=null){
			// 状态 1-可用，0已冻结
			Boolean state = param.getBoolean("state");
			if (state != null) {
				where.append(" and u.state = " + state);
			}
			// 用户名
			String userName = param.getStr("userName");
			if (!StringUtils.isNullOrEmpty(userName)) {
				where.append(" and u.user_name = '" + userName + "'");
			}
			// 代理id
			Long agentId = param.getLong("agentId");
			if (agentId != null && agentId != 0) {
				where.append(" and u.agent_id = " + agentId + "");
			}
		}
		String orderBy = " order by u.id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public User findByUserName(String userName){
		String sql = "select " + FILEDS + " from " + User.TABLE + " where user_name = ?";
		return dao.findFirst(sql, userName);
	}
	
	public boolean updateField(long id, String fieldName, Object value){
		return Db.update(String.format("UPDATE %s SET %s = ? WHERE id = ?", User.TABLE, fieldName), value, id)>0;
	}
	
	public User findByUserNameAndPwd(String userName,String pwd){
		String sql = "select " + FILEDS + " from " + User.TABLE + " where user_name = ? and user_pwd = ?";
		return dao.findFirst(sql, userName,pwd);
	}
}
