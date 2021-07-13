package com.health.data.fitday.device.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserBean extends RealmObject {
    String area;
    String birthday;
    String country;
    String email;
    String headUrl;
    int height = 0;
    int id = 0;
    @PrimaryKey
    String mobile;
    String nickname;
    int sex = 0; // 0 男 1 女
    String token;
    String username;
    int weight = 0;

    public String getArea() {
        return this.area;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getCountry() {
        return this.country;
    }

    public String getEmail() {
        return this.email;
    }

    public String getHeadUrl() {
        return this.headUrl;
    }

    public int getHeight() {
        return this.height;
    }

    public int getId() {
        return this.id;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getSex() {
        return this.sex;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return this.username;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setArea(String paramString) {
        this.area = paramString;
    }

    public void setBirthday(String paramString) {
        this.birthday = paramString;
    }

    public void setCountry(String paramString) {
        this.country = paramString;
    }

    public void setEmail(String paramString) {
        this.email = paramString;
    }

    public void setHeadUrl(String paramString) {
        this.headUrl = paramString;
    }

    public void setHeight(int paramInt) {
        this.height = paramInt;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public void setMobile(String paramString) {
        this.mobile = paramString;
    }

    public void setNickname(String paramString) {
        this.nickname = paramString;
    }

    public void setSex(int paramInt) {
        this.sex = paramInt;
    }

    public void setToken(String paramString) {
        this.token = paramString;
    }

    public void setUsername(String paramString) {
        this.username = paramString;
    }

    public void setWeight(int paramInt) {
        this.weight = paramInt;
    }
}

