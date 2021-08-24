package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.health.data.fitday.utils.SpUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.tv_goal)
    TextView tvGoal;

    private View mContentView;
    @BindView(R.id.hv_view)
    HeartView heartView;
    @BindView(R.id.sv_view)
    SleepView sleepView;

    private void initData() {
    }

    private void initView() {
        ButterKnife.bind(this, this.mContentView);
        RefreshLayout refreshLayout = (RefreshLayout)this.mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader((RefreshHeader)new ClassicsHeader(this.mContentView.getContext()));
        refreshLayout.setRefreshFooter((RefreshFooter)new ClassicsFooter(this.mContentView.getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout param1RefreshLayout) {
                param1RefreshLayout.finishRefresh(5000);
                System.out.println("onRefresh");
                HomeActivity activity = (HomeActivity) mContext;
                activity.refreshAllData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore(RefreshLayout param1RefreshLayout) {
                param1RefreshLayout.finishLoadMore(5000);
                System.out.println("onLoadMore");
                HomeActivity activity = (HomeActivity) mContext;
                activity.refreshAllData();
            }
        });
        int goal = SpUtils.getInt(mContext, "goal");
        if (goal > 0) {
            tvGoal.setText("目标 | " + goal + "步");
        }

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
        float f = Float.parseFloat(todayData.distance) / 1000;
        DecimalFormat df = new DecimalFormat("#.00");
        String right = df.format(f);
        tvKM.setText(todayData.distance + "公里");
        tvCalValue.setText(todayData.energy);
    }

    public  void refreshUIForHeart(String mHeartData) {
        if (tvHeartValue == null) {
            return;
        }
        tvHeartValue.setText(mHeartData);
    }

    public  void refreshUIForHeart(int[] mHeartData) {
        if (mHeartData != null && mHeartData.length == 24) {
            if (heartView != null) {
                heartView.refreshUI(mHeartData);
            }
        }
    }

    public void refreshUIForSleep(String sleep) {
        if (tvSleepH == null) {
            return;
        }
        String[] array = sleep.split(":");
        tvSleepH.setText(array[0]);
        tvSleepM.setText(array[1]);
    }

    public void refreshUIForSleep(int[] sleep) {
        if (sleep != null && sleep.length == 3) {
            sleepView.refreshUI(sleep);
        }
    }

    public void refreshUIForOxy(String value) {
        tvOXValue.setText(value);
    }

    public void refreshUIBlood(String value) {
        tvBloodValue.setText(value);
    }

    @OnClick({R.id.health_foot, R.id.health_cal, R.id.health_heart, R.id.health_sleep})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.health_foot:
                Intent intent = new Intent(mContext, HealthDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
            case R.id.health_cal:
                Intent intent2 = new Intent(mContext, HealthDetailActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putInt("type", 1);
                intent2.putExtras(bundle2);
                mContext.startActivity(intent2);
                break;
            case R.id.health_heart:
                Intent intent3 = new Intent(mContext, HealthDetailActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putInt("type", 2);
                intent3.putExtras(bundle3);
                mContext.startActivity(intent3);
                break;
            default:
                Intent intent4 = new Intent(mContext, HealthDetailActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putInt("type", 3);
                intent4.putExtras(bundle4);
                mContext.startActivity(intent4);
                break;
        }
    }

    public void refreshUIGoal(String value) {
        tvGoal.setText("目标 | " + value + "步");
    }

    @Override
    public void onResume() {
        super.onResume();
        int goal = SpUtils.getInt(mContext, "goal");
        tvGoal.setText("目标 | " + goal + "步");
    }
}
