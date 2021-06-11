package com.health.data.fitday.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DeviceBean extends RealmObject {
    @PrimaryKey
    int id = 0;
    int userId = 0;
    int battery = 0;
    int btState = 0;
    boolean callReminder = false;
    boolean facebook = false;
    String icon = "";
    boolean linkedin = false;
    String mac = "";
    String name = "";
    boolean qq = false;
    boolean sitReminder = false;
    String sn = "";
    boolean twitter = false;
    String type = "";
    boolean upScreenLight = false;
    String version = "";
    boolean weixin = false;
    boolean whatsapp = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getBtState() {
        return btState;
    }

    public void setBtState(int btState) {
        this.btState = btState;
    }

    public boolean isCallReminder() {
        return callReminder;
    }

    public void setCallReminder(boolean callReminder) {
        this.callReminder = callReminder;
    }

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isLinkedin() {
        return linkedin;
    }

    public void setLinkedin(boolean linkedin) {
        this.linkedin = linkedin;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isQq() {
        return qq;
    }

    public void setQq(boolean qq) {
        this.qq = qq;
    }

    public boolean isSitReminder() {
        return sitReminder;
    }

    public void setSitReminder(boolean sitReminder) {
        this.sitReminder = sitReminder;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public boolean isTwitter() {
        return twitter;
    }

    public void setTwitter(boolean twitter) {
        this.twitter = twitter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isUpScreenLight() {
        return upScreenLight;
    }

    public void setUpScreenLight(boolean upScreenLight) {
        this.upScreenLight = upScreenLight;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isWeixin() {
        return weixin;
    }

    public void setWeixin(boolean weixin) {
        this.weixin = weixin;
    }

    public boolean isWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(boolean whatsapp) {
        this.whatsapp = whatsapp;
    }
}
