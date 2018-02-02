package com.rong.business.service;
import java.math.BigDecimal;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Recharge;

/**
 * 充值接口
 * @author Wenqiang-Rong
 * @date 2018年2月1日
 */
public interface RechargeService extends BaseService<Recharge>{
	boolean save(String userName,int type,BigDecimal money,String orderCode,String remark);
	Page<Recharge> page(int pageNumber, int pageSize, Kv param);
}