package com.rong.business.service;
import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.AdTask;
import com.rong.persist.model.AdTaskDetail;

/**
 * 广告任务接口
 * @author Wenqiang-Rong
 * @date 2018年4月3日
 */
public interface AdTaskService extends BaseService<AdTask>{
	/**
	 * 查询广告任务-分页
	 * @param pageNumber
	 * @param pageSize
	 * @param param
	 * @return
	 */
	Page<AdTask> page(int pageNumber, int pageSize, Kv param);
	/**
	 * 根据订单号查询广告
	 * @param orderCode
	 * @return
	 */
	AdTask findByOrderCode(String orderCode);
	/**
	 * 随机获取一条待执行的广告任务
	 * @return AdTask
	 */
	AdTask rand();
	/**
	 * 保存广告任务
	 * @param userName
	 * @param content
	 * @param projectId
	 * @param back
	 * @param moeny
	 * @param countCall
	 * @return orderCode
	 */
	String save(String userName,String content,Long projectId,Boolean back,BigDecimal moeny,Integer countCall);
	/**
	 * 根据广告任务的订单号查询执行详情-分页
	 * @param pageNumber
	 * @param pageSize
	 * @param orderCode
	 * @return
	 */
	Page<AdTaskDetail> pageDetail(int pageNumber, int pageSize, String orderCode);
	/**
	 * 保存任务执行详情
	 * @param qqGroupName
	 * @param qqGroupNo
	 * @param qq
	 * @return
	 */
	boolean saveDetail(String orderCode,String qqGroupName,String qqGroupNo,String qq) ;
}