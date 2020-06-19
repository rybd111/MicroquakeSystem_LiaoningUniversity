package com.rqma.history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Auther: RQMA
 * @Date: 4/26/2019 11:16 PM
 */
public class DateUtil {

    /**
     * 字符串转化为日期
     */
    public static Date toDate(String dateStr) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = simpleDateFormat.parse(dateStr);
        return date;
    }


    /**
     * 日期转化为字符串
     * @param date
     * @return
     */
    public static String toString(Date date) {

        String time;
        SimpleDateFormat formater=new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd HH:mm:ss");
        time = formater.format(date);
        return time;
    }
    /**
     * 日期转化为字符串
     * @param date
     * @return
     */
    public static String toString2(Date date) {

        String time;
        SimpleDateFormat formater=new SimpleDateFormat();
        formater.applyPattern("yyyyMMddHHmmss");
        time = formater.format(date);
        return time;
    }
}
