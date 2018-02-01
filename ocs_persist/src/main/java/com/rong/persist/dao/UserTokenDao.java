package com.rong.persist.dao;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.rong.common.bean.MyConst;
import com.rong.common.util.GenerateSequenceUtil;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.User;
import com.rong.persist.model.UserToken;

/**
 * 用户token管理
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class UserTokenDao extends BaseDao<UserToken> {
	public static final UserToken dao = UserToken.dao;
	public static final String FILEDS = "id,user_name,token,is_expir,expir_time,create_time";

	/**
	 * 获取最新一条token,有缓存
	 * @return UserToken
	 */
	public UserToken findByUserName(String userName) {
		String sql = "SELECT * FROM " + UserToken.TABLE + " WHERE user_name = ? AND is_expir = 0 ORDER BY id DESC";
		return dao.findFirst(sql, userName);
	}

	public String saveToken(User user) {
		String token = GenerateSequenceUtil.generateSequenceNo();
		UserToken userToken = new UserToken();
		userToken.setUserName(user.getUserName());
		userToken.setToken(token);
		userToken.setCreateTime(new Date());
		userToken.setExpirTime(new Date(System.currentTimeMillis() + MyConst.TOKEN_EXPIR_TIME * 1000));
		userToken.setIsExpir(false);
		userToken.save();
		return token;
	}

	/**
	 * 判断是否有效
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	public boolean checkIsLoginCache(String userName, String token) {
		UserToken userToken = findByUserName(userName);
		if (null == userToken)
			return false;
		boolean isNotExpir = System.currentTimeMillis() < userToken.getExpirTime().getTime()
				&& (!userToken.getIsExpir());
		if (isNotExpir && token.equals(userToken.getToken())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据UserId删除token
	 */
	public boolean delByUserName(String userName) {
		return Db.update("delete from  "+UserToken.TABLE+"  where user_name=?",userName)>0;
	}
}
