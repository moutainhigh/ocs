package com.rong.system.service;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.rong.persist.dao.SystemRoleDao;
import com.rong.persist.model.SystemResource;
import com.rong.persist.model.SystemRole;

/**
 * 用户角色管理服务层实现层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class SystemRoleServiceImpl implements SystemRoleService{
	private SystemRoleDao dao = new SystemRoleDao();
	
	@Override
	public boolean save(SystemRole role) {
		return role.save();
	}

	@Override
	@Before(Tx.class)
	public boolean update(long id, String name, String remark) {
		SystemRole role = SystemRole.dao.findById(id);
		if(name!=null){
			role.setRname(name);
		}
		if(remark!=null){
			role.setRemark(remark);
		}
		return role.update();
	}

	@Override
	public SystemRole getById(long id) {
		return SystemRole.dao.findById(id);
	}

	@Override
	public List<SystemRole> getList() {
		return  dao.getList();
	}

	@Override
	public boolean delete(long id) {
		return SystemRole.dao.deleteById(id);
	}

	@Override
	public SystemRole getByName(String name) {
		return  dao.getByName(name);
	}

	@Override
	public List<SystemResource> getPermissionsByRoleId(long roleId) {
		return dao.getRolePermissions(roleId);
	}

	@Override
	public boolean saveRolePermissions(long roleId,List<Long> permissionsIds) {
		return dao.saveRolePermissions(roleId, permissionsIds);
	}
	
	@Override
	public boolean isExistUserByRname(String rname) {
		return dao.isExistUserByRname(rname);
	}

	@Override
	public List<String> getRolesList() {
		return dao.getRolesList();
	}
}

