package com.example.demo.repository;

import com.example.demo.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface DiaryRepository  extends JpaRepository<Diary, Integer> {

    Diary findDiaryById(int id);

    List<Diary> findDiariesByAuthorIdOrderByUpdatedAtDesc(int id);

    List<Diary> getDiariesByAuthorIdAndMoodTypeIdAndUpdatedAtBetween(Integer authorId, Integer moodTypeId, Timestamp startTime, Timestamp endTime);
}
