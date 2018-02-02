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

public interface TokenDLL extends Library {
	// msvcrt为dll名称,msvcrt目录的位置为:C:\Windows\System32下面,
	TokenDLL Instance = (TokenDLL) Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), TokenDLL.class);
	// printf为msvcrt.dll中的一个方法.
	String getToken(String str);
}
