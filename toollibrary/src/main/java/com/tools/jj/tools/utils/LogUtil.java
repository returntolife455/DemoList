package com.tools.jj.tools.utils;

import android.util.Log;

/**
 * Created by jj on 2018/1/23.
 */

public class LogUtil {

    /**
     * true:打开log  false:关闭所有的日志
     */
    public static boolean OPEN_LOG = true;

    /**
     * true : 打开debug 日志  false:关闭debug日志
     */
    public static boolean DEBUG = true;
    /**
     * TAG 名称
     */
    private static String tag = "LogUtil";
    private String mClassName;
    private volatile static LogUtil log;
    private static final String USER_NAME = "@tool@";


    private LogUtil(String name) {
        mClassName = name;
    }

    /**
     * Get The Current Function Name
     *
     * @return Name
     */
    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return mClassName + "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " "
                    + st.getMethodName() + " ]";
        }
        return null;
    }

    public static void i(Object str) {
        print(Log.INFO, str);
    }

    public static void d(Object str) {
        print(Log.DEBUG, str);
    }

    public static void v(Object str) {
        print(Log.VERBOSE, str);
    }

    public static void w(Object str) {
        print(Log.WARN, str);
    }

    public static void e(Object str) {
        print(Log.ERROR, str);
    }

    /**
     * 用于区分不同接口数据 打印传入参数
     *
     * @param index
     * @param str
     */

    private static void print(int index, Object str) {
        if (!OPEN_LOG) {
            return;
        }
        //懒汉式单例模式,线程安全
        if (log == null) {
            synchronized (LogUtil.class){
                if(log==null){
                    log = new LogUtil(USER_NAME);
                }
            }
        }
        String name = log.getFunctionName();
        if (name != null) {
            str = name + " - " + str;
        }

        // Close the debug log When DEBUG is false
        if (!DEBUG) {
            if (index <= Log.DEBUG) {
                return;
            }
        }
        switch (index) {
            case Log.VERBOSE:
                Log.v(tag, str.toString());
                break;
            case Log.DEBUG:
                Log.d(tag, str.toString());
                break;
            case Log.INFO:
                Log.i(tag, str.toString());
                break;
            case Log.WARN:
                Log.w(tag, str.toString());
                break;
            case Log.ERROR:
                Log.e(tag, str.toString());
                break;
            default:
                break;
        }
    }
}
