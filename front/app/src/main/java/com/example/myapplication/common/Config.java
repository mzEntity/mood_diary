package com.example.myapplication.common;

import java.util.HashMap;

public class Config {
    public static final String httpBasePath = "http://192.168.4.6:8080";

    public static final HashMap<Integer, String> moodMap = new HashMap<>();

    public static final HashMap<Integer, String> requestMap = new HashMap<>();

    public static final String waitForCheck = "1";

    static {
        moodMap.put(1, "开心");
        moodMap.put(2, "期待");
        moodMap.put(3, "难过");
        moodMap.put(4, "懊恼");
        moodMap.put(5, "平静");
        moodMap.put(6, "幸福");
        moodMap.put(7, "后悔");

        requestMap.put(1, "待审核");
        requestMap.put(2, "已通过");
        requestMap.put(3, "已拒绝");
    }
}
