package com.rong.api.jna;

import java.util.Properties;

/****
 * @Project_Name:	ocs_api
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		WebDllTest.java
 * @CreateDate:		2018年2月3日 下午2:21:36
 * @Designer:		Wenqiang-Rong
 * @Desc:			
 * @ModifyHistory:	
 ****/

public class WebDllTest {
	public static void main(String[] args) {
		Properties props = System.getProperties();
		String bits=String.valueOf(props.get("sun.arch.data.model"));  
		System.out.println("系统位数："+bits);
		String str = "{\"data\":\"11111\",\"time\":\"2222\",\"cmd\":\"\"}";
		System.out.println(WebDll.Instance.enc(str, str.length()));
	}
}


