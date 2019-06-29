package youke.order.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author xiaojun
 * @version 2014-4-15
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm）
     */
    public static String formatDateTimeBranch(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm）
     */
    public static String getTimeBranch() {
        return formatDate(new Date(), "HH:mm");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm）
     */
    public static String getDateTimeBranch() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取指定时间前后的时间
     *
     * @param d   当前时间
     * @param day 为正获取几天后,为负获取几天前
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 对比当前时间，大于返回TRUE，小于返回FALSE
     *
     * @return
     */
    public static boolean compareDate(Date date) {
        Date nowDate = new Date();
        return date.getTime() > nowDate.getTime();
    }

    /**
     * 获取现在时间
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 两个时间之间的天数
     */
    public static long getDays(String date1, String date2) {
        if (date1 == null || date1.equals(""))
            return 0;
        if (date2 == null || date2.equals(""))
            return 0;
        // 转换为标准时间
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date mydate = null;
        try {
            date = myFormatter.parse(date1);
            mydate = myFormatter.parse(date2);
        } catch (Exception e) {
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        return Math.abs(day);
    }


    /**
     * 将时间转为年月
     *
     * @param time
     * @return
     */
    public static String timeYears(String time) {
        String stingtime = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            Date date = dateFormat.parse(time);
            stingtime = dateFormat.format(date).replace("-", "/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stingtime;
    }

    /**
     * 获取时间段--30天
     *
     * @return
     */
    public static Map getTimeSlot() {
        Map map = new HashMap();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());//获取当天时间
        String endTime = DateUtil.formatDate(rightNow.getTime(), "yyyy-MM-dd 23:59:59");
        String startTime = "";
        for (int i = 0; i < 29; i++) {
            rightNow.add(Calendar.DAY_OF_YEAR, -1);
            startTime = DateUtil.formatDate(rightNow.getTime(), "yyyy-MM-dd 00:00:01");
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * 获取今天的开始及结束日期
     *
     * @return
     */
    public static Map<String, Object> todayDate() {
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stateTime = sdf.format(date) + " 00:00:01";
        String endTime = sdf.format(date) + " 23:59:59";
        map.put("startTime", stateTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * 获取时间段--30天
     *
     * @return
     */
    public static Map getThreeTimeSlot() {
        Map map = new HashMap();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());//获取当天时间
        String endTime = DateUtil.formatDate(rightNow.getTime(), "yyyy-MM-dd 23:59:59");
        String startTime = "";
        for (int i = 0; i < 2; i++) {
            rightNow.add(Calendar.DAY_OF_YEAR, -1);
            startTime = DateUtil.formatDate(rightNow.getTime(), "yyyy-MM-dd 00:00:01");
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取开始到结束时间
     *
     * @return
     */
    public static Map getStartAndEndTimeSlot(int day) {
        Map map = new HashMap();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());//获取当天时间
        String endTime = DateUtil.formatDate(rightNow.getTime(), "yyyy-MM-dd 23:59:59");
        String startTime = "";
        for (int i = 0; i < day - 1; i++) {
            rightNow.add(Calendar.DAY_OF_YEAR, -1);
            startTime = DateUtil.formatDate(rightNow.getTime(), "yyyy-MM-dd 00:00:01");
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    /**
     * 判断给定日期是否为今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.YEAR, current.get(Calendar.YEAR));
        tomorrow.set(Calendar.MONTH, current.get(Calendar.MONTH));
        tomorrow.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) + 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        current.setTime(date);
        return current.after(today) && current.before(tomorrow);
    }

    /**
     * 判断给定日期是否为昨天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        current.setTime(date);
        return current.after(yesterday) && current.before(today);
    }

    /**
     * 判断指定日期是否在两个时间之间
     *
     * @param date
     * @param begDate
     * @param endDate
     * @return
     */
    public static boolean isBetweenTwoDates(Date date, String begDate, String endDate) {
        if (isDateStringCorrect(begDate, "yyyy-MM-dd") && isDateStringCorrect(endDate, "yyyy-MM-dd")) {
            try {
                Long beginDate = parseDate(begDate, "yyyy-MM-dd").getTime();
                Long endingDate = parseDate(endDate, "yyyy-MM-dd").getTime();
                if (date.getTime() >= beginDate && date.getTime() <= endingDate) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断指定日期是否为给定格式
     *
     * @param date   日期
     * @param format 格式
     * @return
     */
    public static boolean isDateStringCorrect(String date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        try {
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断时间是否在时间段内*
     *
     * @param date         当前时间 yyyy-MM-dd HH:mm:ss
     * @param strDateBegin 开始时间 00:00:00
     * @param strDateEnd   结束时间 00:05:00
     * @return
     */
    public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            // 当前时间小时数在开始时间和结束时间小时数之间
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM >= strDateBeginM
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM
                    && strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                return true;
            }
            // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
            else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM <= strDateEndM) {
                return true;
                // 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
            } else if (strDateH >= strDateBeginH && strDateH == strDateEndH
                    && strDateM == strDateEndM && strDateS <= strDateEndS) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
