package com.example.demo.model;

import jakarta.persistence.*;


@Entity
@Table(name ="mood")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Mood() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mood(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
