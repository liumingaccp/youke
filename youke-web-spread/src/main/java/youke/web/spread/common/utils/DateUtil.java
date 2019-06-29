package youke.web.spread.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
     * 获取指定天数前后的时间
     *
     * @param d   当前时间
     * @param day 为正获取几天前,为负获取几天后
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 获取指定月份之前的时间
     *
     * @param d
     * @param month
     * @return
     */
    public static Date getMonthBefore(Date d, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -month);

        return calendar.getTime();
    }

    /**
     * 获取指定小时之后的时间
     *
     * @param d
     * @param hour
     * @return
     */
    public static String getHourAfter(Date d, int hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        Date time = calendar.getTime();
        return format.format(time);
    }

    /**
     * 获取指定分钟后的时间
     *
     * @param d
     * @param minute
     * @return
     */
    public static Date getMinuteAfter(Date d, int minute) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.setTime(d);
        nowTime.add(Calendar.MINUTE, minute);
        return nowTime.getTime();
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
     * 获取今天的开始及结束日期
     *
     * @return
     */
    public static String todayStartDate() {
        Map<String, Object> map = new HashMap<String, Object>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stateTime = sdf.format(date) + " 00:00:00";
        return stateTime;
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
     * 仅能判断长时间
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
     * 仅能判断长时间
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
    public static boolean isBetweenTwoDates(String date, String begDate, String endDate) {
        Long beginDate = parseDate(begDate).getTime();
        Long endingDate = parseDate(endDate).getTime();
        Long time = parseDate(date).getTime();
        if (time >= beginDate && time <= endingDate) {
            return true;
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
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now;
        Date beginTime;
        Date endTime;
        try {
            now = df.parse(df.format(date));
            beginTime = df.parse(strDateBegin);
            endTime = df.parse(strDateEnd);
            return belongCalendar(now, beginTime, endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        return nowTime.getTime() >= beginTime.getTime() && nowTime.getTime() <= endTime.getTime();
    }

    /**
     * 判断两个时间区间是否有交集
     * true:有交集
     * false:无交集
     *
     * @param startdate1
     * @param enddate1
     * @param startdate2
     * @param enddate2
     * @return
     */
    public static boolean isOverlap(String startdate1, String enddate1, String startdate2, String enddate2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date leftStartDate = null;
        Date leftEndDate = null;
        Date rightStartDate = null;
        Date rightEndDate = null;
        try {
            leftStartDate = format.parse(startdate1);
            leftEndDate = format.parse(enddate1);
            rightStartDate = format.parse(startdate2);
            rightEndDate = format.parse(enddate2);
        } catch (ParseException e) {
            return false;
        }
        return
                ((leftStartDate.getTime() >= rightStartDate.getTime())
                        && leftStartDate.getTime() < rightEndDate.getTime())
                        ||
                        ((leftStartDate.getTime() > rightStartDate.getTime())
                                && leftStartDate.getTime() <= rightEndDate.getTime())
                        ||
                        ((rightStartDate.getTime() >= leftStartDate.getTime())
                                && rightStartDate.getTime() < leftEndDate.getTime())
                        ||
                        ((rightStartDate.getTime() > leftStartDate.getTime())
                                && rightStartDate.getTime() <= leftEndDate.getTime());

    }

    public static String getMouthStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();//获取当前日期
        //cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time = cal.getTime();
        String dateStr = format.format(time);
        return dateStr;
    }

    public static Date getMouthStartTimeForDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();//获取当前日期
        //cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time = cal.getTime();
        return time;
    }

    /**
     * 判断两个短时间是否覆盖
     *
     * @param startdate1 开始时间1
     * @param enddate1   结束时间1
     * @param startdate2 开始时间2
     * @param enddate2   结束时间2
     * @return true:覆盖 false:未覆盖
     */
    public static boolean isOverride(String startdate1, String enddate1, String startdate2, String enddate2) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date leftStartDate = null;
        Date leftEndDate = null;
        Date rightStartDate = null;
        Date rightEndDate = null;
        try {
            leftStartDate = format.parse(startdate1);
            leftEndDate = format.parse(enddate1);
            rightStartDate = format.parse(startdate2);
            rightEndDate = format.parse(enddate2);
        } catch (ParseException e) {
            return false;
        }
        return
                ((leftStartDate.getTime() >= rightStartDate.getTime())
                        && leftStartDate.getTime() < rightEndDate.getTime())
                        ||
                        ((leftStartDate.getTime() > rightStartDate.getTime())
                                && leftStartDate.getTime() <= rightEndDate.getTime())
                        ||
                        ((rightStartDate.getTime() >= leftStartDate.getTime())
                                && rightStartDate.getTime() < leftEndDate.getTime())
                        ||
                        ((rightStartDate.getTime() > leftStartDate.getTime())
                                && rightStartDate.getTime() <= leftEndDate.getTime());

    }

    /**
     * 计算两个日期相差年数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int yearDateDiff(String startDate, String endDate) {
        Calendar calBegin = Calendar.getInstance(); //获取日历实例
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(DateUtil.parseDate(startDate));
        calEnd.setTime(DateUtil.parseDate(endDate));
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }

    public static String formatSecond(String second) {
        String html = "0秒";
        if (second != null) {
            Double s = Double.parseDouble(second);
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            html = String.format(format, array);
        }

        return html;

    }

    /**
     * 获得当前时间点离当前天结束剩余的秒数
     *
     * @param currentDate 秒数
     * @return
     */
    public static Integer getRemainSecondsOneDay(Date currentDate) {
        LocalDateTime midnight = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault()).plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        long seconds = ChronoUnit.SECONDS.between(currentDateTime, midnight);
        return (int) seconds;
    }

    /**
     * 获取指定时间的每月开始日期和结束日期
     *
     * @param date
     * @return
     */
    public static Map<String, String> getMonthFirstDayAndLastDayDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String firstDay = format.format(calendar.getTime());

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);
        String lastDay = format.format(calendar.getTime());

        Map<String, String> map = new HashMap<>();
        map.put("firstDay", firstDay);
        map.put("lastDay", lastDay);
        return map;
    }

    /**
     * 获取两个时间的时间差 如1天2小时30分钟
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取昨天的结束时间
     *
     * @return
     */
    public static String getYesterdayEndTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar yesterday = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 23);
        yesterday.set(Calendar.MINUTE, 59);
        yesterday.set(Calendar.SECOND, 59);
        return format.format(yesterday.getTime());
    }

    /**
     * 获取指定月数之前的开始时间
     *
     * @return
     */
    public static String getMonthBeforeStartTime(Date d, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.MONTH, -month);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return format.format(now.getTime());
    }

    /**
     * 获取指定天数之后的结束时间
     *
     * @return
     */
    public static String getDateBeforeEndTime(Date d, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return format.format(now.getTime());
    }

    public static void main(String[] args) {
        /*String endTime = getYesterdayEndTime();
        System.out.println(endTime);
        String begTime = DateUtil.formatDateTime(getMonthBefore(DateUtil.parseDate(endTime), 3));
        System.out.println(begTime);
        System.out.println(getDays(begTime, endTime));*/
        System.out.println(getMonthBeforeStartTime(new Date(), 3));
        System.out.println(getDateBeforeEndTime(new Date(), -1));
    }
}
