package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name="friend")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "friend_id")
    private Integer friendId;

    @Column(name = "state_id")
    private Integer stateId;

    @Column(name = "validation")
    private String validation;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Friend() {}

    public Friend(Integer userId, Integer friendId, Integer stateId, String validation, Timestamp updatedAt) {
        this.userId = userId;
        this.friendId = friendId;
        this.stateId = stateId;
        this.validation = validation;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Friend(Integer userId, Integer friendId, Integer stateId) {
        this.userId = userId;
        this.friendId = friendId;
        this.stateId = stateId;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
