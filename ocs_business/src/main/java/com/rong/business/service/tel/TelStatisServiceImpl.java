package com.rong.business.service.tel;

import java.util.List;
import java.util.Map;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.model.Tel;

/**
 * 手机号码统计业务实现类
 * @author Wenqiang-Rong
 * @date 2018年5月5日
 */
public class TelStatisServiceImpl extends BaseServiceImpl<Tel> implements TelStatisService{
	private TelStatisDao dao = new TelStatisDao();

	@Override
	public Map<String, List<Record>> statisCollection() {
		return dao.statisCollection();
	}

	@Override
	public Map<String, Integer> statisByCity(Kv param) {
		return dao.statisByCity(param);
	}

	

}


