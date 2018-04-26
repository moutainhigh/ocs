package com.rong.persist.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/****
 * @Project_Name:	ocs_persist
 * @Copyright:		Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version:		1.0.0.1
 * @File_Name:		TelCreateTask.java
 * @CreateDate:		2018年4月20日 上午11:10:52
 * @Designer:		Wenqiang-Rong
 * @Desc:			
 * @ModifyHistory:	
 ****/

class TelCreateTask implements Runnable {
    private int taskNum;
     
    public TelCreateTask(int num) {
        this.taskNum = num;
    }
     
    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        long startTime = System.currentTimeMillis();
        Tel tel = null;
        try {
        	tel = TelCreateUtil.telQueue.take();
        	createTel(tel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("task "+taskNum+"执行完毕,"+tel.getTel() + "生成数据耗时：" + TelCreateUtil.formatTime(endTime - startTime));
    }
    
    /**
	 * 已知号码段，生成剩余手机号码
	 * 如：号码段为1892245 ，第一个号码为：18922450001
	 * 生成9999个手机号码，并保存
	 * @param tel
	 * @param province
	 * @param city
	 * @param areaCode
	 * @param operator
	 */
	public void createTel(Tel tel){
		String tableName = "tel_"+tel.getTel().substring(0,4);
		List<Record> recordList = new ArrayList<Record>();
		for (int i=1;i<10000;i++) {
			String val = "";
			if(i<10){
				val = "000"+i;
			}else if(i>=10 &&i<100){
				val = "00"+i;
			}else if(i>=100 &&i<1000){
				val = "0"+i;
			}else{
				val = String.valueOf(i);
			}
			Record record = new Record();
			record.set("create_time", new Date());
			record.set("tel", tel.getTel()+val);
			record.set("tel_province", tel.getTel_province());
			record.set("tel_city", tel.getTel_city());
			record.set("tel_area_code", tel.getTel_area_code());
			record.set("tel_operator", tel.getTel_operator());
			recordList.add(record);
		}
		Db.batchSave(tableName, recordList, 10000);
	}
}


