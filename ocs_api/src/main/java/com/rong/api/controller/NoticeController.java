package com.rong.api.controller;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.rong.business.service.NoticeService;
import com.rong.business.service.NoticeServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.Notice;

public class NoticeController extends Controller{
	private NoticeService service = new NoticeServiceImpl();
	
	/**
	 * 获取软件公告
	 */
	public void index(){
		String softwareCode = getPara("softwareCode");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("softwareCode", softwareCode);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		Notice notice = service.findBySoftwareCode(softwareCode);
		if(notice==null){
			BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, "", "暂无数据");
			return;
		}
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, notice.getContent(), "获取成功");
	}
}
