package com.base.library.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gaodun on 2018/9/12.
 */

public class FTimeUtils {

    public final static String SYS_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";

    public final static String TIME_WITH_SECOND_FORMATE = "yyyy-MM-dd HH:mm";

    /**
     * 将时间字符串转化为秒值（相对 1970年１月１日）
     *
     * @param time 时间字符串
     * @return　　　失败返回　０
     */
    public static long toSystemTimeLong(String time) {
        long nRet = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(SYS_DATE_FORMATE);
            Date de = dateFormat.parse(time);
            nRet = de.getTime() / 1000;
        } catch (Exception e) {/*DISCARD EXCEPTION*/
        }
        return nRet;
    }

    /**
     * 比较时间先后
     *
     * @param str1 时间参数 1 格式：2011-11-11 11:11:11
     * @param str2 时间参数 2 格式：2011-01-01 12:00:00 (当前时间)
     * @return boolean
     */
    public static boolean getDistance(String str1, String str2) {
        boolean result = false;
        long time1 = toSystemTimeLong(str1);
        long time2 = toSystemTimeLong(str2);
        if (time1 >= time2) {
            result = true;
        }
        return result;
    }

    /**
     * 将时间转换为显示字符串，“全长”格式 2004-08-16 12:08:01
     *
     * @param second 相对于 1970年1月1日零时的秒数
     * @return
     */
    public static String toTimeFullString(long second) {
        StringBuffer sbRet = new StringBuffer();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(second * 1000);

            sbRet.append(calendar.get(Calendar.YEAR)).append("-");
            sbRet.append(integerToString((calendar.get(Calendar.MONTH) + 1), 2)).append("-");
            sbRet.append(integerToString(calendar.get(Calendar.DATE), 2)).append(" ");
            sbRet.append(integerToString(calendar.get(Calendar.HOUR_OF_DAY), 2)).append(":");
            sbRet.append(integerToString(calendar.get(Calendar.MINUTE), 2)).append(":");
            sbRet.append(integerToString(calendar.get(Calendar.SECOND), 2));
        } catch (Exception e) {/* DISCARD EXEPTION */
        }
        return sbRet.toString();
    }

    /**
     * 将输入的整数转换为指定长度的字符串，不足时首部用'0'填充, 整数超长时则直接输出
     *
     * @param input_num     输入的整数
     * @param output_length 输出字符串的长度
     * @return 参数(32, 4），输出：“0032”，参数(3390, 3)，输出：“3390”
     */
    public static String integerToString(int input_num, int output_length) {
        StringBuffer sbPrefix = new StringBuffer();
        String sTempInput = "" + input_num;

        if (sTempInput.length() < output_length) {
            for (int i = 0; i < output_length - sTempInput.length(); i++) {
                sbPrefix.append('0');
            }
        }

        return sbPrefix.append(sTempInput).toString();
    }

    /**
     * 返回当前时间序列
     *
     * @return
     */
    public static long getTimeSeq() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获得当前指定格式的时间字符串
     *
     * @param formate 如yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentWithFormate(String formate) {
        String time = "";
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formate);
        time = formatter.format(dNow);
        return time;
    }

    /**
     * 获取当前时间的n天前的日期
     *
     * @param day
     * @return
     */
    public static String getBeforeCurentTimes(int day) {
        String time = null;
        Calendar calendar = calGetCalendarTime();
        calendar.add(Calendar.DATE, -day);
        time = getSysDateTime(calendar.getTime());
        return time;
    }

    /**
     * 获取当前时间的n天前的日期
     *
     * @param day
     * @return
     */
    public static String getBeforeCurentYMDTimes(int day) {
        String beforeCurentTimes = getBeforeCurentTimes(day);
        if (beforeCurentTimes != null && !beforeCurentTimes.equals("") && !beforeCurentTimes.equals("null")) {
            if (beforeCurentTimes.length() > 11) {
                return beforeCurentTimes.substring(0, 11);
            } else {
                return beforeCurentTimes;
            }
        } else {
            return "";
        }
    }

    /**
     * 得到当前时间的Calendar形式
     *
     * @return Calendar
     */
    public static Calendar calGetCalendarTime() {
        Calendar caltime = Calendar.getInstance();
        return caltime;
    }

    /**
     * 获得系统默认格式的日期字符串
     *
     * @param date
     * @return
     */
    public static String getSysDateTime(Date date) {
        String time = null;
        SimpleDateFormat formatter = new SimpleDateFormat(SYS_DATE_FORMATE);
        time = formatter.format(date);
        return time;
    }

    /**
     * 根据日期字符串得到年，返回数字
     *
     * @param tempStr 时间字符串
     * @return
     */
    public static int intGetYear(String tempStr) {
        int dttime = 0;
        try {
            dttime = Integer.parseInt(tempStr.substring(0, 4));
        } catch (Exception ex) {

        }
        return dttime;
    }

    /**
     * 根据日期字符串得到月，返回数字
     *
     * @param tempStr
     * @return
     */
    public static int intGetMonth(String tempStr) {
        int dttime = 0;
        dttime = Integer.parseInt(tempStr.substring(5, 7));
        return dttime;
    }

    /**
     * 根据日期字符串得到日，返回数字
     * 2017-6-5
     *
     * @param tempStr 标准时间字符串
     * @return int
     */
    public static int intGetDay(String tempStr) {
        int dttime = 0;
        dttime = Integer.parseInt(tempStr.substring(8, 10));
        return dttime;
    }

    /**
     * 获取当前日期格式的前一天
     *
     * @param targetDate 目标日期格式字符串
     * @return
     */
    public static String getBeforeFromTarget(String targetDate) {
        int year = intGetYear(targetDate);
        int month = intGetMonth(targetDate);
        int day = intGetDay(targetDate);
        boolean isLeapYear = false;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            isLeapYear = true;
        } else {
            isLeapYear = false;
        }
        switch (month) {
            case 1:
                if (day == 1) {
                    day = 31;
                    month = 12;
                    year--;
                } else {
                    day--;
                }
                break;
            case 5:
            case 7:
            case 10:
            case 12:
                if (day == 1) {
                    day = 30;
                    month--;
                } else {
                    day--;
                }
                break;
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
                if (day == 1) {
                    day = 31;
                    month--;
                } else {
                    day--;
                }
                break;
            case 3:
                if (isLeapYear) {
                    if (day == 1) {
                        day = 29;
                        month--;
                    } else {
                        day--;
                    }
                } else {
                    if (day == 1) {
                        day = 28;
                        month--;
                    } else {
                        day--;
                    }
                }
                break;
            default:
                break;
        }
        return formatDateFromInt(year, month, day);
    }

    /**
     * 将int数组的  年份、月份、日，转换为  2017-06-05 的格式
     *
     * @return
     */
    public static String formatDateFromInt(int year, int month, int day) {
        String m = "";
        if (month >= 10) {
            m = "" + month;
        } else {
            m = "0" + month;
        }

        String d = "";
        if (day >= 10) {
            d = "" + day;
        } else {
            d = "0" + day;
        }
//        LogUtil.e("3!! y=" + year + ",m=" + m + ",d=" + d);
        String result = year + "-" + m + "-" + d;
        return result;
    }

    /**
     * 计算当前日期
     *
     * @return
     */
    public static int[] getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
    }

    /**
     * @param date 2019-5-3
     * @return 20190503
     */
    @SuppressLint("SimpleDateFormat")
    public static String getConverDateString(String date) {
        if (!TextUtils.isEmpty(date) && !TextUtils.equals("null", date)) {
            Date dateF = null;
            try {
                dateF = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new SimpleDateFormat("yyyyMMdd").format(dateF);
        } else {
            return "--";
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date 2019-05-03
     * @return 2019-5-3
     */
    @SuppressLint("SimpleDateFormat")
    public static String getConverDateStringTwo(String date) {
        if (!TextUtils.isEmpty(date) && !TextUtils.equals("null", date)) {
            Date dateF = null;
            try {
                dateF = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new SimpleDateFormat("yyyy-M-d").format(dateF);
        } else {
            return "--";
        }
    }

    /**
     * @param date 2019-05-03
     * @return 2019-5-3
     */
    @SuppressLint("SimpleDateFormat")
    public static String getConverDateStringThree(String date) {
        if (!TextUtils.isEmpty(date) && !TextUtils.equals("null", date)) {
            Date dateF = null;
            try {
                dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new SimpleDateFormat("yyyy-M-d").format(dateF);
        } else {
            return "--";
        }
    }

    /**
     * @param date 2019-05-03
     * @return 2019-5-3
     */
    @SuppressLint("SimpleDateFormat")
    public static String getConverDateStringFour(String date) {
        if (!TextUtils.isEmpty(date) && !TextUtils.equals("null", date)) {
            Date dateF = null;
            try {
                dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new SimpleDateFormat("HH:mm:ss").format(dateF);
        } else {
            return "--";
        }
    }


    /**
     * @param date 2019-05-03
     * @return 2019-5-3
     */
    @SuppressLint("SimpleDateFormat")
    public static String getConverDateStringFive(String date) {
        if (!TextUtils.isEmpty(date) && !TextUtils.equals("null", date)) {
            Date dateF = null;
            try {
                dateF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return new SimpleDateFormat("HH:mm").format(dateF);
        } else {
            return "--";
        }
    }
}
