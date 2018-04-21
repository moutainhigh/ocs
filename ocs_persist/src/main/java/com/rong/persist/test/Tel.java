package com.rong.persist.test;

import java.util.Date;

/****
 * @Project_Name:	ocs_persist
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		Tel.java
 * @CreateDate:		2018年4月19日 下午8:35:38
 * @Designer:		Wenqiang-Rong
 * @Desc:			
 * @ModifyHistory:	
 ****/

public class Tel {
	// 号码段
	private String tel;
	// 省份
	private String tel_province;
	// 城市
	private String tel_city;
	// 区号
	private String tel_area_code;
	// 运营商
	private String tel_operator;
	private Date create_time;
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel_province() {
		return tel_province;
	}
	public void setTel_province(String tel_province) {
		this.tel_province = tel_province;
	}
	public String getTel_city() {
		return tel_city;
	}
	public void setTel_city(String tel_city) {
		this.tel_city = tel_city;
	}
	public String getTel_area_code() {
		return tel_area_code;
	}
	public void setTel_area_code(String tel_area_code) {
		this.tel_area_code = tel_area_code;
	}
	public String getTel_operator() {
		return tel_operator;
	}
	public void setTel_operator(String tel_operator) {
		this.tel_operator = tel_operator;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}


