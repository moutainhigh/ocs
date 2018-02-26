package com.rong.api.controller;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.rong.business.service.AdService;
import com.rong.business.service.AdServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.Ad;

public class AdController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private AdService adService = new AdServiceImpl();
	
	/**
	 * 保存
	 */
	public void save(){
		String userName = getPara("userName");
		String content = getPara("content");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		paramMap.put("content", content);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		adService.save(userName, content);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "保存成功");
		logger.info("广告保存成功,"+userName+"广告："+content);
	}
	
	/**
	 * 随机返回一条记录，并记录调用一次
	 */
	public void rand(){
		Ad ad = adService.rand();
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, ad.getContent(), "获取成功");
	}
}
