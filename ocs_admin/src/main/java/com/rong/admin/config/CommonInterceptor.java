package com.rong.admin.config;

import java.util.Map;

import org.apache.log4j.MDC;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.RequestUtils;
import com.rong.persist.model.SystemAdmin;

/**
 * 公共拦截器
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class CommonInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		try {
			// 设置当前请求链接和上一次请求链接
			setUrl(ai.getController());
			myIntercept(ai);
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.returnBaseTemplateObj(ai.getController(), MyErrorCodeConfig.REQUEST_FAIL, "系统异常！"+e);
		}
	}

	private void myIntercept(Invocation ai) {
		Controller controller = ai.getController();
		String url = controller.getRequest().getRequestURL().toString();
		String[] temp = url.split("/");
		//验证码部分不需要做判断
		if ("captcha".equals(temp[temp.length - 1]) || "login".equals(temp[temp.length - 1]) || temp.length == 4) {
			ai.invoke();
			return;
		}
		//判断登陆后权限
		SystemAdmin u = controller.getSessionAttr(MyConst.SESSION_KEY);
		if(u!=null){
			MDC.put("userId", u.getUserName());//设置log4j的用户id-不同线程都有自己的MDC的key
			MDC.put("ip",RequestUtils.getRequestIpAddress(controller.getRequest()));
			ai.invoke();
		} else {
			controller.redirect("/");
		}
		return;
	}
	
	/**
	 * 保存单次请求和上次请求的数据至session，以便进一步操作
	 * 如：前端js返回操作，common.js:back()
	 * @param cont
	 */
	public void setUrl(Controller cont){
		Map<String, String[]> params = cont.getRequest().getParameterMap();  
        String queryString = "";  
        for (String key : params.keySet()) {  
            String[] values = params.get(key);  
            for (int i = 0; i < values.length; i++) {  
                String value = values[i];  
                queryString += key + "=" + value + "&";  
            }  
        }
        String url = cont.getRequest().getRequestURL().toString();
        // 去掉最后一个空格  
        if(!"".equals(queryString)){
	        queryString = queryString.substring(0, queryString.length() - 1);  
	        url += "?" + queryString;
        }
        if(cont.getSessionAttr("currUrl")!=null){
        	cont.setSessionAttr("backUrl", cont.getSessionAttr("currUrl"));
        }
        cont.setSessionAttr("currUrl", url);
	}

}
