package com.health.data.fitday.device.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;

public class WeekListAdapter extends BaseAdapter {
    public List<String> list = new ArrayList<>();
    public List<Boolean> selectList = new ArrayList<>();
    public Context context;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_week_list, null);
            vHolder = new ViewHolder();
            vHolder.key = (TextView) convertView.findViewById(R.id.tv_key);
            vHolder.icon = (ImageView) convertView.findViewById(R.id.iv_arrow);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        vHolder.key.setText(list.get(position));
        vHolder.icon.setVisibility(selectList.get(position) ? View.VISIBLE : View.INVISIBLE);
        return convertView;
    }


    class ViewHolder {
        TextView key;
        ImageView icon;
    }
}
