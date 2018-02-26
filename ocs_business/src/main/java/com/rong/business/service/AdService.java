package com.rong.business.service;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Ad;

/**
 * 广告接口
 * @author Wenqiang-Rong
 * @date 2018年2月26日
 */
public interface AdService extends BaseService<Ad>{
	boolean save(String userName,String content);
	Page<Ad> page(int pageNumber, int pageSize, Kv param);
	Ad findByContent(String content);
	Ad rand();
	boolean delAll();
}