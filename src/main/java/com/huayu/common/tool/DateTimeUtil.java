package com.huayu.common.tool;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTimeUtil
 * (时间工具类)
 * @author zxl
 * @version 1.1
 * 2015年5月20日
 */
public class DateTimeUtil {

	/**
	 * 获取时间格式化样式yyyy-MM-dd HH:mm:ss
	 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取时间格式化样式yyyy年MM月dd日 HH时mm分 中文
	 */
	public static final String YYYY_MM_DD_HH_MM_SS_CHINA = "yyyy年MM月dd日 HH时mm分";

	/**
	 * 获取时间格式化样式yyyy年MM月dd日  中文
	 */
	public static final String YYYY_MM_DD_CHINA = "yyyy年MM月dd日";

	/**
	 * 获取时间格式化样式yyyy-MM-dd
	 */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * 获取时间格式化样式yyyy-MM
	 */
	public static final String YYYY_MM = "yyyy-MM";

	/**
	 * 获取时间格式化样式yyyyMMddHHmmss
	 */
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String YYYYMMDDHHMMSS_SSS = "yyyyMMddHHmmssSSS";

	/**
	 * 获取时间格式化样式yyyyMMdd
	 */
	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMM = "yyyyMM";
	public static final String YYYY = "yyyy";

	public static final String MM = "MM";

	/**
	 * 默认时间
	 */
	public static final String DEFAUT_DATETIME = "1970-01-01 00:00:00";

	/**
	 * 默认时间
	 */
	public static final String DEFAUT_DATETIME0 = "1970-01-01 00:00:00.0";

	/**
	 * 增加类型/天
	 */
	public static final String PLUSTYPE_DAY = "day";

	/**
	 * 增加类型/月
	 */
	public static final String PLUSTYPE_MONTH = "month";

	public static final String min = "min";
	public static final String max = "max";


	protected static DateTimeFormatter format;

	/**
	 * 转换时间，是默认时间返回---
	 * @param date 时间
	 * @return
	 * String
	 */
	public static boolean compareDate(Date date,String formatType,String defautDatetime){
		try {
			return defautDatetime.equals(new DateTime(date).toString(DateTimeFormat.forPattern(formatType)));
		} catch (Exception e){
			return false;
		}

	}

	/**
	 * 转换时间，是默认时间返回---
	 * @param date 时间
	 * @return
	 * String
	 */
	public static String convertDefaultDate(Date date){
		if (null!=date) {
			format = DateTimeFormat.forPattern(YYYY_MM_DD_HH_MM_SS);
			String dateStr =  new DateTime(date).toString(format);
			return DateTimeUtil.DEFAUT_DATETIME.equals(dateStr)?"---":dateStr;
		} else {
			return "---";
		}

	}

	/**
	 * 默认时间
	 * @return
	 * Date
	 */
	public static Date defaultVal(){
		return convertStringToDate(DEFAUT_DATETIME,YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * getFormatDateTime
	 * (以某种格式化样式获取时间字符串)
	 * @param formatType 格式化样式(可以获取DateTimeUtil常量默认样式)
	 * @return String    返回类型
	 */
	public static String dateTimeToString(String formatType){
		return new DateTime().toString(formatType);
	}

	public static String dateToString(Date date, String formatType) {
		SimpleDateFormat sdf ;
		if (date != null) {
			sdf = new SimpleDateFormat(formatType);
			return sdf.format(date);
		}
		return null;
	}

	public static String dateToString(Date date) {
		SimpleDateFormat sdf ;
		if (date != null) {
			sdf = new SimpleDateFormat(YYYY_MM_DD);
			return sdf.format(date);
		}
		return null;
	}

	/**
	 * dateTimeStrToString
	 * (字符串时间类型转换)
	 * @param date String
	 * @param formatyType String
	 * @param formathType String
	 * @return String    返回类型
	 */
	public static String dateTimeStrToString(String date,String formatyType,String formathType){
		DateTimeFormatter format_y = DateTimeFormat.forPattern(formatyType);
		DateTimeFormatter format_h = DateTimeFormat.forPattern(formathType);
		return DateTime.parse(date.substring(0, date.lastIndexOf(".")>0?date.lastIndexOf("."):date.length()), format_y).toString(format_h);
	}

	/**
	 * dateTimeToStringPlusDay
	 * (以某种格式化样式获取时间字符串加天数)
	 * @param formatType String
	 * @param days Integer
	 * @return String    返回类型
	 */
	public static String dateTimeToStringPlusDay(String formatType,Integer days){
		return new DateTime().plusDays(days).toString(formatType);
	}

	/**
	 * dateTimeToStringPlusDay
	 * (传入一个时间以某种格式化样式获取时间字符串加天数)
	 * @param date 时间 String
	 * @param formatType 格式 String
	 * @param days 天数 Integer
	 * @return String    返回类型
	 */
	public static String dateTimeToStringPlusDay(String date,String formatType,Integer days){
		DateTimeFormatter format_y = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(date.substring(0, date.lastIndexOf(".")>0?date.lastIndexOf("."):date.length()), format_y).plusDays(days).toString(formatType);
	}

	/**
	 * getFormatStringToDateTime
	 * (以时间字符串转换成DateTime对象)
	 * @param formatType 格式化样式(可以获取DateTimeUtil常量默认样式)
	 * @param dateStr 时间字符串
	 * @return DateTime    返回类型
	 */
	public static DateTime stringToDateTime(String formatType,String dateStr){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(dateStr.substring(0, dateStr.lastIndexOf(".")>0?dateStr.lastIndexOf("."):dateStr.length()), format);
	}

	/**
	 * curMonthDayToNextMonthDay
	 * (获取当前月到下月之间的天数)
	 * @param start 当前月日期时间
	 * @return int  返回类型
	 */
	public static int curMonthDayToNextMonthDay(DateTime start){
		DateTime end = start.plusMonths(1);
		return Days.daysBetween(start, end).getDays();
	}

	/**
	 * cutTwoDateToHour
	 * (计算两个时间之间的小时)
	 * @param formatType String
	 * @param startStr String
	 * @param endStr String
	 * @return int 返回类型
	 */
	public static int cutTwoDateToHour(String formatType,String startStr,String endStr){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		DateTime start = DateTime.parse(startStr.substring(0, startStr.lastIndexOf(".")>0?startStr.lastIndexOf("."):startStr.length()), format);
		DateTime end = DateTime.parse(endStr.substring(0, endStr.lastIndexOf(".")>0?endStr.lastIndexOf("."):endStr.length()), format);
		return Hours.hoursBetween(start, end).getHours();
	}

	/**
	 * cutTwoDateToDay
	 * (计算两个时间之间的天数)
	 * @param formatType String
	 * @param startStr String
	 * @param endStr String
	 * @param sublength Integer
	 * @return int
	 */
	public static int cutTwoDateToDay(String formatType,String startStr,String endStr,Integer sublength){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		DateTime start = DateTime.parse(startStr.substring(0, sublength), format);
		DateTime end = DateTime.parse(endStr.substring(0, sublength), format);
		return Days.daysBetween(start, end).getDays();
	}

	/**
	 * dateTimePlusNum
	 * (时间增加天/月/年...)
	 * @param formatType String
	 * @param dateStr String
	 * @param type String
	 * @param num Integer
	 * @return String    返回类型
	 */
	public static String dateTimePlusNum(String formatType,String dateStr,String type,Integer num) {
		formatType = StringUtils.isBlank(formatType) ? YYYY_MM_DD_HH_MM_SS : formatType;
		String retDateTime = "";
		format = DateTimeFormat.forPattern(formatType);
		if (DateTimeUtil.PLUSTYPE_DAY.equals(type)) {
			retDateTime = DateTime.parse(dateStr.substring(0, dateStr.lastIndexOf(".") > 0 ? dateStr.lastIndexOf(".") : dateStr.length()), format).plusDays(num).toString(format);
		}
		if (DateTimeUtil.PLUSTYPE_MONTH.equals(type)) {
			retDateTime = DateTime.parse(dateStr.substring(0, dateStr.lastIndexOf(".") > 0 ? dateStr.lastIndexOf(".") : dateStr.length()), format).plusMonths(num).toString(format);
		}
		return retDateTime;
	}

	/**
	 * 增加分钟数
	 * @param formatType 格式
	 * @param minutes 分数数
	 * @return
	 * String
	 */
	public static String addMinutes(String formatType,Integer minutes){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		return new DateTime().plusMinutes(minutes).toString(formatType);
	}

	/**
	 * compareToDate
	 * (比较时间大小)
	 * @param formatType 时间格式
	 * @param sourceDate 源时间
	 * @param targetDate 目标时间
	 * @param subStringNum 比较类型
	 * @return boolean    返回类型
	 */
	public static boolean isAfter(String formatType,String sourceDate,String targetDate,Integer subStringNum){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(sourceDate.substring(0, subStringNum), format).isAfter(DateTime.parse(targetDate.substring(0, subStringNum), format));
	}

	/**
	 * compareToDate
	 * (比较时间大小)
	 * @param formatType 时间格式
	 * @param sourceDate 源时间
	 * @param targetDate 目标时间
	 * @param subStringNum 比较类型
	 * @return boolean    返回类型
	 */
	public static boolean isBefore(String formatType,String sourceDate,String targetDate,Integer subStringNum){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(sourceDate.substring(0, subStringNum), format).isBefore(DateTime.parse(targetDate.substring(0, subStringNum), format));
	}

	public static boolean isBefore(String formatType,Date sourceDate,Date targetDate,Integer subStringNum){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(new DateTime(sourceDate).toString(formatType).substring(0, subStringNum), format).isBefore(DateTime.parse(new DateTime(targetDate).toString(formatType).substring(0, subStringNum), format));
	}

	public static boolean isAfter(String formatType,Date sourceDate,Date targetDate,Integer subStringNum){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(new DateTime(sourceDate).toString(formatType).substring(0, subStringNum), format).isAfter(DateTime.parse(new DateTime(targetDate).toString(formatType).substring(0, subStringNum), format));
	}

	public static boolean isBefore(Date sourceDate,Date targetDate){
		return new DateTime(sourceDate).isBefore(new DateTime(targetDate));
	}

	public static boolean isAfter(Date sourceDate,Date targetDate){
		return new DateTime(sourceDate).isAfter(new DateTime(targetDate));
	}

	/**
	 * replyDate
	 * (获取好久以前)
	 * @param formatType String
	 * @param sourceDate String
	 * @return String    返回类型
	 */
	public static String replyDate(String formatType,String sourceDate){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		Long millis = System.currentTimeMillis()-DateTime.parse(sourceDate, format).getMillis();
		long day=millis/(24*60*60*1000);
		long hour=(millis/(60*60*1000)-day*24);
		long min=((millis/(60*1000))-day*24*60-hour*60);
		long s=(millis/1000-day*24*60*60-hour*60*60-min*60);
		if(day>0){
			return day+"天前";
		}else if(hour>0){
			return hour+"小时前";
		}else if(min>0){
			return min+"分钟前";
		}else{
			return s+"秒钟前";
		}
	}

	/**
	 * 判断 这个时间，是否是这个月的末尾
	 * @param strTime DateTime
	 * @return
	 * boolena
	 */
	public static boolean checkTheEndOfMonth(DateTime strTime){
		return strTime.getMonthOfYear()!=strTime.plusDays(1).getMonthOfYear();
	}

	/**
	 * 时间戳转日期
	 * @param source Long
	 * @return
	 * Date
	 */
	public static Date convertLongToDate(Long source){
		return new DateTime(source).toDate();
	}

	/**
	 * 获取当前时间戳
	 * @return
	 * Long
	 */
	public static Long getDateToTimestamp(){
		return new DateTime().getMillis();
	}

	/**
	 * 字符时间转时间对象
	 * @param source String
	 * @param formatType String
	 * @return
	 * Date
	 */
	public static Date convertStringToDate(String source,String formatType){
		if (StringUtils.isBlank(source)){
			return null;
		}
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		return DateTime.parse(source,format).toDate();
	}

	/**
	 * 获取年数
	 * @param stringDate 字符串型时间对象
	 * @param date 时间对象
	 * @param formatType 时间格式
	 * @return
	 * int
	 */
	public static int getYear(String stringDate,Date date,String formatType){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		if (StringUtils.isBlank(stringDate)&&null==date) {
			return DateTime.now().getYear();
		} else if (StringUtils.isNotBlank(stringDate)) {
			return DateTime.parse(stringDate,format).getYear();
		} else {
			return new DateTime(date).getYear();
		}
	}

	public static int getYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH)+1;
	}

	public static String[] getLastMonth(Date date){
		String[] ym = new String[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,-1);
		ym[0] = String.valueOf(calendar.get(Calendar.YEAR));
		int m = calendar.get(Calendar.MONTH) + 1;
		if (m<10){
			ym[1] = "0"+String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}else{
			ym[1] = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		return ym;
	}

	public static String[] getLastMonth(Integer year, Integer month){
		String[] ym = new String[2];
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.MONTH,month);
		calendar.add(Calendar.MONTH,-2);
		ym[0] = String.valueOf(calendar.get(Calendar.YEAR));
		int m = calendar.get(Calendar.MONTH) + 1;
		if (m<10){
			ym[1] = "0"+String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}else{
			ym[1] = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		}
		return ym;
	}

	public static String[] getNextMonth(Integer year, Integer month){
		String[] ym = new String[2];
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.MONTH,month);
		ym[0] = String.valueOf(calendar.get(Calendar.YEAR));
		int m = calendar.get(Calendar.MONTH)+1;
		System.out.println(m);
		if (m<10){
			ym[1] = "0"+String.valueOf(calendar.get(Calendar.MONTH)+1);
		}else{
			ym[1] = String.valueOf(calendar.get(Calendar.MONTH)+1);
		}
		return ym;
	}

	public static Date getNextMonthFirstDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,1);
		calendar.set(Calendar.DATE,1);
		return calendar.getTime();
	}

	public static Date getNextMonthLastDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DATE));
		calendar.add(Calendar.MONTH,1);
		return calendar.getTime();
	}

	public static int getSeason(Date date) {
		int season = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
		}
		return season;
	}
	/**
	 * 获取月数
	 * @param stringDate 字符串型时间对象
	 * @param date 时间对象
	 * @param formatType 时间格式
	 * @return
	 * int
	 */
	public static int getMonthOfYear(String stringDate,Date date,String formatType){
		formatType = StringUtils.isBlank(formatType)?YYYY_MM_DD_HH_MM_SS:formatType;
		format = DateTimeFormat.forPattern(formatType);
		if (StringUtils.isBlank(stringDate)&&null==date) {
			return DateTime.now().getMonthOfYear();
		} else if (StringUtils.isNotBlank(stringDate)) {
			return DateTime.parse(stringDate,format).getMonthOfYear();
		} else {
			return new DateTime(date).getMonthOfYear();
		}
	}

	/**
	 * 计算两个时间的小时差
	 * @param date 大的时间
	 * @param date2 小的时间
	 * @return
	 */
	public static int getTimeHourDifference(Date date,Date date2){
		Long len = (date.getTime() - date2.getTime()) / (1000 * 60 * 60);
		return len.intValue();
	}

	/**
	 * 计算相差月份
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getMonthSpace(Date startDate, Date endDate){
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		int result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		int month = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12;
		return Math.abs(month + result);
	}

	/**
	 * 计算相差年份
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getYearSpace(Date startDate, Date endDate){
		if (null == startDate || null ==endDate)
			return 0;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		return Math.abs(c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));
	}


	public static String[] getYearMonth(Date date){
		String[] ym = new String[2];
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		ym[0] = String.valueOf(calendar.get(Calendar.YEAR));
		int m = calendar.get(Calendar.MONTH)+1;
		System.out.println(m);
		if (m<10){
			ym[1] = "0"+String.valueOf(calendar.get(Calendar.MONTH)+1);
		}else{
			ym[1] = String.valueOf(calendar.get(Calendar.MONTH)+1);
		}
		return ym;
	}

	/**
	 * 根据日期获得星期
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
//		String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

    /**
     * 获取第一天/最后一天日期字符串
     * @param valType
     * @param formatType
     * @return
     */
    public static String getMinOrMaxDate(String valType,String formatType){
        DateTime dateTime=new DateTime();
        if (valType.equals(min)){
            return dateTime.dayOfMonth().withMinimumValue().toString(formatType);
        } else{
            return dateTime.dayOfMonth().withMaximumValue().toString(formatType);
        }
        //System.out.println(dateTime.dayOfMonth().withMinimumValue().dayOfMonth().get());
        //System.out.println(dateTime.dayOfMonth().withMaximumValue().dayOfMonth().get());
    }

	/**
	 * 获取某个时间所在月第一天
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,0);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}

	public static Date getYesterday(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-1);
		return cal.getTime();
	}

	public static Date getYesterdayEnd(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE,-1);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		return cal.getTime();
	}

	public static Date getFirstDateOfMonth(Integer yyyy, Integer month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,yyyy);
		calendar.set(Calendar.MONTH,month-1);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.getTime();
	}

	/**
	 * 获取某个时间所在月第一天星期几
	 * @param date
	 * @return
	 */
	public static int getFirstDateWeekOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,0);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取最后一天时间数
	 * @param date
	 * @return
	 */
	public static int getLastDateNumOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date getLastDateOfMonth(Integer yyyy, Integer month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,yyyy);
		calendar.set(Calendar.MONTH,month-1);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getLastDateOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static void main(String[] args) {
		/*System.out.println(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYYMMDDHHMMSS));
		System.out.println(DateTimeUtil.dateTimeToString(DateTimeUtil.YYYYMMDDHHMMSS_SSS));
		System.out.println(DateTimeUtil.isBefore(new Date(),DateTimeUtil.convertStringToDate("2016-10-28 16:50:58",DateTimeUtil.YYYY_MM_DD_HH_MM_SS)));
		System.out.println(DateTime.now());
		System.out.println(DateTime.now().getYear());
		System.out.println(DateTime.now().getMonthOfYear());
		System.out.println(DateTimeUtil.getMonthOfYear(null,null,null));
		System.out.println(DateTimeUtil.getMonthOfYear("2016-09-28 16:50:58",null,null));
		System.out.println(DateTimeUtil.getMonthOfYear(null,new Date(),null));*/
//		Long l = 0L;
//		System.out.println(l>0);
//		System.out.println(null!=l);
//		System.out.println(Arrays.toString(DateTimeUtil.getLastMonth(2017,3)));
		//System.out.println(getLastDateNumOfMonth(new Date()));
		/*System.out.println(DateTimeUtil.isBefore(DateTimeUtil.YYYY_MM_DD,"2017-05-27 01:00:00","2017-05-27 01:00:00",10));
		Date date = new Date();
		String s = DateTimeUtil.dateToString(date, DateTimeUtil.YYYY_MM_DD_HH_MM_SS);
		System.out.println(dateTimePlusNum(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, s, DateTimeUtil.PLUSTYPE_DAY, -1));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String forceDate = dateFormat.format(new Date());     //获取执行时的时间
		String nowDate = DateTimeUtil.dateTimePlusNum(DateTimeUtil.YYYY_MM_DD_HH_MM_SS, forceDate, DateTimeUtil.PLUSTYPE_DAY, -1);
		System.out.println(nowDate + "ss");*/
//        System.out.println(DateTimeUtil.getMinOrMaxDate(DateTimeUtil.max,DateTimeUtil.YYYY_MM_DD));
//		System.out.println(DateTimeUtil.dateToString(DateTimeUtil.getYesterdayEnd(new Date()),"yyyy-MM-dd HH:mm:ss"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int i = calendar.get(calendar.DAY_OF_MONTH);
		System.out.println(i);
    }



}
