package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.health.data.fitday.device.SearchDeviceActivity;
import com.health.data.fitday.global.RunUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.Health_HeartBldPrs;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;

import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class HealthFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";

    @BindView(R.id.tv_step_value)
    TextView tvStepValue;
    @BindView(R.id.tv_km)
    TextView tvKM;
    @BindView(R.id.tv_cal_value)
    TextView tvCalValue;
    @BindView(R.id.tv_heart_value)
    TextView tvHeartValue;
    @BindView(R.id.tv_sleep_value)
    TextView tvSleepH;
    @BindView(R.id.tv_sleep_m_value)
    TextView tvSleepM;
    @BindView(R.id.tv_blood_value)
    TextView tvBloodValue;
    @BindView(R.id.tv_blood_ox_value)
    TextView tvOXValue;

    private View mContentView;

    private void initData() {
    }

    private void initView() {
        ButterKnife.bind(this, this.mContentView);
        RefreshLayout refreshLayout = (RefreshLayout)this.mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader((RefreshHeader)new ClassicsHeader(this.mContentView.getContext()));
        refreshLayout.setRefreshFooter((RefreshFooter)new ClassicsFooter(this.mContentView.getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout param1RefreshLayout) {
                param1RefreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore(RefreshLayout param1RefreshLayout) {
                param1RefreshLayout.finishLoadMore(2000);
            }
        });
    }

    public static HealthFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        HealthFragment healthFragment = new HealthFragment();
        healthFragment.setArguments(bundle);
        return healthFragment;
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mContext = (Activity)getActivity();
        this.mContentView = paramLayoutInflater.inflate(R.layout.fragment_health, paramViewGroup, false);
        initView();
        initData();
        return this.mContentView;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void refreshUIForSport(Health_TodayPedo.TodayStepPageData todayData) {
        if (todayData == null || tvStepValue == null) {
            return;
        }
        tvStepValue.setText(todayData.step + "");
        tvKM.setText(todayData.distance + "公里");
        tvCalValue.setText(todayData.energy);
    }

    public  void refreshUIForHeart(String mHeartData) {
        tvHeartValue.setText(mHeartData);
    }

    public void refreshUIForSleep(String sleep) {
        String[] array = sleep.split(":");
        tvSleepH.setText(array[0]);
        tvSleepM.setText(array[1]);
    }

    public void refreshUIForOxy(String value) {
        tvOXValue.setText(value);
    }

    public void refreshUIBlood(String value) {
        tvBloodValue.setText(value);
    }

    @OnClick({R.id.health_foot})
    void onClick(View view) {
        Intent intent = new Intent(mContext, HealthDetailActivity.class);
        mContext.startActivity(intent);
    }
}
