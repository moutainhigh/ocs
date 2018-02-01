package com.rong.persist.dao;

import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.persist.base.BaseDao;

/**
 * 日志dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class logDao extends BaseDao<Record>{
	public Page<Record> list(int pageNumber, int pageSize, Map<String, Object> parMap) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");

		if (parMap != null) {
			if (parMap.containsKey("log_from")) {
				sqlWhere.append(" and log_from = '").append(parMap.get("logFrom")).append("'");

			}
			if (parMap.containsKey("userId")) {
				sqlWhere.append(" and user_id = '").append(parMap.get("userId")).append("'");

			}
			if (parMap.containsKey("logLevel")) {
				sqlWhere.append(" and log_level = '").append(parMap.get("logLevel")).append("'");

			}
			if (parMap.containsKey("method")) {
				sqlWhere.append(" and method = '").append(parMap.get("method")).append("'");

			}
			if (parMap.containsKey("msg")) {
				sqlWhere.append(" and msg like '%").append(parMap.get("msg")).append("%'");

			}
			if (parMap.containsKey("datetimeStart")) {
				sqlWhere.append(" and create_time >= '").append(parMap.get("datetimeStart")).append("'");

			}
			if (parMap.containsKey("datetimeEnd")) {
				sqlWhere.append(" and create_time <= '").append(parMap.get("datetimeEnd")).append("'");

			}
		}

		String sqlExceptSelect = "from log_" + parMap.get("nowDate").toString() + sqlWhere.toString()
				+ " order by create_time desc";
		Page<Record> logList = Db.use("log").paginate(pageNumber, pageSize, sqlSelect, sqlExceptSelect);

		return logList;
	}
}
