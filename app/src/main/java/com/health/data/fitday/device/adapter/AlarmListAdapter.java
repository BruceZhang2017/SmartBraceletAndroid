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
import com.tjdL4.tjdmain.contr.AlarmClock;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.DecimalFormat;
import java.util.List;

public class AlarmListAdapter extends BaseAdapter {
    private Context context;
    private List<AlarmClock.AlarmClockData> list;

    public AlarmListAdapter(Context context, List<AlarmClock.AlarmClockData> list) {
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

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlarmViewHolder comHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_alarm_list, null);
            comHolder = new AlarmViewHolder();
            comHolder.com = (TextView) convertView.findViewById(R.id.tv_key);
            comHolder.icon = (SwitchButton) convertView.findViewById(R.id.switch_button);
            comHolder.detail = (TextView)convertView.findViewById(R.id.tv_week);
            convertView.setTag(comHolder);
        } else {
            comHolder = (AlarmViewHolder) convertView.getTag();
        }
        AlarmClock.AlarmClockData model = list.get(position);
        DecimalFormat decimalFormat =new DecimalFormat("00");
        String hour = decimalFormat.format(model.hours);
        String minute = decimalFormat.format(model.minutes);
        comHolder.com.setText(hour + ":" + minute);
        comHolder.detail.setText(bytesToString(model.week));
        comHolder.icon.setChecked(model.clock_switch > 0);
        comHolder.icon.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                model.clock_switch = isChecked ? 1 : 0;
                String ret = L4Command.AlarmClockSet(model);
            }
        });
        return convertView;
    }

    class AlarmViewHolder {
        TextView detail;
        TextView com;
        SwitchButton icon;
    }

    private String bytesToString(int week) {
        String value = "";
        if ((week & 0x01) > 0) {
            value += "星期日、";
        }
        if ((week >> 1 & 0x01) > 0) {
            value += "星期一、";
        }
        if ((week >> 2 & 0x01) > 0) {
            value += "星期二、";
        }
        if ((week >> 3 & 0x01) > 0) {
            value += "星期三、";
        }
        if ((week >> 4 & 0x01) > 0) {
            value += "星期四、";
        }
        if ((week >> 5 & 0x01) > 0) {
            value += "星期五、";
        }
        if ((week >> 6 & 0x01) > 0) {
            value += "星期六、";
        }
        if (value.length() > 0) {
            value = value.substring(0, value.length() - 1);
        } else {
            value = "无";
        }
        return value;
    }
}



