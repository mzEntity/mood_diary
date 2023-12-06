package com.example.myapplication.fragment.Friend;

public class FriendItem {
    private int userId;
    private String name;



    public FriendItem(int userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }
}
