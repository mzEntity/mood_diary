package com.example.demo.controller.diary;

import com.example.demo.Service.DiaryService;
import com.example.demo.Service.MoodService;
import com.example.demo.Service.UserService;
import com.example.demo.exception.FieldMismatchException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.transfer.DiaryDTO;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommitDiaryController {

    private UserService userService;
    private DiaryService diaryService;
    private MoodService moodService;

    @Autowired
    public CommitDiaryController(UserService userService, DiaryService diaryService, MoodService moodService){
        this.userService = userService;
        this.diaryService = diaryService;
        this.moodService = moodService;
    }
    @PostMapping("/commitDiary")
    public Result commitDiary(@RequestBody CommitDiaryPackage commitDiaryPackage){
        int author_id = commitDiaryPackage.getAuthor_id();
        int mood_type_id = commitDiaryPackage.getMood_type_id();
        String title = commitDiaryPackage.getTitle();
        String content = commitDiaryPackage.getContent();
        String avatar = commitDiaryPackage.getAvatar();
        boolean author_exist = this.userService.isUserExist(author_id);
        if(!author_exist){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }

        boolean mood_type_exist = this.moodService.isMoodExist(mood_type_id);
        if(!mood_type_exist){
            return ResultFactory.buildFailResult(null, "Mood type doesn't exist.");
        }

        DiaryDTO diaryDTO = new DiaryDTO(author_id, mood_type_id, title, content, avatar, MyUtils.getCurrentTimestamp());
        try{
            int diaryId = this.diaryService.createDiary(diaryDTO);
            return ResultFactory.buildSuccessResult(diaryId);
        } catch(NewEntityException e){
            return ResultFactory.buildFailResult(null, e.getMessage());
        }
    }
}


class CommitDiaryPackage{
    private int author_id;
    private int mood_type_id;
    private String title;
    private String content;

    private String avatar;

    public int getAuthor_id() {
        return author_id;
    }

    public int getMood_type_id() {
        return mood_type_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAvatar() {
        return avatar;
    }
}
