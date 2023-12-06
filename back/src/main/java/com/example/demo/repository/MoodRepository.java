package com.example.demo.repository;

import com.example.demo.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodRepository extends JpaRepository<Mood, Integer> {
    Mood findMoodById(int moodId);

    Mood findMoodByName(String name);

    List<Mood> findAll();
}
