package com.rong.api.jna;

import java.util.Properties;

/****
 * @Project_Name: ocs_api
 * @Copyright: Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version: 1.0.0.1
 * @File_Name: WebDllTest.java
 * @CreateDate: 2018年2月3日 下午2:21:36
 * @Designer: Wenqiang-Rong
 * @Desc:
 * @ModifyHistory:
 ****/

public class WebDllTest {
	public static void main(String[] args) {
		Properties props = System.getProperties();
		String bits = String.valueOf(props.get("sun.arch.data.model"));
		System.out.println("系统位数：" + bits);
		String str = "{\"cmd\":\"login\",\"data\":\"eyJpbWVpbWFjTWQ1IjoiZFZrdjAwM3JUWVpGellmd3U4Q1lIdz09IiwibXNnY29va2llcyI6NDEwODk0MTA5MCwicXEzMiI6MTg4MDA2MDUyLCJxcXNlcSI6MTAwMTgsInNlc3Npb25LZXkiOiJKVHA3ZFdjcExEWjdhU3RKUUZoSVVRPT0iLCJ0b2tlbjAwMkMiOiJQUnpNeUI2a0gwMFR0cVV6ZGN4enRMNGJxTDhBdHY2Um1Mc20zaVd5R1NFbkdXWDhocHFxY1BMT0JHRTFBQXJXOW43VlJBc1pYSXR0bC9RY28rRFhpQT09IiwidG9rZW4wMDRDIjoiaDJpRVpNVDhwclcrMFhPZFlVYjZERlFZRDVScXQrekl6eEpRUk91ajBFYTJyNG5UZDRJVnkrcUtnbVBtMnA2TnhSMTd5OFkyUzJJYzBjLzJSRkd6VmEranpYM1ZxZEQvIn0=\",\"rand_req\":\"162613563\"}";
		System.out.println(WebDll.Instance.enc(str, str.length()));
	}
}
