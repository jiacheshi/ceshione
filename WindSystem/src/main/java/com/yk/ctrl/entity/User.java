package com.yk.ctrl.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户表
 */

public class User {
    //用户编号
    @JsonProperty("Id")
    private int id;

    //用户账号
    @JsonProperty("UName")
    private String uName;

    //用户密码
    @JsonProperty("UPass")
    private String uPass;

    //用户电话
    @JsonProperty("PhoneNumber")
    private String phoneNumber;

    //用户qq
    @JsonProperty("QQ")
    private String qq;

    //用户微信
    @JsonProperty("WeChat")
    private String weChat;

    //用户E-Mail
    @JsonProperty("Email")
    private String email;

    @JsonProperty("Mac")
    private String mac;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPass() {
        return uPass;
    }

    public void setuPass(String uPass) {
        this.uPass = uPass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uName='" + uName + '\'' +
                ", uPass='" + uPass + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qq='" + qq + '\'' +
                ", weChat='" + weChat + '\'' +
                ", email='" + email + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
