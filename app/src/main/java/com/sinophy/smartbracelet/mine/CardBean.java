package com.sinophy.smartbracelet.mine;

import com.contrarywind.interfaces.IPickerViewData;

public class CardBean implements IPickerViewData {
    String cardNo;

    int id;

    public CardBean(int paramInt, String paramString) {
        this.id = paramInt;
        this.cardNo = paramString;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public int getId() {
        return this.id;
    }

    public String getPickerViewText() {
        return this.cardNo;
    }

    public void setCardNo(String paramString) {
        this.cardNo = paramString;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }
}
