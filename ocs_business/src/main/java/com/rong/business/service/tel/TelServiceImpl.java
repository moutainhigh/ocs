package com.rong.business.service.tel;

import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.rong.common.bean.MyPage;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.TelDao;
import com.rong.persist.dao.TelStatisDao;
import com.rong.persist.dto.TelDTO;
import com.rong.persist.model.Tel;

/**
 * 手机号码业务实现类
 * @author Wenqiang-Rong
 * @date 2018年4月20日
 */
public class TelServiceImpl extends BaseServiceImpl<Tel> implements TelService{
	private TelDao dao = new TelDao();
	private TelStatisDao telStatisDao = new TelStatisDao();

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

	/**
	 * 更新本表，并更新统计相对应的表
	 */
	@Override
	public boolean updateTel(String tel, String platform, String alipayName, String qqNickName, String sex, Date age,
			String addr,String register,TelDTO telDTO) {
		dao.updateTel(tel, platform, alipayName, qqNickName, sex, age, addr,register,telDTO);
		telStatisDao.saveOrUpdateTel(tel, platform, alipayName, qqNickName, sex, age, addr, register,telDTO);
		return true;
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


