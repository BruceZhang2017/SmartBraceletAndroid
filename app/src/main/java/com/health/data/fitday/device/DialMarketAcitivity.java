package com.health.data.fitday.device;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.mine.HelpCenterActivity;
import com.health.data.fitday.sport.NavitationLayout;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DialMarketAcitivity extends BaseActivity {
    @BindView(R.id.dial_type_all) NavitationLayout navitationLayout;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    @BindView(R.id.vp_dial) ViewPager vpSport;

    private ViewPagerAdapter viewPagerAdapter;
    private String[] types = new String[] { "我的表盘", "表盘市场"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dial_market;
    }

    @Override
    protected void initData() {
        navitationLayout.setViewPager(this, types, vpSport, R.color.color_333333, R.color.color_333333, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 0, R.color.color_3ACF95);
        navitationLayout.setNavLine(this, 1, R.color.color_3ACF95, 0);

        List fragments1 =  new ArrayList<>();
        fragments1.add(new LocalDialFragment());
        fragments1.add(new CloudDialFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments1);
        vpSport.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

}