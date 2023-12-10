package com.example.demo.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public static LocalDate getStartLocalDateOfMonth(int year, int month){
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate getStartLocalDateOfNextMonth(int year, int month){
        return LocalDate.of(year, month, 1).plusMonths(1);
    }

    public static Date getDateFromLocalDate(LocalDate localDate){
        return Date.valueOf(localDate);
    }

    public static Timestamp getStartTimeStampOfLocalDate(LocalDate localDate){
        LocalDateTime startOfDay = localDate.atStartOfDay();
        return Timestamp.valueOf(startOfDay);
    }

    public static Timestamp getEndTimeStampOfLocalDate(LocalDate localDate){
        Timestamp startOfDay = MyUtils.getStartTimeStampOfLocalDate(localDate.plusDays(1));
        Instant instant = startOfDay.toInstant();
        return Timestamp.from(instant.minusMillis(1));
    }
}
