package com.example.myapplication.common;

public enum Mood {
    JOY(1, "开心", "happy"),
    ANTICIPATE(2, "期待", "anticipate"),
    SAD(3, "难过", "sad"),
    CALM(4, "平静", "calm"),

    HAPPY(5, "幸福", "happy"),
    REGRET(6, "后悔", "regret");

    private int id;
    private String name;
    private String english;

    private Mood(int id, String name, String english){
        this.id = id;
        this.name = name;
        this.english = english;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEnglish() {
        return english;
    }
}
