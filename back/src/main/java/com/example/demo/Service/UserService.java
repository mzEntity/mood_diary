package com.example.demo.Service;

import com.example.demo.exception.EntityDuplicateException;
import com.example.demo.exception.FieldMismatchException;
import com.example.demo.exception.NewEntityException;
import com.example.demo.exception.NoEntityInDatabaseException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.transfer.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean isUserExist(int userId){
        return userRepository.findUserById(userId) != null;
    }
    public boolean isUserExist(String username){
        return userRepository.findByUsername(username) != null;
    }

    public List<User> getAllUsers(){
        List<User> users = this.userRepository.findAll();
        return users;
    }

    public int createNewUser(User user) throws NewEntityException {
        try {
            return userRepository.save(user).getId();
        } catch (Exception e) {
            throw new NewEntityException(e);
        }
    }

    public User login(String username, String password) throws NoEntityInDatabaseException, FieldMismatchException {
        User userFromDatabase = this.userRepository.findByUsername(username);
        if(userFromDatabase == null){
            throw new NoEntityInDatabaseException("用户不存在");
        } else {
            if(!userFromDatabase.getPassword().equals(password)){
                throw new FieldMismatchException("密码不匹配");
            } else {
                return userFromDatabase;
            }
        }
    }

    public void signin(UserDTO userDTO) throws EntityDuplicateException, NewEntityException{
        if(isUserExist(userDTO.getUsername())){
            throw new EntityDuplicateException("用户名已存在");
        } else {
            User registerUser = new User(userDTO);
            createNewUser(registerUser);
        }
    }

    public User getUserByUserId(int userId){
        return this.userRepository.findUserById(userId);
    }

    public User getUserByUserName(String userName){
        return this.userRepository.findByUsername(userName);
    }

}

