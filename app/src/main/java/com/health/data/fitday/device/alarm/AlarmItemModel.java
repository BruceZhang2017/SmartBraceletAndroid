package com.health.data.fitday.device.alarm;

public class AlarmItemModel {
    boolean bNull;
    boolean bEdit;
    String key;
    boolean bOpen;
    boolean bSection; // 是否为标题

    public boolean isbNull() {
        return bNull;
    }

    public void setbNull(boolean bNull) {
        this.bNull = bNull;
    }

    public boolean isbEdit() {
        return bEdit;
    }

    public void setbEdit(boolean bEdit) {
        this.bEdit = bEdit;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isbOpen() {
        return bOpen;
    }

    public void setbOpen(boolean bOpen) {
        this.bOpen = bOpen;
    }

    public boolean isbSection() {
        return bSection;
    }

    public void setbSection(boolean bSection) {
        this.bSection = bSection;
    }
}
