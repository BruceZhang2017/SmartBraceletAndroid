package com.health.data.fitday.device.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.health.data.fitday.MyApplication;
import com.health.data.fitday.device.model.BLEModel;
import com.shehuan.niv.NiceImageView;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;

import java.util.ArrayList;
import java.util.List;

public class DeviceSwitchAdapter extends BaseAdapter {
    public Context context;
    public List<BLEModel> list = new ArrayList<>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_switch, null);
        }
        ConstraintLayout cl = (ConstraintLayout)convertView.findViewById(R.id.cl_head);
        ConstraintLayout cl2 = (ConstraintLayout)convertView.findViewById(R.id.cl_add_device);
        if (position == list.size() - 1) {
            cl.setVisibility(View.INVISIBLE);
            cl2.setVisibility(View.VISIBLE);
        } else {
            cl.setVisibility(View.VISIBLE);
            cl2.setVisibility(View.INVISIBLE);
        }
        if (position < list.size() - 1) {
            BLEModel model = list.get(position);
            TextView tvName = (TextView)convertView.findViewById(R.id.tv_device_name);
            tvName.setText(model.getName());
            TextView tvBT = (TextView)convertView.findViewById(R.id.tv_bt);
            TextView tvBattery = (TextView)convertView.findViewById(R.id.tv_battery);
            ImageView ivArrow = (ImageView)convertView.findViewById(R.id.iv_arrow);
            String mac = model.getMac();
            System.out.println("当前连接成功的mac:" + L4M.GetConnectedMAC() + "当前设备的mac:" + mac);
            if (L4M.GetConnectedMAC().equals(mac)) {
                cl.setBackground(context.getResources().getDrawable(R.drawable.drawable_border_corner_4, null));
                ivArrow.setVisibility(View.VISIBLE);
                tvBT.setText("蓝牙已连接");
                String battery = MyApplication.getInstance().map.get(mac);
                if (battery == null || battery.length() == 0) {
                    battery = "0";
                }
                tvBattery.setText("剩余电量" + battery + "%");
                tvBT.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.content_blueteeth_link, null), null, null, null);
                int draw = 0;
                int b = Integer.parseInt(battery);
                if (b < 20) {
                    draw = R.mipmap.dianliang1;
                } else if (b < 40) {
                    draw = R.mipmap.dianliang2;
                } else if (b < 60) {
                    draw = R.mipmap.dianliang3;
                } else {
                    draw = R.mipmap.dianliang4;
                }
                tvBattery.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(draw, null), null, null, null);
            } else {
                cl.setBackground(context.getResources().getDrawable(R.drawable.drawable_border_corner_dddddd, null));
                ivArrow.setVisibility(View.INVISIBLE);
                tvBT.setText("蓝牙未连接");
                tvBattery.setText("剩余电量未知");
                tvBT.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.content_blueteeth_unlink, null), null, null, null);
                tvBattery.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.conten_battery_null, null), null, null, null);

            }
        }
        
        return convertView;
    }
}
