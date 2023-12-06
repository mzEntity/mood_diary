package com.example.demo.exception;

public class EntityDuplicateException extends RuntimeException{
    public EntityDuplicateException(String message) {
        super(message);
    }

    public EntityDuplicateException(Exception e){
        super(e);
    }
}
