package com.rong.business.service.tel;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.rong.common.bean.MyPage;
import com.rong.common.util.StringUtils;
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
		MyPage page = dao.page(limit, offset, tel, param);
		// 如果查询结果为空，则多做2次查询
		if ((page==null || page.getList().size()==0) && StringUtils.isNullOrEmpty(tel)) {
			for (int i = 0; i < 2; i++) {
				page = dao.page(limit, offset, tel, param);
				if (page!=null && page.getList().size()>0) {
					break;
				}
			}
		}
		return page;
	}

	@Override
	public boolean updateTel(String tel, String platform, String alipayName, String qqNickName, String sex, Date age,
			String addr,String register) {
		return dao.updateTel(tel, platform, alipayName, qqNickName, sex, age, addr,register);
	}

	@Override
	public Tel findTel(String tel) {
		return dao.findTel(tel);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List list(int limit, int offset, String tel, Kv param) {
		List list = dao.list(limit, offset, tel, param);
		return list;
	}

}


