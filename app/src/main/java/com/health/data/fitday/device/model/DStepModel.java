package com.health.data.fitday.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DStepModel extends RealmObject {

    String mac;

    /// 单位  步
    /// unit step
    int step;

    /// 单位 卡
    int cal;
    @PrimaryKey
    int timeStamp;

    int distance;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getCal() {
        return cal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
