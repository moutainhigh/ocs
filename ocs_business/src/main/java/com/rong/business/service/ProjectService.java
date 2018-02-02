package com.rong.business.service;

import java.math.BigDecimal;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Project;

/**
 * 项目接口
 * 
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public interface ProjectService extends BaseService<Project> {
	boolean save(String projectName, BigDecimal price, String remark);
	Page<Project> page(int pageNumber, int pageSize, String name);
}