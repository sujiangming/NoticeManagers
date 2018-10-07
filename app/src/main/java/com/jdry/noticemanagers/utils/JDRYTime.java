package com.jdry.noticemanagers.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by jdry on 2016/9/9.
 * jdry 工具类
 */
public class JDRYTime {
    public static SimpleDateFormat ymd = new SimpleDateFormat("yyyy.MM.dd");
    public static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    public static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static GregorianCalendar gc = new GregorianCalendar();
    public final static long oneDay = 24 * 3600000;

    public static String getStrDate() {
        return ymd.format(new Date());
    }

    public static String getStrDate(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    public static Date getDate(String date) {
        Date d = new Date();
        try {
            d = ymd.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static Date getFullDate(String date) {
        Date d = new Date();
        try {
            d = ymdhms.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static String addDay(int n, String date) {
        String str = "";
        try {
            Date d = ymd.parse(date);
            gc.setTime(d);
            gc.add(Calendar.DATE, n);
            str = ymd.format(gc.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static String getFullTimeBySec(long sec) {
        Date d = new Date(sec);
        return ymdhms.format(d);
    }

    public static String getDayHourMinBySec(long sec) {
        Date d = new Date(sec);
        return ymdhm.format(d);
    }

    public static String getDayBySec(long sec) {
        Date d = new Date(sec);
        return ymd.format(d);
    }

    public static String getHourDateBySec(long sec) {
        Date d = new Date(sec);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH");
        String strDate = sdf.format(d);
        String[] str = strDate.split(" ");
        return str[1];
    }

    public static int getDifferDay(Date startDate, Date endDate) {
        long differ = endDate.getTime() - startDate.getTime();
        int ret = (int) (differ / oneDay);
        return ret + 1;
    }

    public static Date addDay(Date date, int day) {
        Date newDate = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);//把日期往后增加一天.整数往后推,负数往前移动
        newDate = calendar.getTime();   //这个时间就是日期往后推一天的结果
        return newDate;
    }

    /**
     * 将时间格式的字符串变成Date类型
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Date transformString2Date(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //将long类型的时间转换成string类型的时间
    public static String transferLongToDate(Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    //将long类型的时间转换成string类型的时间
    public static String transferLongToString(Long millSec, String partern) {
        if (null == millSec) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(partern);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    //获取当前的时间戳
    public static long getNowTime() {
        Date date = new Date();
        return date.getTime();
    }

    //将string类型的时间转换成long类型的时间
    public static long formateTime(String str) {
        Date date = null;
        long time = 0L;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(str);
            time = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    //时间比较
    public static long compareTime(long longTime) {
        Date date = new Date();
        long time = date.getTime();
        long result = longTime - time;
        return result;
    }

    public static String splitDateStr(String dateStr) {
        if (null == dateStr || "".equals(dateStr)) return "";
        return dateStr.split(" ")[0].replace("-", ".");
    }

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }
}
