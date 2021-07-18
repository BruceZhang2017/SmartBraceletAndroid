package com.health.data.fitday.device.alarm;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.health.data.fitday.device.adapter.AlarmListAdapter;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.AlarmClock;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    @BindView(R.id.tv_null)
    TextView tvNull;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.ib_add)
    Button ibAdd;
    AlarmListAdapter adapter;
    List<AlarmClock.AlarmClockData> alarmList = new ArrayList<>();
    List<AlarmItemModel> list = new ArrayList<>();
    boolean bEdit = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm_list;
    }

    @Override
    protected void initData() {
        adapter = new AlarmListAdapter(this, list);
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
        actionBarCommon.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {

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
                        System.out.println("获取到闹钟：" + myAlarmClockData.clockId_int);
                        if(myAlarmClockData.clockId_int>=0 && myAlarmClockData.clockId_int<=4) {
                            boolean bTem = false;
                            int i = 0;
                            for (AlarmClock.AlarmClockData item : alarmList) {
                                if (item.clockId_int == myAlarmClockData.clockId_int) {
                                    alarmList.set(i, myAlarmClockData);
                                    bTem = true;
                                    break;
                                }
                                i++;
                            }
                            if (bTem == false) {
                                alarmList.add(myAlarmClockData);
                            }
                        }
                        if (myAlarmClockData.clockId_int == 4) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isHavenAlarm()) {
                                        System.out.println("没有闹钟");
                                        tvNull.setVisibility(View.VISIBLE);
                                        tvAdd.setVisibility(View.GONE);
                                        ibAdd.setVisibility(View.GONE);
                                        listView.setVisibility(View.INVISIBLE);
                                        actionBarCommon.getRightIconView().setImageResource(R.mipmap.content_icon_add);
                                    } else {
                                        System.out.println("有闹钟");
                                        tvNull.setVisibility(View.GONE);
                                        listView.setVisibility(View.VISIBLE);
                                        if (alarmTotalCount() < 5) {
                                            tvAdd.setVisibility(View.VISIBLE);
                                            ibAdd.setVisibility(View.VISIBLE);
                                        } else {
                                            tvAdd.setVisibility(View.GONE);
                                            ibAdd.setVisibility(View.GONE);
                                        }
                                        refreshUIForList(bEdit);
                                        adapter.notifyDataSetChanged();
                                        actionBarCommon.getRightIconView().setImageResource(R.mipmap.nav_icon_edit);
                                    }

                                }
                            });
                        }
                    }
                }
            });

            ibAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AlarmListActivity.this, AddAlarmActivity.class);
                    startActivity(intent);
                }
            });
        }
    };

    private void refreshUIForList(boolean bEdit) {
        list.clear();
        AlarmItemModel model = new AlarmItemModel();
        model.setbNull(true);
        list.add(model);
        boolean bAM = false;
        boolean bPM = false;
        for (AlarmClock.AlarmClockData data: alarmList) {
            if (data.hours == 0 && data.minutes == 0) {
                continue;
            }
            if (data.hours < 12) {
                bAM = true;
            } else {
                bPM = true;
            }
        }
        Collections.sort(alarmList, new Comparator<AlarmClock.AlarmClockData>() {
            @Override
            public int compare(AlarmClock.AlarmClockData o1, AlarmClock.AlarmClockData o2) {
                if (o1.hours < o2.hours) {
                    return 1;
                } else if (o1.hours == o2.hours) {
                    if (o1.minutes < o2.minutes) {
                        return 1;
                    }
                }
                return -1;
            }
        });
        if (bAM) {
            model = new AlarmItemModel();
            model.setbSection(true);
            model.setKey("AM");
            list.add(model);
        }
        for (AlarmClock.AlarmClockData data: alarmList) {
            if (data.hours == 0 && data.minutes == 0) {
                continue;
            }
            if (data.hours < 12) {
                model = new AlarmItemModel();
                model.setbOpen(data.clock_switch > 0);
                DecimalFormat decimalFormat=new DecimalFormat("00");
                model.setKey(decimalFormat.format(data.hours) + ":" + decimalFormat.format(data.minutes));
                model.setbEdit(bEdit);
                list.add(model);
            }
        }
        if (bPM) {
            model = new AlarmItemModel();
            model.setbNull(true);
            list.add(model);
            model = new AlarmItemModel();
            model.setbSection(true);
            model.setKey("PM");
            list.add(model);
        }
        for (AlarmClock.AlarmClockData data: alarmList) {
            if (data.hours >= 12) {
                model = new AlarmItemModel();
                model.setbOpen(data.clock_switch > 0);
                DecimalFormat decimalFormat=new DecimalFormat("00");
                model.setKey(decimalFormat.format(data.hours) + ":" + decimalFormat.format(data.minutes));
                model.setbEdit(bEdit);
                list.add(model);
            }
        }
    }

    private boolean isHavenAlarm() {
        for (AlarmClock.AlarmClockData data: alarmList) {
            if (data.hours != 0 || data.minutes != 0) {
                return true;
            }
        }
        return false;
    }

    private int alarmTotalCount() {
        int count = 0;
        for (AlarmClock.AlarmClockData data: alarmList) {
            if (data.hours == 0 && data.minutes == 0) {
                count++;
            }
        }
        return  5 - count;
    }
}