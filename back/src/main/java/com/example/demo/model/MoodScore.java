package com.example.demo.model;

import com.example.demo.transfer.DiaryDTO;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;


@Entity
@Table(name="mood_score")
public class MoodScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "mood_score")
    private Integer moodScore;

    @Column(name = "date")
    private Date date;

    public MoodScore(Integer id, Integer userId, Integer moodScore, Date date) {
        this.id = id;
        this.userId = userId;
        this.moodScore = moodScore;
        this.date = date;
    }

    public MoodScore(Integer userId, Integer moodScore, Date date) {
        this.userId = userId;
        this.moodScore = moodScore;
        this.date = date;
    }

    public MoodScore() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMoodScore() {
        return moodScore;
    }

    public void setMoodScore(Integer moodScore) {
        this.moodScore = moodScore;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}