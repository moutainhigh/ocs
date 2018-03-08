package com.rong.business.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Notice;

/**
 * 软件公告接口
 * 
 * @author Wenqiang-Rong
 * @date 2018年3月7日
 */
public interface NoticeService extends BaseService<Notice> {
	Page<Notice> page(int pageNumber, int pageSize, Kv param);
	boolean save(String content,String softwareCode,String softwareName,Boolean enable);
	Notice findBySoftwareCode(String softwareCode);
}