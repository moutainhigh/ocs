package com.rong.persist.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.SystemResource;

/**
 * 系统资源dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class SystemResourceDao extends BaseDao<SystemResource>{
	private SystemResource dao = SystemResource.dao;
	
	public Page<SystemResource> getPage(int pageNo, int pageSize, String key, long id, String name) {
		StringBuffer where = new StringBuffer(" where 1=1");
		if(!StringUtils.isNullOrEmpty(key)){
			where.append(" and `key` = '"+key+"'");
		}
		if(id!=0){
			where.append(" and (pid = "+id+" or id="+id+")");
		}
		if(!StringUtils.isNullOrEmpty(name)){
			where.append(" and name like '%"+name+"%'");
		}
		String select = "select *";
		String sqlExceptSelect = "from "+SystemResource.TABLE;
		return dao.paginate(pageNo, pageSize, select, sqlExceptSelect+where.toString()+" order by create_time desc");
	}
	
	public List<SystemResource> getAll() {
		String sql = "select * from "+SystemResource.TABLE;
		return dao.find(sql);
	}
	
	public SystemResource getByKey(String key) {
		String sql = "select * from "+SystemResource.TABLE+" where `key` = ?";
		return dao.findFirst(sql,key);
	}
	
	public List<SystemResource> getMenus() {
		String sql = "select * from "+SystemResource.TABLE+" where type = 1";
		return dao.find(sql);
	}
}
