package com.rong.common.util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class CommonUtil {

	/**
	 * 讲list转换成带,分隔的字符串
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	/**
	 * 重写线程等待方法
	 * @param time
	 */
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 默认等待2秒
	 */
	public static void sleep(){
		sleep(3000);
	}

	/**
	 * md5加密
	 * @param src
	 * @return
	 */
	public static String getMD5(String src) {
		StringBuffer sb = new StringBuffer();
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			for (byte b : md.digest())
				sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(
						Integer.toString(b & 0xF, 16));
		} catch (NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}
}
