package com.rong.persist.dao;

import java.util.Date;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.AdTaskDetail;

/**
 * 广告任务详情dao
 * @author Wenqiang-Rong
 * @date 2018年4月3日
 */
public class AdTaskDetailDao extends BaseDao<AdTaskDetail> {

	public static final AdTaskDetail dao = AdTaskDetail.dao;

	public static final String FILEDS = "id,create_time,qq_group_name,qq_group_no,qq,order_code";

	public Page<AdTaskDetail> page(int pageNumber, int pageSize, String orderCode) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + AdTaskDetail.TABLE ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if (!StringUtils.isNullOrEmpty(orderCode)) {
			where.append(" and order_code = '" + orderCode + "'");
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public boolean save(String orderCode,String qqGroupName,String qqGroupNo,String qq) {
		AdTaskDetail model = new AdTaskDetail();
		model.setCreateTime(new Date());
		model.setQqGroupName(qqGroupName);
		model.setQqGroupNo(qqGroupNo);
		model.setQq(qq);
		model.setOrderCode(orderCode);
		return save(model);
	}
}
