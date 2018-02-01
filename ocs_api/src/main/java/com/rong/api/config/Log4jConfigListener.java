package com.rong.api.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
