package com.example.demo.utils;

import java.sql.Timestamp;

public class MyUtils {

    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static String timestampToString(Timestamp target){
        return target.toString();
    }
}
