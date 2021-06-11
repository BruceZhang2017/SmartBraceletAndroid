package com.health.data.fitday.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinophy.smartbracelet.R;
import com.suke.widget.SwitchButton;

public class DeviceInfoAdapter extends BaseAdapter {
    private Context context;
    private String[] comNames;
    private String[] values;

    public DeviceInfoAdapter(Context context, String[] comNames, String[] values) {
        super();
        this.context = context;
        this.comNames = comNames;
        this.values = values;
    }

    @Override
    public int getCount() {
        return comNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComViewHolder comHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_device_info, null);
            comHolder = new ComViewHolder();
            comHolder.com = (TextView) convertView.findViewById(R.id.tv_key);
            comHolder.icon = (TextView) convertView.findViewById(R.id.tv_value);
            convertView.setTag(comHolder);
        } else {
            comHolder = (ComViewHolder) convertView.getTag();
        }
        comHolder.com.setText(comNames[position]);
        comHolder.icon.setText(values[position]);
        return convertView;
    }

    class ComViewHolder {
        TextView com;
        TextView icon;
    }
}


