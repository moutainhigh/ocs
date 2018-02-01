package com.rong.business.service;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.InterfaceCall;

/**
 * api调用接口
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public interface InterfaceCallService extends BaseService<InterfaceCall>{
	Page<InterfaceCall> page(int pageNumber, int pageSize, Kv param);
	int countByProjectId(long projectId);
}