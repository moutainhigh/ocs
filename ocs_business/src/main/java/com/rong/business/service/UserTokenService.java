package com.rong.business.service;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.User;
import com.rong.persist.model.UserToken;

/**
 * token接口
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public interface UserTokenService extends BaseService<UserToken>{
	UserToken findByUserName(String userName);
	String saveToken(User user);
	boolean checkIsLoginCache(String userName, String token);
	boolean delByUserName(String userName);
}