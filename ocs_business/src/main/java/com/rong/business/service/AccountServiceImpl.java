package com.rong.business.service;

import java.util.Date;

import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AccountDao;
import com.rong.persist.model.Account;

/**
 * 账户业务实现类
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public class AccountServiceImpl extends BaseServiceImpl<Account> implements AccountService{
	private AccountDao dao = new AccountDao();

	@Override
	public boolean save(String userName) {
		Account item = new Account();
		item.setCreateTime(new Date());
		item.setUserName(userName);
		return dao.save(item);
	}

	@Override
	public Account findByUserName(String userName) {
		return dao.findByUserName(userName);
	}
	
}
