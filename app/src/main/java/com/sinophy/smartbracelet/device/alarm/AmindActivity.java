package com.sinophy.smartbracelet.device.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sinophy.smartbracelet.device.adapter.WeekListAdapter;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class AmindActivity extends BaseActivity {
    @BindView(R.id.lv_week)
    ListView listView;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    WeekListAdapter adapter;
    List<String> list = new ArrayList<>();
    List<Boolean> selectList = new ArrayList<>();
    int internal = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_amind;
    }

    @Override
    protected void initData() {
        adapter = new WeekListAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = selectList.get(position);
                selectList.set(position, !b);
                adapter.selectList = selectList;
                adapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("amind", (position + 1) * 10);
                // 设置返回码和返回携带的数据
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("amind", 0);
                // 设置返回码和返回携带的数据
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int internal = 0;
        if (bundle != null) {
            internal = bundle.getInt("internal");
        }
        this.internal = internal;
        for (int i = 0; i < 9; i++) {
            list.add(((i + 1) * 10) + "分钟");
            if (((i + 1) * 10) == internal) {
                selectList.add(true);
            } else {
                selectList.add(false);
            }
        }
        adapter.list = list;
        adapter.selectList = selectList;
        adapter.context = this;
        listView.setAdapter(adapter);
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4 && paramKeyEvent.getAction() == 0) {
            Intent intent = new Intent();
            intent.putExtra("amind", 0);
            // 设置返回码和返回携带的数据
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }
}