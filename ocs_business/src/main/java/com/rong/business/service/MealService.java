package com.rong.business.service;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Meal;
import com.rong.persist.model.MealProject;
import com.rong.persist.model.UserMeal;

/**
 * 套餐接口
 * 
 * @author Wenqiang-Rong
 * @date 2018年3月7日
 */
public interface MealService extends BaseService<Meal> {
	Page<Meal> page(int pageNumber, int pageSize, Kv param);
	Page<UserMeal> pageUserMeal(int pageNumber, int pageSize, Kv param);
	boolean save(String mealName,BigDecimal money,Integer time,String remark);
	boolean saveMealProject(Long mealId,Long projectId,String projectName);
	boolean deleteUserMeal(Long userMealId);
	List<MealProject> findMealProject(Long mealId);
	UserMeal findByUserMealId(Long id);
	List<Meal> findAll();
}