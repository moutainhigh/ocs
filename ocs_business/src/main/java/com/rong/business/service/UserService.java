package com.rong.business.service;
import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.User;

/**
 * 会员用户业务接口
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public interface UserService extends BaseService<User>{
	//管理后台方法
	Page<Record> getUserList(int page,int pageSize,Kv param);
	boolean resetPwd(long id, String pwd);
	boolean editExpirDate(long id, String date);
	boolean setEnable(long id, boolean isEnable);
	User findByUserName(String userName);
	User findByUserNameAndPwd(String userName,String pwd);
	boolean saveUserMeal(String userName,Long mealId);
	int countLoginToday();
	//接口开通套餐方法
	boolean openMeal(String userName, Long mealId,BigDecimal account,BigDecimal mealMoney);
	boolean haveValidMealProject(String userName,Long projectId);
}