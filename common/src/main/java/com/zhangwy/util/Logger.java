package com.zhangwy.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Author: zhangwy(张维亚)
 * 创建时间：2017/4/6 上午10:46
 * 修改时间：2017/4/6 上午10:46
 * Description:
 */

public class Logger {

    private static final String Tag = "logger";

    public static final int LEVEL_ASSERT = Log.ASSERT;//7
    public static final int LEVEL_ERROR = Log.ERROR;//6
    public static final int LEVEL_WARN = Log.WARN;//5
    public static final int LEVEL_INFO = Log.INFO;//4
    public static final int LEVEL_DEBUG = Log.DEBUG;//3
    public static final int LEVEL_VERBOSE = Log.VERBOSE;//2

    private static boolean PRINT_LOG = true;
    private static final boolean PRINT_LOG_2FILE = true;
    private static int level = PRINT_LOG ? LEVEL_VERBOSE : LEVEL_ASSERT;

    public static void setLevel(int level) {
        Logger.level = level;
    }

    public static void e(String msg) {
        e(Tag, msg);
    }

    public static void e(String msg, Throwable e) {
        e(Tag, msg, e);
    }

    public static void e(String tag, String msg) {
        if (printLog(LEVEL_ERROR, msg)) {
            Log.e(tag, msg);
            loggerWriter("e", tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        final String eMsg = throwableMsg(msg, e);
        if (printLog(LEVEL_ERROR, eMsg)) {
            Log.e(tag, msg, e);
            loggerWriter("e", tag, e.getMessage());
        }
    }

    public static void w(String msg){
        w(Tag, msg);
    }

    public static void w(String msg, Throwable e) {
        w(Tag, msg, e);
    }

    public static void w(String tag, String msg) {
        if (printLog(LEVEL_WARN, msg)) {
            Log.w(tag, msg);
            loggerWriter("w", tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        final String eMsg = throwableMsg(msg, e);
        if (printLog(LEVEL_WARN, eMsg)) {
            Log.w(tag, msg, e);
            loggerWriter("w", tag, e.getMessage());
        }
    }

    public static void i(String msg) {
        i(Tag, msg);
    }

    public static void i(String msg, Throwable e) {
        i(Tag, msg, e);
    }

    public static void i(String tag, String msg) {
        if (printLog(LEVEL_INFO, msg)) {
            Log.i(tag, msg);
            loggerWriter("i", tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable e) {
        final String eMsg = throwableMsg(msg, e);
        if (printLog(LEVEL_INFO, eMsg)) {
            Log.i(tag, msg, e);
            loggerWriter("i", tag, e.getMessage());
        }
    }

    public static void d(String msg) {
        d(Tag, msg);
    }

    public static void d(String msg, Throwable e) {
        d(Tag, msg, e);
    }

    public static void d(String tag, String msg) {
        if (printLog(LEVEL_DEBUG, msg)) {
            Log.d(tag, msg);
            loggerWriter("d", tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable e) {
        final String eMsg = throwableMsg(msg, e);
        if (printLog(LEVEL_DEBUG, eMsg)) {
            Log.d(tag, msg, e);
            loggerWriter("d", tag, e.getMessage());
        }
    }

    public static void v(String msg) {
        v(Tag, msg);
    }

    public static void v(String msg, Throwable e) {
        v(Tag, msg, e);
    }

    public static void v(String tag, String msg) {
        if (printLog(LEVEL_VERBOSE, msg)) {
            Log.d(tag, msg);
            loggerWriter("v", tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable e) {
        final String eMsg = throwableMsg(msg, e);
        if (printLog(LEVEL_VERBOSE, eMsg)) {
            Log.v(tag, msg, e);
            loggerWriter("v", tag, e.getMessage());
        }
    }

    private static boolean printLog(int type, String msg) {
        return PRINT_LOG && (level <= type) && !TextUtils.isEmpty(msg);
    }

    private static String throwableMsg(String msg, Throwable e) {
        String text = "";
        if (!TextUtils.isEmpty(msg)) {
            text += msg;
            text += "		";
        }
        if (e != null) {
            text += e.getMessage();
        }
        return text;
    }


    private static void loggerWriter(String type, String tag, String msg){
        if (!PRINT_LOG_2FILE)
            return;
        try {
            LoggerWriter.getInstance().print(type, tag, msg);
        } catch (Exception e) {
            Log.d(tag, "loggerWriter", e);
        }
    }
}
