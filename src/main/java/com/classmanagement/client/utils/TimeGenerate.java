package com.classmanagement.client.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Network_Management_Association
 *
 * @author Richard-J
 * @description 生成时间
 * @date 2019.03
 */

public class TimeGenerate {

    public static String getTime(){
        String time;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY.MM.dd HH:mm");
        time = dateFormat.format(date);
        return time;
    }

    public static Date getDay(){
        String hour;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        hour = dateFormat.format(date);
        if(Integer.parseInt(hour)>8) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
        }
        return date;
    }
}