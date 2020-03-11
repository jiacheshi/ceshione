package com.yk.ctrl.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ps on 2019/12/16.
 */
public class BindDevice {
    @Expose
    @SerializedName("nickname")
    private String nickname;

    @Expose
    @SerializedName("mac")
    private String mac;

    @Expose
    @SerializedName("DeviceData")
    private DeviceData deviceData;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }

    @Override
    public String toString() {
        return "BindDevice{" +
                "nickname='" + nickname + '\'' +
                ", mac='" + mac + '\''+
                ", deviceData=" + deviceData +
                '}';
    }
}
