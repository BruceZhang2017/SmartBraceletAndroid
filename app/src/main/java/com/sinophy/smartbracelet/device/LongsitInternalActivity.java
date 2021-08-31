package com.sinophy.smartbracelet.device;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cktim.camera2library.camera.MessageEvent;
import com.sinophy.smartbracelet.device.adapter.WeekListAdapter;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.BractletSedentarySet;
import com.tjdL4.tjdmain.contr.L4Command;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class LongsitInternalActivity extends BaseActivity {
    @BindView(R.id.lv_week)
    ListView listView;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    WeekListAdapter adapter;
    List<Boolean> selectList = new ArrayList<>();
    List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_longsit_internal;
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

                int minute = 0;
                if (position == 0) {
                    minute = 30;
                } else if (position == 1) {
                    minute = 60;
                } else if (position == 2) {
                    minute = 120;
                } else {
                    minute = 180;
                }
                BractletSedentarySet.SedentarySetData mySedSetData=new BractletSedentarySet.SedentarySetData();
                mySedSetData.allminutes = minute;//minute 总分钟

//设置
                String ret = L4Command.SedentarySet(mySedSetData);/*ret  返回值类型文档最下面*/
                SpUtils.putInt(LongsitInternalActivity.this, "longsit", minute);
                EventBus.getDefault().post(new MessageEvent("longsit"));
                finish();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add("30分钟");
        list.add("60分钟");
        list.add("120分钟");
        list.add("180分钟");
        adapter.list = list;
        int value = SpUtils.getInt(this, "longsit");
        if (value <= 0) {
            value = 60;
        }
        if (value == 30) {
            selectList.add(true);
        } else {
            selectList.add(false);
        }
        if (value == 60) {
            selectList.add(true);
        } else {
            selectList.add(false);
        }
        if (value == 120) {
            selectList.add(true);
        } else {
            selectList.add(false);
        }
        if (value == 180) {
            selectList.add(true);
        } else {
            selectList.add(false);
        }
        adapter.selectList = selectList;
        adapter.context = this;
        listView.setAdapter(adapter);
    }
}