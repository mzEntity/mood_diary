package com.example.demo.Service;

import com.example.demo.exception.NewEntityException;
import com.example.demo.model.MoodScore;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.MoodScoreRepository;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Component
public class MoodScoreService {
    private MoodScoreRepository moodScoreRepository;

    @Autowired
    public MoodScoreService(MoodScoreRepository moodScoreRepository) {
        this.moodScoreRepository = moodScoreRepository;
    }

    public MoodScore getMoodScoreByUserIdAndDate(int userId, Date date){
        return moodScoreRepository.getMoodScoreByUserIdAndDate(userId, date);
    }


    public int updateMoodScore(int userId, int score, Date date) throws NewEntityException{
        MoodScore moodScore = this.getMoodScoreByUserIdAndDate(userId, date);
        if(moodScore == null){
            moodScore = new MoodScore(userId, score, date);
        } else {
            moodScore.setMoodScore(score);
        }
        try {
            return moodScoreRepository.save(moodScore).getId();
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }

    public List<MoodScore> getMonthlyMoodScore(int userId, int year, int month){
        Date startDate = MyUtils.getDateFromLocalDate(MyUtils.getStartLocalDateOfMonth(year, month));
        Date endDate = MyUtils.getDateFromLocalDate(MyUtils.getStartLocalDateOfNextMonth(year, month).minusDays(1));
        return this.moodScoreRepository.getMoodScoresByUserIdAndDateBetween(
                userId, startDate, endDate
        );
    }
}
