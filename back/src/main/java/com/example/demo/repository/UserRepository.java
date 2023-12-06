package com.example.demo.repository;


import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(int userId);

    User findByUsername(String username);

    @Query("select id from User")
    List<Integer> getAllIds();

    List<User> findAll();

}
