package com.rong.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.dao.SystemConfigDao;
import com.rong.persist.model.SystemConfig;

public class SystemConfigServiceImpl implements SystemConfigService {
	private SystemConfigDao dao = new SystemConfigDao();
	
	@Override
	public Page<SystemConfig> list(int pageNo, int pageSize, Integer id, String key, String value) {
		return dao.list(pageNo, pageSize, id, key, value);
	}
	
	@Override
	public List<SystemConfig> getAll() {
		return dao.getAll();
	}
	
	@Override
	public List<SystemConfig> getAppConf() {
		return dao.getAppConf();
	}

	@Override
	public SystemConfig getById(long id) {
		return SystemConfig.dao.findById(id);
	}

	@Override
	public SystemConfig getByKey(String key) {
		return dao.getByKey(key);
	}

	@Override
	public boolean delete(long id) {
		return SystemConfig.dao.deleteById(id);
	}

	/**
	 * 获取系统参数列表
	 * 如：val:String"一：1，二：2，三：3"  ==》 Map {"一=1","二=2","三=3"}
	 * 注意"："和"，"使用中文字符保存
	 */
	@Override
	public Map<String, Object> getMapByKey(String key) {
		SystemConfig conf = getByKey(key);
		if(conf==null){
			return null;
		}
		String val = conf.getValue();
		String [] valArr = val.split("，");
		Map<String, Object> map = new HashMap<>();
		for (String str : valArr) {
			map.put(str.split("：")[1], str.split("：")[0]);
		}
		return map;
	}
	
	/**
	 * 获取系统参数列表
	 * 如：val:String"一，二，三"  ==》 List {"一","二","三"}
	 * 注意"："和"，"使用中文字符保存
	 */
	@Override
	public List<Object> getListByKey(String key) {
		SystemConfig conf = getByKey(key);
		if(conf==null){
			return null;
		}
		String val = conf.getValue();
		String [] valArr = val.split("，");
		List<Object> list = new ArrayList<>();
		for (String str : valArr) {
			list.add(str);
		}
		return list;
	}


}
