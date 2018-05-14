package com.rong.persist.test;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/****
 * @Project_Name: ocs_persist
 * @Copyright: Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version: 1.0.0.1
 * @File_Name: TelCreateTask.java
 * @CreateDate: 2018年4月20日 上午11:10:52
 * @Designer: Wenqiang-Rong
 * @Desc:
 * @ModifyHistory:
 ****/

class TelTableSplitTask implements Runnable {
	private int taskNum;

	public TelTableSplitTask(int num) {
		this.taskNum = num;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		String table = "";
		try {
			table = TelTableSplitUtil.tableQueue.take();
			for (int i = 0; i < 10; i++) {
				long startTimeOne = System.currentTimeMillis();
				String newTable = table + i;
				String tel = newTable.split("_")[1];
				String selectSql = "select tel,tel_province,tel_city,tel_area_code,tel_operator,col1 platform_collection,alipay_name,qq_nickname,sex,age,addr,col2 register"
						+ " from " + table +" where tel like '"+tel+"%' and col1 = '3'";
				List<Record> findResult = Db.find(selectSql);
				int [] num = Db.batchSave(newTable, findResult, 100);
				long endTimeOne = System.currentTimeMillis();
				System.out.println("[" + newTable + "]插入"+num+"条数据总耗时：" + TelCreateUtil.formatTime(endTimeOne - startTimeOne));
			}
			long endTime = System.currentTimeMillis();
			System.out.println("任务ID："+taskNum+",复制数据总耗时：" + TelCreateUtil.formatTime(endTime - startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
