package com.health.data.fitday.device.adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.health.data.fitday.device.model.DeviceBean;
import com.sinophy.smartbracelet.R;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class SimpleAdapter extends BaseBannerAdapter<DeviceBean> {
    View.OnClickListener mOnClickListener;

    protected void bindData(BaseViewHolder<DeviceBean> holder, DeviceBean paramDeviceBean, int position, int pagesize) {
        holder.itemView.setOnClickListener(mOnClickListener);
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
