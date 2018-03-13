package com.rong.admin.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.InterfaceCallService;
import com.rong.business.service.InterfaceCallServiceImpl;
import com.rong.business.service.ProjectService;
import com.rong.business.service.ProjectServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.Project;

public class ProjectController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private ProjectService projectService = new ProjectServiceImpl();
	private InterfaceCallService interfaceCallService = new InterfaceCallServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		projectService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + id);
	}
	
	public void save() {
		String projectName = getPara("projectName");
		String price = getPara("price");
		String remark = getPara("remark");
		projectService.save(projectName,new BigDecimal(price), remark);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增成功,项目名称:" + projectName);
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			Project model = projectService.findById(id);
			setAttr("item", model);
		}
		render("/views/project/edit.jsp");
	}
	
	public void edit(){
		Long id = getParaToLong("id");
		String projectName = getPara("projectName");
		String price = getPara("price");
		String remark = getPara("remark");
		Project model = projectService.findById(id);
		model.setProjectName(projectName);
		model.setPrice(new BigDecimal(price));
		model.setRemark(remark);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改成功");
	}

	public void list() {
		int page = getParaToInt("page", 1);
		String projectName = getPara("projectName");
		Page<Project> list = projectService.page(page, pageSize, projectName);
		List<Project> plist = list.getList();
		List<Record> returnList = new ArrayList<>();
		for (Project project : plist) {
			Record record = new Record();
			record.setColumns(project);
			int count = interfaceCallService.countByProjectName(project.getProjectName());
			record.set("count", count);
			returnList.add(record);
		}
		Page<Record> returnPage = new Page<>(returnList, page, pageSize, list.getTotalPage(), list.getTotalRow());
		keepPara();
		setAttr("page", returnPage);
		render("/views/project/list.jsp");
	}
	
	public void enable(){
		Long id = getParaToLong("id");
		Boolean enable = getParaToBoolean("enable");
		Project model = projectService.findById(id);
		model.setEnable(enable);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
	}
}
