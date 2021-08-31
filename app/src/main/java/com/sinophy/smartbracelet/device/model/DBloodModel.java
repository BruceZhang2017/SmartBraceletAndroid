package com.sinophy.smartbracelet.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DBloodModel extends RealmObject {
    String mac;

    /// 血压高值
    /// High blood pressure
    int max;

    /// 血压低值
    /// low blood pressure
    int min;
    @PrimaryKey
    int timeStamp;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
