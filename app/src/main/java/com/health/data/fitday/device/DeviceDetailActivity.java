package com.health.data.fitday.device;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.health.data.fitday.MyApplication;
import com.health.data.fitday.device.adapter.ComListAdapter;
import com.health.data.fitday.device.model.BLEModel;
import com.health.data.fitday.device.model.DeviceManager;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceDetailActivity extends BaseActivity {
    @BindView(R.id.lv_detail) ListView listView;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    @BindView(R.id.tv_device_name) TextView tvName;
    @BindView(R.id.tv_bt) TextView tvBT;
    @BindView(R.id.tv_battery) TextView tvBattery;
    ComListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"", "推送设置", "来电提醒", "抬手亮屏", "久坐提醒", "天气推送", "", "闹钟设置", "查找设置", "设置信息"};
        boolean[] values = new boolean[]{false, false, false};
        adapter = new ComListAdapter(this, titles, values);
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
        BLEModel model = DeviceManager.getInstance().currentModel;
        tvName.setText(model.getName());
        String mac = model.getMac();
        System.out.println("当前连接成功的mac:" + L4M.GetConnectedMAC() + "当前设备的mac:" + mac);
        if (L4M.GetConnectedMAC().equals(mac)) {
            tvBT.setText("蓝牙已连接");
            String battery = MyApplication.getInstance().map.get(mac);
            if (battery == null || battery.length() == 0) {
                battery = "0";
            }
            tvBattery.setText("剩余电量" + battery + "%");
            tvBT.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.content_blueteeth_link, null), null, null, null);
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
            tvBattery.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(draw, null), null, null, null);
        } else {
            tvBT.setText("蓝牙未连接");
            tvBattery.setText("剩余电量未知");
            tvBT.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.content_blueteeth_unlink, null), null, null, null);
            tvBattery.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.conten_battery_null, null), null, null, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}