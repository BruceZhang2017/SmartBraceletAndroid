package com.health.data.fitday.mine;

public class DataRecordBean {
    public static final int ITEM = 0;

    public static final int SECTION = 1;

    public int icon;

    public String key;

    public int listPosition;

    public int sectionPosition;

    public int type;

    public String unit;

    public String value;

    public int getIcon() {
        return this.icon;
    }

    public String getKey() {
        return this.key;
    }

    public int getListPosition() {
        return this.listPosition;
    }

    public int getSectionPosition() {
        return this.sectionPosition;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getValue() {
        return this.value;
    }

    public void setIcon(int paramInt) {
        this.icon = paramInt;
    }

    public void setKey(String paramString) {
        this.key = paramString;
    }

    public void setListPosition(int paramInt) {
        this.listPosition = paramInt;
    }

    public void setSectionPosition(int paramInt) {
        this.sectionPosition = paramInt;
    }

    public void setUnit(String paramString) {
        this.unit = paramString;
    }

    public void setValue(String paramString) {
        this.value = paramString;
    }
}
