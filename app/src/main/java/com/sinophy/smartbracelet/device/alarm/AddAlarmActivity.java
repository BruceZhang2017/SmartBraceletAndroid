package com.sinophy.smartbracelet.device.alarm;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.AlarmClock;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class AddAlarmActivity extends BaseActivity {
    private static String TAG = "AddAlarmActivity";
    private FrameLayout mFrameLayout;
    private TimePickerView pvTime;
    private ConstraintLayout cl;
    private ConstraintLayout cl2;
    ActionBarCommon actionBarCommon;
    private TextView tvRepeatValue;
    private TextView tvAmindValue;
    String time;
    AlarmClock.AlarmClockData model;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_alarm;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mFrameLayout = (FrameLayout) findViewById(R.id.fragmen_fragment);
        cl = (ConstraintLayout)findViewById(R.id.cl_repeat);
        cl2 = (ConstraintLayout)findViewById(R.id.cl_amind);
        tvRepeatValue = (TextView)findViewById(R.id.tv_repeat_value) ;
        tvAmindValue = (TextView)findViewById(R.id.tv_amind_value);
        initTimePicker();

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model == null) {
                    return;
                }
                int week = model.week;
                Intent intent = new Intent(AddAlarmActivity.this, WeekChooseActivity.class);
                intent.putExtra("week", week);
                startActivityForResult(intent, 1);
            }
        });

        cl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model == null) {
                    return;
                }
                int internal = model.interval;
                Intent intent = new Intent(AddAlarmActivity.this, AmindActivity.class);
                intent.putExtra("internal", internal);
                startActivityForResult(intent, 2);
            }
        });

        actionBarCommon = (ActionBarCommon)findViewById(R.id.simple_action_bar);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        actionBarCommon.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                if (time == null || time.length() == 0) {
                    Toast.makeText(AddAlarmActivity.this, "请选择时间", Toast.LENGTH_LONG).show();
                    return;
                }
                String[] s = time.split(":");
                model.hours  = Integer.parseInt(s[0]);          //hour   时
                model.minutes = Integer.parseInt(s[1]);         //minute 分
                if (model.weeks == null || model.weeks.length == 0) {
                    model.weeks = bytesToByteArray(model.week);
                }
                String ret = L4Command.AlarmClockSet(model);	/*ret  返回值类型在文档最下面*/
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int i = bundle.getInt("i");
            model = AlarmListActivity.alarmList.get(i);
        }

        if (model != null) {
            refreshRepeat();
            refreshInternal();
            Calendar date = Calendar.getInstance();
            date.set(2021, 8, 30, model.hours, model.minutes);
            time = model.hours + ":" + model.minutes;
            pvTime.setDate(date);
        }
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2021, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                System.out.println("选择了时间");
                time = getTime(date);
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                System.out.println("选择了时间");
                time = getTime(date);
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setDecorView(mFrameLayout)
                .setOutSideColor(0xffffffff)
                .setOutSideCancelable(false)
                .isDialog(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            int week = data.getIntExtra("week", -1);
            if (week > 0) {
                model.week = week;
                refreshRepeat();
            }
        } else {
            int amind = data.getIntExtra("amind", -1);
            if (amind > 0) {
                model.interval = amind;
                refreshInternal();
            }
        }
    }

    private void refreshRepeat() {
        Log.i(TAG, "refreshRepeat" + model.clockId_int);
        int week = model.week;
        String strRepeat = "";
        if (((week >> 1) & 0x01) > 0) {
            strRepeat += "星期一、";
        }
        if (((week >> 2) & 0x01) > 0) {
            strRepeat += "星期二、";
        }
        if (((week >> 3) & 0x01) > 0) {
            strRepeat += "星期三、";
        }
        if (((week >> 4) & 0x01) > 0) {
            strRepeat += "星期四、";
        }
        if (((week >> 5) & 0x01) > 0) {
            strRepeat += "星期五、";
        }
        if (((week >> 6) & 0x01) > 0) {
            strRepeat += "星期六、";
        }
        if ((week & 0x01) > 0) {
            strRepeat += "星期日、";
        }
        if (strRepeat.length() > 0) {
            strRepeat = strRepeat.substring(0, strRepeat.length() - 1);
        } else {
            strRepeat = "无";
        }
        tvRepeatValue.setText(strRepeat);
    }

    private void refreshInternal() {
        Log.i(TAG, "refreshInternal" + model.clockId_int);
        tvAmindValue.setText(model.interval + "分钟");
    }

    private byte[] bytesToByteArray(int week) {
        byte[] array = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        if ((week & 0x01) > 0) {
            array[0] = 0x01;
        }
        if ((week >> 1 & 0x01) > 0) {
            array[1] = 0x01;
        }
        if ((week >> 2 & 0x01) > 0) {
            array[2] = 0x01;
        }
        if ((week >> 3 & 0x01) > 0) {
            array[3] = 0x01;
        }
        if ((week >> 4 & 0x01) > 0) {
            array[4] = 0x01;
        }
        if ((week >> 5 & 0x01) > 0) {
            array[5] = 0x01;
        }
        if ((week >> 6 & 0x01) > 0) {
            array[6] = 0x01;
        }
        return array;
    }
}