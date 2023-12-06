package com.example.demo.exception;

public class FieldMismatchException extends RuntimeException{
    public FieldMismatchException(String msg){
        super(msg);
    }

    public FieldMismatchException(Exception e){
        super(e);
    }
}
