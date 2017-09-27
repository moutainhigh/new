package com.huayu.signWechat.utils;

import com.huayu.common.tool.DateTimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
public class HolidayUitl {
    /**
     * <p>Title: main </P>
     * <p>Description: TODO </P>
     * @param args
     * return void    返回类型
     * throws
     * date 2014-11-24 上午09:11:47
     */
    public static void main(String[] args) {
        try {


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            List<Calendar> holidayList = getDayListInScope(df.parse("2017-04-02"), df.parse("2017-04-04"));
            /*for (Calendar cc : holidayList) {
                System.out.println(cc.get(Calendar.YEAR)+"-"+(cc.get(Calendar.MONTH)+1)+"-"+cc.get(Calendar.DAY_OF_MONTH));
            }
            System.out.println(holidayList);*/

            /*List<Calendar> weekendAndHoliday = getWeekendAndHoliday(df.parse("2017-03-01"),df.parse("2017-06-16"), holidayList);
            for (Calendar can : weekendAndHoliday) {
                System.out.println(can.get(Calendar.YEAR)+"-"+(can.get(Calendar.MONTH)+1)+"-"+can.get(Calendar.DAY_OF_MONTH));
            }*/
            Date parse = sdf.parse("2017-08-27 01:00:01");
            Date parse1 = sdf.parse("2017-08-27 01:00:01");
            Date parse2 = sdf.parse("2017-08-27 01:00:01");
            boolean inDate = isInDate(parse, parse1, parse2);
            System.out.println(inDate);
            Date parse3 = sdf.parse("2017-07-18 12:22:50");
            Date parse4 = sdf.parse("2017-07-18 18:23:08");
            List<Calendar> dayListInScope = getDayListInScope(parse3, parse4);

            System.out.println(dayListInScope.size());
//            System.out.println(getDayListInScope(parse3, parse4).size());

        } catch ( Exception e) {
            // TODO: handle exception

            e.printStackTrace();
        }

    }

    /**
     *
     * <p>Title: checkHoliday </P>
     * <p>Description: TODO 验证日期是否是节假日</P>
     * @param calendar  传入需要验证的日期
     * @return
     * return boolean    返回类型  返回true是节假日，返回false不是节假日
     * throws
     * date 2014-11-24 上午10:13:07
     */
    public static boolean checkHoliday(Calendar calendar, List<Calendar> holidayList) throws Exception{

        //判断日期是否是周六周日
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            return true;
        }
        //判断日期是否是节假日
        for (Calendar ca : holidayList) {
            if(ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)&&
                    ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
                return true;
            }
        }

        return false;
    }

    /**
     * 根据某两个时间获取这两个时间之间的每天
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Calendar> getDayListInScope(Date startDate, Date endDate){
        List<Calendar> calendarList = new ArrayList<>();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startDate);
        int yearStart = calendarStart.get(Calendar.YEAR);
        int monthStart = calendarStart.get(Calendar.MONTH);
        int dayStart = calendarStart.get(Calendar.DAY_OF_MONTH);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endDate);
        int yearEnd = calendarEnd.get(Calendar.YEAR);
        int monthEnd = calendarEnd.get(Calendar.MONTH);
        int dayEnd = calendarEnd.get(Calendar.DAY_OF_MONTH);
        if (monthEnd == monthStart) {
            for (int i = dayStart;i<=dayEnd;i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, yearStart);
                calendar.set(Calendar.MONTH, monthStart);// 月份比正常小1,0代表一月
                calendar.set(Calendar.DAY_OF_MONTH, i);
                calendarList.add(calendar);
            }
        } else if (monthStart < monthEnd) {
            int lastDateNumOfMonth = DateTimeUtil.getLastDateNumOfMonth(startDate);
            for (int i = dayStart;i<= lastDateNumOfMonth;i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, yearStart);
                calendar.set(Calendar.MONTH, monthStart);// 月份比正常小1,0代表一月
                calendar.set(Calendar.DAY_OF_MONTH, i);
                calendarList.add(calendar);
            }
            for (int i = 1;i <= dayEnd;i++) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, yearStart);
                calendar.set(Calendar.MONTH, monthEnd);// 月份比正常小1,0代表一月
                calendar.set(Calendar.DAY_OF_MONTH, i);
                calendarList.add(calendar);
            }
        }
        return calendarList;
    }

    /**
     * 获取某月的指定时段所有法定假和周末
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param holidayList 某年所有的法定假日
     * @return
     * @throws Exception
     */
    public static List<Calendar> getWeekendAndHoliday(Date startDate, Date endDate, List<Calendar> holidayList) throws Exception {
        List<Calendar> weekendAndHolidayList = new ArrayList<>();
//        int lastDay = DateTimeUtil.getLastDateNumOfMonth(startDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int startYear = calendar.get(Calendar.YEAR);
        int startMonth = calendar.get(Calendar.MONTH);
        int startDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(endDate);
        int endtYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH);
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (startMonth == endMonth) {
            for (int i = startDay;i<=endDay;i++) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, startYear);
                c.set(Calendar.MONTH, startMonth);
                c.set(Calendar.DAY_OF_MONTH, i);
                boolean b = checkHoliday(c, holidayList);
                if (b) {
                    weekendAndHolidayList.add(c);
                }
            }
        } else if (startMonth < endMonth) {
            for (int i = startMonth;i <= endMonth;i++) {
                if (i == startMonth) {
                    int lastDay = DateTimeUtil.getLastDateNumOfMonth(startDate);
                    for (int j = startDay;j <= lastDay;j++) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, startYear);
                        c.set(Calendar.MONTH, i);
                        c.set(Calendar.DAY_OF_MONTH, j);
                        boolean b = checkHoliday(c, holidayList);
                        if (b) {
                            weekendAndHolidayList.add(c);
                        }
                    }
                } else if (i < endMonth){
                    int m = i + 1;
                    int lastDay = DateTimeUtil.getLastDateNumOfMonth(DateTimeUtil.convertStringToDate(startYear+"-"+(i+1)+"-"+"01", DateTimeUtil.YYYY_MM_DD));
                    for (int j = 1;j <= lastDay;j++) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, startYear);
                        c.set(Calendar.MONTH, i);
                        c.set(Calendar.DAY_OF_MONTH, j);
                        boolean b = checkHoliday(c, holidayList);
                        if (b) {
                            weekendAndHolidayList.add(c);
                        }
                    }
                } else {
                    for (int j = 1;j <= endDay;j++) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, startYear);
                        c.set(Calendar.MONTH, i);
                        c.set(Calendar.DAY_OF_MONTH, j);
                        boolean b = checkHoliday(c, holidayList);
                        if (b) {
                            weekendAndHolidayList.add(c);
                        }
                    }
                }

            }
        }
        return weekendAndHolidayList;
    }

    /**
     * 判断某天是否在另一个时间段
     * @param date
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean isInDate(Date date, Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(date);
        String strDateBegin = sdf.format(startDate);
        String strDateEnd = sdf.format(endDate);
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(0, 4));
        int strDateM = Integer.parseInt(strDate.substring(5, 7));
        int strDateS = Integer.parseInt(strDate.substring(8, 10));
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 4));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(5, 7));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(8, 10));
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 4));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(5, 7));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(8, 10));

        if (strDateBeginH == strDateEndH && strDateBeginH == strDateH) {
            if (strDateBeginM == strDateEndM && strDateBeginM == strDateM) {
                if (strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                    return true;
                } else {
                    return false;
                }
            } else if (strDateBeginM < strDateEndM && strDateBeginM <= strDateM && strDateM <= strDateEndM) {
                return true;
            } else {
                return false;
            }
        } else if (strDateBeginH < strDateEndH) {
            if (strDateBeginM == strDateEndM && strDateBeginM == strDateM) {
                if (strDateS >= strDateBeginS && strDateS <= strDateEndS) {
                    return true;
                } else {
                    return false;
                }
            } else if (strDateBeginM < strDateEndM && (strDateBeginM <= strDateM && strDateM <= strDateEndM)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }



    }

}
