package com.rong.business.service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Auth;
import com.rong.persist.model.User;

/**
 * 软件权限接口
 * @author Wenqiang-Rong
 * @date 2019年3月14日
 */
public interface AuthService extends BaseService<Auth>{
	/**
	 * 新增权限
	 * @param authKey
	 * @param authName
	 * @return
	 */
	boolean saveAuth(String authKey,String authName);
	
	/**
	 * 保存用户权限
	 * @param userName
	 * @param authList
	 * @return
	 */
	boolean saveUserAuth(String userName,List<Auth> authList);
	/**
	 * 批量更新帐号
	 * @param userNameList
	 * @param authList
	 * @param mealList
	 * @param expirDate
	 * @param money
	 * @return
	 */
	int updateUserBatch(List<String> userNameList,List<Auth> authList,List<Long> mealIdList,Date expirDate,BigDecimal money);
	/**
	 * 批量注册帐号
	 * @param userList
	 * @param authList
	 * @param mealList
	 * @param expirDate
	 * @param money
	 * @return
	 */
	int regUserBatch(List<User> userList,List<Auth> authList,List<Long> mealIdList,Date expirDate,BigDecimal money);
	/**
	 * 查询权限列表
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	Page<Auth> page(int pageNumber, int pageSize, Kv param);
	/**
	 * 查询用户权限
	 * @param userName
	 * @return
	 */
	List<Auth> findByUserName(String userName);
	
	/**
	 * 用户是否有相关权限
	 * @param userName
	 * @param authKey
	 * @return
	 */
	boolean hasAuth(String userName,String authKey);
	
	/**
	 * 根据key查询软件权限类别
	 * @param authKeyList
	 * @return
	 */
	public List<Auth> findByAuthKey(List<String> authKeyList);
	public List<Auth> findAll();
}