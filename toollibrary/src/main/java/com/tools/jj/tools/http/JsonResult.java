package com.tools.jj.tools.http;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HeJiaJun on 2018/7/1.
 * des:服务端返回数据
 * version:1.0.0
 */

public class JsonResult<T> {

    @SerializedName("statusCode")
    public int status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public T data;


    public JsonResult() {
    }

    public JsonResult(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }


    public boolean isOk(){
        return status==200;
    }
}
