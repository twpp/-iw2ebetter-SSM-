package com.myproject.iw2ebetter.pojo;


import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回响应数据包
 */
public class ResponseData {
    private String message;
    private Integer statusCode;
    private Map<String,Object> data = new HashMap<>();
    private final static  Integer SUCCESS = 2000;
    private final static Integer FAIL = 2001;

    public static ResponseData success(String message){
        ResponseData result = new ResponseData();
        result.setStatusCode(SUCCESS);
        result.setMessage(message);
        return result;
    }

    public static ResponseData fail(String message){
        ResponseData result = new ResponseData();
        result.setStatusCode(FAIL);
        result.setMessage(message);
        return result;
    }

    public ResponseData addData(String key,Object value){
        this.getData().put(key,value);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
