package com.sinophy.smartbracelet.mine;

import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DataRecordActivity extends BaseActivity {
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    private DataRecordAdapter adapter;

    @BindView(R.id.lv_data_record)
    ListView listView;

    private List<DataRecordBean> mList = new ArrayList<>();

    protected int getLayoutId() {
        return R.layout.activity_data_record;
    }

    protected void initData() {
        DataRecordBean dataRecordBean = new DataRecordBean();
        dataRecordBean.setKey("活动数据");
                dataRecordBean.type = 1;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_steps);
        dataRecordBean.setKey("步数");
                dataRecordBean.setUnit("步");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_distance);
        dataRecordBean.setKey("距离");
                dataRecordBean.setUnit("公里");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_calorie);
        dataRecordBean.setKey("热量");
                dataRecordBean.setUnit("千卡");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_running);
        dataRecordBean.setKey("跑步");
                dataRecordBean.setUnit("小时");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_mountainneering);
        dataRecordBean.setKey("登山");
                dataRecordBean.setUnit("米");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_cycling);
        dataRecordBean.setKey("骑车");
                dataRecordBean.setUnit("公里");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_hiking);
        dataRecordBean.setKey("徒步");
                dataRecordBean.setUnit("公里");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setKey("健康数据");
                dataRecordBean.type = 1;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_hreartrate);
        dataRecordBean.setKey("心率");
                dataRecordBean.setUnit("次/分");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_weight);
        dataRecordBean.setKey("体重");
                dataRecordBean.setUnit("KG");
        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_sleeping);
        dataRecordBean.setKey("睡眠");
                dataRecordBean.setUnit("小时");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(R.mipmap.icon_pressure);
        dataRecordBean.setKey("血压");
                dataRecordBean.setUnit("MMHG");
        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        DataRecordAdapter dataRecordAdapter = new DataRecordAdapter(this.mList, (Context)this);
        this.adapter = dataRecordAdapter;
        this.listView.setAdapter((ListAdapter)dataRecordAdapter);
    }

    protected void initView() {
        ButterKnife.bind(this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        DataRecordActivity.this.finish();
            }
        });
    }
}
