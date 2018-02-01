package com.rong.persist.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.SystemAdmin;
import com.rong.persist.model.SystemResource;
import com.rong.persist.model.SystemRole;
import com.rong.persist.model.SystemRoleResource;

/**
 * 系统角色dao
 * @author Wenqiang-Rong
 * @date 2017年12月22日
 */
public class SystemRoleDao extends BaseDao<SystemRole>{
	private SystemRole dao = SystemRole.dao;

	public boolean isExistUserByRname(String rname) {				
		SystemAdmin u = SystemAdmin.dao.findFirst("SELECT id FROM "+SystemAdmin.TABLE+" WHERE role=? ",rname);
		return null!=u;
	}
	
	public List<SystemRole> getList() {
		String sql = "select * from "+SystemRole.TABLE;
		return  dao.find(sql);
	}
	
	public SystemRole getByName(String name) {
		String sql = "select * from "+SystemRole.TABLE+" where rname =?";
		return  dao.findFirst(sql,name);
	}
	
	public List<SystemResource> getRolePermissions(long roleId) {
		String sql = "select r.id,r.key,r.name from "+SystemResource.TABLE+" r,"+SystemRoleResource.TABLE+" rr where r.id = rr.resource_id and rr.role_id = ?";
		return  SystemResource.dao.find(sql,roleId);
	}
	
	public boolean saveRolePermissions(long roleId,List<Long> permissionsIds) {
		Db.update("delete from "+SystemRoleResource.TABLE+" where roleId = ?",roleId);
		for (Long resourceId : permissionsIds) {
			SystemRoleResource model = new SystemRoleResource();
			model.setResourceId(resourceId);
			model.setRoleId(roleId);
			model.save();
		}
		return true;
	}
	

	public List<String> getRolesList(){
		List<String> list=new ArrayList<String>();
		List<SystemRole> roles = SystemRole.dao.find("select * from "+SystemRole.TABLE);
		for (SystemRole SystemRole : roles) {
			list.add(SystemRole.getRname());
		}
		return list;
	}
}
