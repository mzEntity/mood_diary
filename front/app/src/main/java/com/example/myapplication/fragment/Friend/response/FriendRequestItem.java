package com.example.myapplication.fragment.Friend.response;

public class FriendRequestItem {

    private int id;
    private int senderId;
    private int receiverId;

    private String senderName;

    private String validation;

    private String updateTime;

    public FriendRequestItem(int id, int senderId, int receiverId, String senderName, String validation, String updateTime) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.validation = validation;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getValidation() {
        return validation;
    }

    public String getUpdateTime() {
        return updateTime;
    }
}
