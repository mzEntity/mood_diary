package com.example.demo.model;

import com.example.demo.transfer.FriendDTO;
import jakarta.persistence.*;



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

    public Friend() {

    }

    public Friend(FriendDTO friendDTO){
        this.id = friendDTO.getId();
        this.userId = friendDTO.getUser_id();
        this.friendId = friendDTO.getFriend_id();
        this.stateId = friendDTO.getState_id();
        this.validation = friendDTO.getValidation();
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
}
