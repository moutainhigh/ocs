package com.rong.api.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByActionKeyRegex;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.rong.api.controller.AdController;
import com.rong.api.controller.AdTaskController;
import com.rong.api.controller.ConsumController;
import com.rong.api.controller.MealController;
import com.rong.api.controller.NoticeController;
import com.rong.api.controller.RechargeController;
import com.rong.api.controller.UserController;
import com.rong.api.controller.UserController_v2;
import com.rong.api.handler.DruidMonitorHandler;
import com.rong.common.bean.MyConst;
import com.rong.persist.dao.SystemConfigDao;
import com.rong.persist.model.SystemConfig;
import com.rong.persist.model._MappingKit;

public class MyConfig extends JFinalConfig {
	
	private final Log logger = Log.getLog(this.getClass());

	/**
	 * 加载配置文件
	 * 
	 * @param mode
	 * @return
	 */
	private boolean myLoadPropertyFile(int mode) {
		switch (mode) {
		case MyConst.RUNNING_MODE_DEV_SERVER:
			loadPropertyFile("config_dev.txt");
			break;

		case MyConst.RUNNING_MODE_TEST_SERVER:
			loadPropertyFile("config_test.txt");
			break;

		case MyConst.RUNNING_MODE_ONLINE_SERVER:
			loadPropertyFile("config_online.txt");
			break;

		}
		return true;
	}

	@Override
	public void configConstant(Constants me) {
		myLoadPropertyFile(MyConst.RUNNING_MODE);
		MyConst.devMode = getPropertyToBoolean("devMode", false);
		me.setDevMode(MyConst.devMode);
		me.setViewType(ViewType.JSP);
		me.setEncoding("UTF8");
		initConst();
	}
	
	/**
	 * 初始刷常量
	 */
	public void initConst() {
		
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/api/user", UserController.class);
		me.add("/api/v2/user", UserController_v2.class);
		me.add("/api/recharge", RechargeController.class);
		me.add("/api/ad", AdController.class);
		me.add("/api/notice", NoticeController.class);
		me.add("/api/meal", MealController.class);
		me.add("/api/consum", ConsumController.class);
		me.add("/api/adtask", AdTaskController.class);
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configPlugin(Plugins me) {
		final String username = getProperty("user");
		final String password = getProperty("password").trim();
		final String instance_read_source1_jdbcUrl = getProperty("jdbcUrl");
		dataSourceConfig(me, instance_read_source1_jdbcUrl, username, password);
	}
	
	private void dataSourceConfig(Plugins me, String source1_url, String username, String password) {
		//1.主库
		DruidPlugin druidPlugin = new DruidPlugin(source1_url, username, password);
		druidPlugin.setDriverClass("com.mysql.jdbc.Driver");
		druidPlugin.setInitialSize(10).setMaxActive(1000).setMinIdle(10).setTestOnBorrow(false).setMaxWait(20*1000);
		// 2.druid监控
		StatFilter statFilter = new StatFilter();
		statFilter.setMergeSql(true);
		statFilter.setLogSlowSql(true);
		// 2.1慢查询目前设置为1s,随着优化一步步进行慢慢更改
		statFilter.setSlowSqlMillis(1000);
		druidPlugin.addFilter(statFilter);
		// 2.2防注入插件
//		WallFilter wall = new WallFilter();
//		wall.setDbType("mysql");
//		druidPlugin.addFilter(wall);
		me.add(druidPlugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin("yun", druidPlugin);
		if (MyConst.devMode) {
			arp.setShowSql(true);
		}
		_MappingKit.mapping(arp);
		me.add(arp);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new SessionInViewInterceptor());
		me.add(new CommonInterceptor());
		me.add(new TxByActionKeyRegex("(.*save.*|.*update.*|.*edit.*|.*delete.*)"));
	}

	@Override
	public void configHandler(Handlers me) {
		// 开启druid监控
		me.add(new DruidMonitorHandler("/druid"));

	}

	@Override
	public void afterJFinalStart() {
		logger.info("api 启动成功");
		super.afterJFinalStart();
		//ip白名单
		SystemConfig config = new SystemConfigDao().getByKey("apiAuthIp");
		if(config!=null){
			MyConst.apiAuthIp = config.getValue();
		}
	}
}
