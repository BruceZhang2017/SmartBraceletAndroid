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

public class PushListAdapter extends BaseAdapter {
    public static final int TYPE_ARROW = 0;
    public static final int TYPE_SWITCH = 1;
    public static final int TYPE_NULL = 2;
    private Context context;
    private String[] comNames;
    private boolean[] values;

    public PushListAdapter(Context context, String[] comNames, boolean[] values) {
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
    public int getItemViewType(int position) {
        if (position == 0 || position == 7) {
            return TYPE_NULL;
        } else {
            return TYPE_SWITCH;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComViewHolder comHolder;
        switch (getItemViewType(position)) {
            case TYPE_SWITCH:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_switch, null);
                    comHolder = new ComViewHolder();
                    comHolder.com = (TextView) convertView.findViewById(R.id.tv_key);
                    comHolder.icon = (SwitchButton) convertView.findViewById(R.id.switch_button);
                    convertView.setTag(comHolder);
                } else {
                    comHolder = (ComViewHolder) convertView.getTag();
                }
                comHolder.com.setText(comNames[position]);
                comHolder.icon.setChecked(values[position]);
                break;
            case TYPE_NULL:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_null, null);
                }
                break;
        }
        return convertView;
    }

    class ComViewHolder {
        TextView com;
        SwitchButton icon;
    }
}


