package com.flinkinfo.monitordata.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化
 */
public class DateUtil
{
    /**
     * 输出格式为20160522
     *
     * @param time
     * @return
     */
    public static String changeToYYYYMMDD(Date time)
    {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = format.format(time);
        return date;
    }

    /**
     * 输出格式为20160522124532
     *
     * @param time
     * @return
     */
    public static String changeToYYYYMMDDHHMMSS(Date time)
    {
        DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = format.format(time);
        return date;
    }

    /**
     * 输出格式为2016-05-22 12:45:32
     *
     * @param time
     * @return
     */
    public static String changToYYYY_MM_DD_HH_MM_SS(Date time)
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = format.format(time);
        return date;
    }

    /**
     * 输出格式为124532
     *
     * @param time
     * @return
     */
    public static String changeToHHMMSS(Date time)
    {
        DateFormat format = new SimpleDateFormat("hhmmss");
        String date = format.format(time);
        return date;
    }
}
