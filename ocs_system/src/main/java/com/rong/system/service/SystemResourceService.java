package com.rong.system.service;

import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.SystemResource;

/**
 * 权限资源配置
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface SystemResourceService {
	boolean save(SystemResource resource);
	boolean delete(long id);
	boolean update(SystemResource resource);
	Page<SystemResource> getPage(int pageNo,int pageSize,String key,long id,String name);
	List<SystemResource> getAll();
	SystemResource getByKey(String key);
	SystemResource getById(long id);
	List<SystemResource> getMenus(); 
	Set<String> getAllKey();
}

