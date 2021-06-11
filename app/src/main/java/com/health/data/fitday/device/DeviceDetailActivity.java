package com.health.data.fitday.device;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.style.IconMarginSpan;
import android.view.View;
import android.widget.ListView;

import com.health.data.fitday.device.adapter.ComListAdapter;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceDetailActivity extends BaseActivity {
    @BindView(R.id.lv_detail) ListView listView;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}