package com.sinophy.smartbracelet.mine;

public class AccountSafeBean {
    private String key;

    private String keyDesc;

    private boolean safe;

    private String value;

    public String getKey() {
        return this.key;
    }

    public String getKeyDesc() {
        return this.keyDesc;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isSafe() {
        return this.safe;
    }

    public void setKey(String paramString) {
        this.key = paramString;
    }

    public void setKeyDesc(String paramString) {
        this.keyDesc = paramString;
    }

    public void setSafe(boolean paramBoolean) {
        this.safe = paramBoolean;
    }

    public void setValue(String paramString) {
        this.value = paramString;
    }
}
