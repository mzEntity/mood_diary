package com.example.demo.controller.friend;


import com.example.demo.DTO.FriendListItemDTO;
import com.example.demo.Service.FriendService;
import com.example.demo.Service.UserService;

import com.example.demo.exception.EntityDuplicateException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.Diary;
import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;

import com.example.demo.transfer.FriendDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class MakeFriendsController {

    private UserService userService;

    private FriendService friendService;

    @Autowired
    public MakeFriendsController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/getAllRequestsSend")
    public Result getAllRequestsSend(
            @RequestParam(value = "userId") Integer userId,
            @RequestParam(value = "stateId") Integer stateId
    ){
        boolean user_exist = this.userService.isUserExist( userId);
        if(!user_exist){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }
        boolean state_exist = this.friendService.isRequestStateExist(stateId);
        if(!state_exist){
            return ResultFactory.buildFailResult(null, "State doesn't exist.");
        }
        List<Friend> allRequests = this.friendService.getAllRequestsSend(userId, stateId);
        return ResultFactory.buildSuccessResult(allRequests);
    }


    @GetMapping("/getAllFriends")
    public Result getAllFriends(
            @RequestParam(value = "userId") Integer userId
    ){
        boolean user_exist = this.userService.isUserExist( userId);
        if(!user_exist){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }
        List<Integer> allFriends = this.friendService.getAllFriendIds(userId);
        List<FriendListItemDTO> allFriendListItems = new ArrayList<>();
        for(int id: allFriends){
            User user = this.userService.getUserByUserId(id);
            allFriendListItems.add(new FriendListItemDTO(id, user.getUsername()));
        }
        return ResultFactory.buildSuccessResult(allFriendListItems);
    }

    @PostMapping("/requestFriend")
    public Result requestFriend(@RequestBody RequestFriendPackage requestFriendPackage){
        int user_id = requestFriendPackage.getUser_id();
        String friend_name = requestFriendPackage.getFriend_name();
        String validation_msg = requestFriendPackage.getValidation_msg();
        User friend = this.userService.getUserByUserName(friend_name);
        User user = this.userService.getUserByUserId(user_id);
        boolean user_exist = friend != null && user != null;
        if(!user_exist){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }
        try{
            friendService.requestFriend(user_id, friend.getId(),  validation_msg);
            return ResultFactory.buildSuccessResult(null);
        } catch(EntityDuplicateException entityDuplicateException){
            return ResultFactory.buildFailResult(null, entityDuplicateException.getMessage());
        } catch(NewEntityException newEntityException){
            return ResultFactory.buildInternalServerErrorResult(null);
        }
    }

    @PostMapping("/respondFriend")
    public Result respondFriend(@RequestBody RespondFriendPackage respondFriendPackage){
        int id = respondFriendPackage.getRequest_id();
        int respond_id = respondFriendPackage.getType_id();
        boolean request_exist = friendService.isRequestExist(id);
        if(!request_exist){
            return ResultFactory.buildFailResult(null, "Request doesn't exist.");
        }
        boolean state_exist = this.friendService.isRequestStateExist(respond_id);
        if(!state_exist){
            return ResultFactory.buildFailResult(null, "Invalid request state.");
        }

        try{
            friendService.updateFriend(id, respond_id);
            return ResultFactory.buildSuccessResult(null);
        } catch(NoEntityInDatabaseException noEntityInDatabaseException){
            return ResultFactory.buildFailResult(null, noEntityInDatabaseException.getMessage());
        } catch(NewEntityException newEntityException){
            return ResultFactory.buildInternalServerErrorResult(null);
        }
    }
}

class RequestFriendPackage{
    private int user_id;
    private String friend_name;
    private String validation_msg;

    public RequestFriendPackage(int user_id, String friend_name, String validation_msg) {
        this.user_id = user_id;
        this.friend_name = friend_name;
        this.validation_msg = validation_msg;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public String getValidation_msg() {
        return validation_msg;
    }
}

class RespondFriendPackage{
    private int request_id;
    private int type_id;

    public int getRequest_id() {
        return request_id;
    }

    public int getType_id() {
        return type_id;
    }

    public RespondFriendPackage() {
    }
}