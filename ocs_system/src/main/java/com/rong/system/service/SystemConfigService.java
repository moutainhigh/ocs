package com.rong.system.service;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.SystemConfig;

public interface SystemConfigService {
	public Page<SystemConfig> list(int pageNo,int pageSize,Integer id,String key,String value);
	public List<SystemConfig> getAll();
	public List<SystemConfig> getAppConf();
	public SystemConfig getById(long id);
	public SystemConfig getByKey(String key);
	public boolean delete(long id);
	public Map<String, Object> getMapByKey(String key);
	public List<Object> getListByKey(String key);
}
