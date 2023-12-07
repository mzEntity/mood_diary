package com.example.demo.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

public class MyUtils {

    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static String timestampToString(Timestamp target){
        return target.toString();
    }

    public static Date getCurrentDate(){
        return Date.valueOf(LocalDate.now());
    }
    public static Date getStartDateOfMonth(int year, int month){
        LocalDate localDate = LocalDate.of(year, month, 1);
        return Date.valueOf(localDate);
    }

    public static Date getStartDateOfNextMonth(int year, int month){
        LocalDate localDate = LocalDate.of(year, month, 1).plusMonths(1);
        return Date.valueOf(localDate);
    }
}
