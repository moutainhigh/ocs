package com.rong.system.service;

import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.model.SystemAdmin;
/**
 * 系统用户服务层接口
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface SystemAdminService {
	public Page<SystemAdmin> getList(int page,int pagesize);
	public SystemAdmin getByUserName(String username);
	public Set<String> findPermissions(String username);
	public List<SystemAdmin> getAllUser();
	public List<SystemAdmin> getUserList();
	public boolean delete(long id);
	public boolean backPassword(long id);
}
 