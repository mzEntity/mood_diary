package com.example.myapplication.common;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPHelper {
    public static void postFile(String relativeUrl, File file, HTTPCallBack callBack){
        new Thread(() -> {
            String url = Config.httpBasePath + relativeUrl;
            OkHttpClient client = new OkHttpClient();
            RequestBody fileBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
            MultipartBody body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("fileUpload", file.getName(), fileBody)
                    .build();

            Request request = new Request.Builder().url(url).post(body).build();
            try{
                Response response = client.newCall(request).execute();
                if(response.code() == 200){
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    onSuccess(jsonResponse, callBack);
                }
                else{
                    onFailure(callBack);
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    public static void post(String relativeUrl, JSONObject requestObject, HTTPCallBack callBack){
        new Thread(() -> {
            String url = Config.httpBasePath + relativeUrl;
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(requestObject.toString(), MediaType.parse("application/json;charset=utf-8"));
            Request request = new Request.Builder().url(url).post(body).build();
            try{
                Response response = client.newCall(request).execute();
                if(response.code() == 200){
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    onSuccess(jsonResponse, callBack);
                }
                else{
                    onFailure(callBack);
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void get(String relativeUrl, HashMap<String, String> queryParam, HTTPCallBack callBack){
        new Thread(() -> {
            String url = Config.httpBasePath + relativeUrl;
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
            for(String key: queryParam.keySet()){
                String val = queryParam.get(key);
                urlBuilder.addQueryParameter(key, val);
            }
            String urlWithParams = urlBuilder.build().toString();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(urlWithParams).get().build();
            try{
                Response response = client.newCall(request).execute();
                if(response.code() == 200){
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    onSuccess(jsonResponse, callBack);
                }
                else{
                    onFailure(callBack);
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static HTTPResponseCode getResponseCode(JSONObject returnObject){
        try{
            int code = returnObject.getInt("code");
            return parseResponseCode(code);
        } catch (JSONException e){
            return HTTPResponseCode.INVALID_CODE;
        }
    }

    public static String getResponseMessage(JSONObject returnObject){
        try{
            String msg = returnObject.getString("msg");
            return msg;
        } catch (JSONException e){
            return null;
        }
    }

    private static HTTPResponseCode parseResponseCode(int code){
        switch (code){
            case 200:
                return HTTPResponseCode.SUCCESS;
            case 400:
                return HTTPResponseCode.FAIL;
            case 401:
                return HTTPResponseCode.UNAUTHORIZED;
            case 404:
                return HTTPResponseCode.NOT_FOUND;
            case 500:
                return HTTPResponseCode.INTERNAL_SERVER_ERROR;
            default:
                return HTTPResponseCode.INVALID_CODE;
        }
    }
    private static void onSuccess(JSONObject returnObject, HTTPCallBack callBack){
        HTTPResponseCode code = HTTPHelper.getResponseCode(returnObject);
        String msg = HTTPHelper.getResponseMessage(returnObject);

        if(!code.isValid() || msg == null){
            callBack.getInformalResponse(returnObject);
            return;
        }
        if(code.isSuccess()){
            callBack.getSuccess(returnObject, msg);
            return;
        }

        callBack.getNotSuccess(returnObject, msg);
        switch (code){
            case FAIL:
                callBack.getFail(returnObject, msg);
                break;
            case UNAUTHORIZED:
                callBack.getUnauthorized(returnObject, msg);
                break;
            case NOT_FOUND:
                callBack.getNotFound(returnObject, msg);
                break;
            case INTERNAL_SERVER_ERROR:
                callBack.getInternalServerError(returnObject, msg);
                break;
            default:
                callBack.getInformalResponse(returnObject);
        }
    }

    private static void onFailure(HTTPCallBack callBack){
        callBack.getBadResponse();
    }
}
