package com.rong.persist.dao;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Notice;

/**
 * 软件广告dao
 * @author Wenqiang-Rong
 * @date 2018年3月7日
 */
public class NoticeDao extends BaseDao<Notice> {
	public static final Notice dao = Notice.dao;
	public static final String FILEDS = "id,create_time,update_time,enable,content,software_code,software_name";
	
	public Page<Notice> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + Notice.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		if(param!=null){
			String softwareCode = param.getStr("softwareCode");
			if (!StringUtils.isNullOrEmpty(softwareCode)) {
				where.append(" and software_code = '" + softwareCode + "'");
			}
			String content = param.getStr("content");
			if (!StringUtils.isNullOrEmpty(content)) {
				where.append(" and content like '%" + content + "%'");
			}
			Boolean enable = param.getBoolean("enable");
			if (!StringUtils.isNullOrEmpty(enable)) {
				where.append(" and enable = " + enable + "");
			}
		}
		String orderBy = " order by id desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public boolean save(String content,String softwareCode,String softwareName,Boolean enable){
		Notice item = new Notice();
		item.setCreateTime(new Date());
		item.setContent(content);
		item.setSoftwareCode(softwareCode);
		item.setSoftwareName(softwareName);
		item.setEnable(enable);
		return save(item);
	}
	
	public Notice findBySoftwareCode(String softwareCode){
		String sql = "select " + FILEDS + " from " + Notice.TABLE + " where software_code = ? and enable = ?";
		return dao.findFirst(sql, softwareCode,true);
	}
}
