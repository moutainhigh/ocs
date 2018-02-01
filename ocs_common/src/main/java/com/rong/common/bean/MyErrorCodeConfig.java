package com.rong.common.bean;
/**
 * 错误码
 * @author rongwq
 *
 */
public class MyErrorCodeConfig {
	// 错误代码
	public static final String ERROR_TOKEN_MISS = "301"; // token不正确
	public static final String ERROR_TOKEN_EXPIRE = "302"; // token过期
	public static final String ERROR_BAD_REQUEST = "400"; // 参数 不正确
	public static final String ERROR_FAIL = "500"; // 请求异常
	public static final String REQUEST_SUCCESS = "1"; // 请求成功
	public static final String REQUEST_FAIL = "0"; // 请求失败
	
	public static final String USER_EXIST = "1001"; // 用户已经存在
	public static final String USER_NOT_EXIST = "1002"; // 用户不存在
	public static final String USER_NAME_ERROR = "1003"; // 用户名格式错误，格式为5-11位数字
	public static final String USER_DISABLE = "1004"; // 用户禁用
	public static final String USER_LOGIN_ERROR = "1005"; // 用户名或者密码错误
	public static final String MONEY_ERROR = "1006"; // 金额需要大于0
}
