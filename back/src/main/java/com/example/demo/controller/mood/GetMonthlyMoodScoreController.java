package com.example.demo.controller.mood;


import com.example.demo.Service.MoodScoreService;
import com.example.demo.Service.UserService;
import com.example.demo.model.MoodScore;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetMonthlyMoodScoreController {

    private UserService userService;
    private MoodScoreService moodScoreService;

    @Autowired
    public GetMonthlyMoodScoreController(UserService userService, MoodScoreService moodScoreService) {
        this.userService = userService;
        this.moodScoreService = moodScoreService;
    }

    @GetMapping("/getMonthlyMoodScore")
    private Result getMonthlyMoodScore(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "year") Integer year,
            @RequestParam(value = "month") Integer month){
        User user = this.userService.getUserByUserId(userId);
        if(user == null){
            return ResultFactory.buildFailResult(null, "no such user");
        }

        List<MoodScore> allScores = this.moodScoreService.getMonthlyMoodScore(userId, year, month);
        Package p = new Package(userId, user.getUsername(), year, month, allScores);
        return ResultFactory.buildSuccessResult(p);
    }

    private static class Package {
        private int userId;
        private String userName;

        private int year;

        private int month;

        private List<MoodScore> moodScores;

        public Package(int userId, String userName, int year, int month, List<MoodScore> moodScores) {
            this.userId = userId;
            this.userName = userName;
            this.year = year;
            this.month = month;
            this.moodScores = moodScores;
        }

        public int getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public List<MoodScore> getMoodScores() {
            return moodScores;
        }
    }
}
