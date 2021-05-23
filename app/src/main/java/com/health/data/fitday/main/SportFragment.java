package com.health.data.fitday.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.health.data.fitday.sport.NavitationLayout;
import com.sinophy.smartbracelet.R;

public class SportFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";

    private View mContentView;

    @BindView(R.id.sport_map)
    MapView mapView;

    @BindView(R.id.sport_type_all)
    NavitationLayout navitationLayout;

    private String[] types = new String[] { "跑步", "登山", "骑车", "步行"};

    private void initData() {
        this.navitationLayout.setViewPager(getContext(), this.types, 2131034178, 2131034176, 16, 16, 0, 0, true);
        this.navitationLayout.setBgLine(getContext(), 1, 2131034171);
        this.navitationLayout.setNavLine((Activity)getActivity(), 3, 2131034172, 0);
    }

    private void initView(Bundle paramBundle) {
        this.mapView.onCreate(paramBundle);
        AMap aMap = this.mapView.getMap();
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

    @OnClick({})
    public void onClick(View paramView) {
        paramView.getId();
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
