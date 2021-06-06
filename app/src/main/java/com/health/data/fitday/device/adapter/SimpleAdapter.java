package com.health.data.fitday.device.adapter;


import com.health.data.fitday.device.model.DeviceBean;
import com.sinophy.smartbracelet.R;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

public class SimpleAdapter extends BaseBannerAdapter<DeviceBean> {
    protected void bindData(BaseViewHolder<DeviceBean> paramBaseViewHolder, DeviceBean paramDeviceBean, int paramInt1, int paramInt2) {
    }

    public int getLayoutId(int paramInt) {
        return R.layout.view_device;
    }
}
