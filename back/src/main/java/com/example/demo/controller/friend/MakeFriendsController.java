package com.example.demo.controller.friend;


import com.example.demo.transfer.FriendListItemDTO;
import com.example.demo.Service.FriendService;
import com.example.demo.Service.UserService;

import com.example.demo.exception.EntityDuplicateException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @GetMapping("/getAllFriends")
    private Result getAllFriends(
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
    private Result requestFriend(@RequestBody RequestFriendPackage requestFriendPackage){
        int user_id = requestFriendPackage.getUser_id();
        String friend_name = requestFriendPackage.getFriend_name();
        String validation_msg = requestFriendPackage.getValidation_msg();
        User friend = this.userService.getUserByUserName(friend_name);
        User user = this.userService.getUserByUserId(user_id);
        if(friend == null || user == null){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }
        if(user_id == friend.getId()){
            return ResultFactory.buildFailResult(null, "不能向自己发送好友请求");
        }
        if(this.friendService.isFriend(user_id, friend.getId())){
            return ResultFactory.buildFailResult(null, "不能重复添加好友");
        }
        try{
            friendService.requestFriend(user_id, friend.getId(), validation_msg);
            return ResultFactory.buildSuccessResult(null);
        } catch(EntityDuplicateException entityDuplicateException){
            return ResultFactory.buildFailResult(null, entityDuplicateException.getMessage());
        } catch(NewEntityException newEntityException){
            return ResultFactory.buildInternalServerErrorResult(null);
        }
    }

    @PostMapping("/respondFriend")
    private Result respondFriend(@RequestBody RespondFriendPackage respondFriendPackage){
        int id = respondFriendPackage.getRequest_id();
        int respond_id = respondFriendPackage.getType_id();
        Friend friendRequest = this.friendService.getRequestById(id);

        if(friendRequest == null){
            return ResultFactory.buildFailResult(null, "Request doesn't exist.");
        }
        int expectedStateId = this.friendService.getStateIdByName("待审核");
        if(friendRequest.getStateId() != expectedStateId){
            return ResultFactory.buildFailResult(null, "不是待审核的请求");
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