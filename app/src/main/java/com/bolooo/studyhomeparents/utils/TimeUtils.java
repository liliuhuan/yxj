package com.bolooo.studyhomeparents.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Guojunhong on 15/5/19.
 */

public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat MONTH_YEAR = new SimpleDateFormat("yyyy.MM");
    public static final SimpleDateFormat MONTH_YEAR_ = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat DAY_FORMAT_DATE = new SimpleDateFormat("MM 月 dd");
    public static final SimpleDateFormat END_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat STR_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static final SimpleDateFormat MM_DD_DATE = new SimpleDateFormat("MM 月 dd 日 HH:mm");

    public static SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat Formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat Formatter1 = new SimpleDateFormat(" HH:mm");

    public static String getMD(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return " ";
        }
        return DAY_FORMAT_DATE.format(parseDate(strDate));
    }

    public static String getHHMM(Date date) {
        return Formatter1.format(date);
    }

    public static String getHHMM(String date) {
        return Formatter1.format(parseDate(date));
    }

    public static String getEndMD(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        return DEFAULT_DATE_FORMAT.format(parseDate(strDate));
    }

    public static boolean isToday(String startTime) {
        Calendar calendar = Calendar.getInstance();
        HashMap<String, Integer> date = getDate(startTime);
        int day = date.get("day");
        int year = date.get("year");
        int month = date.get("month") - 1;
        if ((calendar.get(Calendar.YEAR)) == year && (calendar.get(Calendar.MONTH)) == month && (calendar.get(Calendar.DAY_OF_MONTH)) == day) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isYesterday(String startTime) {
        Calendar calendar = Calendar.getInstance();
        HashMap<String, Integer> date = getDate(startTime);
        int day = date.get("day");
        int year = date.get("year");
        int month = date.get("month") - 1;
        if ((calendar.get(Calendar.YEAR)) == year && (calendar.get(Calendar.MONTH)) == month && (calendar.get(Calendar.DAY_OF_MONTH)-1) == day) {
            return true;
        } else {
            return false;
        }
    }
    public static String getNeedDay(String strdate) {
        String t = strdate.split("T")[0];
        String day = t.split("-")[2];
        return day;
    }

    public static String getNeedMouth(String strdate) {
        String t = strdate.split("T")[0];
        String month = t.split("-")[1];
        switch (Integer.valueOf(month)){
            case 1: month = "一月";break;
            case 2: month = "二月";break;
            case 3: month = "三月";break;
            case 4: month = "四月";break;
            case 5: month = "五月";break;
            case 6: month = "六月";break;
            case 7: month = "七月";break;
            case 8: month = "八月";break;
            case 9: month = "九月";break;
            case 10: month = "十月";break;
            case 11: month = "十一月";break;
            case 12: month = "十二月";break;
        }
        return month;
    }
    public static String getMMDDTime(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        return MM_DD_DATE.format(parseDate(strDate));
    }

    public static String getYearAndMonth(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        return MONTH_YEAR.format(parseDate(strDate));
    }

    public static String getYearAndMonth1(String strDate) {
        if (TextUtils.isEmpty(strDate)) {
            return "";
        }
        return MONTH_YEAR_.format(parseDate(strDate));
    }

    // 字符串类型日期转化成date类型
    public static Date strToDate(String style, String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String dateToStr(String style, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(date);
    }

    public static String getTEndMD(String strDate) {
        return END_FORMAT_DATE.format(parseDate(strDate));
    }
    public static String getDateFormat(String strDate) {
        return DATE_FORMAT_DATE.format(parseDate(strDate));
    }
    public static Date quackDate(String dateStr) {
        try {
            return STR_FORMAT_DATE.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Calendar.getInstance().getTime();
    }

    public static String getCreateTime(Date date) {
        return mFormatter.format(date);
    }

    public static String getYMDTime(Date date) {
        if (date != null)
            return DATE_FORMAT_DATE.format(date);
        return null;
    }

    public static String getYMDtime(String date) {
        if (date != null)
            return DATE_FORMAT_DATE.format(parseDate(date));
        return "";
    }

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isHaveNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isIdentification(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static final String[] zodiacArr = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    public static final String[] constellationArr = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};

    public static final int[] constellationEdgeDay = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};

    public static HashMap<String, Integer> constellationMap = null;


    /**
     * 根据日期获取生肖
     *
     * @return
     */
    public static String getZodica(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return zodiacArr[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 根据日期获取星座
     *
     * @return
     */
    public static String getConstellation(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day < constellationEdgeDay[month]) {
            month = month - 1;
        }
        if (month >= 0) {
            return constellationArr[month];
        }
        // default to return 魔羯
        return constellationArr[11];
    }

    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
//            LogUtils.showLog(
//                    "出生时间大于当前时间!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;//注意此处，如果不加1的话计算结果是错误的
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                    //do nothing
                }
            } else {
                //monthNow>monthBirth
                age--;
            }
        } else {
            //monthNow<monthBirth
            //donothing
        }

        return age;
    }

    public static String getWeek(int i) {
        String week = null;
        switch (i) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * Calculates the elapsed time after the given parameter date.
     *
     * @param time ISO formatted time when the event occurred in local time zone.
     **/
    public static String getElapsedTime(String time) {
        TimeZone defaultTimeZone = TimeZone.getDefault();

        // TODO: its advisable not to use this method as it changes the
        // timezone.
        // Change it at some time in future.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Date eventTime = parseDate(time);

        Date currentDate = new Date();

        long diffInSeconds = (currentDate.getTime() - eventTime.getTime()) / 1000;
        String elapsed = "";
        long seconds = diffInSeconds;
        long mins = diffInSeconds / 60;
        long hours = diffInSeconds / (60 * 60);
        long days = diffInSeconds / 86400;
        long weeks = diffInSeconds / 604800;
        long months = diffInSeconds / 2592000;

        // Log.v( TAG, "#getElapsedTime seconds: " + seconds + " mins: " + mins
        // + " hours: " + hours + " days: " + days );

        if (seconds < 120) {
            elapsed = "a min ago";
        } else if (mins < 60) {
            elapsed = mins + " mins ago";
        } else if (hours < 24) {
            elapsed = hours + " " + (hours > 1 ? "hrs" : "hr") + " ago";
        } else if (hours < 48) {
            elapsed = "a day ago";
        } else if (days < 7) {
            elapsed = days + " days ago";
        } else if (weeks < 5) {
            elapsed = weeks + " " + (weeks > 1 ? "weeks" : "week") + " ago";
        } else if (months < 12) {
            elapsed = months + " " + (months > 1 ? "months" : "months") + " ago";
        } else {
            elapsed = "more than a year ago";
        }

        TimeZone.setDefault(defaultTimeZone);

        return elapsed;
    }


    /****
     * Parses date string and return a {@link Date} object
     *
     * @return The ISO formatted date object
     *****/
    public static Date parseDate(String date) {

        if (date == null) {
            return null;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(date);
        String newDate = null;
        Date dateDT = null;

        try {
            newDate = sbDate.substring(0, 19).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String nDate = null;
        if (newDate != null && newDate.contains("T")){
            String rDate = newDate.replace("T", " ");
            if (rDate!= null && rDate.contains("-"))
             nDate = rDate.replaceAll("-", "/");
        }
      

        try {
            dateDT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(nDate);
            // Log.v( TAG, "#parseDate dateDT: " + dateDT );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;


    public static String getTimeAgo(String timeStr) {

        long time = parseDate(timeStr).getTime();

        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        final long diff = now - time;
        if (diff < MINUTE) {
            return "刚刚";
        } else if (diff < 2 * MINUTE) {
            return "1分钟前";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " 分钟前";
        } else if (diff < 90 * MINUTE) {
            return "1小时前";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " 小时前";
        } else if (diff < 48 * HOUR) {
            return "昨天";
        } else {
            return diff / DAY + " 天前";
        }
    }

    public static String getTimeStart(String timeStr) {

        long time = 0;
        try {
            time = Formatter.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();

        final long diff = time - now;

        if (diff > 0) {
            if (diff < MINUTE) {
                return "马上开始";
            } else if (diff < 2 * MINUTE) {
                return "1分钟后开始";
            } else if (diff < 50 * MINUTE) {
                return diff / MINUTE + " 分钟后开始";
            } else if (diff < 90 * MINUTE) {
                return "1小时后开始";
            } else if (diff < 24 * HOUR) {
                return diff / HOUR + " 小时后开始";
            } else if (diff < 48 * HOUR) {
                return "明天开始";
            } else {
                return diff / DAY + " 天后开始";
            }
        } else {
            return "活动已开始";
        }

    }

    public static HashMap<String, Integer> getDate(String strdate) {
        String t = strdate.split("T")[0];
        String year = t.split("-")[0];
        String month = t.split("-")[1];
        String day = t.split("-")[2];
        HashMap<String, Integer> dateHashMap = new HashMap<>();
        dateHashMap.put("year", Integer.parseInt(year));
        dateHashMap.put("month", Integer.parseInt(month));
        dateHashMap.put("day", Integer.parseInt(day));
        return dateHashMap;
    }

}
