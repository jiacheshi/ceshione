package com.yk.ctrl.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceData {
    @Expose
    @SerializedName("PM25Level")
    private int PM25Level;
    @Expose
    @SerializedName("TVOC")
    private double TVOC;
    @Expose
    @SerializedName("HCHO")
    private double HCHO;
    @Expose
    @SerializedName("PM25")
    private int PM25;
    @Expose
    @SerializedName("Error_Code")
    private int Error_Code;
    @Expose
    @SerializedName("OutPM25")
    private int OutPM25;
    @Expose
    @SerializedName("co2")
    private int co2;
    @Expose
    @SerializedName("WindSpeed")
    private int WindSpeed;
    @Expose
    @SerializedName("Scale")
    private int Scale;
    @Expose
    @SerializedName("WorkMode")
    private int WorkMode;
    @Expose
    @SerializedName("IndoorTemperature")
    private double IndoorTemperature;
    @Expose
    @SerializedName("RoomHumidity")
    private double RoomHumidity;

    public int getPM25Level() {
        return PM25Level;
    }

    public void setPM25Level(int PM25Level) {
        this.PM25Level = PM25Level;
    }

    public double getTVOC() {
        return TVOC;
    }

    public void setTVOC(double TVOC) {
        this.TVOC = TVOC;
    }

    public double getHCHO() {
        return HCHO;
    }

    public void setHCHO(double HCHO) {
        this.HCHO = HCHO;
    }

    public int getPM25() {
        return PM25;
    }

    public void setPM25(int PM25) {
        this.PM25 = PM25;
    }

    public int getError_Code() {
        return Error_Code;
    }

    public void setError_Code(int error_Code) {
        Error_Code = error_Code;
    }

    public int getOutPM25() {
        return OutPM25;
    }

    public void setOutPM25(int outPM25) {
        OutPM25 = outPM25;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getWindSpeed() {
        return WindSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        WindSpeed = windSpeed;
    }

    public int getScale() {
        return Scale;
    }

    public void setScale(int scale) {
        Scale = scale;
    }

    public int getWorkMode() {
        return WorkMode;
    }

    public void setWorkMode(int workMode) {
        WorkMode = workMode;
    }

    public double getIndoorTemperature() {
        return IndoorTemperature;
    }

    public void setIndoorTemperature(double indoorTemperature) {
        IndoorTemperature = indoorTemperature;
    }

    public double getRoomHumidity() {
        return RoomHumidity;
    }

    public void setRoomHumidity(double roomHumidity) {
        RoomHumidity = roomHumidity;
    }

    @Override
    public String toString() {
        return "DeviceData{" +
                "PM25Level=" + PM25Level +
                ", TVOC=" + TVOC +
                ", HCHO=" + HCHO +
                ", PM25=" + PM25 +
                ", Error_Code=" + Error_Code +
                ", OutPM25=" + OutPM25 +
                ", co2=" + co2 +
                ", WindSpeed=" + WindSpeed +
                ", Scale=" + Scale +
                ", WorkMode=" + WorkMode +
                ", IndoorTemperature=" + IndoorTemperature +
                ", RoomHumidity=" + RoomHumidity +
                '}';
    }
}