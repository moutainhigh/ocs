package com.rong.persist.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.DateTimeUtil;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Meal;
import com.rong.persist.model.MealProject;
import com.rong.persist.model.UserMeal;

/**
 * 套餐dao
 * @author Wenqiang-Rong
 * @date 2018年3月7日
 */
public class MealDao extends BaseDao<Meal> {
	public static final Meal dao = Meal.dao;
	public static final String FILEDS = "id,create_time,update_time,meal_name,meal_time,remark,money";
	
	/**
	 * 查询
	 * @param pageNumber 当前第几页
	 * @param pageSize 每页数量
	 * @param param jfinal Kv对象
	 * @return
	 */
	public Page<Meal> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Meal.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String mealName = param.getStr("mealName");
			if (!StringUtils.isNullOrEmpty(mealName)) {
				where.append(" and meal_name like '%" + mealName + "%'");
			}
		}
		String orderBy = " order by id asc";
		sqlExceptSelect = sqlExceptSelect + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	/**
	 * 保存
	 * @param mealName 套餐名称
	 * @param money 价格
	 * @param time 套餐时间/天
	 * @param remark 备注
	 * @return
	 */
	public boolean save(String mealName,BigDecimal money,Integer time,String remark){
		Meal item = new Meal();
		item.setCreateTime(new Date());
		item.setMealName(mealName);
		item.setMealTime(time);
		item.setMoney(money);
		item.setRemark(remark);
		return save(item);
	}
	
	/**
	 * 保存用户套餐
	 * @param mealName 套餐名称
	 * @param money 价格
	 * @param time 套餐时间/天
	 * @param remark 备注
	 * @return
	 */
	public boolean saveUserMeal(String userName,Long mealId){
		Meal meal = dao.findById(mealId);
		UserMeal item = new UserMeal();
		item.setCreateTime(new Date());
		item.setMealName(meal.getMealName());
		item.setMealId(mealId);
		item.setUserName(userName);
		item.setExpirDate(DateTimeUtil.nextDay(0-meal.getMealTime()));
		return save(item);
	}
	
	/**
	 * 查询用户套餐
	 * @param pageNumber 当前第几页
	 * @param pageSize 每页数量
	 * @param param jfinal Kv对象
	 * @return
	 */
	public Page<UserMeal> pageUserMeal(int pageNumber, int pageSize, Kv param) {
		String select = "select * ";
		String sqlExceptSelect = " from " + UserMeal.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String userName = param.getStr("userName");
			if (!StringUtils.isNullOrEmpty(userName)) {
				where.append(" and user_name = '" + userName + "'");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return UserMeal.dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	/**
	 * 获取有效期的套餐包含项目-用户计费校验
	 * @param userName
	 * @return
	 */
	public List<Long> userValidProjectList(String userName){
		String sql = "select mp.project_id proId from " + UserMeal.TABLE + " um,"+ MealProject.TABLE + " mp ";
		String where = " where um.meal_id = mp.meal_id and um.expir_date > now() and um.user_name = ?";
		String orderBy = " group by mp.project_id";
		List<Record> list = Db.find(sql+where+orderBy,userName);
		List<Long> returnList = new ArrayList<Long>();
		for (Record record : list) {
			returnList.add(record.getLong("proId"));
		}
		return returnList;
	}
}
