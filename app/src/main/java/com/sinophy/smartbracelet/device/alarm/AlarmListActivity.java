package com.sinophy.smartbracelet.device.alarm;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sinophy.smartbracelet.device.adapter.AlarmListAdapter;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.AlarmClock;
import com.tjdL4.tjdmain.contr.L4Command;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class AlarmListActivity extends BaseActivity {
    private static String TAG = "AlarmListActivity";
    @BindView(R.id.lv_alarm)
    ListView listView;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    AlarmListAdapter adapter;
    public static List<AlarmClock.AlarmClockData> alarmList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm_list;
    }

    @Override
    protected void initData() {
        adapter = new AlarmListAdapter(this, alarmList);
        listView.setAdapter(adapter);
        L4Command.AlarmClockGet(listener); // 获取闹钟
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlarmListActivity.this, AddAlarmActivity.class);
                intent.putExtra("i", position);
                AlarmListActivity.this.startActivity(intent);
            }
        });
    }

    L4M.BTResultListenr listener = new L4M.BTResultListenr() {
        @Override
        public void On_Result(String TypeInfo, String StrData, Object DataObj) {
            final String tTypeInfo=TypeInfo;
            final String TempStr=StrData;
            final Object TempObj=DataObj;
            Log.e(TAG, "tTypeInfo:" + tTypeInfo + " inTempStr:"+TempStr);

            if(TypeInfo.equals(L4M.ERROR) && StrData.equals(L4M.TIMEOUT)) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(tTypeInfo.equals(L4M.SetAlarmClock) && TempStr.equals(L4M.OK)) {
                        L4Command.AlarmClockGet(null);
                    } else if(tTypeInfo.equals(L4M.GetAlarmClock) && TempStr.equals(L4M.Data)) {
                        AlarmClock.AlarmClockData myAlarmClockData=(AlarmClock.AlarmClockData)TempObj;
                        if(myAlarmClockData.clockId_int==0) {
                            alarmList.clear();
                        }
                        if(myAlarmClockData.clockId_int>=0 && myAlarmClockData.clockId_int<=4) {
                            alarmList.add(myAlarmClockData);
                        }
                        if (myAlarmClockData.clockId_int == 4) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}