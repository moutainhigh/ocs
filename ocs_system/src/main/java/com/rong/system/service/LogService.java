package com.rong.system.service;

import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 日志业务接口类
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface LogService {
	public Page<Record> list(int pageNumber,int pageSize,Map<String,Object> parMap);
	public Page<Record> operateLogList(int pageNumber,int pageSize,Map<String,Object> parMap) ;
}
