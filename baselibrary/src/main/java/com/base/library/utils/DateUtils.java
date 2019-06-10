package com.base.library.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ASUS on 2019/3/15.
 * 时间转换器
 */

public class DateUtils {

    @TargetApi(Build.VERSION_CODES.N)
    public static Calendar getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 604800);
        return calendar;
    }

    public static Calendar getStartTime(String start){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String day() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String days(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String day(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(date);
    }


    public static String Hm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static String yMdHm(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String H(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        return sdf.format(date);
    }

    public static String H() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String HF() {
        SimpleDateFormat sdf = new SimpleDateFormat("H");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String m(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        return sdf.format(date);
    }

    public static String m() {
        SimpleDateFormat sdf = new SimpleDateFormat("mm");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String yyr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }
    public static String ym() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }

    public static String ym(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }
    public static String y() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }
    public static String ms() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return sdf.format(date);
    }
}
