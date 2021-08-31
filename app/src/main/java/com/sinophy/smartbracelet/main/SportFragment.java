package com.sinophy.smartbracelet.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.sinophy.smartbracelet.sport.NavitationLayout;
import com.sinophy.smartbracelet.R;

public class SportFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";

    private View mContentView;

    @BindView(R.id.sport_map)
    MapView mapView;

    @BindView(R.id.sport_type_all)
    NavitationLayout navitationLayout;

    @BindView(R.id.vp_sport)
    ViewPager vpSport;

    private String[] types = new String[] { "跑步", "骑车", "步行"};

    private void initData() {
        navitationLayout.setViewPager(getActivity(), types, vpSport, R.color.color_333333, R.color.color_2581ff, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(getActivity(), 1, R.color.colorAccent);
        navitationLayout.setNavLine(getActivity(), 3, R.color.colorPrimary, 0);
        navitationLayout.setOnTitleClickListener(new NavitationLayout.OnTitleClickListener() {
            @Override
            public void onTitleClick(View param1View) {
                System.out.println("你为什么点击这里");
            }
        });
    }

    private void initView(Bundle paramBundle) {
        mapView.onCreate(paramBundle);
        AMap aMap = mapView.getMap();
        aMap.setTrafficEnabled(true);
        aMap.setMapType(1);
    }

    public static SportFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        SportFragment sportFragment = new SportFragment();
        sportFragment.setArguments(bundle);
        return sportFragment;
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mContext = (Activity)getActivity();
        View view = paramLayoutInflater.inflate(R.layout.fragment_sport, paramViewGroup, false);
        this.mContentView = view;
        ButterKnife.bind(this, view);
        initView(paramBundle);
        initData();
        return this.mContentView;
    }
}
