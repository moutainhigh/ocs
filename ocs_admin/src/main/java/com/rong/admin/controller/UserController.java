package com.rong.admin.controller;
import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.AccountService;
import com.rong.business.service.AccountServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.CommonUtil;

public class UserController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private UserService userService = new UserServiceImpl();
	private AccountService accountService = new AccountServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		userService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除用户成功,id:" + id);
	}
	
	/**
	 * 批量删除
	 */
	public void batchDelete() {
		String ids = getPara("ids");
		userService.batchDelete(ids.split(","));
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除用户成功,id:" + ids);
	}

	public void resetPwd() {
		Long id = getParaToLong("id");
		String userPwd = getPara("userPwd");
		userService.resetPwd(id, CommonUtil.getMD5(userPwd));
		BaseRenderJson.returnJsonS(this, 1, "还原用户密码成功");
		logger.info("[操作日志]还原用户成功,id：" + id);
	}
	
	public void updateState() {
		Long id = getParaToLong("id");
		Boolean enable = getParaToBoolean("enable");
		userService.setEnable(id, enable);
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]禁用/启用用户成功,id：" + id);
	}

	public void list() {
		int page = getParaToInt("page", 1);
		Boolean state = getParaToBoolean("state");
		String userName = getPara("userName");
		Long dayExpir = getParaToLong("dayExpir");
		Kv param = Kv.by("userName", userName).set("state", state).set("dayExpir",dayExpir);
		if(!isAdmin()){
			param.set("agentId", getUser().getId());
		}
		Page<Record> list = userService.getUserList(page, pageSize,param);
		keepPara();
		setAttr("nowDate", new Date());
		setAttr("page", list);
		render("/views/user/list.jsp");
	}
	
	public void loginList() {
		int page = getParaToInt("page", 1);
		String userName = getPara("userName");
		Kv param = Kv.by("userName", userName).set("orderByLoginTime", true);
		if(!isAdmin()){
			param.set("agentId", getUser().getId());
		}
		Page<Record> list = userService.getUserList(page, pageSize,param);
//		for (Record item : list.getList()) {
//			String ip = item.getStr("login_ip");
//			if(StringUtils.isNullOrEmpty(ip)){
//				continue;
//			}
//			// {"code":0,"data":{"ip":"210.21.41.52","country":"中国","area":"",
//			// "region":"广东","city":"广州","county":"XX","isp":"联通","country_id":"CN","area_id":"",
//			// "region_id":"440000","city_id":"440100","county_id":"xx","isp_id":"100026"}}
//			try {
//				String jsonString = HttpUtils.sendGet("http://ip.taobao.com/service/getIpInfo.php?ip="+ip);
//				Map map = (Map)GsonUtil.fromJson(jsonString, Map.class);
//				Map dataMap =  (Map)map.get("data");
//				String country = (String)dataMap.get("country");
//				String region = (String)dataMap.get("region");
//				String city = (String)dataMap.get("city");
//				item.set("city", country+region+city);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		keepPara();
		setAttr("nowDate", new Date());
		setAttr("page", list);
		setAttr("countLoginToday",userService.countLoginToday());
		render("/views/user/login_list.jsp");
	}
	
	public void editExpirDate() {
		Long id = getParaToLong("id");
		String expirDate = getPara("expirDate");
		userService.editExpirDate(id, expirDate);
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]编辑用户过期时间成功,id：" + id+",expirDate:"+expirDate);
	}
	
	public void editAccount() {
		String userName = getPara("userName");
		String money = getPara("money");
		accountService.updateUserAccount(userName, new BigDecimal(money));
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改用户账户成功,userName：" + userName+",account:"+money);
	}
}
