package com.health.data.fitday.device;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.health.data.fitday.device.adapter.DeviceSwitchAdapter;
import com.health.data.fitday.device.model.BLEModel;
import com.health.data.fitday.device.model.DeviceManager;
import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.main.HomeActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceSwitchActivity extends BaseActivity {
    DeviceSwitchAdapter adapter;
    @BindView(R.id.lv_device)
    ListView listView;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_switch;
    }

    @Override
    protected void initData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == adapter.list.size() - 1) {
                    Intent intent = new Intent(DeviceSwitchActivity.this, SearchDeviceActivity.class);
                    startActivity(intent);
                } else {
                    BLEModel model = adapter.list.get(position);
                    if (L4M.GetConnectedMAC().equals(model.getMac())) {
                        return;
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        adapter = new DeviceSwitchAdapter();
        adapter.context = this;
        adapter.list.clear();
        adapter.list.addAll(DeviceManager.getInstance().models);
        adapter.list.add(new BLEModel());
        listView.setAdapter(adapter);

        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                DeviceSwitchActivity.this.finish();
            }
        });
    }
}