package com.rong.common.bean;

import java.util.List;

/****
 * @Project_Name: ocs_common
 * @Copyright: Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version: 1.0.0.1
 * @File_Name: MyPage.java
 * @CreateDate: 2018年4月20日 下午3:38:30
 * @Designer: Wenqiang-Rong
 * @Desc:大数据自定义分页
 * @ModifyHistory:
 ****/

public class MyPage {
	@SuppressWarnings("rawtypes")
	public MyPage(int limit, int offset, List list) {
		this.limit = limit;
		this.offset = offset + limit;
		this.list = list;
	}
	
	@SuppressWarnings("rawtypes")
	public MyPage(int limit, int offset,int total, List list) {
		this.limit = limit;
		this.offset = offset + limit;
		this.list = list;
		this.total = total;
		this.rows = total/limit;
	}

	public MyPage() {

	}

	// 数量
	private int limit;
	// 页数
	private int rows;
	// 总条数
	private int total;
	// 剔除前N条数据
	private int offset;
	@SuppressWarnings("rawtypes")
	private List list;

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	public void setList(@SuppressWarnings("rawtypes") List list) {
		this.list = list;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
