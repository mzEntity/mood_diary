package com.example.demo.controller.diary;

import com.example.demo.Service.DiaryService;
import com.example.demo.Service.MoodScoreService;
import com.example.demo.Service.UserService;
import com.example.demo.controller.mood.GetMonthlyMoodScoreController;
import com.example.demo.model.MoodScore;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class GetMonthlyDiaryStatsController {

    private UserService userService;
    private DiaryService diaryService;

    @Autowired
    public GetMonthlyDiaryStatsController(UserService userService, DiaryService diaryService) {
        this.userService = userService;
        this.diaryService = diaryService;
    }

    @GetMapping("/getMonthlyDiaryStats")
    private Result getMonthlyDiaryStats(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "year") Integer year,
            @RequestParam(value = "month") Integer month){
        User user = this.userService.getUserByUserId(userId);
        if(user == null){
            return ResultFactory.buildFailResult(null, "no such user");
        }

        HashMap<String, Integer> diaryMoodCount = this.diaryService.getMonthlyDiaryCount(userId, year, month);
        GetMonthlyDiaryStatsController.Package p = new GetMonthlyDiaryStatsController.Package(userId, user.getUsername(), year, month, diaryMoodCount);
        return ResultFactory.buildSuccessResult(p);
    }

    private static class Package {
        private int userId;
        private String userName;

        private int year;

        private int month;

        private HashMap<String, Integer> diaryCount;

        public Package(int userId, String userName, int year, int month, HashMap<String, Integer> diaryCount) {
            this.userId = userId;
            this.userName = userName;
            this.year = year;
            this.month = month;
            this.diaryCount = diaryCount;
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

        public HashMap<String, Integer> getDiaryCount() {
            return diaryCount;
        }
    }
}
