package com.rong.business.service;

import java.math.BigDecimal;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.MealDao;
import com.rong.persist.dao.ProjectDao;
import com.rong.persist.model.Meal;
import com.rong.persist.model.MealProject;
import com.rong.persist.model.UserMeal;

/****
 * @Project_Name:	ocs_business
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		ProjectServiceImpl.java
 * @CreateDate:		2018年2月1日 上午11:49:04
 * @Designer:		Wenqiang-Rong
 * @Desc:			套餐业务实现类
 * @ModifyHistory:	
 ****/
public class MealServiceImpl extends BaseServiceImpl<Meal> implements MealService{
	private MealDao dao = new MealDao();
	private ProjectDao projectDao = new ProjectDao();

	@Override
	public Page<Meal> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public boolean save(String mealName, BigDecimal money, Integer time, String remark) {
		return dao.save(mealName, money, time, remark);
	}

	@Override
	public boolean saveMealProject(Long mealId, Long projectId,String projectName) {
		return projectDao.saveMealProject(mealId, projectId,projectName);
	}

	@Override
	public List<MealProject> findMealProject(Long mealId) {
		return projectDao.findMealProject(mealId);
	}

	@Override
	public Page<UserMeal> pageUserMeal(int pageNumber, int pageSize, Kv param) {
		return dao.pageUserMeal(pageNumber, pageSize, param);
	}

	@Override
	public UserMeal findByUserMealId(Long id) {
		return UserMeal.dao.findById(id);
	}

	@Override
	public boolean deleteUserMeal(Long userMealId) {
		UserMeal userMeal = findByUserMealId(userMealId);
		return userMeal.delete();
	}

}


