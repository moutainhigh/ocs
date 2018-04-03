package com.rong.common.bean;

import com.rong.common.util.PropertiesUtils;

/**
 * 系统参数配置、常量配置
 * @author Wenqiang-Rong
 *
 */
public class MyConst {
	/** ~~~~~~~~~~~~~~~~~~~~系统参数配置~~~~~~~~~~~~~~~~~~~~ */
	public static final String SESSION_KEY = "ADMIN_USER";

	public static String version = PropertiesUtils.get("version", "1.0.0.20180131_beta");
	public static boolean devMode = false;
	public static int RUNNING_MODE = Integer.parseInt(PropertiesUtils.get("RUNNING_MODE", "1")); // 当前模式
	public static String apiAuthIp = "*"; // 接口允许访问的IP

	/** ~~~~~~~~~~~~~~~~~~~~常量配置~~~~~~~~~~~~~~~~~~~~ */
	// 用户管理
	public static final int USERSTATUS_DISABLE = 0;// 禁用
	public static final int USERSTATUS_ENABLE = 1;// 正常

	// 系统配置
	public static final int RUNNING_MODE_DEV_SERVER = 1; // 开发服务器
	public static final int RUNNING_MODE_TEST_SERVER = 2; // 测试服务器
	public static final int RUNNING_MODE_ONLINE_SERVER = 3; // 正式环境服务器
	public static final long TOKEN_EXPIR_TIME = 2592000; // 超时时间 单位秒 30天

	// 对密码格式的限制
	public static final String REGEX = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,12}";
	// 对用户名格式的限制
	public static final String REG_USER_NAME = "\\d{5,11}";
	public static final String REG_MOBILE = "\\d{11}";
	public static final String DEFAULT_PASSWORD = "123456";

}
