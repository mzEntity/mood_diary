package com.example.demo.controller.friend;


import com.example.demo.Service.FriendService;
import com.example.demo.Service.UserService;
import com.example.demo.model.Friend;
import com.example.demo.model.User;
import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GetAllRequestsReceivedController {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public GetAllRequestsReceivedController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/getAllRequestsReceived")
    public Result getAllRequestsReceived(
            @RequestParam(value = "userId") Integer userId
    ){
        User receiver = this.userService.getUserByUserId(userId);
        if(receiver == null){
            return ResultFactory.buildFailResult(null, "User doesn't exist.");
        }
        List<Friend> allRequests = this.friendService.getAllRequestsReceived(userId);
        List<RequestDTO> allRequestDTO = new ArrayList<>();
        for(Friend friend: allRequests){
            User sender = this.userService.getUserByUserId(friend.getUserId());
            RequestDTO requestDTO = new RequestDTO(
                    friend.getId(),
                    friend.getUserId(),
                    sender.getUsername(),
                    friend.getFriendId(),
                    friend.getStateId(),
                    friend.getValidation(),
                    MyUtils.timestampToString(friend.getUpdatedAt())
            );
            allRequestDTO.add(requestDTO);
        }
        return ResultFactory.buildSuccessResult(new GetAllRequestsReceivedResponsePackage(
                receiver.getId(),
                receiver.getUsername(),
                allRequestDTO
        ));
    }

    private static class RequestDTO{
        private int requestId;
        private int senderId;

        private String senderName;
        private int receiverId;
        private int stateId;
        private String validation;

        private String updateTime;

        public RequestDTO(int requestId, int senderId, String senderName, int receiverId, int stateId, String validation, String updateTime) {
            this.requestId = requestId;
            this.senderId = senderId;
            this.senderName = senderName;
            this.receiverId = receiverId;
            this.stateId = stateId;
            this.validation = validation;
            this.updateTime = updateTime;
        }

        public int getRequestId() {
            return requestId;
        }

        public int getSenderId() {
            return senderId;
        }

        public String getSenderName() {
            return senderName;
        }

        public int getReceiverId() {
            return receiverId;
        }

        public int getStateId() {
            return stateId;
        }

        public String getValidation() {
            return validation;
        }

        public String getUpdateTime() {
            return updateTime;
        }
    }

    private static class GetAllRequestsReceivedResponsePackage{
        private int receiverId;
        private String receiverName;
        private List<RequestDTO> allRequests;

        public int getReceiverId() {
            return receiverId;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public List<RequestDTO> getAllRequests() {
            return allRequests;
        }

        public GetAllRequestsReceivedResponsePackage(int receiverId, String receiverName, List<RequestDTO> allRequests) {
            this.receiverId = receiverId;
            this.receiverName = receiverName;
            this.allRequests = allRequests;
        }
    }
}

