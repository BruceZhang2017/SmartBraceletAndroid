package com.health.data.fitday.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DHeartRateModel extends RealmObject {
    String mac;

    int heartRate;
    @PrimaryKey
    int timeStamp;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
