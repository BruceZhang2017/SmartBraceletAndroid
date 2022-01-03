package com.sinophy.smartbracelet.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cktim.camera2library.camera.MessageEvent;
import com.sinophy.smartbracelet.device.model.DialBean;
import com.sinophy.smartbracelet.global.CacheUtils;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
        EventBus.getDefault().register(this);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void refreshUIForSport(Health_TodayPedo.TodayStepPageData todayData) {
        if (todayData == null || tvStepValue == null) {
            return;
        }
        tvStepValue.setText(todayData.step + "");
        if (todayData.distanceUnit.equals("0")) {
            float f = Float.parseFloat(todayData.distance) / 1000;
            DecimalFormat df = new DecimalFormat("#.##");
            String right = df.format(f);
            tvKM.setText(right + "公里");
        } else {
            tvKM.setText(todayData.distance + "公里");
        }

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
            if (sleepView != null) {
                sleepView.refreshUI(sleep);
            }
        }
    }

    public void refreshUIForOxy(String value) {
        if (value.equals("0") || value.equals("00")) {
            return;
        }
        SpUtils.putString(mContext, "oxy", value);
        if (tvOXValue == null) {
            return;
        }
        tvOXValue.setText(value);
    }

    public void refreshUIBlood(String value) {
        if (value.equals("00/00")) {
            return;
        }
        SpUtils.putString(mContext, "blood", value);
        if (tvBloodValue == null) {
            return;
        }
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
        if (goal > 0) {
            tvGoal.setText("目标 | " + goal + "步");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("healthclear")) {
            Health_TodayPedo.TodayStepPageData data = new Health_TodayPedo.TodayStepPageData();
            data.distance = "0";
            data.step = "0";
            data.energy = "0";
            data.distanceUnit = "0";
            refreshUIForSport(data);
            refreshUIForHeart("0");
            refreshUIForHeart(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            refreshUIForSleep("00:00");
            refreshUIForSleep(new int[]{0, 0, 0});
            tvOXValue.setText("0");
            tvBloodValue.setText("00/00");
        }
    }
}