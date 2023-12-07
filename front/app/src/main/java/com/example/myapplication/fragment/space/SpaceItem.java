package com.example.myapplication.fragment.space;

public class SpaceItem {
    private int diaryId;
    private String author;
    private String date;
    private String title;

    private String content;

    public SpaceItem(int diaryId, String author, String date, String title, String content) {
        this.diaryId = diaryId;
        this.author = author;
        this.date = date;
        this.title = title;
        this.content = content;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}
