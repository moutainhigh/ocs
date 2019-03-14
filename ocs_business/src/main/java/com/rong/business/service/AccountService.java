package com.rong.business.service;
import java.math.BigDecimal;

import com.rong.persist.base.BaseService;
import com.rong.persist.model.Account;

/**
 * 账户接口
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public interface AccountService extends BaseService<Account>{
	boolean save(String userName);
	Account findByUserName(String userName);
	boolean updateUserAccount(String userName,BigDecimal money);
}