package com.example.demo.repository;

import com.example.demo.model.MoodScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.time.LocalDate;

public interface MoodScoreRepository  extends JpaRepository<MoodScore, Integer> {
    MoodScore getMoodScoreByUserIdAndDate(Integer userId, Date date);

}
