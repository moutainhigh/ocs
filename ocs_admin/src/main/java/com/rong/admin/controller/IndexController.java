package com.rong.admin.controller;

import com.jfinal.core.Controller;
import com.rong.system.service.IndexService;
import com.rong.system.service.IndexServiceImpl;
/**
 * 首页控制类
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class IndexController extends Controller {
	IndexService service = new IndexServiceImpl();
	
	public void index() {
		render("/views/login.jsp");
	}
}
