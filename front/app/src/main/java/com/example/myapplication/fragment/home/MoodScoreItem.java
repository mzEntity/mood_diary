package com.example.myapplication.fragment.home;

public class MoodScoreItem {
    private int year;
    private int month;

    private int day;

    private int score;

    public MoodScoreItem(int year, int month, int day, int score) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.score = score;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
