package upkit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @description:  时间工具
 * @author: melody
 * @email:  cuzart@163.com
 */
public class TimeUtil {

    /**
     * 判断是否是闰年 是则返回TRUE，不是返回FALSE
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))
            return true;
        return false;
    }

    /**
     * 获得
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Calendar getCalendar(int year, int month, int day) {
        if (year < 1 || (month < 1 && month > 12) || (day < 1 && day > getDayOfMonth(year, month)))
            throw new IllegalArgumentException("输入年或月或日的值不正确.");
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }

    /**
     * 获得某一年某个月的天数 EG:month 是以0为一月开始计数的 在使用set方法之前，必须先clear一下 ，否则很多信息会继承自系统当前时间
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDayOfMonth(int year, int month) {
        if (year < 1 || (month < 1 && month > 12)) {
            throw new IllegalArgumentException("输入年或月的值不正确.");
        }
        Calendar cal = Calendar.getInstance();
        // 在为Calendar新对象赋值时应该先清除以前的值
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        // 返回这个月的天数
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Calendar和Date的转化
     *
     * @param calendar
     * @return
     */
    public static Date CalendarToDate(Calendar calendar) {
        if (calendar == null)
            return null;
        return calendar.getTime(); // 获取date的实例
    }

    public static Calendar DateToCalendar(Date date) {
        if (date == null)
            return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // 设置calendar的实例
        return cal;
    }

    /**
     * 获取这一天是这一年的第几个星期
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getWeekOfYear(int year, int month, int day) {
        if (year < 1 || (month < 1 && month > 12) || (day < 1 && day > getDayOfMonth(year, month)))
            throw new IllegalArgumentException("输入年或月或日的值不正确.");

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        // 第几个星期
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 返回日期增加或减少后的日期 格式为"yyyy-MM-dd"
     *
     * @param year
     * @param month
     * @param day
     * @param add   :该值为负时则为减少日期
     * @return
     */
    public static String addDayToDate(int year, int month, int day, int add) {
        if (year < 1 || (month < 1 && month > 12) || (day < 1 && day > getDayOfMonth(year, month)))
            throw new IllegalArgumentException("输入年或月或日的值不正确.");
        // 设置日历
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        // add更改值
        cal.add(Calendar.DATE, add);
        // 格式化日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = cal.getTime();
        return df.format(date);

    }

    /**
     * 返回日期增加或减少后的日期 格式为"yyyy-MM-dd" 注意该方法是指在当前月份下进行日期的循环 比如 2015-08-03 减少4 则为
     * 2015-08-30
     *
     * @param year
     * @param month
     * @param day
     * @param add   :该值为负时则为减少日期
     * @return
     */
    public static String rollDayToDate(int year, int month, int day, int add) {
        if (year < 1 || (month < 1 && month > 12) || (day < 1 && day > getDayOfMonth(year, month)))
            throw new IllegalArgumentException("输入年或月或日的值不正确.");
        // 设置日历
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        // roll更改值
        cal.roll(Calendar.DATE, add);
        // 格式化日期
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = cal.getTime();
        return df.format(date);

    }

    /**
     * 按照指定的格式将Sting类型转换成java.util.Date类型
     *
     * @param dateStr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public final static Date toFormatDate(String dateStr, String pattern) {
        if (StringUtil.isBlank(dateStr)) // 如果dateStr为空直接返回null
            return null;
        String defaultPattern = "yyyy-MM-dd"; // 默认转换格式
        Date date = null;
        SimpleDateFormat format = (!StringUtil.isNull(pattern)) ? new SimpleDateFormat(pattern)
                : new SimpleDateFormat(defaultPattern);
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * 获得格式化了的Date
     *
     * @param date
     * @return
     */
    public final static String getFormatDate(Date date, String pattern) {
        if (StringUtil.isBlank(date.toString())) // 如果date为空直接返回null
            return null;
        String defaultPattern = "yyyy-MM-dd"; // 默认转换格式
        SimpleDateFormat format = (!StringUtil.isNull(pattern)) ? new SimpleDateFormat(pattern)
                : new SimpleDateFormat(defaultPattern);
        return format.format(date);
    }

    /**
     * 计算两个时间之间相隔天数
     *
     * @param startday 开始时间
     * @param endday   结束时间
     * @return
     */
    public int getIntervalDays(Calendar startday, Calendar endday) {
        // 确保startday在endday之前
        if (startday.after(endday)) {
            Calendar cal = startday;
            startday = endday;
            endday = cal;
        }
        // 分别得到两个时间的毫秒数
        long sl = startday.getTimeInMillis();
        long el = endday.getTimeInMillis();

        long ei = el - sl;
        // 根据毫秒数计算间隔天数
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算两个时间之间相隔天数
     *
     * @param startday 开始时间
     * @param endday   结束时间
     * @return
     */
    public int getIntervalDays(Date startday, Date endday) {
        // 确保startday在endday之前
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        // 分别得到两个时间的毫秒数
        long sl = startday.getTime();
        long el = endday.getTime();

        long ei = el - sl;
        // 根据毫秒数计算间隔天数
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

}
