package com.example.demo.Service;


import com.example.demo.exception.EntityDuplicateException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.model.Diary;
import com.example.demo.model.Mood;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.DiaryDTO;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

@Component
public class DiaryService {
    private UserRepository userRepository;
    private DiaryRepository diaryRepository;
    private MoodRepository moodRepository;

    @Autowired
    public DiaryService(UserRepository userRepository, DiaryRepository diaryRepository, MoodRepository moodRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        this.moodRepository = moodRepository;
    }

    public int createDiary(DiaryDTO diaryDTO) throws NewEntityException{
        Diary newDiary = new Diary(diaryDTO);
        return createNewDiary(newDiary);
    }

    private int createNewDiary(Diary diary) throws NewEntityException {
        try {
            return diaryRepository.save(diary).getId();
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }

    public HashMap<Integer, List<Diary>> getAllDiaries(){
        List<Integer> idList = userRepository.getAllIds();
        return this.getAllDiaries(idList);
    }

    public HashMap<String, Integer> getMonthlyDiaryCount(int userId, int year, int month){
        List<Mood> allMoods = this.moodRepository.findAll();
        Timestamp startTime = MyUtils.getStartTimeStampOfLocalDate(MyUtils.getStartLocalDateOfMonth(year, month));
        Timestamp endTime = MyUtils.getEndTimeStampOfLocalDate(MyUtils.getStartLocalDateOfNextMonth(year, month).minusDays(1));
        HashMap<String, Integer> result = new HashMap<>();
        for(Mood mood: allMoods){
            List<Diary> diaryOfCurrentMood = this.diaryRepository.getDiariesByAuthorIdAndMoodTypeIdAndUpdatedAtBetween(
                    userId, mood.getId(), startTime, endTime
            );
            result.put(mood.getName(), diaryOfCurrentMood.size());
        }
        return result;
    }

    public List<Diary> getUserDiaries(int id){
        return diaryRepository.findDiariesByAuthorId(id);
    }
    private HashMap<Integer, List<Diary>> getAllDiaries(List<Integer> idList){
        HashMap<Integer, List<Diary>> result = new HashMap<>();
        for(int author_id: idList){
            List<Diary> diaries = diaryRepository.findDiariesByAuthorId(author_id);
            if(diaries.isEmpty()){
                continue;
            }
            result.put(author_id, diaries);
        }
        return result;
    }

    public Diary getDiaryByDiaryId(int id){
        return diaryRepository.findDiaryById(id);
    }
}
