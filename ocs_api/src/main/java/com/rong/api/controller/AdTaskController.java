package com.rong.api.controller;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.business.service.AdTaskService;
import com.rong.business.service.AdTaskServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.AdTask;
import com.rong.persist.model.AdTaskDetail;

public class AdTaskController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private AdTaskService service = new AdTaskServiceImpl();
	
	/**
	 * 保存广告任务，返回订单号
	 */
	public void save(){
		String userName = getPara("userName");//必填字段
		String content = getPara("content");//必填字段
		Long projectId = getParaToLong("projectId");//必填字段
		Boolean back = getParaToBoolean("back");
		String money = getPara("money");
		BigDecimal moneyVal = null;
		if(money!=null){
			moneyVal = new BigDecimal(money);
		}
		Integer countCall = getParaToInt("countCall");//必填字段
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("content", content);
		paramMap.put("projectId", projectId);
		paramMap.put("countCall", countCall);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		String orderCode = service.save(userName, content, projectId, back,moneyVal , countCall);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS,orderCode, "保存成功");
		logger.info("广告任务保存成功,"+userName+"广告："+content);
	}
	
	/**
	 * 返回最早的一条待执行记录，并更新其状态为执行中
	 */
	public void first(){
		AdTask adTask = service.getFirst();
		if(adTask!=null){
			adTask.removeNullValueAttrs();
			adTask.remove("create_time","update_time","user_name","state","id");
		}
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, adTask, "获取成功");
	}
	
	/**
	 * 获取执行详情
	 */
	public void taskDetail(){
		String orderCode = getPara("orderCode");//必填字段
		int page = getParaToInt("page",1);//可选字段
		int pageSize = getParaToInt("pageSize",10);//可选字段
		// 校验参数
		if (CommonValidatorUtils.requiredValidate("orderCode", orderCode, this)) {
			return;
		}
		Page<AdTaskDetail> returnPage = service.pageDetail(page, pageSize, orderCode);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, returnPage, "获取成功");
	}
	
	/**
	 * 保存执行详情
	 */
	public void saveDetail(){
		String orderCode = getPara("orderCode");//必填字段
		String qqGroupName = getPara("qqGroupName");//必填字段
		String qqGroupNo = getPara("qqGroupNo");//必填字段
		String qq = getPara("qq");//必填字段
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderCode", orderCode);
		paramMap.put("qqGroupName", qqGroupName);
		paramMap.put("qqGroupNo", qqGroupNo);
		paramMap.put("qq", qq);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		service.saveDetail(orderCode, qqGroupName, qqGroupNo, qq);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "保存成功");
		logger.info("广告任务详情保存成功");
	}
}
