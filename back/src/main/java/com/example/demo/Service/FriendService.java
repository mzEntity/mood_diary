package com.example.demo.Service;


import com.example.demo.exception.NewEntityException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.Friend;
import com.example.demo.model.RequestState;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.RequestStateRepository;
import com.example.demo.utils.MyUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class FriendService {
    private RequestStateRepository requestStateRepository;
    private FriendRepository friendRepository;

    @Autowired
    public FriendService(RequestStateRepository requestStateRepository, FriendRepository friendRepository) {
        this.requestStateRepository = requestStateRepository;
        this.friendRepository = friendRepository;
    }

    public List<Friend> getAllRequestsSend(int userId, int stateId){
        return this.friendRepository.findAllByUserIdAndStateId(userId, stateId);
    }

    public List<Friend> getAllRequestsReceived(int friendId){
        int stateId = this.getStateIdByName("待审核");
        return this.friendRepository.findAllByFriendIdAndStateId(friendId, stateId);
    }

    public List<Integer> getAllFriendIds(int userId){
        int id = this.getStateIdByName("已通过");
        HashMap<Integer, Integer> resultMap = new HashMap<>();
        List<Friend> allFriends = this.friendRepository.findAllByFriendIdAndStateId(userId, id);
        for(Friend f: allFriends){
            resultMap.put(f.getUserId(), 1);
        }
        allFriends = this.friendRepository.findAllByUserIdAndStateId(userId, id);
        for(Friend f: allFriends){
            resultMap.put(f.getFriendId(), 1);
        }
        return new ArrayList<>(resultMap.keySet());
    }

    public boolean isRequestStateExist(int id){
        return requestStateRepository.findById(id) != null;
    }

    public Friend getRequestById(int id){
        return friendRepository.findById(id);
    }

    private RequestState getRequestStateByName(String name){
        return requestStateRepository.findByName(name);
    }

    public boolean isFriend(int user_id, int friend_id){
        int stateId = this.getStateIdByName("已通过");
        Friend request = this.friendRepository.getFriendByUserIdAndFriendIdAndStateId(user_id, friend_id, stateId);
        if(request != null) return true;
        request = this.friendRepository.getFriendByUserIdAndFriendIdAndStateId(friend_id, user_id, stateId);
        return request != null;
    }


    public void requestFriend(int user_id, int friend_id, String validation_msg) throws NewEntityException{
        int stateId = this.getStateIdByName("待审核");

        if(isFriend(user_id, friend_id)) return;
        Friend friend = this.friendRepository.getFriendByUserIdAndFriendIdAndStateId(user_id, friend_id, stateId);
        if(friend == null){
            friend = new Friend(
                    user_id, friend_id, stateId, validation_msg, MyUtils.getCurrentTimestamp()
            );
        } else {
            friend.setValidation(validation_msg);
            friend.setUpdatedAt(MyUtils.getCurrentTimestamp());
        }
        try {
            friendRepository.save(friend);
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }

    public int getStateIdByName(String name){
        return getRequestStateByName(name).getId();
    }

    public void updateFriend(int id, int state_id) throws NewEntityException, NoEntityInDatabaseException {
        Friend friend = this.friendRepository.findById(id);
        try {
            friend.setStateId(state_id);
            friendRepository.save(friend);
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }
}
