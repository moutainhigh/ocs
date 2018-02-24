package com.rong.business.service;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Qq;

/**
 * 项目接口
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public interface QqService extends BaseService<Qq>{
	Page<Qq> page(int pageNumber, int pageSize, Kv param);
	boolean save(String qq,String pwd,String token,String userName);
	boolean saveOrUpdate(String qq,String userName,String data);
	Qq findByQqAndUserName(String qq,String userName);
}