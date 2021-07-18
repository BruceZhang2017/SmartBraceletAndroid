package com.health.data.fitday.device.alarm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview2.builder.TimePickerBuilder;
import com.bigkoo.pickerview2.listener.CustomListener;
import com.bigkoo.pickerview2.listener.OnTimeSelectListener;
import com.bigkoo.pickerview2.view.TimePickerView;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.AlarmClock;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAlarmActivity extends BaseActivity {
    private FrameLayout mFrameLayout;
    private TimePickerView pvTime;
    private ConstraintLayout cl;
    private Button btnSubmit;
    private TextView tvRepeatValue;
    String time;
    byte[] repeat = new byte[7];

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
        btnSubmit = (Button)findViewById(R.id.btn_add);
        tvRepeatValue = (TextView)findViewById(R.id.tv_repeat_value) ;
        initTimePicker();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (time == null || time.length() == 0) {
                    Toast.makeText(AddAlarmActivity.this, "请选择时间", Toast.LENGTH_LONG).show();
                    return;
                }
                AlarmClock.AlarmClockData myAlarmClockData=new AlarmClock.AlarmClockData();
                myAlarmClockData.clockId_int = 1;     //clockId_int闹钟序号（id）
                myAlarmClockData.clock_switch = 1;   //clock_switch  开关 0关闭1打开
                myAlarmClockData.interval = 10;       //interval  重复间隔
                myAlarmClockData.weeks = repeat;          //weeks 重复模式
                String[] s = time.split(":");
                myAlarmClockData.hours  = Integer.parseInt(s[0]);          //hour   时
                myAlarmClockData.minutes = Integer.parseInt(s[1]);         //minute 分

                String ret = L4Command.AlarmClockSet(myAlarmClockData);	/*ret  返回值类型在文档最下面*/
                finish();
            }
        });

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAlarmActivity.this, WeekChooseActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
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
        int week = data.getIntExtra("week", -1);
        String strRepeat = "";
        if (((week >> 1) & 0x01) > 0) {
            strRepeat += "星期一/";
            repeat[1] = 1;
        }
        if (((week >> 2) & 0x01) > 0) {
            strRepeat += "星期二/";
            repeat[2] = 1;
        }
        if (((week >> 3) & 0x01) > 0) {
            strRepeat += "星期三/";
            repeat[3] = 1;
        }
        if (((week >> 4) & 0x01) > 0) {
            strRepeat += "星期四/";
            repeat[4] = 1;
        }
        if (((week >> 5) & 0x01) > 0) {
            strRepeat += "星期五/";
            repeat[5] = 1;
        }
        if (((week >> 6) & 0x01) > 0) {
            strRepeat += "星期六/";
            repeat[6] = 1;
        }
        if ((week & 0x01) > 0) {
            strRepeat += "星期日/";
            repeat[0] = 1;
        }
        if (strRepeat.length() > 0) {
            strRepeat = strRepeat.substring(0, strRepeat.length() - 1);
        }
        tvRepeatValue.setText(strRepeat);
    }
}