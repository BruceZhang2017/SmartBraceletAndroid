package com.health.data.fitday.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bigkoo.pickerview2.view.TimePickerView;
import com.bigkoo.pickerview2.builder.TimePickerBuilder;
import com.bigkoo.pickerview2.listener.OnTimeSelectListener;
import com.bigkoo.pickerview2.listener.OnTimeSelectChangeListener;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.health.data.fitday.main.adapter.HealthAdapter;
import com.health.data.fitday.main.adapter.HealthBean;
import com.health.data.fitday.mine.AboutActivity;
import com.health.data.fitday.mine.UserInfoActivity;
import com.health.data.fitday.mine.UserInfoBean;
import com.health.data.fitday.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.Health_HeartBldPrs;
import com.tjdL4.tjdmain.contr.Health_Sleep;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class HealthDetailActivity extends BaseActivity {
    private static String TAG = "HealthDetailActivity";
    @BindView(R.id.lv_health_knowledge)
    ListView lvKnowledge;
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    HealthAdapter adapter;
    private LineChart chart;
    private ConstraintLayout cl;
    private ConstraintLayout clBig;
    private TextView tvGoal;
    private TextView tvDate;
    int type = 0; // 当前类型：1.步数
    private Date currentDate;
    LineDataSet set1;
    static final String startTime="22:00:00";
    static final String endTime="10:00:00";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Bundle bundle = getIntent().getExtras();
        int type = bundle.getInt("type");
        this.type = type;
        if (type == 0) {
            cl.setVisibility(View.VISIBLE);
        }

        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        Description description = new Description();
        description.setText(" ");
        chart.setDescription(description);

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();
            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMaximum(4f);
            xAxis.setAxisMinimum(0f);
            xAxis.setLabelCount(5, true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int v = (int)value;
                    if (v == 0) {
                        return "00:00";
                    } else if (v == 1) {
                        return "06:00";
                    } else if (v == 2) {
                        return "12:00";
                    } else if (v == 3) {
                        return "18:00";
                    } else {
                        return "00:00";
                    }
                }
            });
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisRight();
            // axis range
            yAxis.setAxisMinimum(0f);
            yAxis.setGridColor(Color.parseColor("#ffF6F1EA"));
            yAxis.setDrawZeroLine(false);
            yAxis.setAxisLineColor(Color.TRANSPARENT);
            yAxis.setGridLineWidth(0.5f);
            yAxis.setTextColor(Color.WHITE);
            chart.getAxisLeft().setEnabled(false);
            chart.getAxisLeft().setAxisMaximum(5);
            chart.getAxisLeft().setLabelCount(6, true);
            yAxis.setAxisMaximum(5);
            yAxis.setLabelCount(6, true);

            if (type == 3) {
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        if (value == 1) {
                            return "清醒";
                        } else if (value == 3) {
                            return "浅睡";
                        } else if (value == 5) {
                            return "深睡";
                        }
                        return "";
                    }
                });
            } else if (type == 0) {
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + ((int)value * 1000);
                    }
                });
            } else if (type == 1) {
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + ((int)value * 20000);
                    }
                });
            } else if (type == 2) {
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return "" + ((int)value * 40);
                    }
                });
            }
        }

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        // draw legend entries as lines
        l.setForm(Legend.LegendForm.NONE);

        currentDate = new Date();
        tvDate.setText(StringData(currentDate));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });

        setData(getCurrentDate(), false);

        if (type == 0) {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color_2);
        } else if (type == 1) {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color_3);
        } else if (type == 2) {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color);
        } else {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color_4);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int goal = SpUtils.getInt(HealthDetailActivity.this, "goal");
        tvGoal.setText(goal + "");
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                HealthDetailActivity.this.finish();
            }
        });

        List<HealthBean> list = new ArrayList<>();
        HealthBean walkBean = new HealthBean();
        walkBean.setDatetime("2020-11-08");
        walkBean.setAuthor("作者：营养学专家");
        walkBean.setTitle("饭后散步是否有助于消化？");
        walkBean.setImageResource(R.mipmap.walk);
        walkBean.setUrl("https://www.baidu.com/sf?openapi=1&dspName=iphone&from_sf=1&pd=wenda_kg&resource_id=5243&word=%E9%A5%AD%E5%90%8E%E6%95%A3%E6%AD%A5%E6%9C%89%E5%8A%A9%E4%BA%8E%E6%B6%88%E5%8C%96%E5%90%97&dsp=iphone&title=%E9%A5%AD%E5%90%8E%E6%95%A3%E6%AD%A5%E6%9C%89%E5%8A%A9%E4%BA%8E%E6%B6%88%E5%8C%96%E5%90%97&aptstamp=1604826136&top=%7B%22sfhs%22%3A11%7D&alr=1&fromSite=pc&total_res_num=40&ms=1&frsrcid=5242&frorder=2&lid=15488310758445452662&pcEqid=d6f1889300008d76000000065fa7b4a7");
        list.add(walkBean);
        HealthBean appleBean = new HealthBean();
        appleBean.setDatetime("2020-11-08");
        appleBean.setAuthor("作者：营养学专家");
        appleBean.setTitle("一天一个苹果远离医生的说法…");
        appleBean.setImageResource(R.mipmap.apple);
        appleBean.setUrl("https://www.baidu.com/sf?openapi=1&dspName=iphone&from_sf=1&pd=wenda_kg&resource_id=5243&word=%E4%B8%80%E5%A4%A9%E4%B8%80%E4%B8%AA%E8%8B%B9%E6%9E%9C%E5%AF%B9%E8%BA%AB%E4%BD%93%E6%9C%89%E4%BB%80%E4%B9%88%E5%A5%BD%E5%A4%84&dsp=iphone&title=%E4%B8%80%E5%A4%A9%E4%B8%80%E4%B8%AA%E8%8B%B9%E6%9E%9C%E5%AF%B9%E8%BA%AB%E4%BD%93%E6%9C%89%E4%BB%80%E4%B9%88%E5%A5%BD%E5%A4%84&aptstamp=1604826358&top=%7B%22sfhs%22%3A11%7D&alr=1&fromSite=pc&total_res_num=97&ms=1&frsrcid=5242&frorder=3&lid=10352777440170252320&pcEqid=8fac743b00069820000000065fa7b523");
        list.add(appleBean);
        HealthBean waterBean = new HealthBean();
        waterBean.setDatetime("2020-11-08");
        waterBean.setAuthor("作者：营养学专家");
        waterBean.setTitle("起床一杯白开水有助于排毒的…");
        waterBean.setImageResource(R.mipmap.water);
        waterBean.setUrl("https://www.baidu.com/sf?openapi=1&dspName=iphone&from_sf=1&pd=wenda_kg&resource_id=5243&word=%E8%B5%B7%E5%BA%8A%E4%B8%80%E6%9D%AF%E7%99%BD%E5%BC%80%E6%B0%B4%E6%9C%89%E5%8A%A9%E4%BA%8E&dsp=iphone&title=%E8%B5%B7%E5%BA%8A%E4%B8%80%E6%9D%AF%E7%99%BD%E5%BC%80%E6%B0%B4%E6%9C%89%E5%8A%A9%E4%BA%8E&aptstamp=1604828505&top=%7B%22sfhs%22%3A11%7D&alr=1&fromSite=pc&total_res_num=71&ms=1&frsrcid=5242&frorder=5&lid=9733609938258418419&pcEqid=8714baab001adaf3000000065fa7bd59");
        list.add(waterBean);
        adapter = new HealthAdapter();
        adapter.list = list;
        lvKnowledge.setAdapter(adapter);
        LayoutInflater layoutInflater= LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.view_health_detail, null);
        lvKnowledge.addHeaderView(view);

        chart = (LineChart)view.findViewById(R.id.chart1);
        cl = (ConstraintLayout) findViewById(R.id.cl);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthDetailActivity.this, SetTargetStepActivity.class);
                startActivity(intent);
            }
        });

        tvGoal = (TextView) findViewById(R.id.tv_step_value);
        tvDate = (TextView) findViewById(R.id.tv_date);

        clBig = (ConstraintLayout) findViewById(R.id.cl_curve);

    }

    private void setData(String d, Boolean b) {

        ArrayList<Entry> values = null;
        if (type == 0) {
            values = pedoData(d, true);
        } else if (type == 1) {
            values = calData(d, true);
        } else if (type == 2) {
            values = Hrt(d, true);
        } else if (type == 3) {
            sleep(d, true);
        }

        if (b) {
            chart.invalidate();
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");
            // black lines and points
            set1.setColor(Color.TRANSPARENT);
            set1.setDrawCircles(false);
            set1.setDrawCircleHole(false);
            set1.setDrawValues(false);

            // text size of values
            set1.setValueTextSize(9f);
            //set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.drawable_gradient_curve_bg);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets
            //set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(currentDate);
        return time;
    }

    private ArrayList<Entry> pedoData(String dateStr, boolean LoadDB) {
        ArrayList<Entry> array = new ArrayList<>();
        if (LoadDB) {
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_TodayPedo.TodayStepPageData todayData = Health_TodayPedo.GetHealth_Data(tempAddr, dateStr);
                Health_TodayPedo.StepDiz[] mStepDiz=todayData.stepDIZ;
                if(mStepDiz!=null && mStepDiz.length>0){
                    for(int i=0;i<mStepDiz.length;i++){
                        String mTime=L4M.getCrtValStr(mStepDiz[i].mTime,"0");
                        String mStep=L4M.getCrtValStr(mStepDiz[i].mStep,"0");
                        if (!(mTime.equals("00") || mTime.equals("0"))) {
                            float x = Float.parseFloat(mTime) / 6;
                            float y = Float.parseFloat(mStep) / 1000;
                            Entry entry = new Entry(x, y);
                            Log.e(TAG, "计步详细数据" + x + "  y  " + y);
                            array.add(entry);
                        }
                    }
                }
            }
        }
        return array;
    }

    private ArrayList<Entry> calData(String dateStr, boolean LoadDB) {
        ArrayList<Entry> array = new ArrayList<>();
        if (LoadDB) {
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_TodayPedo.TodayStepPageData todayData = Health_TodayPedo.GetHealth_Data(tempAddr, dateStr);
                Health_TodayPedo.StepDiz[] mStepDiz=todayData.stepDIZ;
                if(mStepDiz!=null && mStepDiz.length>0){
                    for(int i=0;i<mStepDiz.length;i++){
                        String mTime=L4M.getCrtValStr(mStepDiz[i].mTime,"0");
                        String mStep=L4M.getCrtValStr(mStepDiz[i].mStep,"0");
                        String mCalorie=L4M.getCrtValStr(mStepDiz[i].mCalorie,"0");
                        if (!(mTime.equals("00") || mTime.equals("0"))) {
                            float x = Float.parseFloat(mTime) / 6;
                            float s = Float.parseFloat(mStep);
                            float c = s * 235080 / 10532;
                            float y = c / 20000;
                            Entry entry = new Entry(x, y);
                            Log.e(TAG, "热量详细数据" + x + "  y  " + y);
                            array.add(entry);
                        }
                    }
                }
            }
        }
        return array;
    }

    private void sleep(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_Sleep.HealthSleepData sleepData= Health_Sleep.GetSleep_Data(tempAddr,dateStr,startTime,endTime);
                List<Health_Sleep.TimeSlpDiz> TimeSlpDizList=sleepData.mTimeSlpDiz;
                if (TimeSlpDizList!=null && TimeSlpDizList.size()>0){
                    for (int i = 0; i < TimeSlpDizList.size(); i++) {
                        Health_Sleep.TimeSlpDiz mTimeSlpDiz=TimeSlpDizList.get(i);

                        Log.e(TAG,"睡眠详细数据  "+mTimeSlpDiz.mRcdTime+"  "+mTimeSlpDiz.mSlpMode);
                    }
                }

            }

        }
    }

    private ArrayList<Entry> Hrt(String dateStr, boolean LoadDB) {
        ArrayList<Entry> array = new ArrayList<>();
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            System.out.println("[Hrt]设备mac地址: " + tempAddr + "日期：" + dateStr);
            if (tempAddr!=null){
                Health_HeartBldPrs.HeartPageData mHeartData = Health_HeartBldPrs.GetHeart_Data(tempAddr, dateStr);
                Log.e(TAG, "心率  " + mHeartData.HeartRate);
                List<Health_HeartBldPrs.HrtDiz> HrtRateDizList = mHeartData.mHrtDiz;
                if (HrtRateDizList != null) {
                    for (int i = 0; i < HrtRateDizList.size(); i++) {
                        Health_HeartBldPrs.HrtDiz mHrtRateDiz = HrtRateDizList.get(i);
                        if (Integer.parseInt(mHrtRateDiz.mHrtRate) > 0) {
                            int n = timeToTimestamp(mHrtRateDiz.mMsrTime);
                            int o = getZeroTime(mHrtRateDiz.mMsrTime);
                            int dis = n - o;
                            float x = (float)dis / (6 * 60 * 60);
                            float y = Integer.parseInt(mHrtRateDiz.mHrtRate) / (float)40;
                            Entry entry = new Entry(x, y);
                            Log.e(TAG, "心率详细数据" + x + "  y  " + y);
                            array.add(entry);
                        }
                    }
                }
            }
            if (array.size() == 1) {
                Entry entry = array.get(0);
                array.add(0, new Entry(entry.getX() - 0.33f, 0));
                array.add(new Entry(entry.getX() + 0.33f, 0));
            }
        }
        return array;
    }

    private static int timeToTimestamp(String startDate) {
        long start = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(startDate).getTime();
        } catch (Exception e) {

        }
        int minutes = (int)(start / 1000);
        return minutes;
    }

    private static int getZeroTime(String startDate) {
        long start = 0;
        try {
            String date = startDate.substring(0, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(date + " 00:00:00").getTime();
        } catch (Exception e) {

        }
        int minutes = (int)(start / 1000);
        return minutes;
    }

    public static String StringData(Date date){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(date);
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "星期" + mWay +" " + mMonth + "月" + mDay + "日";
    }

    private void initTimePicker() {
        TimePickerView timePickerView = (new TimePickerBuilder((Context) this, new OnTimeSelectListener() {
            public void onTimeSelect(Date param1Date, View param1View) {
                Log.i("pvTime", "onTimeSelect");
                currentDate = param1Date;
                tvDate.setText(StringData(param1Date));
                setData(getCurrentDate(), true);
            }
        })).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            public void onTimeSelectChanged(Date param1Date) {
                Log.i("pvTime", "onTimeSelectChanged");
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).isDialog(false).isCenterLabel(false).setLabel(getResources().getString(R.string.pickerview_year), getResources().getString(R.string.pickerview_month), getResources().getString(R.string.pickerview_day), "", "", "").addOnCancelClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Log.i("pvTime", "onCancelClickListener");
            }
        }).setLineSpacingMultiplier(2.0F).setContentTextSize(20).isAlphaGradient(true).build();
        Dialog dialog = timePickerView.getDialog();
        if (dialog != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams((ViewGroup.LayoutParams) layoutParams);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(com.bigkoo.pickerview2.R.style.picker_view_slide_anim);
                window.setGravity(Gravity.BOTTOM);
                window.setDimAmount(0.3F);
            }
        }
        timePickerView.show();
    }
}