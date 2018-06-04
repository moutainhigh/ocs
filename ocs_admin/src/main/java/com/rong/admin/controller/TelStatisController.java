package com.rong.admin.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.business.service.tel.TelStatisService;
import com.rong.business.service.tel.TelStatisServiceImpl;
import com.rong.common.util.DateTimeUtil;
import com.rong.common.util.GsonUtil;
import com.rong.common.util.StringUtils;
import com.rong.common.util.TxtExportUtil;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.dto.TelDTO;
import com.rong.persist.model.Tel;
import com.rong.persist.model.TelCityStatis;
import com.rong.persist.model.TelCollectionStatis;
import com.rong.persist.model.TelStatisJob;
import com.rong.persist.test.TelCreateUtil;

public class TelStatisController extends BaseController{
	private TelStatisService service = new TelStatisServiceImpl();
	
	/**
	 * 默认查询方法
	 * 查询最近一次查询记录
	 * 5.28补充，查询最近一次相同条件的上一次结果
	 */
	@SuppressWarnings("unchecked")
	public void index() {
		Long id = getParaToLong("id");
		TelStatisJob job = null;
		if(id!=null){
			job = service.getById(id);
		}else{
			job = service.getLastOne();
			if(job==null) {
				render("/views/tel/statis_list.jsp");
				return;
			}
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
		//查询最近一次相同条件的数据
		statisBeforeParam(id);
		render("/views/tel/statis_list.jsp");
	}

	private void statisBeforeParam(Long id) {
		//查询最近一次相同条件的数据
		TelStatisJob beforeJob = null;
		if(id==null){
			beforeJob = service.getLastOneBeforeData();
		}else{
			beforeJob = service.getBeforeData(id);
		}
		// 统计查询-最近一次相同条件的数据
		if(beforeJob!=null){
			List<TelCityStatis> statisSearchList_before = service.getCityStatis(beforeJob.getId());
			for (TelCityStatis telCityStatis : statisSearchList_before) {
				if ("sum".equals(telCityStatis.getCity())) {
					setAttr("beforeSum", telCityStatis.getTelCount());
					break;
				}
			}
			setAttr("beforeJob", beforeJob);
			setAttr("beforeJob_time", TelCreateUtil.formatTime(beforeJob.getFinishTime().getTime()-beforeJob.getCreateTime().getTime()));
		}
	}
	
	/**
	 * 统计
	 */
	public void statis() {
		Boolean collectionType = getParaToBoolean("collectionType",true);
		Boolean city = getParaToBoolean("city",false);
		Integer platform = getParaToInt("platform",TelStatisDao.platform.ALIPAY);
		String sex = getPara("sex");
		String age = getPara("age");
		String register = getPara("register");
		String operator = getPara("operator");
		Boolean profession = getParaToBoolean("profession");
		Boolean education = getParaToBoolean("education");
		Boolean qq = getParaToBoolean("qq");
		Boolean trueName = getParaToBoolean("trueName");
		Boolean idCard = getParaToBoolean("idCard");
		Boolean email = getParaToBoolean("email");
		Boolean userAccount = getParaToBoolean("userAccount");
		Boolean userAccountPwd = getParaToBoolean("userAccountPwd");
		Kv param = Kv.by("collectionType",collectionType).set("city",city).set("platform", platform).set("operator",operator);
		param.set("sex", sex).set("age", age).set("register",register).set("profession",profession).set("education",education);
		param.set("qq", qq).set("trueName", trueName).set("idCard",idCard).set("email",email).set("userAccount",userAccount).set("userAccountPwd",userAccountPwd);
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
		//查询最近一次相同条件的数据
		statisBeforeParam(null);
		render("/views/tel/statis_list.jsp");
	}
	
	/**
	 * 导出TXT列表
	 */
	public void exportTxt() {
		Boolean collectionType = getParaToBoolean("collectionType",true);
		Boolean city = getParaToBoolean("city",false);
		Integer platform = getParaToInt("platform",TelStatisDao.platform.ALIPAY);
		String sex = getPara("sex");
		String age = getPara("age");
		String register = getPara("register");
		String operator = getPara("operator");
		String col = getPara("col","tel");
		String col2 = getPara("col2","");
		Integer exportLimit = getParaToInt("exportLimit",10000);
		Kv param = Kv.by("collectionType",collectionType).set("city",city).set("platform", platform).set("operator",operator);
		param.set("sex", sex).set("age", age).set("register",register);
		List<Tel> list = service.getAllTel(col, exportLimit, param);
		StringBuffer write = new StringBuffer();
		String tab = "-";
		String enter = "\r\n";
		for (Tel tel : list) {
			TelDTO telDTO = null;
			if(!StringUtils.isNullOrEmpty(tel.getCol1())){
				telDTO = (TelDTO)GsonUtil.fromJson(tel.getCol1(), TelDTO.class);
			}
			if(col.contains("tel")){
				write.append(tel.getTel());
			}
			if(col.contains("tel_province")){
				write.append(tab).append(tel.getTelProvince());
			}
			if(col.contains("tel_city")){
				write.append(tab).append(tel.getTelCity());
			}
			if(col.contains("tel_area_code")){
				write.append(tab).append(tel.getTelAreaCode());
			}
			if(col.contains("tel_operator")){
				write.append(tab).append(tel.getTelOperator());
			}
			if(col.contains("platform_collection")){
				write.append(tab).append(TelStatisDao.platform.NAMELIST[Integer.parseInt((tel.getPlatformCollection()))-1]);
			}
			if(col2.contains("qq")){
				if(telDTO!=null && !StringUtils.isNullOrEmpty(telDTO.getQq())){
					write.append(tab).append(telDTO.getQq());
				}else{
					write.append(tab).append("无");
				}
			}
			if(col.contains("qq_nickname")){
				write.append(tab).append(tel.getQqNickname());
			}
			if(col.contains("sex")){
				String str = "保密";
				if("1".equals(tel.getSex())){
					str="男";
				}else if("2".equals(tel.getSex())){
					str="女";
				}
				write.append(tab).append(str);
			}
			if(col.contains("age")){
				if(tel.getAge()!=null){
					write.append(tab).append(DateTimeUtil.getAge(tel.getAge()));
				}else{
					write.append(tab).append("无");
				}
			}
			if(col.contains("addr")){
				if(!StringUtils.isNullOrEmpty(tel.getAddr())){
					write.append(tab).append(tel.getAddr());
				}else{
					write.append(tab).append("无");
				}
			}
			if(col.contains("register")){
				String str = "未注册";
				if("1".equals(tel.getRegister())){
					str = "注册";
				}
				write.append(tab).append(str);
			}
			if(col.contains("alipay_name")){
				if(!StringUtils.isNullOrEmpty(tel.getAlipayName())){
					write.append(tab).append(tel.getAlipayName());
				}else{
					write.append(tab).append("无");
				}
			}
			if(telDTO!=null){
				if(col2.contains("trueName")){
					if(!StringUtils.isNullOrEmpty(telDTO.getTrueName())){
						write.append(tab).append(telDTO.getTrueName());
					}else{
						write.append(tab).append("无");
					}
				}
				if(col2.contains("idCard")){
					if(!StringUtils.isNullOrEmpty(telDTO.getIdCard())){
						write.append(tab).append(telDTO.getIdCard());
					}else{
						write.append(tab).append("无");
					}
				}
				if(col2.contains("userAccount")){
					if(!StringUtils.isNullOrEmpty(telDTO.getUserAccount())){
						write.append(tab).append(telDTO.getUserAccount());
					}else{
						write.append(tab).append("无");
					}
				}
				if(col2.contains("userAccountPwd")){
					if(!StringUtils.isNullOrEmpty(telDTO.getUserAccountPwd())){
						write.append(tab).append(telDTO.getUserAccountPwd());
					}else{
						write.append(tab).append("无");
					}
				}
				if(col2.contains("email")){
					if(!StringUtils.isNullOrEmpty(telDTO.getEmail())){
						write.append(tab).append(telDTO.getEmail());
					}else{
						write.append(tab).append("无");
					}
				}
				if(col2.contains("profession")){
					if(StringUtils.isNullOrEmpty(telDTO.getProfession())){
						write.append(tab).append(telDTO.getProfession());
					}else{
						write.append(tab).append("无");
					}
				}
				if(col2.contains("education")){
					if(!StringUtils.isNullOrEmpty(telDTO.getEducation())){
						write.append(tab).append(telDTO.getEducation());
					}else{
						write.append(tab).append("无");
					}
				}
			}
			write.append(enter);
		}
		String webPath = PathKit.getWebRootPath();
		String pathname = webPath + File.separator + "导出数据.txt";
		File file = new File(pathname);
		try {
			TxtExportUtil.createFile(file);
			TxtExportUtil.writeTxtFile(write.toString(), file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderFile(file);
	}
	
	public void jobList() {
		int page = getParaToInt("page", 1);
		Page<TelStatisJob> list = service.pageTelStaisJob(page, pageSize, null);
		List<TelStatisJob> mylist = list.getList();
		for (TelStatisJob telStatisJob : mylist) {
			telStatisJob.put("json", GsonUtil.fromJson(telStatisJob.getParam(), Map.class));
		}
		Page<TelStatisJob> returnList = new Page<>(mylist, page, pageSize, list.getTotalPage(), list.getTotalRow());
		setAttr("page", returnList);
		render("/views/tel/job_list.jsp");
	}
	
}
