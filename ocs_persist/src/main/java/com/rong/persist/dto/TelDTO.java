package com.rong.persist.dto;

public class TelDTO {
	public TelDTO(){}
	public TelDTO(String qq,String trueName,String idCard,String userAccount,String userAccountPwd,String email,String profession,String education){
		this.qq = qq;
		this.trueName = trueName;
		this.idCard = idCard;
		this.userAccount = userAccount;
		this.userAccountPwd = userAccountPwd;
		this.email = email;
		this.profession = profession;
		this.education = education;
	}
	
	// qq
	private String qq;
	// 真实姓名
	private String trueName;
	// 身份证
	private String idCard;
	// 账号
	private String userAccount;
	// 密码
	private String userAccountPwd;
	// 邮箱
	private String email;
	// 职业
	private String profession;
	// 学历
	private String education;

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserAccountPwd() {
		return userAccountPwd;
	}

	public void setUserAccountPwd(String userAccountPwd) {
		this.userAccountPwd = userAccountPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

}
