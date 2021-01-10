package com.health.data.fitday.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.health.data.fitday.device.DeviceBean;
import com.health.data.fitday.device.SimpleAdapter;
import com.health.data.fitday.device.adapter.GiftPackageAdapter;
import com.health.data.fitday.device.widget.HorizontalListView;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends BaseFragment {
    @BindView(2131230998)
    HorizontalListView horizontalListView;

    List<DeviceBean> list = new ArrayList<>();

    private View mContentView;

    @BindView(2131230825)
    BannerViewPager<DeviceBean> mViewPager;

    public static DeviceFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        DeviceFragment deviceFragment = new DeviceFragment();
        deviceFragment.setArguments(bundle);
        return deviceFragment;
    }

    void initData() {
        this.list.add(new DeviceBean());
        this.mViewPager.setLifecycleRegistry(getLifecycle()).setAdapter((BaseBannerAdapter)new SimpleAdapter()).create(this.list);
        this.horizontalListView.setAdapter((ListAdapter)new GiftPackageAdapter());
    }

    void initView() {}

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mContext = (Activity)getActivity();
        View view = paramLayoutInflater.inflate(2131427402, paramViewGroup, false);
        this.mContentView = view;
        ButterKnife.bind(this, view);
        initView();
        initData();
        return this.mContentView;
    }

    @OnClick
    public void onDialIntroduction(View paramView) {}

    public void onViewCreated(View paramView, Bundle paramBundle) {
        super.onViewCreated(paramView, paramBundle);
    }
}
