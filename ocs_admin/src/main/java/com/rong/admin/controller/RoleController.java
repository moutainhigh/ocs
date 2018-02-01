package com.rong.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.jfinal.aop.Duang;
import com.jfinal.json.Json;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.SystemResource;
import com.rong.persist.model.SystemRole;
import com.rong.system.service.SystemResourceService;
import com.rong.system.service.SystemResourceServiceImpl;
import com.rong.system.service.SystemRoleService;
import com.rong.system.service.SystemRoleServiceImpl;

/**
 * 系统角色管理
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class RoleController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	SystemRoleService roleService = Duang.duang(SystemRoleServiceImpl.class);//明显开启事务
	SystemResourceService resourceService = new SystemResourceServiceImpl();
	
	public void list(){
		setAttr("list", roleService.getList());
		render("/views/system/role/list.jsp");
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			SystemRole role = roleService.getById(id);
			setAttr("role", role);
		}
		render("/views/system/role/edit.jsp");
	}
	
	public void edit() {
		String rname = getPara("rname");
		String remark = getPara("remark");
		Long id = getParaToLong("id");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("rname", rname);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		if(id==null){
			if (roleService.getByName(rname)!=null) {
				BaseRenderJson.returnJsonS(this,0,"角色已经存在");
				return;
			}
			SystemRole model = new SystemRole();
			model.setRname(rname);
			model.setRemark(remark);
			model.setCreateTime(new Date());
			if (model.save()) {
				BaseRenderJson.returnAddObj(this, true);
				logger.info("[操作日志]添加角色"+rname+"成功");
			} else {
				BaseRenderJson.returnAddObj(this, false);
				logger.info("[操作日志]添加角色"+rname+"失败");
			}
		}else{
			SystemRole model = roleService.getById(id);
			model.setRemark(remark);
			if (roleService.update(id, null, remark)) {
				BaseRenderJson.returnUpdateObj(this, true);
				logger.info("[操作日志]修改角色"+rname+"成功");
			} else {
				BaseRenderJson.returnUpdateObj(this, false);
				logger.info("[操作日志]修改角色"+rname+"成功");
			} 
		}
	}

	public void delete() {
		Long id = getParaToLong("id");
		String rname = roleService.getById(id).getRname();
		if(roleService.isExistUserByRname(rname)){
			BaseRenderJson.returnJsonS(this,0,"温馨提示：该角色下有多个用户，无法删除！");
			return;
		}
		if(roleService.delete(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除角色"+rname+"成功");
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]删除角色"+rname+"失败");
		}
	}
	
	public void toPermissions() {
		Long id = getParaToLong("id");
		if(id!=null){
			SystemRole role = roleService.getById(id);
			setAttr("role", role);
			List<SystemResource> resourceList = resourceService.getAll();
			List<SystemResource> roleResourceList = roleService.getPermissionsByRoleId(id);
			List<Map<String, Object>> list = new ArrayList<>();
			for (SystemResource adminResource : resourceList) {
				long resId = adminResource.getId();
				Map<String, Object> map = new HashMap<>();
				map.put("id", adminResource.getId());
				map.put("name", adminResource.getName());
				Long pid = adminResource.getPid();
				if(pid==null){
					map.put("pId", 0);
				}else{
					map.put("pId", pid);
				}
				map.put("open", true);
				for (SystemResource roleResource : roleResourceList) {
					if(resId==roleResource.getId()){
						map.put("checked", true);
						break;
					}
				}
				list.add(map);
			}
			setAttr("mytree", Json.getJson().toJson(list));
			render("/views/system/role/rolePermissions.jsp");
		}
	}
	
	public void savePermissions() {
		Long roleId = getParaToLong("roleId");
		String ids = getPara("resources");
		String [] arr = ids.split(",");
		List<Long> list = new ArrayList<>();
		for (int i=0;i<arr.length;i++) {
			list.add(Long.parseLong(arr[i]));
		}
		if (roleService.saveRolePermissions(roleId, list)) {
			clearCache();
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]权限保存成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.info("[操作日志]权限保存失败");
		}
	}

	//清理shiro缓存
	private void clearCache() {
		RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		MemoryConstrainedCacheManager cache = (MemoryConstrainedCacheManager)securityManager.getCacheManager();
		cache.getCache("dbRealm.authorizationCache").clear();
	}
}

