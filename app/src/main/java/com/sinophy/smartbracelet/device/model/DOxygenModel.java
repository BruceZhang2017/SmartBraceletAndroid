package com.sinophy.smartbracelet.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DOxygenModel extends RealmObject {
    String mac;

    int oxygen;
    @PrimaryKey
    int timeStamp;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}
