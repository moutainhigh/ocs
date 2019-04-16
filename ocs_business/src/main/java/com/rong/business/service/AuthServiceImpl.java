package com.rong.business.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.dao.AuthDao;
import com.rong.persist.dao.MealDao;
import com.rong.persist.dao.UserDao;
import com.rong.persist.model.Auth;
import com.rong.persist.model.User;
import com.rong.persist.model.UserAuth;

/**
 * 软件权限业务实现类
 * @author Wenqiang-Rong
 * @date 2019年3月14日
 */
public class AuthServiceImpl extends BaseServiceImpl<Auth> implements AuthService{
	private AuthDao dao = new AuthDao();
	private UserDao userDao = new UserDao();
	private AccountDao accountDao = new AccountDao();
	private MealDao mealDao = new MealDao();

	@Override
	public Auth findById(long id) {
		return dao.findById(id);
	}

	@Override
	public boolean saveAuth(String authKey, String authName) {
		Auth model = new Auth();
		model.setAuthKey(authKey);
		model.setAuthName(authName);
		model.setCreateTime(new Date());
		return model.save();
	}

	@Override
	public boolean saveUserAuth(String userName, List<Auth> authList) {
		dao.delAuthByUserName(userName);
		for (Auth auth : authList) {
			UserAuth userAuth = new UserAuth();
			userAuth.setAuthId(auth.getId());
			userAuth.setUserName(userName);
			userAuth.save();
		}
		return true;
	}

	@Override
	public int updateUserBatch(List<String> userNameList, List<Auth> authList, List<Long> mealIdList, Date expirDate,
			BigDecimal money,Boolean state) {
		List<User> userList =  userDao.findByUserNameList(userNameList);
		int i = 0;
		for (User user : userList) {
			if(user!=null){
				user.setState(state);
				batchSave(authList, mealIdList, expirDate, money, user,false);
				i++;
			}
		}
		return i;
	}

	private void batchSave(List<Auth> authList, List<Long> mealIdList, Date expirDate, BigDecimal money, User user,Boolean isadd) {
		if(expirDate!=null){
			user.setExpirDate(expirDate);
		}
		if(isadd){
			user.save();
		}else{
			user.update();
		}
		if(authList!=null && !authList.isEmpty()){
			//批量更新用户权限
			saveUserAuth(user.getUserName(), authList);
		}
		if(money!=null){
			//批量更新用户余额
			accountDao.updateUserAccount(user.getUserName(), money);
		}
		//批量更新用户套餐
		if(mealIdList!=null && !mealIdList.isEmpty()){
			for (Long mealId : mealIdList) {
				mealDao.saveUserMeal(user.getUserName(), mealId);
			}
		}
	}

	@Override
	public int regUserBatch(List<User> userList, List<Auth> authList, List<Long> mealIdList, Date expirDate,
			BigDecimal money) {
		int i = 0;
		for (User user : userList) {
			//用户存在则直接跳过
			if(userDao.findByUserName(user.getUserName())!=null){
				continue;
			}
			batchSave(authList, mealIdList, expirDate, money, user,true);
			i++;
		}
		return i;
	}

	@Override
	public Page<Auth> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public List<Auth> findByUserName(String userName) {
		return dao.findByUserName(userName);
	}

	@Override
	public Auth hasAuth(String userName, String authKey) {
		return dao.hasAuth(userName, authKey);
	}

	@Override
	public List<Auth> findByAuthKey(List<String> authKeyList) {
		return dao.findByAuthKey(authKeyList);
	}

	@Override
	public List<Auth> findAll() {
		return dao.findAll();
	}
}


