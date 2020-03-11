package com.yk.ctrl.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ps on 2019/12/18.
 */
public class Adjust {

    //功能名
    @JsonProperty("name")
    private String name;

    //需要调节的值，上报String类型
    @JsonProperty("dataStr")
    private String dataStr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    @Override
    public String toString() {
        return "Adjust{" +
                "name='" + name + '\'' +
                ", dataStr='" + dataStr + '\'' +
                '}';
    }
}
