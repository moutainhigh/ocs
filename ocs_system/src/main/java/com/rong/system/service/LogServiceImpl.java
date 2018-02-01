package com.rong.system.service;

import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.dao.logDao;

/**
 * 日志业务实现类
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class LogServiceImpl implements LogService{
	private logDao dao = new logDao();
	
	@Override
	public Page<Record> list(int pageNumber, int pageSize, Map<String, Object> parMap) {
		return dao.list(pageNumber, pageSize, parMap);
	}

	@Override
	public Page<Record> operateLogList(int pageNumber, int pageSize, Map<String, Object> parMap) {
		if(parMap.get("msg")!=null){
			parMap.put("msg", "[操作日志]%"+parMap.get("msg"));
		}
		return dao.list(pageNumber, pageSize, parMap);
	}

}
