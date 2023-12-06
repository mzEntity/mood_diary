package com.example.myapplication.fragment.space;

public class SpaceItem {
    private String author;
    private String date;
    private String title;

    private String content;

    public SpaceItem(String author, String date, String title, String content) {
        this.author = author;
        this.date = date;
        this.title = title;
        this.content = content;
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
