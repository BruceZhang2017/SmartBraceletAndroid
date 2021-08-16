package com.health.data.fitday.device.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.health.data.fitday.MyApplication;
import com.health.data.fitday.device.model.BLEModel;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class SimpleAdapter extends BaseBannerAdapter<BLEModel> {
    public Context context;
    public View.OnClickListener mOnClickListener;

    protected void bindData(BaseViewHolder<BLEModel> holder, BLEModel bleModel, int position, int pagesize) {
        BLEModel model = bleModel;
        holder.itemView.setOnClickListener(mOnClickListener);
        TextView tvAddDevice = (TextView) holder.itemView.findViewById(R.id.tv_add_device);
        if (model.getMac() == null || model.getMac().length() == 0) {
            tvAddDevice.setVisibility(View.VISIBLE);
            return;
        }
        tvAddDevice.setVisibility(View.INVISIBLE);
        TextView tvName = (TextView)holder.itemView.findViewById(R.id.tv_device_name);
        tvName.setText(model.getName());
        TextView tvBT = (TextView)holder.itemView.findViewById(R.id.tv_bt);
        TextView tvBattery = (TextView)holder.itemView.findViewById(R.id.tv_battery);
        ImageView ivS = (ImageView)holder.itemView.findViewById(R.id.iv_shezhi);
        String mac = model.getMac();
        System.out.println("当前连接成功的mac:" + L4M.GetConnectedMAC() + "当前设备的mac:" + mac);
        if (L4M.GetConnectedMAC().equals(mac)) {
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
            tvBT.setText("蓝牙未连接");
            tvBattery.setText("剩余电量未知");
            tvBT.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.content_blueteeth_unlink, null), null, null, null);
            tvBattery.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.conten_battery_null, null), null, null, null);

        }
    }

    public int getLayoutId(int paramInt) {
        return R.layout.view_device;
    }

    public View.OnClickListener getmOnClickListener() {
        return mOnClickListener;
    }

    public void setmOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }
}
