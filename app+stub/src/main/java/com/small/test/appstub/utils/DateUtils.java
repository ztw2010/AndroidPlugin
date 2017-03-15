package com.small.test.appstub.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils
{
    
    /**
     * 把日期格式化为时期时间格式
     */
    public static final DateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 把日期格式化为时期时间格式
     */
    public static final DateFormat DATETIME_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    /**
     * 把日期格式化为时期时间格式
     */
    public static final String DATE_FORMAT3 = "yyyyMMdd_HHmmss";
    
    /**
     * 短日期的格式化
     */
    public static final DateFormat SHORT_TIME_FORMAT = new SimpleDateFormat("HH:mm");
    
    /**
     * 把日期格式化为时期时间格式
     */
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 简单的日期格式化.
     */
    public static final String FORMAT_SIMPLE = "yyyy-MM-dd";
    
    private DateUtils()
    {
        
    }
    
    /**
     * 转成日期时间格式的字符串.
     * 格式如：'yyyy-MM-dd'
     * @param date 日期.
     * @return 日期时间格式字符串.
     */
    public static String toDateTimeString(Date date)
    {
        if (date == null)
        {
            return null;
        }
        
        return DATE_FORMAT.format(date);
    }
    
    /**
     * 把日期格式字符串解析成日期.
     * 
     * @param dateStr
     *            日期字符串.
     * @param regex
     *            格式.
     * @return 日期.
     */
    public static Date parse(String dateStr, String regex)
    {
        if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(regex))
        {
            return null;
        }
        DateFormat df = new SimpleDateFormat(regex);
        try
        {
            return df.parse(dateStr);
        }
        catch (ParseException e)
        {
        }
        return null;
    }
    
    /**
     * 把日期格式化成字符串.
     * 
     * @param date
     *            日期.
     * @param regex
     *            格式.
     * @return 日期字符串.
     */
    public static String format(Date date, String regex)
    {
        DateFormat df = new SimpleDateFormat(regex, Locale.getDefault());
        return df.format(date);
    }
    
    /**
     * 将微秒数的时间转成日期类型.
     * @param timeMillis 微秒数的时间.
     * @return 日期.
     */
    public static Date timeToDate(Long timeMillis)
    {
        if (timeMillis == null)
        {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        return c.getTime();
    }
    
    /**
     * 日期转换为长整型
     * @param date
     * @return
     */
    public static Long dateToLong(Date date)
    {
        return new Long(DATE_FORMAT.format(date));
    }
    
    public static String longToStr(Long tims)
    {
        if (tims == null)
        {
            return null;
        }
        Date date = new Date(tims);
        return DATE_FORMAT.format(date);
    }
    
    public static String getDt()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 ");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }
    
    /**
    
     * 把毫秒转化成日期
    
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
    
     * @param millSec(毫秒数)
    
     * @return
     * @throws ParseException 
    
     */
    
    public static Date transferLongToDate(Long millSec)
        throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(sdf.format(millSec));
    }
    
}
