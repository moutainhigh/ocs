package com.rong.business.service;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.RechargeSet;

/**
 * 充值业务接口
 * @author Wenqiang-Rong
 * @date 2018年1月31日
 */
public interface RechargeSetService extends BaseService<RechargeSet>{
	Page<RechargeSet> page(int page,int pageSize);
	boolean setEnable(long id, boolean isEnable);
	boolean save(int rechargeMoney,int give,String remark);
	int giveMoney(int money);
}