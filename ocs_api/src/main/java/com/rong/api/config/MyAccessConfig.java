package com.rong.api.config;
/**
 * 不校验token的请求URL配置文件
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class MyAccessConfig {

	// 公共操作   用逗号(,)分隔  不作token登陆验证
	public static final String PUBLIC_ACTIONS = "user/reg,user/login,user/refreshConf,meal/allList";
	// ip白名单限定接口
	public static final String AUTH_IP_ACTIONS = "recharge/saveRecharge,recharge/saveRechargeAgent2,recharge/saveRechargeAgent3";
}
