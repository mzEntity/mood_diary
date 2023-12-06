package com.example.demo.exception;

public class NoEntityInDatabaseException extends RuntimeException{
    public NoEntityInDatabaseException(String msg) {
        super(msg);
    }

    public NoEntityInDatabaseException(Exception e) {
        super(e);
    }
}
