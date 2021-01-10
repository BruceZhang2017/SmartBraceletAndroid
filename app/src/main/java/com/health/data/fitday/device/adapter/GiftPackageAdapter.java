package com.health.data.fitday.device.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.health.data.fitday.device.DialBean;

public class GiftPackageAdapter extends BaseAdapter {
    public int getCount() {
        return 3;
    }

    public Object getItem(int paramInt) {
        return null;
    }

    public long getItemId(int paramInt) {
        return 0L;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null) {
            DialBean dialBean = new DialBean();
            paramView = LayoutInflater.from(paramViewGroup.getContext()).inflate(2131427424, null);
            paramView.setTag(dialBean);
        } else {
            DialBean dialBean = (DialBean)paramView.getTag();
        }
        return paramView;
    }
}
