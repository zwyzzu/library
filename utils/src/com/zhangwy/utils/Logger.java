/*************************************************************************************
* Module Name:com.zwy.demo
* File Name:Log.java
* Author: 张维亚
* Copyright 2007-, Funshion Online Technologies Ltd.
* All Rights Reserved
* 版权 2007-，北京风行在线技术有限公司
* 所有版权保护
* This is UNPUBLISHED PROPRIETARY SOURCE CODE of Funshion Online Technologies Ltd.;
* the contents of this file may not be disclosed to third parties, copied or
* duplicated in any form, in whole or in part, without the prior written
* permission of Funshion Online Technologies Ltd.
* 这是北京风行在线技术有限公司未公开的私有源代码。本文件及相关内容未经风行在线技术有
* 限公司事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
***************************************************************************************/
package com.zhangwy.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Author: zhangwy
 * 创建时间：2015年9月17日 下午3:44:54
 * 修改时间：2015年9月17日 下午3:44:54
 * Description: log日志打印
 **/
public class Logger {

	public static boolean PRINT_LOG = true;
	public static boolean PRINT_LOG_2FILE = true;
	public static String Tag = "logger";

	public static void i(String tag, String msg) {
		if (printlog(msg)) {
			Log.i(tag, msg);
			printLog("i", tag, msg);
		}
	}

	public static void i(String msg) {
		if (printlog(msg)) {
			Log.i(Tag, msg);
			printLog("i", Tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (printlog(msg)) {
			Log.d(tag, msg);
			printLog("d", tag, msg);
		}
	}

	public static void d(String msg) {
		if (printlog(msg)) {
			Log.d(Tag, msg);
			printLog("d", Tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (printlog(msg)) {
			Log.e(tag, msg);
			printLog("e", tag, msg);
		}
	}

	public static void e(String msg) {
		if (printlog(msg)) {
			Log.e(Tag, msg);
			printLog("e", Tag, msg);
		}
	}

	public static void printLog(String type, String tag, String msg){
		if (!PRINT_LOG_2FILE)
			return;
		try {
			LoggerWriter.getInstance().print(type, tag, msg);
		} catch (Exception e) {
		}
	}

	private static boolean printlog(String msg) {
		return (PRINT_LOG) && !TextUtils.isEmpty(msg);
	}
}