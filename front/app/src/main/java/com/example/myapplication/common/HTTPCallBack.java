package com.example.myapplication.common;

import org.json.JSONObject;

public interface HTTPCallBack {
    default void getSuccess(JSONObject returnObject, String msg){}

    default void getNotSuccess(JSONObject returnObject, String msg){}

    default void getFail(JSONObject returnObject, String msg){}

    default void getUnauthorized(JSONObject returnObject, String msg){}

    default void getNotFound(JSONObject returnObject, String msg){}

    default void getInternalServerError(JSONObject returnObject, String msg){}

    default void getBadResponse(){}

    default void getInformalResponse(JSONObject returnObject){}
}
