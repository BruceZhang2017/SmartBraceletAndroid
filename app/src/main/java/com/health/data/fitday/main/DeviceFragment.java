package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.health.data.fitday.device.CustomPopup;
import com.health.data.fitday.device.DeviceDetailActivity;
import com.health.data.fitday.device.DialMarketAcitivity;
import com.health.data.fitday.device.SearchDeviceActivity;
import com.health.data.fitday.device.adapter.GiftPackageAdapter;
import com.health.data.fitday.device.adapter.SimpleAdapter;
import com.health.data.fitday.device.model.BLEModel;
import com.health.data.fitday.device.model.DeviceManager;
import com.health.data.fitday.device.model.DialBean;
import com.health.data.fitday.device.widget.HorizontalListView;
import com.health.data.fitday.global.CacheUtils;
import com.health.data.fitday.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.sinophy.smartbracelet.R;
import com.zhpan.bannerview.BannerViewPager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceFragment extends BaseFragment {
    @BindView(R.id.horizontal_listview)
    HorizontalListView horizontalListView;

    List<BLEModel> list = new ArrayList<>();
    GiftPackageAdapter dailAdapter;
    SimpleAdapter deviceAdapter;
    String[] clockArray = new String[3];
    private View mContentView;

    @BindView(R.id.banner_view)
    BannerViewPager<BLEModel> mViewPager;

    public static DeviceFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        DeviceFragment deviceFragment = new DeviceFragment();
        deviceFragment.setArguments(bundle);
        return deviceFragment;
    }

    void initData() {
        if (DeviceManager.getInstance().models.size() > 0) {
            list = DeviceManager.getInstance().models;
        }
        if (list.size() == 0) {
            list.add(new BLEModel());
        }
        deviceAdapter = new SimpleAdapter();
        deviceAdapter.context = getContext();
        mViewPager.setLifecycleRegistry(getLifecycle()).setAdapter(deviceAdapter).create(list);
        dailAdapter = new GiftPackageAdapter();
        List<DialBean> list = new ArrayList<>();
        String mac = SpUtils.getString(mContext, "lastDeviceMac");
        boolean b = false;
        if (mac != null && mac.length() > 0) {
            LinkedHashMap<String,String> mapss = new LinkedHashMap<>();
            mapss = CacheUtils.getMap(mContext, "MyClock");
            if (mapss != null && mapss.size() > 0) {
                String value = mapss.get(mac);
                if (value != null && value.length() > 0) {
                    String[] arr = value.split("&&&");
                    clockArray = arr;
                    for (String str: arr) {
                        String[] array = str.split("&&");
                        DialBean dial = new DialBean();
                        dial.setDialName(array[0]);
                        dial.setImage(Integer.parseInt(array[1]));
                        dial.setAsset(array[2]);
                        list.add(dial);
                        b = true;
                    }
                    if (arr.length < 3) {
                        for (int i=0;i < 3 - arr.length; i++) {
                            DialBean dial = new DialBean();
                            dial.setDialName("");
                            dial.setImage(0);
                            list.add(dial);
                        }
                    }
                }
            }
        }

        if (b == false) {
            for (int i=0;i < 3; i++) {
                DialBean dial = new DialBean();
                dial.setDialName("");
                dial.setImage(0);
                list.add(dial);
            }
        }

        dailAdapter.list = list;
        horizontalListView.setAdapter(dailAdapter);
        dailAdapter.setmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DialMarketAcitivity.class);
                startActivity(intent);
            }
        });

        deviceAdapter.setmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DeviceManager.getInstance().models.size() == 0) {
                    Intent intent = new Intent(mContext, SearchDeviceActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(mContext, DeviceDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    void initView() {}

    @Override
    public void onResume() {
        super.onResume();
        if (DeviceManager.getInstance().models.size() > 0) {
            list = DeviceManager.getInstance().models;
            deviceAdapter.notifyDataSetChanged();
        }
    }

    public void refreshUI() {
        deviceAdapter.notifyDataSetChanged();
    }

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
