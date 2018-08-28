package com.tools.jj.tools.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author : zhouyx
 * Date   : 2016/02/03
 * 时间格式
 */
public class DateUtil {
    public static final String yyMMdd = "yy年MM月dd日";
    public static final String yyyyMMdd = "yyyy年MM月dd日";
    public static final String HHmmss = "HH:mm:ss";
    public static final String HHmm = "HH:mm";
    public static final String yyMMddHHmmss = "yy年MM月dd日 HH:mm:ss";
    public static final String yy_MM_ddHHmmss = "yy/MM/dd HH:mm:ss";
    public static final String yyyyMMddHHmm = "yyyy年MM月dd日 HH:mm";
    public static final String yyyyMMddHHmmss = "yyyy年MM月dd日 HH:mm:ss";
    public static final String MMddHHmm = "MM月dd日 hh:mm aa";
    public static final String MMdd = "MM月dd日";
    public static final String yyyyMM = "yyyy年MM";
    public static final String yyyy_MM_ddHHmm = "yyyy-MM-dd HH:mm";
    public static final String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyxMMxddHHmmss = "yyyy/MM/dd HH:mm:ss";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyyxMMxdd = "yyyy/MM/dd";
    public static final String yyyy_MM_dd_T_HH_mm_ss_fffZ = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static final String M_D = "M-d";

    public static Date parseToDate(String s, String style) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        if (s == null || s.length() < 5) {
            return null;
        }
        try {
            Date date = simpleDateFormat.parse(s);
            if (date != null) {
                return date;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long parseToLong(String s, String style) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        if (s == null || s.length() < 5) {
            return 0;
        }
        try {
            Date date = simpleDateFormat.parse(s);
            if (date != null) {
                return date.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String parseToyyyymmddhhmmss(long curentTime) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(curentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(now.getTime());
        return str;
    }

    public static Date parseToDate(String sTime) {
        if (sTime == null || "".equals(sTime)) {
            return null;
        }
        try {
            return Timestamp.valueOf(sTime);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseToString(String s, String fromStyle, String toStyle) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        String str = "";
        if (s == null || s.length() < 8) {
            return null;
        }
        try {
            simpleDateFormat.applyPattern(fromStyle);
            Date date = simpleDateFormat.parse(s);
            simpleDateFormat.applyPattern(toStyle);
            str = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseToString(Date date, String style) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        return simpleDateFormat.format(date);
    }

    public static String getNowTime(String style) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        return simpleDateFormat.format(now.getTime());
    }

    public static String parseToString(long time, String style) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(time);
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        return simpleDateFormat.format(now.getTime());
    }

    public static String getNextDate(long time, int i, String style) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(time));
        now.add(Calendar.DAY_OF_MONTH, +(i));
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        return simpleDateFormat.format(now.getTime());
    }

    public static String getNextTime(long time, int i, String style) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date(time));
        now.add(Calendar.MINUTE, +(i));
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance();
        simpleDateFormat.applyPattern(style);
        return simpleDateFormat.format(now.getTime());
    }

    public static String getTimeDiffer(long time, String style) {
        // 这样得到的差值是微秒级别
        long t = new Date(System.currentTimeMillis()).getTime() - new Date(time).getTime();
        // 是否大于一天
        if (t < 1000 * 60 * 60 * 24) {
            // 是否大于一小时
            t = t / 1000 / 60;
            if (t > 60) {
                return t / 60 + "小时前";
            } else {
                return t + "分钟前";
            }
        } else {
            return parseToString(time, style);
        }
    }


    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日",
                "周一",
                "周二",
                "周三",
                "周四",
                "周五",
                "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static int getBetweenDays(long beginTime, long endTime) {
        return (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));
    }


    public static String getTimeDiffer(long time) {
        // 这样得到的差值是微秒级别
        long t = new Date(System.currentTimeMillis()).getTime() - new Date(time).getTime();
        // 是否大于一天
        if (t < 1000 * 60 * 60 * 24) {
            return parseToString(time, HHmm);
        } else {
            return parseToString(time, M_D);
        }
    }

    public static boolean isAnotherDay(long time) {
        // 这样得到的差值是微秒级别
        long t = new Date(System.currentTimeMillis()).getTime() - new Date(time).getTime();
        // 是否大于一天
        if (t < 1000 * 60 * 60 * 24) {
            return false;
        } else {
            return true;
        }
    }
}