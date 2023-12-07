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
}
