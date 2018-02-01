package com.rong.persist.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Project;

/**
 * 计费项目dao
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public class ProjectDao extends BaseDao<Project> {
	public static final Project dao = Project.dao;
	public static final String FILEDS = "id,create_time,update_time,project_name,price,remark";
	
	public Page<Project> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Project.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String projectName = param.getStr("projectName");
			if (!StringUtils.isNullOrEmpty(projectName)) {
				where.append(" and project_name like '%" + projectName + "%'");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public boolean save(String projectName,BigDecimal price,String remark){
		Project pro = new Project();
		pro.setCreateTime(new Date());
		pro.setProjectName(projectName);
		pro.setPrice(price);
		pro.setRemark(remark);
		return save(pro);
	}
}
