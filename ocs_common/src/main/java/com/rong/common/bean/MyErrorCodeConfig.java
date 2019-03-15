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
	public static final String ERROR_IP_NOT_AUTH = "303"; // ip没有访问权限，非白名单
	public static final String REQUEST_SUCCESS = "1"; // 请求成功
	public static final String REQUEST_FAIL = "0"; // 请求失败
	public static final String DATA_NULL = "4"; // 数据为空
	
	public static final String USER_EXIST = "1001"; // 用户已经存在
	public static final String USER_NOT_EXIST = "1002"; // 用户不存在
	public static final String USER_NAME_ERROR = "1003"; // 用户名格式错误，格式为5-11位数字
	public static final String USER_DISABLE = "1004"; // 用户禁用
	public static final String USER_LOGIN_ERROR = "1005"; // 用户名或者密码错误
	public static final String MONEY_ERROR = "1006"; // 金额需要大于0
	public static final String ACCOUNT_NOT_ENOUGH = "1007"; // 余额不足
	public static final String PROJECT_NOT_EXIST = "1008"; //项目不存在
	public static final String DLL_ERROR = "1009"; //调用DLL异常
	public static final String REG_ORDERCODE_ERROR = "1010"; //注册时异常，使用无效的订单号
	public static final String AGENT_NOT_EXIST = "1011"; // 代理不存在
	public static final String RECHARGE_ORDERCODE_ERROR = "1012"; //充值时异常，订单号已使用
	public static final String NOT_EXIST = "1013"; //查询数据不存在
	public static final String USER_AUTH_ERROR = "1014"; //软件没有授权
}
