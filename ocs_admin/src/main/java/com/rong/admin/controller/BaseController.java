/**
 * 后台控制器基类
 */
package com.rong.admin.controller;

import java.lang.reflect.Field;

import com.jfinal.core.Controller;
import com.rong.common.bean.MyConst;
import com.rong.persist.model.SystemAdmin;

public class BaseController extends Controller {
	public static int pageSize = 10;
	public Field serviceField = null;
	
	public Field getService() {
		if (serviceField != null) {
			return serviceField;
		}
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if ("service".equals(field.getName())) {
				serviceField = field;
				serviceField.setAccessible(true);
				break;
			}
		}
		return serviceField;
	}
	
	public String path() {
		String name = this.getClass().getSimpleName();
		name = name.substring(0, name.indexOf("Controller"));
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
		return "/views/" + name;
	}
	
	public SystemAdmin getUser(){
		SystemAdmin u = (SystemAdmin) getSessionAttr(MyConst.SESSION_KEY);
		return u;
	}
	
	public boolean isAdmin(){
		SystemAdmin u = (SystemAdmin) getSessionAttr(MyConst.SESSION_KEY);
		if(u.getRole().equals("admin")){
			return true;
		}
		return false;
	}
}
