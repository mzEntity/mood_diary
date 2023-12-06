package com.example.demo.transfer;

public class FriendDTO {
    private int id;
    private int user_id;
    private int friend_id;
    private int state_id;

    private String validation;

    public FriendDTO(int user_id, int friend_id, int state_id, String validation) {
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.state_id = state_id;
        this.validation = validation;
    }

    public FriendDTO(int id, int user_id, int friend_id, int state_id) {
        this.id = id;
        this.user_id = user_id;
        this.friend_id = friend_id;
        this.state_id = state_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }
}
