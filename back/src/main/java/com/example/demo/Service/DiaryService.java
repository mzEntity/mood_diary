package com.example.demo.Service;


import com.example.demo.exception.EntityDuplicateException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.model.Diary;
import com.example.demo.repository.DiaryRepository;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.DiaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class DiaryService {
    private UserRepository userRepository;
    private DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(UserRepository userRepository, DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
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
