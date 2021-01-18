package com.utils;

/**
 * @author wqr
 * @data 2020-10-19 - 10:10
 */
public class DateTimeUtils {
    /**
     * 根据一个时间日期字符串获得日期 小时 分钟
     * @param dateTime 时间日期字符串
     */
    public static String[] getDateHourAndMinutes(String dateTime) {
        int spaceIndex = dateTime.indexOf(" ");
        int hourEndIndex = dateTime.indexOf(":");
        int minutedEndIndex = dateTime.lastIndexOf(":");
        String date = dateTime.substring(0,spaceIndex);
        date = date.replaceAll("-", "/");
        String hour = dateTime.substring(spaceIndex + 1, hourEndIndex);
        String minutes = dateTime.substring(hourEndIndex+1, minutedEndIndex);
        return new String[] {date,hour,minutes};
    }

}
