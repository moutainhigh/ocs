package com.rong.admin.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.tel.TelStatisService;
import com.rong.business.service.tel.TelStatisServiceImpl;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.model.TelCityStatis;
import com.rong.persist.model.TelCollectionStatis;
import com.rong.persist.model.TelStatisJob;

public class TelStatisController extends BaseController{
	private TelStatisService service = new TelStatisServiceImpl();
	
	public void index() {
		TelStatisJob job = service.getLastOne();
		if(job==null) {
			render("/views/tel/statis_list.jsp");
			return;
		}
		Long jobId = job.getId();
		// 统计查询
		Map<String, Integer> statisSearchMap = new HashMap<>();
		List<TelCityStatis> statisSearchList = service.getCityStatis(jobId);
		for (TelCityStatis telCityStatis : statisSearchList) {
			statisSearchMap.put(telCityStatis.getCity(), telCityStatis.getTelCount());
		}
		// 统计采集和未采集
		List<TelCollectionStatis> allCollectioned = service.getCollectionStatis(jobId);
		List<TelCollectionStatis> collectioned = new ArrayList<>();
		List<TelCollectionStatis> unCollectioned = new ArrayList<>();
		for (TelCollectionStatis telCollectionStatis : allCollectioned) {
			if(telCollectionStatis.getCollectionType()) {
				collectioned.add(telCollectionStatis);
			}else {
				unCollectioned.add(telCollectionStatis);
			}
		}
		setAttr("sum", statisSearchMap.get("sum"));
		statisSearchMap.remove("sum");
		setAttr("statisCollection", collectioned);
		setAttr("statisUnCollection", unCollectioned);
		setAttr("statisSearch", statisSearchMap);
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
		Date createTime = new Date();
		Map<String, Integer> statisSearch = service.statisByCity(param);
		// 统计采集和未采集
		Map<String, List<Record>> statisCollectionMap = service.statisCollection();
		Date finishTime = new Date();
		// 保存查询任务
		Long jobId = service.save(param, createTime, finishTime);
		// 保存统计城市查询结果
		for (String key : statisSearch.keySet()) {
			TelCityStatis cityStatis = new TelCityStatis();
			cityStatis.setJobId(jobId);
			cityStatis.setCity(key);
			cityStatis.setTelCount(statisSearch.get(key));
			cityStatis.save();
		}
		// 保存统计采集结果
		for (Record item : statisCollectionMap.get("collectioned")) {
			TelCollectionStatis collectionStatis = new TelCollectionStatis();
			collectionStatis.setJobId(jobId);
			collectionStatis.setCollectionType(true);
			collectionStatis.setTelCount(item.getInt("count"));
			collectionStatis.setPlatform(item.getStr("item"));
			collectionStatis.save();
		}
		// 保存统计未采集结果
		for (Record item : statisCollectionMap.get("collectioned")) {
			TelCollectionStatis collectionStatis = new TelCollectionStatis();
			collectionStatis.setJobId(jobId);
			collectionStatis.setCollectionType(false);
			collectionStatis.setTelCount(item.getInt("count"));
			collectionStatis.setPlatform(item.getStr("item"));
			collectionStatis.save();
		}
		keepPara();
		setAttr("sum", statisSearch.get("sum"));
		statisSearch.remove("sum");
		setAttr("statisCollection", statisCollectionMap.get("collectioned"));
		setAttr("statisUnCollection", statisCollectionMap.get("unCollectioned"));
		setAttr("statisSearch", statisSearch);
		render("/views/tel/statis_list.jsp");
	}
	
}
