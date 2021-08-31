package com.sinophy.smartbracelet.main;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.List;

public class GuideViewPagerAdapter extends PagerAdapter {
    private List<View> views;

    public GuideViewPagerAdapter(List<View> paramList) {
        this.views = paramList;
    }

    public void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject) {
        ((ViewPager)paramViewGroup).removeView(this.views.get(paramInt));
    }

    public int getCount() {
        List<View> list = this.views;
        return (list != null) ? list.size() : 0;
    }

    public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
        ((ViewPager)paramViewGroup).addView(this.views.get(paramInt), 0);
        return this.views.get(paramInt);
    }

    public boolean isViewFromObject(View paramView, Object paramObject) {
        boolean bool;
        if (paramView == (View)paramObject) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }
}
