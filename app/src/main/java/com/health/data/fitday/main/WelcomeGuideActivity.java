package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.health.data.fitday.utils.SpUtils;
import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends Activity implements View.OnClickListener {
    private static final int[] pics = new int[] { 2131427410, 2131427411, 2131427412, 2131427413, 2131427414 };

    private GuideViewPagerAdapter adapter;

    private int currentIndex;

    private List<View> views;

    private ViewPager vp;

    private void enterMainActivity() {
        startActivity(new Intent((Context)this, SplashActivity.class));
        SpUtils.putBoolean((Context)this, "first_open", Boolean.valueOf(true));
        finish();
    }

    private void setCurDot(int paramInt) {
        if (paramInt < 0 || paramInt > pics.length || this.currentIndex == paramInt)
            return;
        this.currentIndex = paramInt;
    }

    private void setCurView(int paramInt) {
        if (paramInt < 0 || paramInt >= pics.length)
            return;
        this.vp.setCurrentItem(paramInt);
    }

    public void onClick(View paramView) {
        if (((Integer)paramView.getTag()).intValue() == pics.length - 1)
            enterMainActivity();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(2131427368);
        this.views = new ArrayList<>();
        byte b = 0;
        while (true) {
            int[] arrayOfInt = pics;
            if (b < arrayOfInt.length) {
                View view = LayoutInflater.from((Context)this).inflate(arrayOfInt[b], null);
                view.setTag(Integer.valueOf(b));
                view.setOnClickListener(this);
                this.views.add(view);
                b++;
                continue;
            }
            this.vp = (ViewPager)findViewById(2131231368);
            GuideViewPagerAdapter guideViewPagerAdapter = new GuideViewPagerAdapter(this.views);
            this.adapter = guideViewPagerAdapter;
            this.vp.setAdapter(guideViewPagerAdapter);
            this.vp.setOnPageChangeListener(new PageChangeListener());
            return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        SpUtils.putBoolean((Context)this, "first_open", Boolean.valueOf(true));
        finish();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        private PageChangeListener() {}

        public void onPageScrollStateChanged(int param1Int) {}

        public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {}

        public void onPageSelected(int param1Int) {}
    }
}
