package com.health.data.fitday.main.widget;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.health.data.fitday.main.OnTabChangedListner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlphaTabsIndicator extends LinearLayout {
    private static final String STATE_INSTANCE = "instance_state";

    private static final String STATE_ITEM = "state_item";

    private boolean ISINIT;

    private int mChildCounts;

    private int mCurrentItem = 0;

    private OnTabChangedListner mListner;

    private List<AlphaTabView> mTabViews;

    private ViewPager mViewPager;

    public AlphaTabsIndicator(Context paramContext) {
        this(paramContext, (AttributeSet)null);
    }

    public AlphaTabsIndicator(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public AlphaTabsIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        post(new Runnable() {
            public void run() {
                AlphaTabsIndicator.this.isInit();
            }
        });
    }

    private void init() {
        this.ISINIT = true;
        this.mTabViews = new ArrayList<>();
        this.mChildCounts = getChildCount();
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null)
            if (viewPager.getAdapter() != null) {
                if (this.mViewPager.getAdapter().getCount() == this.mChildCounts) {
                    this.mViewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener)new MyOnPageChangeListener());
                } else {
                    throw new IllegalArgumentException("");
                }
            } else {
                throw new NullPointerException("viewpager");
            }
        byte b = 0;
        while (b < this.mChildCounts) {
            if (getChildAt(b) instanceof AlphaTabView) {
                AlphaTabView alphaTabView = (AlphaTabView)getChildAt(b);
                this.mTabViews.add(alphaTabView);
                alphaTabView.setOnClickListener(new MyOnClickListener(b));
                b++;
                continue;
            }
            throw new IllegalArgumentException("TabIndicator");
        }
        ((AlphaTabView)this.mTabViews.get(this.mCurrentItem)).setIconAlpha(1.0F);
    }

    private void isInit() {
        if (!this.ISINIT)
            init();
    }

    private void resetState() {
        for (byte b = 0; b < this.mChildCounts; b++)
            ((AlphaTabView)this.mTabViews.get(b)).setIconAlpha(0.0F);
    }

    public AlphaTabView getCurrentItemView() {
        isInit();
        return this.mTabViews.get(this.mCurrentItem);
    }

    public AlphaTabView getTabView(int paramInt) {
        isInit();
        return this.mTabViews.get(paramInt);
    }

    protected void onRestoreInstanceState(Parcelable paramParcelable) {
        List<AlphaTabView> list;
        if (paramParcelable instanceof Bundle) {
            Bundle bundle = (Bundle)paramParcelable;
            this.mCurrentItem = bundle.getInt("state_item");
            list = this.mTabViews;
            if (list == null || list.isEmpty()) {
                super.onRestoreInstanceState(bundle.getParcelable("instance_state"));
                return;
            }
            resetState();
            ((AlphaTabView)this.mTabViews.get(this.mCurrentItem)).setIconAlpha(1.0F);
            super.onRestoreInstanceState(bundle.getParcelable("instance_state"));
        } else {
            super.onRestoreInstanceState((Parcelable)list);
        }
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instance_state", super.onSaveInstanceState());
        bundle.putInt("state_item", this.mCurrentItem);
        return (Parcelable)bundle;
    }

    public void removeAllBadge() {
        isInit();
        Iterator<AlphaTabView> iterator = this.mTabViews.iterator();
        while (iterator.hasNext())
            ((AlphaTabView)iterator.next()).removeShow();
    }

    public void setOnTabChangedListner(OnTabChangedListner paramOnTabChangedListner) {
        this.mListner = paramOnTabChangedListner;
        isInit();
    }

    public void setTabCurrenItem(int paramInt) {
        if (paramInt < this.mChildCounts && paramInt > -1) {
            ((AlphaTabView)this.mTabViews.get(paramInt)).performClick();
            return;
        }
        throw new IllegalArgumentException("IndexOutOfBoundsException");
    }

    public void setViewPager(ViewPager paramViewPager) {
        this.mViewPager = paramViewPager;
        init();
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int currentIndex;

        public MyOnClickListener(int param1Int) {
            this.currentIndex = param1Int;
        }

        public void onClick(View param1View) {
            AlphaTabsIndicator.this.resetState();
            ((AlphaTabView)AlphaTabsIndicator.this.mTabViews.get(this.currentIndex)).setIconAlpha(1.0F);
            if (AlphaTabsIndicator.this.mListner != null)
                AlphaTabsIndicator.this.mListner.onTabSelected(this.currentIndex);
            if (AlphaTabsIndicator.this.mViewPager != null)
                AlphaTabsIndicator.this.mViewPager.setCurrentItem(this.currentIndex, false);
            AlphaTabsIndicator.access$302(AlphaTabsIndicator.this, this.currentIndex);
        }
    }

    private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        private MyOnPageChangeListener() {}

        public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {
            if (param1Float > 0.0F) {
                ((AlphaTabView)AlphaTabsIndicator.this.mTabViews.get(param1Int1)).setIconAlpha(1.0F - param1Float);
                ((AlphaTabView)AlphaTabsIndicator.this.mTabViews.get(param1Int1 + 1)).setIconAlpha(param1Float);
            }
            AlphaTabsIndicator.access$302(AlphaTabsIndicator.this, param1Int1);
        }

        public void onPageSelected(int param1Int) {
            super.onPageSelected(param1Int);
            AlphaTabsIndicator.this.resetState();
            ((AlphaTabView)AlphaTabsIndicator.this.mTabViews.get(param1Int)).setIconAlpha(1.0F);
            AlphaTabsIndicator.access$302(AlphaTabsIndicator.this, param1Int);
        }
    }
}
