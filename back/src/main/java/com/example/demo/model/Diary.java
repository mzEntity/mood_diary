package com.example.demo.model;


import com.example.demo.transfer.DiaryDTO;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "mood_type_id")
    private Integer moodTypeId;
    private String title;
    private String content;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Diary(DiaryDTO diaryDTO) {
        this.id = diaryDTO.getId();
        this.authorId = diaryDTO.getAuthor_id();
        this.moodTypeId = diaryDTO.getMood_type_id();
        this.title = diaryDTO.getTitle();
        this.content = diaryDTO.getContent();
        this.updatedAt = diaryDTO.getUpdated_at();
    }

    public Diary(Integer id, Integer authorId, Integer moodTypeId, String title, String content, Timestamp updatedAt) {
        this.id = id;
        this.authorId = authorId;
        this.moodTypeId = moodTypeId;
        this.title = title;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public Diary() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getMoodTypeId() {
        return moodTypeId;
    }

    public void setMoodTypeId(Integer moodTypeId) {
        this.moodTypeId = moodTypeId;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}