package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.health.data.fitday.device.CustomPopup;
import com.health.data.fitday.device.DialMarketAcitivity;
import com.health.data.fitday.device.model.DeviceBean;
import com.health.data.fitday.device.model.DialBean;
import com.health.data.fitday.device.adapter.SimpleAdapter;
import com.health.data.fitday.device.adapter.GiftPackageAdapter;
import com.health.data.fitday.device.widget.HorizontalListView;
import com.lxj.xpopup.XPopup;
import com.sinophy.smartbracelet.R;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.BaseBannerAdapter;
import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends BaseFragment {
    @BindView(R.id.horizontal_listview)
    HorizontalListView horizontalListView;

    List<DeviceBean> list = new ArrayList<>();
    GiftPackageAdapter dailAdapter;
    BaseBannerAdapter deviceAdapter;

    private View mContentView;

    @BindView(R.id.banner_view)
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
        deviceAdapter = (BaseBannerAdapter)new SimpleAdapter();
        mViewPager.setLifecycleRegistry(getLifecycle()).setAdapter(deviceAdapter).create(this.list);
        dailAdapter = new GiftPackageAdapter();
        List<DialBean> list = new ArrayList<>();
        DialBean dialA = new DialBean();
        dialA.setDialName("ITIME-1");
        dialA.setImage(R.mipmap.preview_watch1);
        DialBean dialB = new DialBean();
        dialB.setDialName("ITIME-2");
        dialB.setImage(R.mipmap.preview_watch2);
        DialBean dialC = new DialBean();
        dialC.setDialName("ITIME-3");
        dialC.setImage(R.mipmap.preview_watch3);
        list.add(dialA);
        list.add(dialB);
        list.add(dialC);
        dailAdapter.list = list;
        horizontalListView.setAdapter(dailAdapter);
        dailAdapter.setmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DialMarketAcitivity.class);
                startActivity(intent);
            }
        });
    }

    void initView() {}

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContext = (Activity)getActivity();
        View view = paramLayoutInflater.inflate(R.layout.fragment_device, viewGroup, false);
        this.mContentView = view;
        ButterKnife.bind(this, view);
        initView();
        initData();
        return this.mContentView;
    }

    @OnClick(R.id.ib_desc)
    public void onDialIntroduction(View view) {
        Context context = (Context)mContext;
        new XPopup.Builder(context)
                .asCustom(new CustomPopup(context))
                .show();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }
}
