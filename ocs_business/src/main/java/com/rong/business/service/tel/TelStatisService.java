package com.rong.business.service.tel;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Tel;

/**
 * 手机号码统计接口
 * @author Wenqiang-Rong
 * @date 2018年5月5日
 */
public interface TelStatisService extends BaseService<Tel>{
	Map<String,List<Record>> statisCollection();
	Map<String,Integer> statisByCity(Kv param);
}