package com.rong.system.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.dao.SystemResourceDao;
import com.rong.persist.model.SystemResource;

/**
 * 权限资源服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class SystemResourceServiceImpl implements SystemResourceService{
	SystemResourceDao dao = new SystemResourceDao();
	
	@Override
	public boolean save(SystemResource resource) {
		return resource.save();
	}

	@Override
	public boolean delete(long id) {
		return SystemResource.dao.deleteById(id);
	}

	@Override
	public boolean update(SystemResource resource) {
		return resource.update();
	}

	@Override
	public Page<SystemResource> getPage(int pageNo, int pageSize, String key, long id, String name) {
		return dao.getPage(pageNo, pageSize, key, id, name);
	}

	@Override
	public List<SystemResource> getAll() {
		return dao.getAll();
	}

	@Override
	public SystemResource getByKey(String key) {
		return dao.getByKey(key);
	}

	@Override
	public SystemResource getById(long id) {
		return SystemResource.dao.findById(id);
	}

	@Override
	public List<SystemResource> getMenus() {
		return dao.getMenus();
	}

	@Override
	public Set<String> getAllKey() {
		List<SystemResource> list = getAll();
		Set<String> returnSet = new HashSet<>();
		for (SystemResource systemResource : list) {
			returnSet.add(systemResource.getKey());
		}
		return returnSet;
	}

}

