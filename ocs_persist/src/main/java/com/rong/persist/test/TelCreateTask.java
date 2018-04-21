package com.rong.persist.test;

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
        long endTime  = 0L;
        Tel tel = null;
        try {
        	tel = TelCreateUtil.telQueue.take();
			TelCreateUtil.createTel(tel);
			System.out.println();
    		endTime = System.currentTimeMillis();
            Thread.currentThread();
			Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕,"+tel.getTel() + "创建数据耗时：" + TelCreateUtil.formatTime(endTime - startTime));
    }
}


