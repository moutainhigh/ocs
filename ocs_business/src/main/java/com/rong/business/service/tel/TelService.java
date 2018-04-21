package com.rong.business.service.tel;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.rong.common.bean.MyPage;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Tel;

/**
 * 手机号码接口
 * @author Wenqiang-Rong
 * @date 2018年4月20日
 */
public interface TelService extends BaseService<Tel>{
	MyPage page(int pageNumber, int pageSize, String tel,Kv param);
	boolean updateTel(String tel,String platform,String alipayName,String qqNickName,String sex,Date age,String addr);
	Tel findTel(String tel);
	@SuppressWarnings("rawtypes")
	List list(int limit, int offset, String tel,Kv param) ;
}