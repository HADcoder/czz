package com.blackchicktech.healthdiet.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LiuYu
 */
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 默认日期格式
     */
    public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

    /**
     * 日期格式化
     */
    private static DateFormat dateFormat = null;

    /**
     * 时间格式化
     */
    private static DateFormat dateTimeFormat = null;

    private static DateFormat timeFormat = null;

    private static Calendar gregorianCalendar = null;

    static {

        dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
        timeFormat = new SimpleDateFormat(TIME_DEFAULT_FORMAT);
        gregorianCalendar = new GregorianCalendar();
    }

    /**
     * 日期格式化
     *
     * @param date yyyy-MM-dd
     * @return
     */
    public static Date formatDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            logger.error("getDateFormat error.", e);
        }
        return null;
    }

    /**
     * 时间格式化
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date formatDateTime(String date) {
        try {
            return dateTimeFormat.parse(date);
        } catch (ParseException e) {
            logger.error("getDateTimeFormat error.", e);
        }
        return null;
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param format 日期格式
     * @return
     */
    public static Date formatDate(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            logger.error("formatDate error.", e);
        }
        return null;
    }

    /**
     * 日期格式化 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 日期格式化 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        return dateTimeFormat.format(date);
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    public static String getTimeFormat(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param formatStr 格式类型
     * @return
     */
    public static String getDateFormat(Date date, String formatStr) {
        if (StringUtils.isNotBlank(formatStr)) {
            return new SimpleDateFormat(formatStr).format(date);
        }
        return null;
    }

    /**
     * 获取时间段的每一天
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 日期列表 yyyyMMdd
     */
    public static List<String> getEveryDay(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }

        // 格式化日期(yyyy-MM-dd)
        startDate = DateUtil.formatDate(DateUtil.getDateFormat(startDate));
        endDate = DateUtil.formatDate(DateUtil.getDateFormat(endDate));
        List<String> dates = new ArrayList<String>();
        gregorianCalendar.setTime(startDate);
        dates.add(getDateFormat(gregorianCalendar.getTime(), "yyyy-MM-dd"));
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(getDateFormat(gregorianCalendar.getTime(), "yyyy-MM-dd"));
        }
        return dates;
    }

    /**
     * 获取日期的每个小时
     *
     * @param date 计算日期
     * @return 时间列表 yyyyMMddHH
     */
    public static List<String> getEveryDayTime(Date date) {
        if (date == null) {
            return null;
        }

        // 格式化日期(yyyy-MM-dd)
        Date startDate = DateUtil.formatDate(DateUtil.getDateFormat(date, "yyyy-MM-dd ") + "00", "yyyy-MM-dd HH");
        Date endDate = DateUtil.formatDate(DateUtil.getDateFormat(date, "yyyy-MM-dd ") + "23", "yyyy-MM-dd HH");
        List<String> dates = new ArrayList<String>();
        gregorianCalendar.setTime(startDate);
        dates.add(getDateFormat(gregorianCalendar.getTime(), "yyyyMMddHH"));
        while (gregorianCalendar.getTime().compareTo(endDate) < 0) {
            // 加1小时
            gregorianCalendar.add(Calendar.HOUR_OF_DAY, 1);
            dates.add(getDateFormat(gregorianCalendar.getTime(), "yyyyMMddHH"));
        }
        return dates;
    }

    /**
     * 获取时间
     *
     * @param date 计算时间
     * @param num  计算数量  例如传入-1，即为上个小时
     * @return yyyyMMddHH
     */
    public static String getDayTime(Date date, int num) {
        gregorianCalendar.setTime(date);
        gregorianCalendar.add(Calendar.HOUR_OF_DAY, num);
        return getDateFormat(gregorianCalendar.getTime(), "yyyyMMddHH");
    }

    /**
     * 获取日期前N天
     *
     * @param date 计算日期
     * @param num  计算天数
     * @return
     */
    public static Date getDayBefore(Date date, int num) {
        gregorianCalendar.setTime(date);
        int day = gregorianCalendar.get(Calendar.DATE);
        gregorianCalendar.set(Calendar.DATE, day - num);
        return gregorianCalendar.getTime();
    }

    public static void main(String[] args) {
//        Date startDate = getDateFormat("2017-08-19");
//        Date endDate = getDateFormat("2017-09-19");
//        List<String> result = getEveryDay(startDate, endDate);
//        System.out.println(getDateFormat(getDayBefore(new Date(), -30)));
        List<String> result = getEveryDayTime(new Date());
        System.out.println();
//        System.out.println(getPrevDayTime(new Date(), -15));
    }
}
