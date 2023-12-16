package com.example.demo.transfer;

import java.sql.Time;
import java.sql.Timestamp;

public class DiaryDTO {
    private int id;
    private int author_id;
    private int mood_type_id;

    private String title;
    private String content;

    private String avatar;

    private Timestamp updated_at;

    public DiaryDTO(int author_id, int mood_type_id, String title, String content, String avatar, Timestamp updated_at) {
        this.author_id = author_id;
        this.mood_type_id = mood_type_id;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
        this.updated_at = updated_at;
    }

    public DiaryDTO(int id, int author_id, int mood_type_id, String title, String content, String avatar, Timestamp updated_at) {
        this.id = id;
        this.author_id = author_id;
        this.mood_type_id = mood_type_id;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getMood_type_id() {
        return mood_type_id;
    }

    public void setMood_type_id(int mood_type_id) {
        this.mood_type_id = mood_type_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
