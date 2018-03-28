package com.rong.api.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.AccountService;
import com.rong.business.service.AccountServiceImpl;
import com.rong.business.service.MealService;
import com.rong.business.service.MealServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.Account;
import com.rong.persist.model.Meal;
import com.rong.persist.model.UserMeal;

public class MealController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private MealService mealService = new MealServiceImpl();
	private UserService userService = new UserServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	
	/**
	 * 开通套餐
	 */
	public void save(){
		String userName = getPara("userName");
		Long mealId = getParaToLong("meal");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("mealId", mealId);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		// 校验套餐是否存在
		Meal meal = mealService.findById(mealId);
		if(meal==null){
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.NOT_EXIST, "套餐不存在");
			return;
		}
		// 开通套餐方法中，会校验余额是否足够
		Account userAccount = accountService.findByUserName(userName);
		userService.openMeal(userName, mealId, userAccount.getAccount(), meal.getMoney());
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "保存成功");
		logger.info("开通套餐,"+userName+"套餐："+meal.getMealName());
	}
	
	/**
	 * 默认方法，获取用户已开通套餐
	 */
	public void index(){
		String userName = getPara("userName");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		Kv param = Kv.by("userName", userName);
		Page<UserMeal> page = mealService.pageUserMeal(1, 999, param);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,page.getList(), "获取成功");
	}
	
	/**
	 * 获取所有套餐
	 */
	public void allList(){
		String mealName = getPara("mealName","包月");
		if(mealName.contains("-")){
			mealName = mealName.split("-")[0];
		}
		Kv param = Kv.by("mealName", mealName);
		Page<Meal> page = mealService.page(1, 999, param);
		List<Meal> returnList = page.getList();
		if("包月".equals(mealName)){
			for (Meal meal : returnList) {
				meal.setMealName(meal.getMealName().split("-")[0]);
			}
		}
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,returnList, "获取成功");
		
	}
}
