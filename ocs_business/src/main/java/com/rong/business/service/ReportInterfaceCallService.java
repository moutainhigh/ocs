package com.rong.business.service;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.ReportInterfaceCall;

/**
 * 接口调用报表接口
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public interface ReportInterfaceCallService extends BaseService<ReportInterfaceCall>{
	Page<ReportInterfaceCall> page(int pageNumber, int pageSize, Kv param);
	Long sumCall();
	Long sumCall7day();
}