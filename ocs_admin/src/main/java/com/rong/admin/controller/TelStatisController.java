package com.rong.admin.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.tel.TelStatisService;
import com.rong.business.service.tel.TelStatisServiceImpl;
import com.rong.common.util.GsonUtil;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.model.TelCityStatis;
import com.rong.persist.model.TelCollectionStatis;
import com.rong.persist.model.TelStatisJob;
import com.rong.persist.test.TelCreateUtil;

public class TelStatisController extends BaseController{
	private TelStatisService service = new TelStatisServiceImpl();
	
	@SuppressWarnings("unchecked")
	public void index() {
		TelStatisJob job = service.getLastOne();
		if(job==null) {
			render("/views/tel/statis_list.jsp");
			return;
		}
		Long jobId = job.getId();
		// 统计查询
		List<TelCityStatis> statisSearchList = service.getCityStatis(jobId);
		for (TelCityStatis telCityStatis : statisSearchList) {
			if("sum".equals(telCityStatis.getCity())){
				setAttr("sum", telCityStatis.getTelCount());
				break;
			}
		}
		Collections.sort(statisSearchList);
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
		// 解析查询参数:{"collectionType":true,"city":false,"sex":"",
		// "platform":1,"operator":"","age":"","register":""}
		Map<String,Object> param = ((Map<String, Object>)GsonUtil.fromJson(job.getParam(), Map.class));
		Double platform = (Double)param.get("platform");
		String sex = (String)param.get("sex");
		String operator = (String)param.get("operator");
		String age = (String)param.get("age");
		String register = (String)param.get("register");
		Boolean city = (Boolean)param.get("city");
		setAttr("platform", platform);
		setAttr("sex", sex);
		setAttr("operator", operator);
		setAttr("age", age);
		setAttr("register", register);
		setAttr("city", city);
		setAttr("statisCollection", collectioned);
		setAttr("statisUnCollection", unCollectioned);
		setAttr("statisSearch", statisSearchList);
		setAttr("job", job);
		setAttr("job_time", TelCreateUtil.formatTime(job.getFinishTime().getTime()-job.getCreateTime().getTime()));
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
		Map<String, Long> statisSearch = service.statisByCity(param);
		// 统计采集和未采集
		Map<String, List<Record>> statisCollectionMap = service.statisCollection();
		Date finishTime = new Date();
		// 保存查询任务
		Long jobId = service.save(param, createTime, finishTime);
		// 保存统计城市查询结果
		List<TelCityStatis> statisSearchList = new ArrayList<>();
		for (String key : statisSearch.keySet()) {
			TelCityStatis cityStatis = new TelCityStatis();
			cityStatis.setJobId(jobId);
			cityStatis.setCity(key);
			cityStatis.setTelCount(statisSearch.get(key));
			cityStatis.save();
			statisSearchList.add(cityStatis);
		}
		List<TelCollectionStatis> collectioned = new ArrayList<>();
		List<TelCollectionStatis> unCollectioned = new ArrayList<>();
		// 保存统计采集结果
		for (Record item : statisCollectionMap.get("collectioned")) {
			TelCollectionStatis collectionStatis = new TelCollectionStatis();
			collectionStatis.setJobId(jobId);
			collectionStatis.setCollectionType(true);
			collectionStatis.setTelCount(item.getLong("count"));
			collectionStatis.setPlatform(item.getStr("item"));
			collectionStatis.save();
			collectioned.add(collectionStatis);
		}
		// 保存统计未采集结果
		for (Record item : statisCollectionMap.get("unCollectioned")) {
			TelCollectionStatis collectionStatis = new TelCollectionStatis();
			collectionStatis.setJobId(jobId);
			collectionStatis.setCollectionType(false);
			collectionStatis.setTelCount(item.getLong("count"));
			collectionStatis.setPlatform(item.getStr("item"));
			collectionStatis.save();
			unCollectioned.add(collectionStatis);
		}
		Collections.sort(statisSearchList);
		keepPara();
		TelStatisJob job = service.getLastOne();
		setAttr("job", job);
		setAttr("job_time", TelCreateUtil.formatTime(job.getFinishTime().getTime()-job.getCreateTime().getTime()));
		setAttr("sum", statisSearch.get("sum"));
		statisSearch.remove("sum");
		setAttr("statisCollection", collectioned);
		setAttr("statisUnCollection",unCollectioned);
		setAttr("statisSearch", statisSearchList);
		render("/views/tel/statis_list.jsp");
	}
	
}
