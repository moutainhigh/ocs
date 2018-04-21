package com.rong.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class TxtExportUtil {
	public static boolean writeTxtFile(String content, File file) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			o.write(content.getBytes("GBK"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(File file) throws Exception {
		boolean flag = false;
		try {
			if (!file.exists()) {
				file.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 */
	public static void readFileByLines(int startLine,String fileName) {
		File file = new File(fileName);
		InputStreamReader read = null;    
		BufferedReader reader = null;
		try {
			read = new InputStreamReader(new FileInputStream(file),"gbk");   
			reader = new BufferedReader(read);
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				//业务逻辑
				if(line>=startLine){
					System.out.println(line+":"+tempString);
				}
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {

				}
			}
		}
	}
	
	public static void main(String[] args) {
		readFileByLines(10,"E:\\test.txt");
	}
}
