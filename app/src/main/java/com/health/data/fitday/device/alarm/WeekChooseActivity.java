package com.health.data.fitday.device.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.health.data.fitday.device.adapter.WeekListAdapter;
import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.utils.SpUtils;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class WeekChooseActivity extends BaseActivity {
    @BindView(R.id.lv_week)
    ListView listView;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    WeekListAdapter adapter;
    List<Boolean> selectList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_week_choose;
    }

    @Override
    protected void initData() {
        adapter = new WeekListAdapter();
        List<String> list = new ArrayList<>();
        list.add("星期一");
        list.add("星期二");
        list.add("星期三");
        list.add("星期四");
        list.add("星期五");
        list.add("星期六");
        list.add("星期日");
        adapter.list = list;

        selectList.add(false);
        selectList.add(false);
        selectList.add(false);
        selectList.add(false);
        selectList.add(false);
        selectList.add(false);
        selectList.add(false);
        adapter.selectList = selectList;
        adapter.context = this;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean b = selectList.get(position);
                selectList.set(position, !b);
                adapter.selectList = selectList;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        actionBarCommon.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                int value = 0;
                if (selectList.get(0)) {
                    value += 2;
                }
                if (selectList.get(1)) {
                    value += 4;
                }
                if (selectList.get(2)) {
                    value += 8;
                }
                if (selectList.get(3)) {
                    value += 16;
                }
                if (selectList.get(4)) {
                    value += 32;
                }
                if (selectList.get(5)) {
                    value += 64;
                }
                if (selectList.get(6)) {
                    value += 1;
                }
                Intent intent = new Intent();
                intent.putExtra("week", value);
                // 设置返回码和返回携带的数据
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}