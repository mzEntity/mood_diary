package com.example.demo.repository;

import com.example.demo.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository  extends JpaRepository<Friend, Integer> {
    Friend findByUserIdAndFriendIdAndStateId(Integer userId, Integer friendId, Integer stateId);

    Friend findById(int id);
    List<Friend> findAllByStateId(Integer stateId);

    List<Friend> findAllByUserIdAndStateId(Integer userId, Integer stateId);

    List<Friend> findAllByFriendIdAndStateId(Integer friendId, Integer stateId);

    List<Friend> findAllByFriendId(Integer friendId);
}
