package com.rong.system.service;

import java.util.List;

import com.rong.persist.model.SystemResource;
import com.rong.persist.model.SystemRole;

/**
 * 用户角色管理服务层接口
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface SystemRoleService {
	public boolean save(SystemRole role);
	public boolean update(long id,String name,String remark);
	public boolean delete(long id);
	public SystemRole getById(long id);
	public SystemRole getByName(String name);
	public List<SystemRole> getList();
	public List<SystemResource> getPermissionsByRoleId(long roleId);
	public boolean saveRolePermissions(long roleId,List<Long> permissionsIds);
	public boolean isExistUserByRname(String rname);
	public List<String> getRolesList();
}

