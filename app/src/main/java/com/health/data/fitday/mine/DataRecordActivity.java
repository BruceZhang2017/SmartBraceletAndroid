package com.health.data.fitday.mine;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.health.data.fitday.main.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DataRecordActivity extends BaseActivity {
    @BindView(2131231197)
    ActionBarCommon actionBarCommon;

    private DataRecordAdapter adapter;

    @BindView(2131231064)
    ListView listView;

    private List<DataRecordBean> mList = new ArrayList<>();

    protected int getLayoutId() {
        return 2131427365;
    }

    protected void initData() {
        DataRecordBean dataRecordBean = new DataRecordBean();
        dataRecordBean.setKey("活动数据");
                dataRecordBean.type = 1;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558429);
        dataRecordBean.setKey("步数");
                dataRecordBean.setUnit("步");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558416);
        dataRecordBean.setKey("距离");
                dataRecordBean.setUnit("公里");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558413);
        dataRecordBean.setKey("热量");
                dataRecordBean.setUnit("千卡");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558426);
        dataRecordBean.setKey("跑步");
                dataRecordBean.setUnit("小时");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558422);
        dataRecordBean.setKey("登山");
                dataRecordBean.setUnit("米");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558414);
        dataRecordBean.setKey("骑车");
                dataRecordBean.setUnit("公里");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558419);
        dataRecordBean.setKey("徒步");
                dataRecordBean.setUnit("公里");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setKey("健康数据");
                dataRecordBean.type = 1;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558420);
        dataRecordBean.setKey("心率");
                dataRecordBean.setUnit("次/分");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558433);
        dataRecordBean.setKey("体重");
                dataRecordBean.setUnit("KG");
        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558428);
        dataRecordBean.setKey("睡眠");
                dataRecordBean.setUnit("小时");
                        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        dataRecordBean = new DataRecordBean();
        dataRecordBean.setIcon(2131558424);
        dataRecordBean.setKey("血压");
                dataRecordBean.setUnit("MMHG");
        dataRecordBean.type = 0;
        this.mList.add(dataRecordBean);
        DataRecordAdapter dataRecordAdapter = new DataRecordAdapter(this.mList, (Context)this);
        this.adapter = dataRecordAdapter;
        this.listView.setAdapter((ListAdapter)dataRecordAdapter);
    }

    protected void initView() {
        ButterKnife.bind((Activity)this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        DataRecordActivity.this.finish();
            }
        });
    }
}
