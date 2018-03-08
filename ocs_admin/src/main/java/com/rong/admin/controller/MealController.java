package com.rong.admin.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.json.Json;
import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.MealService;
import com.rong.business.service.MealServiceImpl;
import com.rong.business.service.ProjectService;
import com.rong.business.service.ProjectServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.model.Meal;
import com.rong.persist.model.MealProject;
import com.rong.persist.model.Project;
import com.rong.persist.model.UserMeal;

public class MealController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private MealService service = new MealServiceImpl();
	private ProjectService projectService = new ProjectServiceImpl();
	private UserService userService = new UserServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		service.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + id);
	}
	
	public void save() {
		String mealName = getPara("mealName");
		String remark = getPara("remark");
		String money = getPara("money");
		Integer time = getParaToInt("mealTime");
		service.save(mealName, new BigDecimal(money), time, remark);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增成功:" + mealName+"-"+money+"-"+time);
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			Meal model = service.findById(id);
			setAttr("item", model);
		}
		render("/views/meal/edit.jsp");
	}
	
	public void edit(){
		Long id = getParaToLong("id");
		String mealName = getPara("mealName");
		String remark = getPara("remark");
		String money = getPara("money");
		Integer time = getParaToInt("mealTime");
		Meal model = service.findById(id);
		model.setMealName(mealName);
		model.setRemark(remark);
		model.setMoney(new BigDecimal(money));
		model.setMealTime(time);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改成功");
	}

	public void list() {
		int page = getParaToInt("page", 1);
		String mealName = getPara("mealName");
		Kv param = Kv.by("mealName",mealName);
		Page<Meal> returnPage = service.page(page, pageSize, param);
		keepPara();
		setAttr("page", returnPage);
		render("/views/meal/list.jsp");
	}
	
	public void toSaveMealProject() {
		Long mealId = getParaToLong("id");
		if(mealId!=null){
			List<Project> projectList = projectService.page(1, 99, null).getList();
			setAttr("projectList", projectList);
			List<Map<String, Object>> list = new ArrayList<>();
			List<MealProject> mealProject = service.findMealProject(mealId);
			for (Project item : projectList) {
				Long projectId = item.getId();
				Map<String, Object> map = new HashMap<>();
				map.put("id", item.getId());
				map.put("name", item.getProjectName());
				map.put("open", true);
				for (MealProject project : mealProject) {
					if(projectId==project.getProjectId()){
						map.put("checked", true);
						break;
					}
				}
				list.add(map);
			}
			setAttr("id", mealId);
			setAttr("mytree", Json.getJson().toJson(list));
			render("/views/meal/meal_project_add.jsp");
		}
	}
	
	public void saveMealProject() {
		Long mealId = getParaToLong("id");
		String ids = getPara("resources");
		String [] arr = ids.split(",");
		// 清理原有选择
		List<MealProject> mealProject = service.findMealProject(mealId);
		for (MealProject item : mealProject) {
			item.delete();
		}
		// 保存新的选择
		for (int i=0;i<arr.length;i++) {
			Long projectId = Long.parseLong(arr[i].split("-")[0]);
			String projectName = arr[i].split("-")[1];
			service.saveMealProject(mealId, projectId,projectName);
		}
		BaseRenderJson.returnUpdateObj(this, true);
	}
	
	public void userMealList() {
		int page = getParaToInt("page", 1);
		String userName = getPara("userName");
		Kv param = Kv.by("userName",userName);
		Page<UserMeal> returnPage = service.pageUserMeal(page, pageSize, param);
		keepPara();
		setAttr("page", returnPage);
		render("/views/user/meal_list.jsp");
	}
	
	public void deleteUserMeal() {
		Long userMealId = getParaToLong("id");
		service.deleteUserMeal(userMealId);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + userMealId);
	}
	
	public void saveUserMeal() {
		String userName = getPara("userName");
		Long mealId = getParaToLong("mealId");
		userService.saveUserMeal(userName, mealId);
		BaseRenderJson.returnAddObj(this, true);
	}
	
	public void editUserMealExpirDate() {
		Long id = getParaToLong("id");
		String expirDate = getPara("expirDate");
		UserMeal userMeal = service.findByUserMealId(id);
		userMeal.setExpirDate(DateTimeUtil.parseDateTime(expirDate, DateTimeUtil.DEFAULT_FORMAT_DAY));
		userMeal.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]编辑用户套餐过期时间成功,id：" + id+",expirDate:"+expirDate);
	}
	
	public void mealList() {
		Page<Meal> returnPage = service.page(1, 99, null);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, returnPage.getList(), "获取成功");
	}
}
