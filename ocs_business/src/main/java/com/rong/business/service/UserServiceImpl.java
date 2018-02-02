package com.rong.business.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.bean.MyConst;
import com.rong.common.util.CommonUtil;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.UserDao;
import com.rong.persist.model.User;

/**
 * 会员用户业务实现类
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	private UserDao dao = new UserDao();
	@Override
	public Page<Record> getUserList(int page,int pagesize,Boolean state) {
		Kv param = Kv.by("state", state);
		return dao.page(page,pagesize,param);
	}
	
	@Override
	public boolean setEnable(long id, boolean isEnable){
		return dao.updateField(id, "state", isEnable ? MyConst.USERSTATUS_ENABLE : MyConst.USERSTATUS_DISABLE);
	}
	
	@Override
	public boolean resetPwd(long id, String pwd) {
		return dao.updateField(id, "user_pwd", pwd);
	}

	@Override
	public User findByUserName(String userName) {
		return dao.findByUserName(userName);
	}

	@Override
	public User findByUserNameAndPwd(String userName, String pwd) {
		pwd = CommonUtil.getMD5(pwd);
		return dao.findByUserNameAndPwd(userName, pwd);
	}
}
