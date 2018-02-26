package com.rong.business.service;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AdDao;
import com.rong.persist.model.Ad;

/**
 * 广告业务实现类
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public class AdServiceImpl extends BaseServiceImpl<Ad> implements AdService{
	private AdDao dao = new AdDao();

	@Override
	public boolean save(String userName, String content) {
		if(findByContent(content)!=null){
			return true;
		}
		Ad ad = new Ad();
		ad.setContent(content);
		ad.setUserName(userName);
		ad.setCreateTime(new Date());;
		return dao.save(ad);
	}

	@Override
	public Page<Ad> page(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public Ad findByContent(String content) {
		return dao.findByContent(content);
	}

	@Override
	public Ad rand() {
		Ad ad = dao.Rand();
		if(ad!=null){
			dao.countCallAutoAdd(ad.getId());
		}
		return ad;
	}

	@Override
	public boolean delAll() {
		return dao.delAll();
	}
	

}


