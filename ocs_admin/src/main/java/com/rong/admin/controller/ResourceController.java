package com.rong.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rong.common.bean.BaseRenderJson;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.SystemResource;
import com.rong.system.service.SystemResourceService;
import com.rong.system.service.SystemResourceServiceImpl;

/**
 * 资源管理
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class ResourceController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	SystemResourceService resourceService = new SystemResourceServiceImpl();
	
	public void list(){
		int page = getParaToInt("page", 1);
		String key = getPara("key");
		Long id = getParaToLong("_id",0L);
		String name = getPara("name");
		keepPara();
		setAttr("page", resourceService.getPage(page,pageSize,key,id,name));
		render("/views/system/resource/list.jsp");
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			SystemResource model = resourceService.getById(id);
			setAttr("resource", model);
		}
		setAttr("menus", resourceService.getMenus());
		keepPara();//保留传递过来的参数
		render("/views/system/resource/edit.jsp");
	}
	
	public void edit() {
		String key = getPara("key");
		String name = getPara("name");
		String remark = getPara("remark");
		Long pid = getParaToLong("pid");
		Long id = getParaToLong("id");
		Integer type = getParaToInt("type");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("key", key);
		paramMap.put("name", name);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		if(id==null){
			if (resourceService.getByKey(key)!=null) {
				BaseRenderJson.returnJsonS(this,0,"权限key已经存在");
				return;
			}
			SystemResource model = new SystemResource();
			model.setCreateTime(new Date());
			setModel(key, name, remark, pid, type, model);
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加权限成功："+key+","+name );
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.info("[操作日志]添加权限失败："+key+","+name );
			}
		}else{
			SystemResource model = resourceService.getById(id);
			setModel(key, name, remark, pid, type, model);
			if (model.update()) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]修改权限成功："+key+","+name );
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.info("[操作日志]修改权限失败："+key+","+name );
			}
		}
	}

	private void setModel(String key, String name, String remark, Long pid, Integer type, SystemResource model) {
		model.setKey(key);
		model.setName(name);
		model.setPid(pid);
		model.setRemark(remark);
		model.setType(type==1);
	}

	public void delete() {
		Integer id = getParaToInt("id");
		if(resourceService.delete(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除权限成功:id:" + id);
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]删除权限失败:id:" + id);
		}
	}
}

