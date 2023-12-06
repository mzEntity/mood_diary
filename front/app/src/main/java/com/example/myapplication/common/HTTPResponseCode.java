package com.example.myapplication.common;

public enum HTTPResponseCode {

    SUCCESS(200, "SUCCESS"),
    FAIL(400, "FAIL"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),

    INVALID_CODE(-1, "INVALID");

    private int code;
    private String name;

    private HTTPResponseCode(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "HTTPResponseCode(" + this.code + ", " + this.name + ")";
    }

    public boolean isSuccess(){
        return this.code == 200;
    }

    public boolean isValid(){
        return this.code != -1;
    }
}
