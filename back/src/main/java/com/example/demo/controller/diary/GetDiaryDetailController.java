package com.example.demo.controller.diary;

import com.example.demo.Service.DiaryService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Diary;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetDiaryDetailController {

    private UserService userService;
    private DiaryService diaryService;


    @Autowired
    public GetDiaryDetailController(UserService userService, DiaryService diaryService) {
        this.userService = userService;
        this.diaryService = diaryService;
    }


    @GetMapping("/getDiaryDetail")
    public Result getDiaryDetail(
            @RequestParam(value = "diaryId") Integer diaryId
    ){
        Diary diary = this.diaryService.getDiaryByDiaryId(diaryId);
        if(diary == null){
            return ResultFactory.buildFailResult(null, "Diary doesn't exist.");
        }

        User author = this.userService.getUserByUserId(diary.getAuthorId());
        GetDiaryDetailResponsePackage result = new GetDiaryDetailResponsePackage(
                diary.getId(),
                diary.getAuthorId(),
                author.getUsername(),
                MyUtils.timestampToString(diary.getUpdatedAt()),
                diary.getTitle(),
                diary.getContent(),
                diary.getAvatar(),
                diary.getMoodTypeId()
        );
        return ResultFactory.buildSuccessResult(result);
    }
}

class GetDiaryDetailResponsePackage{
    private int diaryId;
    private int authorId;
    private String authorName;
    private String issueDate;
    private String title;
    private String content;

    private String avatar;
    private int moodTypeId;

    public GetDiaryDetailResponsePackage(int diaryId, int authorId, String authorName, String issueDate, String title, String content, String avatar, int moodTypeId) {
        this.diaryId = diaryId;
        this.authorId = authorId;
        this.authorName = authorName;
        this.issueDate = issueDate;
        this.title = title;
        this.content = content;
        this.avatar = avatar;
        this.moodTypeId = moodTypeId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getMoodTypeId() {
        return moodTypeId;
    }

    public String getAvatar() {
        return avatar;
    }
}

