package com.health.data.fitday.device;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.health.data.fitday.device.adapter.ComListAdapter;
import com.health.data.fitday.device.adapter.PushListAdapter;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceDetailPushActivity extends BaseActivity {
    @BindView(R.id.lv_detail) ListView listView;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    PushListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail_push;
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"", "微信", "QQ", "LIKEDIN", "FACEBOOK", "TIWTTER", "", "WHATSAPP"};
        boolean[] values = new boolean[]{false, false, false, false, false, false, false, false};
        adapter = new PushListAdapter(this, titles, values);
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