package com.example.myapplication.fragment.home;

public class DiaryStatsItem {
    private int moodId;
    private String moodName;
    private int count;
    private int year;
    private int month;

    public DiaryStatsItem(int moodId, String moodName, int count, int year, int month) {
        this.moodId = moodId;
        this.moodName = moodName;
        this.count = count;
        this.year = year;
        this.month = month;
    }

    public int getMoodId() {
        return moodId;
    }

    public void setMoodId(int moodId) {
        this.moodId = moodId;
    }

    public String getMoodName() {
        return moodName;
    }

    public void setMoodName(String moodName) {
        this.moodName = moodName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}

