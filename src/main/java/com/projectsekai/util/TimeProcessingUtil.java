package com.projectsekai.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName TimeProcessingUtil
 * @description
 * @Author XinlindeYu
 * @Date 2020/11/26 0026 下午 4:05
 * @Version 1.0
 **/
public class TimeProcessingUtil {
    public static void main(String[] args) {
        String dataStr = "2020-11-26 15:51:20";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd", Locale.CHINA);
        try {
            Date ckDate = sdf.parse(dataStr);
            System.out.println("原始时间为：" + dataStr);
            System.out.println("转换后时间为：" + getTime(ckDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // 将传入时间与当前时间进行对比，是否今天\昨天\前天\同一年
    public static String getTime(Date date) {
        boolean sameYear = false;
        String todySDF = "HH:mm";
        String yesterDaySDF = "昨天";
        String beforYesterDaySDF = "前天";
        String otherSDF = "MM-dd HH:mm";
        String otherYearSDF = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)) {
            sameYear = true;
        } else {
            sameYear = false;
        }
        // 判断是不是今天
        if (dateCalendar.after(todayCalendar)) {
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            // 判断是不是昨天
            if (dateCalendar.after(todayCalendar)) {
                sfd = new SimpleDateFormat(todySDF);
                time = yesterDaySDF + " " + sfd.format(date);
                return time;
            }
            todayCalendar.add(Calendar.DATE, -1);
            // 判断是不是前天
            if (dateCalendar.after(todayCalendar)) {

                sfd = new SimpleDateFormat(todySDF);
                time = beforYesterDaySDF + " " + sfd.format(date);
                return time;
            }
        }
        if (sameYear) {
            sfd = new SimpleDateFormat(otherSDF);
            time = sfd.format(date);
        } else {
            sfd = new SimpleDateFormat(otherYearSDF);
            time = sfd.format(date);
        }
        return time;
    }
}
