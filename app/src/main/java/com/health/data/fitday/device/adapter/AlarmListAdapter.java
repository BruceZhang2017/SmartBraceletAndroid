package com.health.data.fitday.device.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.data.fitday.device.alarm.AlarmItemModel;
import com.sinophy.smartbracelet.R;
import com.suke.widget.SwitchButton;
import com.tjdL4.tjdmain.contr.BractletFuncSet;

import java.util.List;

public class AlarmListAdapter extends BaseAdapter {
    public static final int TYPE_SWITCH = 1;
    public static final int TYPE_NULL = 2;
    private Context context;
    private List<AlarmItemModel> list;

    public AlarmListAdapter(Context context, List<AlarmItemModel> list) {
        super();
        this.context = context;
        this.list = list;
    }

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
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        AlarmItemModel model = list.get(position);
        if (model.isbNull()) {
            return TYPE_NULL;
        }
        return TYPE_SWITCH;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmViewHolder comHolder;
        switch (getItemViewType(position)) {
            case TYPE_SWITCH:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_alarm_list, null);
                    comHolder = new AlarmViewHolder();
                    comHolder.com = (TextView) convertView.findViewById(R.id.tv_key);
                    comHolder.icon = (SwitchButton) convertView.findViewById(R.id.switch_button);
                    comHolder.ivArrow = (ImageView)convertView.findViewById(R.id.iv_arrow_right);
                    comHolder.ivDelete = (ImageView)convertView.findViewById(R.id.iv_delete);
                    convertView.setTag(comHolder);
                } else {
                    comHolder = (AlarmViewHolder) convertView.getTag();
                }
                AlarmItemModel model = list.get(position);
                comHolder.com.setText(model.getKey());
                comHolder.icon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {

                    }
                });
                if (model.isbSection()) {
                    comHolder.ivArrow.setVisibility( View.GONE);
                    comHolder.ivDelete.setVisibility( View.GONE);
                    comHolder.icon.setVisibility( View.GONE);
                } else {
                    comHolder.ivArrow.setVisibility(model.isbEdit() ? View.VISIBLE : View.GONE);
                    comHolder.ivDelete.setVisibility(model.isbEdit() ? View.VISIBLE : View.GONE);
                    comHolder.icon.setVisibility(model.isbEdit() ? View.GONE : View.VISIBLE);
                }

                break;
            case TYPE_NULL:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_device_detail_null, null);
                }
                break;
        }
        return convertView;
    }

    class AlarmViewHolder {
        TextView com;
        SwitchButton icon;
        ImageView ivDelete;
        ImageView ivArrow;
    }
}



