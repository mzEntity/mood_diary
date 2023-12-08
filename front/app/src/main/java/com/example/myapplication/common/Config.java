package com.example.myapplication.common;

import java.util.HashMap;

public class Config {
    public static final String httpBasePath = "http://192.168.4.6:8080";
//    public static final String httpBasePath = "http://10.223.231.244:8080";
    public static final HashMap<Integer, Mood> moodMap = new HashMap<>();

    public static final HashMap<Integer, String> requestMap = new HashMap<>();

    public static final HashMap<String, Integer> requestMapIS = new HashMap<>();

    static {
        moodMap.put(1, Mood.JOY);
        moodMap.put(2, Mood.ANTICIPATE);
        moodMap.put(3, Mood.SAD);
        moodMap.put(4, Mood.CALM);
        moodMap.put(5, Mood.HAPPY);
        moodMap.put(6, Mood.REGRET);

        requestMap.put(1, "待审核");
        requestMap.put(2, "已通过");
        requestMap.put(3, "已拒绝");
        requestMapIS.put("待审核", 1);
        requestMapIS.put("已通过", 2);
        requestMapIS.put("已拒绝", 3);
    }
}
