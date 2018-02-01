package com.rong.admin.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统日志监听器
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class Log4jConfigListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	    ServletContext sc = sce.getServletContext();
		String logPath = sc.getRealPath("/WEB-INF");
		System.setProperty("logPath",logPath);
	}

}
