/*************************************************************************************
* Module Name:com.zwy.demo
* File Name:LoggerWriter.java
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
package com.zhangwy.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

/**
 * Author: zhangwy
 * 创建时间：2015年9月17日 下午3:51:03
 * 修改时间：2015年9月17日 下午3:51:03
 * Description: log日志输出至文本
 **/
public class LoggerWriter {

	private static LoggerWriter mLogWriter;
	private Writer mWriter;

	private LoggerWriter() {
	}

	public static LoggerWriter getInstance(){
		if (mLogWriter == null) {
			synchronized (LoggerWriter.class) {
				if (mLogWriter == null){
					mLogWriter = new LoggerWriter();
				}
			}
		}
		return mLogWriter;
	}

	public void open(Context ctx) throws IOException {
		File dirFile = Environment.getExternalStorageDirectory();
		File file = new File(dirFile, ctx.getApplicationInfo().name);
		mWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath()), 2048);
	}

	public void close() throws Exception{
		if (mWriter == null)
			return;
		mWriter.close();
		mWriter = null;
	}

	public void print(String type, String tag, String msg) throws Exception {
		if (mWriter == null)
			return;
		mWriter.write(getCurrentSQLDateTimeString());
		if (!TextUtils.isEmpty(type))
			mWriter.write("		" + type);
		if(!TextUtils.isEmpty(tag))
			mWriter.write("		" + tag);
		mWriter.write("		"+msg + "\n");
	}

	public String getCurrentSQLDateTimeString() {
		return getSQLDateTimeString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	@SuppressLint("SimpleDateFormat") 
	public String getSQLDateTimeString(Date dt, String pattern) {
		SimpleDateFormat date = new SimpleDateFormat(pattern);
		if (dt == null)
			dt = new Date();
		return date.format(dt);
	}
}