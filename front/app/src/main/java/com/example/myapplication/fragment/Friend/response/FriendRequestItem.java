package com.example.myapplication.fragment.Friend.response;

public class FriendRequestItem {

    private int id;
    private int userId;

    private int friendId;
    private String friendName;
    private String validation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public FriendRequestItem(int id, int userId, int friendId, String friendName, String validation) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.friendName = friendName;
        this.validation = validation;
    }
}
