package com.rong.business.service.tel;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.rong.common.bean.MyPage;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.TelDao;
import com.rong.persist.model.Tel;

/**
 * 手机号码业务实现类
 * @author Wenqiang-Rong
 * @date 2018年4月20日
 */
public class TelServiceImpl extends BaseServiceImpl<Tel> implements TelService{
	private TelDao dao = new TelDao();

	@Override
	public MyPage page(int pageNumber, int pageSize, String tel, Kv param) {
		int limit = pageSize;
		int offset = (pageNumber-1)*pageSize;
		return dao.page(limit, offset, tel, param);
	}

	@Override
	public boolean updateTel(String tel, String platform, String alipayName, String qqNickName, String sex, Date age,
			String addr) {
		return dao.updateTel(tel, platform, alipayName, qqNickName, sex, age, addr);
	}

	@Override
	public Tel findTel(String tel) {
		return dao.findTel(tel);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List list(int limit, int offset, String tel, Kv param) {
		return dao.list(limit, offset, tel, param);
	}

}


