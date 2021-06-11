package com.health.data.fitday.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.health.data.fitday.device.DeviceDetailPushActivity;
import com.health.data.fitday.device.DeviceFoundActivity;
import com.health.data.fitday.device.DeviceInfoActivity;
import com.sinophy.smartbracelet.R;
import com.suke.widget.SwitchButton;

public class ComListAdapter extends BaseAdapter {
    public static final int TYPE_ARROW = 0;
    public static final int TYPE_SWITCH = 1;
    public static final int TYPE_NULL = 2;
    private Context context;
    private String[] comNames;
    private boolean[] values;

    public ComListAdapter(Context context, String[] comNames, boolean[] values) {
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
        if (position == 0 || position == 6) {
            return TYPE_NULL;
        } else if (position == 2 || position == 3 || position == 4) {
            return TYPE_SWITCH;
        } else {
            return TYPE_ARROW;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleViewHolder titleHolder;
        ComViewHolder comHolder;
        switch (getItemViewType(position)) {
            case TYPE_ARROW:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail, null);
                    titleHolder = new TitleViewHolder();
                    titleHolder.title = (TextView) convertView.findViewById(R.id.tv_key);
                    convertView.setTag(titleHolder);
                } else {
                    titleHolder = (TitleViewHolder) convertView.getTag();
                }
                titleHolder.title.setText(comNames[position]);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 1) {
                            Intent intent = new Intent(context, DeviceDetailPushActivity.class);
                            context.startActivity(intent);
                        } else if (position == 7) {
                            Toast.makeText(context, "敬请期待", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (position == 8) {
                            Intent intent = new Intent(context, DeviceFoundActivity.class);
                            context.startActivity(intent);
                        } else if (position == 9) {
                            Intent intent = new Intent(context, DeviceInfoActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
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
                comHolder.icon.setChecked(values[position - 2]);
                break;
            case TYPE_NULL:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_null, null);
                }
                break;
        }
        return convertView;
    }

    class TitleViewHolder {
        TextView title;
    }

    class ComViewHolder {
        TextView com;
        SwitchButton icon;
    }
}


