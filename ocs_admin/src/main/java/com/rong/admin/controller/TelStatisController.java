package com.rong.admin.controller;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.tel.TelStatisService;
import com.rong.business.service.tel.TelStatisServiceImpl;
import com.rong.persist.dao.TelStatisDao;

public class TelStatisController extends BaseController{
	private TelStatisService service = new TelStatisServiceImpl();
	
	public void index() {
		render("/views/tel/statis_list.jsp");
	}
	
	public void statis() {
		Boolean collectionType = getParaToBoolean("collectionType",true);
		Boolean city = getParaToBoolean("city",false);
		Integer platform = getParaToInt("platform",TelStatisDao.platform.ALIPAY);
		String sex = getPara("sex");
		String age = getPara("age");
		String register = getPara("register");
		String operator = getPara("operator");
		Kv param = Kv.by("collectionType",collectionType).set("city",city).set("platform", platform).set("operator",operator);
		param.set("sex", sex).set("age", age).set("register",register);
		// 统计查询
		Map<String, Integer> statisSearch = service.statisByCity(param);
		// 统计采集和未采集
		Map<String, List<Record>> statisCollectionMap = service.statisCollection();
		keepPara();
		setAttr("sum", statisSearch.get("sum"));
		statisSearch.remove("sum");
		setAttr("statisCollection", statisCollectionMap.get("collectioned"));
		setAttr("statisUnCollection", statisCollectionMap.get("unCollectioned"));
		setAttr("statisSearch", statisSearch);
		render("/views/tel/statis_list.jsp");
	}
}
