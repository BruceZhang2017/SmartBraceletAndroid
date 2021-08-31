package com.sinophy.smartbracelet.main;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.bigkoo.pickerview.view.TimePickerView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.sinophy.smartbracelet.main.adapter.HealthAdapter;
import com.sinophy.smartbracelet.main.adapter.HealthBean;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.sinophy.smartbracelet.utils.SpUtils;
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
    private PieChart chart2;
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
        if (type == 0 && cl != null) {
            cl.setVisibility(View.VISIBLE);
        }



        currentDate = new Date();
        tvDate.setText(StringData(currentDate));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initTimePicker();
            }
        });

        chart2 = findViewById(R.id.chart2);
        if (type == 3) {
            chart.setVisibility(View.INVISIBLE);
            initializePie();
            setPieData(getYestodayDate(), false);
        } else {
            chart2.setVisibility(View.INVISIBLE);
            initializeLineChart();
            setData(getCurrentDate(), false);
        }

        if (type == 0) {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color_2);
        } else if (type == 1) {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color_3);
        } else if (type == 2) {
            clBig.setBackgroundResource(R.drawable.drawable_gradient_bg_color);
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

    private void initializeLineChart() {
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
            xAxis.setAxisMaximum(23f);
            xAxis.setAxisMinimum(0f);
            xAxis.setLabelCount(24, true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setTextSize(8f);
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
            chart.getAxisLeft().setAxisMinimum(0f);
            chart.getAxisLeft().setAxisMaximum(5f);
            chart.getAxisLeft().setLabelCount(6, true);
            yAxis.setAxisMaximum(5f);
            yAxis.setLabelCount(6, true);

            if (type == 0) {
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
    }

    private void setData(String d, Boolean b) {

        ArrayList<Entry> values = null;
        if (type == 0) {
            values = pedoData(d, true);
        } else if (type == 1) {
            values = calData(d, true);
        } else if (type == 2) {
            values = Hrt(d, true);
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
            set1.setColor(Color.WHITE);
            set1.setDrawCircles(true);
            set1.setDrawCircleHole(false);
            set1.setDrawValues(false);
            set1.setLineWidth(2);
            // text size of values
            set1.setValueTextSize(8f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // set the filled area
            set1.setDrawFilled(false);
//            set1.setFillFormatter(new IFillFormatter() {
//                @Override
//                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                    return chart.getAxisLeft().getAxisMinimum();
//                }
//            });

            // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.drawable_gradient_curve_bg);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }

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

    private String getYestodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format( new Date(currentDate.getTime() - 24 * 60 * 60 * 1000));
        return time;
    }

    private ArrayList<Entry> pedoData(String dateStr, boolean LoadDB) {
        ArrayList<Entry> array = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Entry entry = new Entry(i, 0);
            array.add(entry);
        }
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
                            int x = Integer.parseInt(mTime);
                            float y = Float.parseFloat(mStep) / 1000;
                            Entry entry = new Entry(x, y);
                            array.remove(x);
                            array.add(x, entry);
                        }
                    }
                }
            }
        }
        return array;
    }

    private ArrayList<Entry> calData(String dateStr, boolean LoadDB) {
        ArrayList<Entry> array = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Entry entry = new Entry(i, 0);
            array.add(entry);
        }
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
                            int x = Integer.parseInt(mTime);
                            float s = Float.parseFloat(mStep);
                            float c = s * 235080 / 10532;
                            float y = c / 20000;
                            Entry entry = new Entry(x, y);
                            Log.e(TAG, "热量详细数据" + x + "  y  " + y);
                            array.remove(x);
                            array.add(x, entry);
                        }
                    }
                }
            }
        }
        return array;
    }

    private int[] sleep2(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_Sleep.HealthSleepData sleepData= Health_Sleep.GetSleep_Data(tempAddr,dateStr,startTime,endTime);
                Log.e(TAG, "睡眠  质量  " + sleepData.sleelLevel
                        + "  清醒  " + sleepData.awakeHour+":"+sleepData.awakeMinute
                        + "  浅睡  " + sleepData.lightHour+":"+sleepData.lightMinute
                        + "  深睡  " + sleepData.deepHour+":"+sleepData.deepMinute
                        + "  总时长  " + sleepData.sumHour+":"+sleepData.sumMinute);
                int ah = Integer.parseInt(sleepData.awakeHour);
                int am = Integer.parseInt(sleepData.awakeMinute);
                int lh = Integer.parseInt(sleepData.lightHour);
                int lm = Integer.parseInt(sleepData.lightMinute);
                int dh = Integer.parseInt(sleepData.deepHour);
                int dm = Integer.parseInt(sleepData.deepMinute);
                if (ah == 0 && am == 0 && lh == 0 && lm == 0 && dh == 0 && dm == 0) {
                    return new int[]{100, 0, 0};
                }
                int[] array = new int[]{ah * 60 + am, lh * 60 + lm, dh * 60 + dm};
                return array;
            }

        }
        return new int[]{100, 0, 0};
    }

    private ArrayList<Entry> Hrt(String dateStr, boolean LoadDB) {
        ArrayList<Entry> array = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            Entry entry = new Entry(i, 0);
            array.add(entry);
        }
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
                            float x = (float)dis / (60 * 60);
                            int x1 = (int)x;
                            float y = Integer.parseInt(mHrtRateDiz.mHrtRate) / (float)40;
                            Entry entry = new Entry(x1, y);
                            Log.e(TAG, "心率详细数据" + x + "  y  " + y);
                            array.remove(x1);
                            array.add(x1, entry);
                        }
                    }
                }
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
                if (type != 3) {
                    setData(getCurrentDate(), true);
                } else {
                    setPieData(getYestodayDate(), true);
                }
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
                window.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                window.setGravity(Gravity.BOTTOM);
                window.setDimAmount(0.3F);
            }
        }
        timePickerView.show();
    }

    private void initializePie() {
        chart2.setUsePercentValues(true);
        chart2.getDescription().setEnabled(false);
        chart2.setExtraOffsets(5, 10, 5, 5);

        chart2.setDragDecelerationFrictionCoef(0.95f);
        chart2.setDrawHoleEnabled(false);
        //chart2.setHoleColor(Color.WHITE);

        chart2.setTransparentCircleColor(Color.WHITE);
        chart2.setTransparentCircleAlpha(110);

        chart2.setHoleRadius(58f);
        chart2.setTransparentCircleRadius(61f);

        chart2.setDrawCenterText(true);

        chart2.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart2.setRotationEnabled(false);
        chart2.setHighlightPerTapEnabled(false);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        //chart2.setOnChartValueSelectedListener(this);

        chart2.animateY(400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = chart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        chart2.setEntryLabelColor(Color.BLACK);
        chart2.setEntryLabelTextSize(12f);
    }

    private void setPieData(String d, Boolean b) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        int[] array = sleep2(d, true);
        String[] titles = new String[]{"清醒", "浅睡", "深睡"};
        for (int i = 0; i < array.length ; i++) {
            entries.add(new PieEntry((float)array[i],
                    titles[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart2.setData(data);

        // undo all highlights
        chart2.highlightValues(null);

        chart2.invalidate();
    }
}