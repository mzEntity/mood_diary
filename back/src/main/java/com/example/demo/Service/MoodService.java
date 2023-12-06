package com.example.demo.Service;


import com.example.demo.repository.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoodService {
    private MoodRepository moodRepository;

    @Autowired
    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    public boolean isMoodExist(int moodId){
        return moodRepository.findMoodById(moodId) != null;
    }
    public boolean isUserExist(String name){
        return moodRepository.findMoodByName(name) != null;
    }
}
