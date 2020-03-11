package com.yk.ctrl.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ThinkPad on 2018/9/25.
 */
public class ServiceResponse {
    public static int CMD_PARAS_ERROR = 1001;//请求参数错误
    public static int REGISTER_ERROR = 1002;//注册错误
    public static int NO_USER = 2001;//无此用户
    public static int NO_ERROR_CODE = 0;
    public static int ERROR = 3001;//系统错误
    public static int BIND_ERROR_DEVICE = 4001;   //超过用户绑定上限
    public static int BIND_ERROR_USER = 4002;
    public static int BIND_ERROR_HAVE = 4003;
    public static int BIND_DEVICE_NULL = 4004;//无绑定设备
    public static int FIND_NO_DEVICE = 4005;//查询不到设备


    @JsonProperty("ErrorCode")
    private int errorCode;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ResultNode")
    private Object resultNode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResultNode() {
        return resultNode;
    }

    public void setResultNode(Object resultNode) {
        this.resultNode = resultNode;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", resultNode=" + resultNode +
                '}';
    }

}
