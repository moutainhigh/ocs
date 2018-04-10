package com.rong.business.service;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Consume;

/**
 * 消费流水接口
 * @author Wenqiang-Rong
 * @date 2018年4月10日
 */
public interface ConsumeService extends BaseService<Consume>{
	Page<Consume> page(int pageNumber, int pageSize, Kv param);
}