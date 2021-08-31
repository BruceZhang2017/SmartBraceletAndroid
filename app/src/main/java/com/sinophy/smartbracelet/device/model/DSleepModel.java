package com.sinophy.smartbracelet.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DSleepModel extends RealmObject {
    String mac;

    int state;
    @PrimaryKey
    int timeStamp;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
