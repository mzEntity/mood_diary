package com.example.demo.exception;

public class NewEntityException extends RuntimeException{
    public NewEntityException(String msg){
        super(msg);
    }

    public NewEntityException(Exception e){
        super(e);
    }
}
