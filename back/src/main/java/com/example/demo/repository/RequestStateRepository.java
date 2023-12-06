package com.example.demo.repository;

import com.example.demo.model.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestStateRepository  extends JpaRepository<RequestState, Integer> {
    List<RequestState> findAll();

    RequestState findByName(String name);

    RequestState findById(int id);
}
