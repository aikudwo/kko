package com.aikudwo.ccy.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static Date addDay(Date date, int addNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addNum);
        return calendar.getTime();
    }

    public static Date addHour(Date date, int addNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, addNum);
        return calendar.getTime();
    }


    public static Date addMinute(Date date, int addNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, addNum);
        return calendar.getTime();
    }

    public static Date theDayFirstTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static void main(String[] strings) {
        Date today=new Date();
        System.out.println(addDay(theDayFirstTime(today),111));
        System.out.println(today);
    }
}
