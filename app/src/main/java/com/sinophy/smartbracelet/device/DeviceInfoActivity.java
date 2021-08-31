package com.sinophy.smartbracelet.device;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.sinophy.smartbracelet.device.adapter.DeviceInfoAdapter;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceInfoActivity extends BaseActivity {
    @BindView(R.id.lv_detail) ListView listView;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    DeviceInfoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_info;
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"设备型号", "MAC地址", "软件版本", "硬件版本"};
        String[] values = new String[]{Dev.get_TypeCode() ,L4M.GetConnectedMAC(), Dev.get_SWVerCode(), Dev.get_HWVerCode()};
        adapter = new DeviceInfoAdapter(this, titles, values);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}