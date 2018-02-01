package com.rong.system.service;

import java.util.List;
import java.util.Set;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.MyConst;
import com.rong.common.util.CommonUtil;
import com.rong.persist.dao.SystemAdminDao;
import com.rong.persist.model.SystemAdmin;

/**
 * 系统用户服务层实现
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class SystemAdminServiceImpl implements SystemAdminService {
	
	private SystemAdminDao dao = new SystemAdminDao();
	
	@Override
	public Page<SystemAdmin> getList(int page,int pagesize) {
		return dao.getList(page,pagesize);
	}
	
	@Override
	public SystemAdmin getByUserName(String username) {
		return dao.getByUserName(username);
	}

	@Override
	public Set<String> findPermissions(String username) {
		return dao.findPermissions(username);
	}

	@Override
	public List<SystemAdmin> getAllUser() {
		return dao.getAllUser();
	}
	@Override
	public List<SystemAdmin> getUserList() {
		return dao.getAdminList();
	}

	@Override
	public boolean delete(long id) {
		return dao.deleteById(id);
	}

	@Override
	public boolean backPassword(long id) {
		SystemAdmin admin = dao.findById(id);
		admin.setUserPassword(CommonUtil.getMD5(MyConst.DEFAULT_PASSWORD)+admin.getSalt());
		return dao.update(admin);
	}


}
