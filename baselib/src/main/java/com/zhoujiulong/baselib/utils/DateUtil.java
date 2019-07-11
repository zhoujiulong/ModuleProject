package com.zhoujiulong.baselib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    public static long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }

    /**
     * 格式化12小时制<br>
     * 格式：yyyy-MM-dd hh-mm-ss
     *
     * @param time 时间
     */
    public static String format12Time(long time) {
        return format(time, "yyyy-MM-dd hh:mm:ss");
    }

    /**
     * 格式化24小时制<br>
     * 格式：yyyy-MM-dd HH-mm-ss
     *
     * @param time 时间
     */
    public static String format24Time(long time) {
        return format(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化到天<br>
     * 格式：yyyy-MM-dd
     *
     * @param time 时间
     */
    public static String formatDay(long time) {
        return format(time, "yyyy-MM-dd");
    }

    /**
     * 格式化时间,自定义标签
     *
     * @param time    时间
     * @param pattern 格式化时间用的标签
     */
    public static String format(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        return sdf.format(new Date(time));
    }

    /**
     * 获取当前天
     *
     * @return 天
     */
    @SuppressWarnings("static-access")
    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.DAY_OF_MONTH;
    }

    /**
     * 获取当前月
     *
     * @return 月
     */
    @SuppressWarnings("static-access")
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.MONTH;
    }

    /**
     * 获取当前时
     */
    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前年
     *
     * @return 年
     */
    @SuppressWarnings("static-access")
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.YEAR;
    }

    /**
     * 获取当前秒
     *
     * @return 秒
     */
    @SuppressWarnings("static-access")
    public static int getCurrentSecond() {
        Calendar calendar = Calendar.getInstance();
        return calendar.SECOND;
    }

    /**
     * 根据时间格式返回时间戳
     */
    @SuppressWarnings("static-access")
    public static long getCurrentTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        long date = 0;
        try {
            date = dateFormat.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据时间格式返回年月
     */
    public static String getYearMonth(String time) {
        String timeString = "--";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        long date;
        try {
            date = dateFormat.parse(time).getTime();
            timeString = date <= 0 ? "--" : format(date, "yyyy-MM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }

    /**
     * 截取日期保留年月日
     */
    public static String subDate(String time) {
        try {
            return time.substring(0, 10);
        } catch (Exception e) {
            return "--";
        }
    }

    /**
     * 根据时间格式返回年月
     */
    public static String getYearMonth1(String time) {
        String timeString = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        long date;
        try {
            date = dateFormat.parse(time).getTime();
            timeString = date <= 0 ? null : format(date, "yyyy-MM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }

    /**
     * 判断2个时间是不是同一天
     */
    public static boolean areSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }


    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime, boolean showHourIfZero) {
        return getCountTimeByLong(finishTime, ":", ":", "", showHourIfZero);
    }

    //毫秒换成00:00:00
    public static String getCountTimeByLong(long finishTime, String hourStr, String minStr, String secondStr, boolean showHourIfZero) {
        int totalTime = (int) (finishTime / 1000);//秒
        int hour = 0, minute = 0, second = 0;

        if (3600 <= totalTime) {
            hour = totalTime / 3600;
            totalTime = totalTime - 3600 * hour;
        }
        if (60 <= totalTime) {
            minute = totalTime / 60;
            totalTime = totalTime - 60 * minute;
        }
        if (0 <= totalTime) {
            second = totalTime;
        }
        StringBuilder sb = new StringBuilder();

        if (hour != 0 || showHourIfZero) {
            if (hour < 10) {
                sb.append("0").append(hour).append(hourStr);
            } else {
                sb.append(hour).append(hourStr);
            }
        }
        if (minute < 10) {
            sb.append("0").append(minute).append(minStr);
        } else {
            sb.append(minute).append(minStr);
        }
        if (second < 10) {
            sb.append("0").append(second).append(secondStr);
        } else {
            sb.append(second).append(secondStr);
        }
        return sb.toString();

    }

    /**
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day).append("天");
        }
        if (hour > 0) {
            sb.append(hour).append("小时");
        }
        if (minute > 0) {
            sb.append(minute).append("分");
        }
        if (second > 0) {
            sb.append(second).append("秒");
        }
        if (milliSecond > 0) {
            sb.append(milliSecond).append("毫秒");
        }
        return sb.toString();
    }

    /**
     * 匹配字符串是否是时间格式 "20xx-xx-xx"
     */
    public static boolean valiDateTimeWithLongFormat(String timeStr) {
        String format = "((20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(timeStr);
        return matcher.matches();
    }

    /**
     * 将字符串转换为时间戳
     *
     * @param time xxxx-xx-xx
     * @return long
     */
    public static Long changeTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try {
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 秒转化时分秒
     */
    public static String formatTimeS(Long ms) {
        Integer ss = 1;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        long minute = (ms) / mi;
        long second = (ms - minute * mi) / ss;


        StringBuffer sb = new StringBuffer();

        if (minute > 0) {
            sb.append(minute).append("分");
        }
        if (second > 0) {
            sb.append(second).append("秒");
        }

        return sb.toString();
    }

    /**
     * 获取前n天日期、后n天日期
     * 格式：yyyy-MM-dd
     *
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     */
    public static String getBeforeOrAfterDate(int distanceDay, String pattern) {
        SimpleDateFormat dft = new SimpleDateFormat(pattern, Locale.CHINA);
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    /**
     * 根据当前日期获得是星期几
     * 格式：yyyy-MM-dd
     */
    public static String getWeek(String time, String pattern) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int wek = c.get(Calendar.DAY_OF_WEEK);
        if (wek == 1) {
            Week += "周天";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }
}























