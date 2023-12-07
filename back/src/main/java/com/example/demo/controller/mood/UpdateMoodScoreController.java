package com.example.demo.controller.mood;

import com.example.demo.model.User;
import com.example.demo.Service.MoodScoreService;

import com.example.demo.Service.UserService;
import com.example.demo.exception.NewEntityException;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;

import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateMoodScoreController {

    private final UserService userService;
    private final MoodScoreService moodScoreService;

    @Autowired
    public UpdateMoodScoreController(UserService userService, MoodScoreService moodScoreService) {
        this.userService = userService;
        this.moodScoreService = moodScoreService;
    }


    @PostMapping("/updateMoodScore")
    private Result commitDiary(@RequestBody UpdateMoodScoreRequestPackage requestPackage){
        int userId = requestPackage.getUserId();
        int score = requestPackage.getScore();

        User user = this.userService.getUserByUserId(userId);
        if(user == null){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }

        try{
            int moodScoreId = this.moodScoreService.updateMoodScore(userId, score, MyUtils.getCurrentDate());
            return ResultFactory.buildSuccessResult(moodScoreId);
        } catch(NewEntityException e){
            return ResultFactory.buildFailResult(null, e.getMessage());
        }
    }

    private static class UpdateMoodScoreRequestPackage{
        private int userId;
        private int score;

        public int getUserId() {
            return userId;
        }

        public int getScore() {
            return score;
        }
    }
}
