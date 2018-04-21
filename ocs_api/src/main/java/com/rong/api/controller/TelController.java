package com.rong.api.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.tel.TelService;
import com.rong.business.service.tel.TelServiceImpl;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;

public class TelController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private TelService service = new TelServiceImpl();
	
	/**
	 * 获取手机号码
	 */
	public void list(){
		int offset = getParaToInt("offset",0);
		int limit = getParaToInt("limit",20);
		String tel = getPara("tel");
		String province = getPara("province");
		String city = getPara("city");
		String platform = getPara("platform");
		Kv param = Kv.by("tel", tel).set("province",province).set("city",city).set("platform", platform);
		List list = service.list(limit, offset, tel, param);
		Set<String> returnList = new HashSet<String>(list.size());
		for (Object object : list) {
			Record item = (Record)object;
			returnList.add(item.getStr("tel"));
		}
		Record record = new Record();
		record.set("offset", limit+offset);
		record.set("list", returnList);
		BaseRenderJson.apiReturnObj(this, MyErrorCodeConfig.REQUEST_SUCCESS, record, "获取成功");
	}
	
	/**
	 * 保存手机号码相关信息
	 */
	public void edit(){
		String tel = getPara("tel");
		String platform = getPara("platform");
		String alipayName = getPara("alipayName");
		String qqNickName = getPara("qqNickName");
		String sex = getPara("sex");
		String addr = getPara("addr");
		Date age = getParaToDate("age");
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("tel", tel);
		// 校验所有参数
		if (CommonValidatorUtils.requiredValidate(paramMap, this)) {
			return;
		}
		service.updateTel(tel, platform, alipayName, qqNickName, sex, age, addr);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "保存成功");
		logger.info("保存成功，手机号码信息：".concat("tel:").concat(tel).concat("alipayName:").concat(alipayName));
	}
}
