package com.health.data.fitday.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.health.data.fitday.main.adapter.HealthAdapter;
import com.health.data.fitday.main.adapter.HealthBean;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthDetailActivity extends BaseActivity {

    @BindView(R.id.lv_health_knowledge)
    ListView lvKnowledge;
    HealthAdapter adapter;
    private LineChart chart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_health_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
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
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextColor(Color.WHITE);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisRight();
            // axis range
            yAxis.setAxisMaximum(100f);
            yAxis.setAxisMinimum(0f);
            yAxis.setGridColor(Color.parseColor("#ffF6F1EA"));
            yAxis.setDrawZeroLine(false);
            yAxis.setAxisLineColor(Color.TRANSPARENT);
            yAxis.setGridLineWidth(0.5f);
            yAxis.setTextColor(Color.WHITE);
            chart.getAxisLeft().setEnabled(false);
        }

        setData(10, 100);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
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

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }
}