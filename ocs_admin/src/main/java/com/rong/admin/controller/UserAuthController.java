package com.rong.admin.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.AuthService;
import com.rong.business.service.AuthServiceImpl;
import com.rong.business.service.MealService;
import com.rong.business.service.MealServiceImpl;
import com.rong.business.service.UserService;
import com.rong.business.service.UserServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.CommonUtil;
import com.rong.common.util.DateTimeUtil;
import com.rong.common.util.StringUtils;
import com.rong.persist.model.Auth;
import com.rong.persist.model.Meal;
import com.rong.persist.model.User;

public class UserAuthController extends BaseController{
	private final Log logger = Log.getLog(this.getClass());
	private AuthService authService = new AuthServiceImpl();
	private MealService mealService = new MealServiceImpl();
	private UserService userService = new UserServiceImpl();
	
	public void delete() {
		Long id = getParaToLong("id");
		authService.deleteById(id);
		BaseRenderJson.returnDelObj(this, true);
		logger.info("[操作日志]删除成功,id:" + id);
	}
	
	public void save() {
		String authKey = getPara("authKey");
		String authName = getPara("authName");
		authService.saveAuth(authKey, authName);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增成功");
	}
	
	public void saveUserAuth() {
		String authkeyArr = getPara("authKeys");
		String userName = getPara("userName");
		List<String> authKeyList = Arrays.asList(authkeyArr.split(","));
		List<Auth> authList = authService.findByAuthKey(authKeyList);
		authService.saveUserAuth(userName, authList);
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]软件授权成功");
	}
	
	public void toEdit() {
		Long id = getParaToLong("id");
		if(id!=null){
			Auth model = authService.findById(id);
			setAttr("item", model);
		}
		render("/views/user/auth/edit.jsp");
	}
	
	public void edit(){
		Long id = getParaToLong("id");
		String authKey = getPara("authKey");
		String authName = getPara("authName");
		Auth model = authService.findById(id);
		model.setAuthKey(authKey);
		model.setAuthName(authName);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]修改成功");
	}

	public void list() {
		int page = getParaToInt("page", 1);
		String authName = getPara("authName");
		Kv param = Kv.by("isAdmin","isAdmin").set("authName", authName);
		Page<Auth> list = authService.page(page, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/user/auth/list.jsp");
	}
	
	public void userAuthList() {
		String userName = getPara("userName");
		List<Auth> list = authService.findByUserName(userName);
		keepPara();
		setAttr("list", list);
		render("/views/user/auth_list.jsp");
	}
	
	public void userAuthData() {
		String userName = getPara("userName");
		List<Auth> authList = authService.findAll();
		List<Auth> list = authService.findByUserName(userName);
		List<Record> returnList = new ArrayList<Record>();
		for (Auth auth : authList) {
			Record record = new Record();
			record.set("auth_key", auth.getAuthKey());
			record.set("auth_name", auth.getAuthName());
			record.set("isHas", false);
			for (Auth userAuth : list) {
				if(userAuth.getId()==auth.getId()){
					record.set("isHas", true);
				}
			}
			returnList.add(record);
		}
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, returnList, "获取成功");
	}
	
	public void batchAdd() {
		String [] authkeys = getParaValues("authKeys");
		String userNames = getPara("userNames");
		String [] mealIds = getParaValues("mealIds");
		String userPwd = getPara("userPwd");
		String expirDateStr = getPara("expirDate");
		String money = getPara("money","0");
		Boolean state = getParaToBoolean("state",true);
		List<String> userNameList = new ArrayList<String>();
		if(userNames.indexOf("-")>0){
			int start = Integer.parseInt(userNames.split("-")[0]);
			int end = Integer.parseInt(userNames.split("-")[1]);
			for (int i = start;i<=end;i++) {
				userNameList.add(String.valueOf(i));
			}
		}else{
			//对用户名做分行处理和分割处理
			String userNamesStr [] = userNames.split("\n");
			for (String str : userNamesStr) {
				userNameList.addAll(Arrays.asList(str.split(",")));
			}
		}
		
		// 对软件权限做分割处理
		List<String> authKeyList = null;
		List<Auth> authList = null;
		if (authkeys != null) {
			authKeyList = Arrays.asList(authkeys);
			authList = authService.findByAuthKey(authKeyList);
		}
		List<String> mealIdList = null;
		List<Long> mealList = null;
		if (authkeys != null) {
			mealIdList = Arrays.asList(mealIds);
			mealList = new ArrayList<Long>();
			for (String mealId : mealIdList) {
				mealList.add(Long.parseLong(mealId));
			}
		}
		List<User> userList = new ArrayList<User>();
		for (String userName : userNameList) {
			User u = new User();
			u.setUserPwd(CommonUtil.getMD5(userPwd));
			u.setUserName(userName);
			u.setCreateTime(new Date());
			u.setState(state);
			userList.add(u);
		}
		Date expirDate = DateTimeUtil.parseDateTime(expirDateStr,DateTimeUtil.DEFAULT_FORMAT_DAY);
		int result = authService.regUserBatch(userList, authList, mealList, expirDate, new BigDecimal(money));
		BaseRenderJson.apiReturnJson(this, "1", "总数据："+userNameList.size()+",成功更新:"+result+",跳过:"+(userNameList.size()-result));
		logger.info("[操作日志]批量新增成功");
	}
	
	public void batchUpdate() {
		String [] authkeys = getParaValues("authKeys");
		String userNames = getPara("userNames");
		String [] mealIds = getParaValues("mealIds");
		String expirDateStr = getPara("expirDate");
		String money = getPara("money");
		Boolean state = getParaToBoolean("state");
		List<String> userNameList = new ArrayList<String>();
		// 对用户名做分行处理和分割处理
		if(userNames.indexOf("-")>0){
			int start = Integer.parseInt(userNames.split("-")[0]);
			int end = Integer.parseInt(userNames.split("-")[1]);
			for (int i = start;i<=end;i++) {
				userNameList.add(String.valueOf(i));
			}
		}else{
			//对用户名做分行处理和分割处理
			String userNamesStr [] = userNames.split("\n");
			for (String str : userNamesStr) {
				userNameList.addAll(Arrays.asList(str.split(",")));
			}
		}
		// 对软件权限做分割处理
		List<String> authKeyList = null;
		List<Auth> authList = null;
		if(authkeys!=null){
			authKeyList = Arrays.asList(authkeys);
			authList = authService.findByAuthKey(authKeyList);
		}
		List<String> mealIdList = null;
		List<Long>  mealList = null;
		if(authkeys!=null){
			mealIdList = Arrays.asList(mealIds);
			mealList = new ArrayList<Long>();
			for (String mealId : mealIdList) {
				mealList.add(Long.parseLong(mealId));
			}
		}
		Date expirDate = DateTimeUtil.parseDateTime(expirDateStr,DateTimeUtil.DEFAULT_FORMAT_DAY);
		BigDecimal moneyBigDecimal = null;
		if(!StringUtils.isNullOrEmpty(money)){
			moneyBigDecimal = new BigDecimal(money);
		}
		int result = authService.updateUserBatch(userNameList, authList, mealList, expirDate, moneyBigDecimal,state);
		BaseRenderJson.apiReturnJson(this, "1", "总数据："+userNameList.size()+",成功更新:"+result+",跳过:"+(userNameList.size()-result));
		logger.info("[操作日志]批量更新成功");
	}
	
	public void toBatchAdd() {
		List<Auth> authList = authService.findAll();
		List<Meal> mealList = mealService.findAll();
		setAttr("authList", authList);
		setAttr("mealList", mealList);
		render("/views/user/batchAdd.jsp");
	}
	
	public void toBatchEdit() {
		List<Auth> authList = authService.findAll();
		List<Meal> mealList = mealService.findAll();
		setAttr("authList", authList);
		setAttr("mealList", mealList);
		render("/views/user/batchEdit.jsp");
	}
	
	public void userListToBatchEdit() {
		List<Auth> authList = authService.findAll();
		List<Meal> mealList = mealService.findAll();
		setAttr("authList", authList);
		setAttr("mealList", mealList);
		int page = getParaToInt("page", 1);
		Boolean state = getParaToBoolean("state");
		String userName = getPara("userName");
		Long dayExpir = getParaToLong("dayExpir");
		Kv param = Kv.by("userName", userName).set("state", state).set("dayExpir",dayExpir);
		if(!isAdmin()){
			param.set("agentId", getUser().getId());
		}
		Page<Record> list = userService.getUserList(page, 999999,param);
		keepPara();
		StringBuffer userNameStr = new StringBuffer();
		int i = 0;
		for (Record user : list.getList()) {
			if(i!=0){
				userNameStr.append(",");	
			}
			userNameStr.append(user.getStr("user_name"));
			i++;
		}
		setAttr("userName", userNameStr.toString());
		render("/views/user/batchEdit.jsp");
	}
}
