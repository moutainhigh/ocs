package com.rong.persist.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.AdTask;

/**
 * 广告任务dao
 * @author Wenqiang-Rong
 * @date 2018年4月3日
 */
public class AdTaskDao extends BaseDao<AdTask> {

	public static final AdTask dao = AdTask.dao;

	public static final String FILEDS = "id,create_time,update_time,content,project_id,back,moeny,count_call,order_code,state,user_name";

	public Page<AdTask> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + AdTask.TABLE ;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			Long projectId = param.getLong("projectId");
			if (projectId!=null) {
				where.append(" and project_id = " + projectId);
			}
			String content = param.getStr("content");
			if (!StringUtils.isNullOrEmpty(content)) {
				where.append(" and content like '%" + content + "%'");
			}
			String orderCode = param.getStr("orderCode");
			if (!StringUtils.isNullOrEmpty(orderCode)) {
				where.append(" and order_code = '" + orderCode + "'");
			}
			String userName = param.getStr("userName");
			if (!StringUtils.isNullOrEmpty(userName)) {
				where.append(" and user_name = '" + userName + "'");
			}
			String state = param.getStr("state");
			if (!StringUtils.isNullOrEmpty(state)) {
				where.append(" and state = " + state);
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public AdTask findByOrderCode(String orderCode){
		String sql = "select " + FILEDS + " from " + AdTask.TABLE + " where order_code = ? ";
		return dao.findFirst(sql, orderCode);
	}
	
	
	public AdTask Rand(){
		String sql = "select " + FILEDS + " from " + AdTask.TABLE + " where state == 1 order by RAND() limit 1;";
		AdTask item = dao.findFirst(sql);
		item.setState(2);
		boolean result = item.update();
		if(result){
			return item;
		}
		return null;
	}
	
	public boolean save(String userName,String content,Long projectId,Boolean back,BigDecimal moeny,Integer countCall) {
		AdTask model = new AdTask();
		model.setCreateTime(new Date());
		model.setContent(content);
		model.setProjectId(projectId);
		model.setCountCall(countCall);
		model.setBack(back);
		model.setMoeny(moeny);
		model.setUserName(userName);
		return save(model);
	}
}
