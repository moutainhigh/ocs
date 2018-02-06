package com.rong.api.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.util.RequestUtils;
import com.rong.common.util.StringUtils;
import com.rong.persist.dao.UserTokenDao;
import com.rong.persist.model.UserToken;

public class CommonInterceptor implements Interceptor {

	private static final Logger logger = Logger.getLogger(CommonInterceptor.class);

	/**
	 * 控制器操作主逻辑
	 * 
	 * @param ai
	 * @return
	 */
	private boolean doMain(Invocation ai) {
		try {
			ai.invoke();// 然后调用
		} catch (CommonException e) {
			e.printStackTrace();
			BaseRenderJson.apiReturnJson(ai.getController(), e.getCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.apiReturnJson(ai.getController(), MyErrorCodeConfig.REQUEST_FAIL, e.getMessage());
		}
		return true;
	}

	@Override
	public void intercept(Invocation ai){
		Controller controller = ai.getController();
		if (!MyConst.devMode) {
			// 获取当前action
			HttpServletRequest request = controller.getRequest();
			StringBuffer requestURL = request.getRequestURL();
			String url = requestURL.toString();
			String[] temp = url.split("/");
			String actionName = temp[temp.length - 2] + "/" + temp[temp.length - 1];
			actionName = actionName.toLowerCase();
			// 判断是否ip限制路径
			String[] authIpArr = MyAccessConfig.AUTH_IP_ACTIONS.toLowerCase().split(",");
			java.util.List<String> authIpList = Arrays.asList(authIpArr);
			if (authIpList.contains(actionName)) {
				if(!checkAndSetTrustURL(controller.getRequest(), controller.getResponse())){
					BaseRenderJson.apiReturnJson(ai.getController(), MyErrorCodeConfig.ERROR_IP_NOT_AUTH, "ip未授权:"+RequestUtils.getRequestIpAddress(request));
					return;
				}
				doMain(ai);
				return;
			}
			// 判断是否公共权限路径
			String[] publicActionsArr = MyAccessConfig.PUBLIC_ACTIONS.toLowerCase().split(",");
			java.util.List<String> publicActionsList = Arrays.asList(publicActionsArr);
			if (publicActionsList.contains(actionName)) {
				doMain(ai);
				return;
			}
			// 验证token和当前操作路径权限
			String token = controller.getPara("token");
			String userId = controller.getPara("userName");
			if (StringUtils.isNullOrEmpty(token) || StringUtils.isNullOrEmpty(userId)) {
				BaseRenderJson.baseRenderObj.returnUserIdErrorObj(controller);
				return;
			}
			MDC.put("userId", userId);// 设置log4j的用户id-不同线程都有自己的MDC的key
			UserTokenDao userTokenDao = new UserTokenDao();
			UserToken resultToken = userTokenDao.findByUserName(userId);
			// 判断token
			if (null != resultToken) {
				if (!token.equals(resultToken.get("token"))) {
					BaseRenderJson.baseRenderObj.returnTokenErrorObj(controller, 2);
					return;
				}
				if (!resultToken.getIsExpir()) {
					doMain(ai);
					return;
				} else {
					BaseRenderJson.baseRenderObj.returnUserIdErrorObj(controller);
					return;
				}
			} else {
				BaseRenderJson.baseRenderObj.returnUserIdErrorObj(controller);
				return;
			}
		}
		doMain(ai);
	}

	private static boolean checkAndSetTrustURL(HttpServletRequest request, HttpServletResponse response) {
		String requestURI = RequestUtils.getRequestIpAddress(request);// 获取客户端主机域
		String trustURLStr = MyConst.apiAuthIp; // 获取信任域
		if (trustURLStr.equals("*")) {
			setResponseHeader(response);
			return true;
		}
		String[] trustURLStrArray = trustURLStr.split(",");
		java.util.List<String> authIpList = Arrays.asList(trustURLStrArray);
		if (authIpList.contains(requestURI)) {
			setResponseHeader(response);
			return true;
		}
		logger.error("ip未授权：" + requestURI);
		return false;
	}

	/**
	 * 通过设置响应头里的Header，来指定可以跨域访问的客户端
	 * @param response
	 */
	private static void setResponseHeader(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
		response.setHeader("Access-Control-Expose-Headers", "*");
	}

}
