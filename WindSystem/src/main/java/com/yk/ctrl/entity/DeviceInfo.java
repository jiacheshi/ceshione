package com.yk.ctrl.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DeviceInfo {
    @JsonProperty("id")
    private int id;
    @JsonProperty("mac")
    private String mac;
    @JsonProperty("deviceName")
    private String deviceName;
    @JsonProperty("data1")
    private String data1;
    @JsonProperty("data2")
    private String data2;
    @JsonProperty("finallyTime")
    private Date finallyTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public Date getFinallyTime() {
        return finallyTime;
    }

    public void setFinallyTime(Date finallyTime) {
        this.finallyTime = finallyTime;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                ", finallyTime=" + finallyTime +
                '}';
    }
}
