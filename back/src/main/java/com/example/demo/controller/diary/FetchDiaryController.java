package com.example.demo.controller.diary;

import com.example.demo.Service.DiaryService;
import com.example.demo.Service.MoodService;
import com.example.demo.Service.UserService;
import com.example.demo.exception.NewEntityException;
import com.example.demo.model.Diary;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.transfer.DiaryDTO;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class FetchDiaryController {

    private UserService userService;
    private DiaryService diaryService;

    @Autowired
    public FetchDiaryController(UserService userService, DiaryService diaryService){
        this.userService = userService;
        this.diaryService = diaryService;
    }

    @GetMapping("/getUserDiary")
    public Result getUserDiary(
            @RequestParam(value = "userId") Integer userId
    ){

        User user = this.userService.getUserByUserId(userId);
        if(user == null){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }
        List<Diary> allDiaries = this.diaryService.getUserDiaries(userId);
        GetUserDiaryResponsePackage getUserDiaryResponsePackage = new GetUserDiaryResponsePackage(userId, user.getUsername(), allDiaries);
        return ResultFactory.buildSuccessResult(getUserDiaryResponsePackage);
    }
}

class GetUserDiaryResponsePackage{
    private int userId;
    private String name;
    private List<Diary> allDiaries;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public List<Diary> getAllDiaries() {
        return allDiaries;
    }

    public GetUserDiaryResponsePackage(int userId, String name, List<Diary> allDiaries) {
        this.userId = userId;
        this.name = name;
        this.allDiaries = allDiaries;
    }
}
