package com.rong.business.service;

import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.UserTokenDao;
import com.rong.persist.model.User;
import com.rong.persist.model.UserToken;

/**
 * token业务实现类
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class UserTokenServiceImpl extends BaseServiceImpl<UserToken> implements UserTokenService{
	private UserTokenDao dao = new UserTokenDao();

	@Override
	public UserToken findByUserName(String userName) {
		return dao.findByUserName(userName);
	}

	@Override
	public String saveToken(User user) {
		return dao.saveToken(user);
	}

	@Override
	public boolean checkIsLoginCache(String userName, String token) {
		return dao.checkIsLoginCache(userName, token);
	}

	@Override
	public boolean delByUserName(String userName) {
		return dao.delByUserName(userName);
	}
}
