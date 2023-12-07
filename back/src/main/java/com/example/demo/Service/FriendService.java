package com.example.demo.Service;


import com.example.demo.DTO.FriendListItemDTO;
import com.example.demo.exception.NewEntityException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.Friend;
import com.example.demo.model.RequestState;
import com.example.demo.repository.FriendRepository;
import com.example.demo.repository.RequestStateRepository;
import com.example.demo.transfer.FriendDTO;
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
        List<Integer> result = new ArrayList<>();
        List<Friend> allFriends = this.friendRepository.findAllByFriendIdAndStateId(userId, id);
        for(Friend f: allFriends){
            result.add(f.getUserId());
        }
        allFriends = this.friendRepository.findAllByUserIdAndStateId(userId, id);
        for(Friend f: allFriends){
            result.add(f.getFriendId());
        }
        return result;
    }



    public boolean isRequestStateExist(int id){
        return requestStateRepository.findById(id) != null;
    }

    public boolean isRequestExist(int id){
        return friendRepository.findById(id) != null;
    }

    private List<RequestState> getAllRequestState(){
        return requestStateRepository.findAll();
    }

    private RequestState getRequestStateByName(String name){
        return requestStateRepository.findByName(name);
    }

    private boolean isRecordExist(int user_id, int friend_id, int state_id){
        return friendRepository.findByUserIdAndFriendIdAndStateId(user_id, friend_id, state_id) != null;
    }


    private Friend getRecord(int user_id, int friend_id, int state_id){
        return friendRepository.findByUserIdAndFriendIdAndStateId(user_id, friend_id, state_id);
    }


    public void requestFriend(int user_id, int friend_id, String validation_msg) throws NewEntityException{
        FriendDTO friendDTO = new FriendDTO(user_id, friend_id, getStateIdByName("待审核"), validation_msg);
        Friend friend = new Friend(friendDTO);
        try {
            friendRepository.save(friend);
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }

    private int getStateIdByName(String name){
        return getRequestStateByName(name).getId();
    }

    public void updateFriend(int id, int state_id) throws NewEntityException, NoEntityInDatabaseException {
        Friend friend = this.friendRepository.findById(id);
        if(friend.getStateId() != this.getStateIdByName("待审核")){
            throw new NoEntityInDatabaseException("No such request.");
        }
        try {
            friend.setStateId(state_id);
            friendRepository.save(friend);
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }
}
