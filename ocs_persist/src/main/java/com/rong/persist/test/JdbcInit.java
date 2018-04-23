package com.rong.persist.test;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.rong.common.util.PropertiesUtils;

/**
 * @author rongwq JFinal的Model测试用例
 * 
 */
public class JdbcInit {
	
	protected static DruidPlugin dp;
	protected static ActiveRecordPlugin activeRecord;

	
	/**
	 * 数据连接地址
	 */
	 private static String URL ;

	/**
	 * 数据库账号
	 */
	 private static String USERNAME ;

	/**
	 * 数据库密码
	 */
	 private static String PASSWORD;

	/**
	 * 数据库驱动
	 */
	private static String DRIVER = "com.mysql.jdbc.Driver";

	/**
	 * 数据库类型（如mysql，oracle）
	 */
	private static final String DATABASE_TYPE = "mysql";

	static {
		PropertiesUtils.load("config_dev.txt");
		URL = PropertiesUtils.get("jdbcUrl", "jdbc:mysql://127.0.0.1:3306/tel_db?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8");
		USERNAME = PropertiesUtils.get("user", "root");
		PASSWORD = PropertiesUtils.get("password", "test2016");
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	public static void init() throws Exception {
		dp = new DruidPlugin(URL, USERNAME, PASSWORD, DRIVER);

		dp.addFilter(new StatFilter());

		dp.setInitialSize(3);
		dp.setMinIdle(2);
		dp.setMaxActive(5);
		dp.setMaxWait(60000);
		dp.setTimeBetweenEvictionRunsMillis(120000);
		dp.setMinEvictableIdleTimeMillis(120000);

		WallFilter wall = new WallFilter();
		wall.setDbType(DATABASE_TYPE);
		dp.addFilter(wall);
		dp.getDataSource();
		dp.start();

		activeRecord = new ActiveRecordPlugin(dp);
		activeRecord.setDialect(new MysqlDialect())
				.setDevMode(true)
				.setShowSql(true) // 是否打印sql语句
		;
		activeRecord.start();
	}
}
