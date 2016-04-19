package com.common.androidcore.type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author lifan 创建于 2013年10月25日 下午4:05:07
 * @version Ver 1.0 2013年10月25日 改订
 *          日期工具类
 */
public class DateUtil {
    public static String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 根据给定的格式获取时间
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String formatDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * 根据给定的格式获取时间
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String formatDate(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 根据给定的格式获取时间
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String formatDate(String format, long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(time));
    }


    /**
     * 常用的时间格式
     *
     * @return yyyy年MM月dd日 这种类型的字符串
     */
    public static String getTime() {
        return formatDate("yyyy年MM月dd日");
    }

    /**
     * 常用的时间格式
     *
     * @return yyyy年MM月dd日 这种类型的字符串
     */
    public static String getTime(String format) {
        return formatDate(format);
    }

    /**
     * 通过指定的格式解析时间字符串
     *
     * @param format 时间格式
     * @param time   时间字符串
     * @return Date对象
     */
    public static Date parse(String format, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得明天时间
     *
     * @return 时间字符串
     */
    public static String getNextDate() {
        return getNextDate("yyyy-MM-dd");
    }

    /**
     * 获得明天时间
     *
     * @return 时间字符串
     */
    public static String getNextDate(String format) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * 获得昨天时间
     *
     * @return 时间字符串
     */
    public static String getYesterday() {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(date);
    }


    /**
     * 比较时间
     *
     * @param format   时间字符串格式
     * @param timeStr1 时间字符串1
     * @param timeStr2 时间字符串2
     * @return
     */
    public static long compareTime(String format, String timeStr1, String timeStr2) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        Date date1 = null, date2 = null;
        try {
            date1 = df.parse(timeStr1);
            date2 = df.parse(timeStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1 != null && date2 != null) {
            return date2.getTime() - date1.getTime();
        }
        return -1;
    }

    /**
     * 获取距离当前时间的毫秒数
     *
     * @param format  时间字符串格式
     * @param timeStr 时间字符串
     * @return 距离当前时间的毫秒数
     */
    public static long compareTimeFromNow(String format, String timeStr) {
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        Date date = null;
        try {
            date = df.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null) {
            return date.getTime() - System.currentTimeMillis();
        }
        return -1;
    }

    /**
     * 获取距离当前时间的毫秒数
     *
     * @param date 时间
     * @return 距离当前时间的毫秒数
     */
    public static long compareTimeFromNow(Date date) {
        if (date != null) {
            return date.getTime() - System.currentTimeMillis();
        }
        return -1;
    }

    /**
     * 获得星期
     *
     * @return 0-6几个数字，代表周日－周六
     */
    public static int getWeek() {
        return getWeek(new Date());
    }

    /**
     * 获得星期
     *
     * @return 0-6几个数字，代表周日－周六
     */
    public static int getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return w;
    }

    /**
     * 获得星期字符串
     *
     * @return 星期字符串
     */
    public static String getWeekStr(Date date) {
        int position = getWeek(date);
        return weeks[position];
    }


    /**
     * 获得星期字符串
     *
     * @return 星期字符串
     */
    public static String getWeekStr() {
        int position = getWeek();
        return weeks[position];
    }

    /**
     * 获得月份
     *
     * @return 月份
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }
}
