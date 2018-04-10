package com.rong.persist.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Consume;

/**
 * 消费记录dao
 * @author Wenqiang-Rong
 * @date 2018年4月10日
 */
public class ConsumeDao extends BaseDao<Consume> {

	public static final Consume dao = Consume.dao;

	public static final String FILEDS = "id,create_time,project_id,project_name,user_name,money";

	public Page<Consume> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Consume.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String projectName = param.getStr("projectName");
			if (!StringUtils.isNullOrEmpty(projectName)) {
				where.append(" and project_name = '" + projectName + "'");
			}
			String userName = param.getStr("userName");
			if (!StringUtils.isNullOrEmpty(userName)) {
				where.append(" and user_name = '" + userName + "'");
			}
			String date = param.getStr("date");
			if (!StringUtils.isNullOrEmpty(date)) {
				where.append(" and to_days(create_time) = to_days('"+date+"')");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public boolean save(BigDecimal money,String userName,Long projectId,String projectName) {
		Consume consume = new Consume();
		consume.setCreateTime(new Date());
		consume.setMoney(money);
		consume.setProjectId(projectId);
		consume.setProjectName(projectName);
		consume.setUserName(userName);
		return consume.save();
	}
}
