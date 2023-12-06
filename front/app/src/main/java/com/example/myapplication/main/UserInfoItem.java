package com.example.myapplication.main;

public class UserInfoItem {
    private int id;
    private String username;

    public UserInfoItem(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
