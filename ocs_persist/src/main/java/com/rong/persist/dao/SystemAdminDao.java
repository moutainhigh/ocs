package com.rong.persist.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.SystemAdmin;
import com.rong.persist.model.SystemResource;
import com.rong.persist.model.SystemRole;
import com.rong.persist.model.SystemRoleResource;

/**
 * 系统用户dao类
 * @author Wenqiang-Rong
 * @date 2017年12月22日
 */
public class SystemAdminDao extends BaseDao<SystemAdmin>{
	private SystemAdmin dao = SystemAdmin.dao;
	public Page<SystemAdmin> getList(int page,int pagesize) {
		String select = "select *";
		String sqlExceptSelect = "from "+SystemAdmin.TABLE;
		return dao.paginate(page, pagesize, select, sqlExceptSelect);
	}
	
	public SystemAdmin getByUserName(String userName) {
		String sql = "select * from " + SystemAdmin.TABLE + " where user_name = ?";
		SystemAdmin admin = dao.findFirst(sql, userName);
		return admin;
	}
	
	public Set<String> findPermissions(String userName) {
		SystemAdmin user = getByUserName(userName);
		if(user!=null){
			String sql = "select r.key from "+SystemResource.TABLE+" r,"+SystemRoleResource.TABLE+" rr,"+SystemRole.TABLE+" ro where r.id = rr.resource_id and rr.role_id = ro.id and ro.rname=?";
			List<SystemResource>  list = SystemResource.dao.find(sql,user.getRole());
			Set<String> set = new HashSet<String>();
			for (SystemResource res : list) {
				set.add(res.getKey());
			}
			return set;
		}
		return null;
	}
	 
	public List<SystemAdmin> getAllUser(){
		String sql = "select * from " + SystemAdmin.TABLE + " where role != 'admin'";
		List<SystemAdmin> list = dao.find(sql);
		return list;
	}
	public  List<SystemAdmin> getAdminList(){
		
		List<SystemAdmin> adminList = SystemAdmin.dao.find("select * from "+SystemAdmin.TABLE);
		
		return adminList;
	}
}
