package com.rong.business.service.tel;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Tel;
import com.rong.persist.model.TelCityStatis;
import com.rong.persist.model.TelCollectionStatis;
import com.rong.persist.model.TelStatisJob;

/**
 * 手机号码统计接口
 * @author Wenqiang-Rong
 * @date 2018年5月5日
 */
public interface TelStatisService extends BaseService<Tel>{
	// 统计
	Map<String,List<Record>> statisCollection();
	Map<String,Integer> statisByCity(Kv param);
	
	// 查询保存到数据库的统计数据
	Long save(Kv param,Date createTime,Date finishTime);
	List<TelCollectionStatis> getCollectionStatis(Long jobId);
	List<TelCityStatis> getCityStatis(Long jobId);
	TelStatisJob getLastOne();
}