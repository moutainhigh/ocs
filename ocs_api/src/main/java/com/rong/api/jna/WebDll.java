package com.rong.api.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/****
 * @Project_Name: ocs_api
 * @Copyright: Copyright © 2012-2018 G-emall Technology Co.,Ltd
 * @Version: 1.0.0.1
 * @File_Name: TokenDLL.java
 * @CreateDate: 2018年2月2日 下午3:51:56
 * @Designer: Wenqiang-Rong
 * @Desc: 调用dll获取token
 * @ModifyHistory:
 ****/

public interface WebDll extends Library {
	// WebDll为dll名称,WebDll目录的位置为:C:\Windows\System32下面,
	WebDll Instance = (WebDll) Native.loadLibrary((Platform.isWindows() ? "webdll" : "webdll"), WebDll.class);
	//dll导出函数名字是enc(char*,int len)
//	{"data":"11111","time":"2222","cmd":"3333"}
	String enc(String str,int len);
}
