package com.example.demo.repository;

import com.example.demo.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryRepository  extends JpaRepository<Diary, Integer> {

    Diary findDiaryById(int id);

    List<Diary> findDiariesByAuthorId(int id);
}
