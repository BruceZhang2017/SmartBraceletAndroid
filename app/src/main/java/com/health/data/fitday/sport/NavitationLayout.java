package com.health.data.fitday.sport;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavitationLayout extends RelativeLayout {
    private View bgLine;

    private View navLine;

    private int navWidth = 0;

    private OnNaPageChangeListener onNaPageChangeListener;

    private OnTitleClickListener onTitleClickListener;

    private TextView[] textViews;

    private LinearLayout titleLayout;

    private int txtSelectedColor = 0;

    private int txtSelectedSize = 16;

    private int txtUnselectedColor = 0;

    private int txtUnselectedSize = 16;

    private int widOffset = 0;

    public NavitationLayout(Context paramContext) {
        this(paramContext, (AttributeSet)null);
    }

    public NavitationLayout(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public NavitationLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.titleLayout = new LinearLayout(paramContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.titleLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.titleLayout.setOrientation(0);
        addView((View)this.titleLayout);
    }

    public static int dip2px(Context paramContext, float paramFloat) {
        return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density + 0.5F);
    }

    private static int getScreenWidth(Activity paramActivity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        paramActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private void moveBar(View paramView, int paramInt1, float paramFloat, int paramInt2) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)paramView.getLayoutParams();
        int i = (int)(paramInt1 * paramFloat);
        layoutParams.width = paramInt1 - this.widOffset * 2;
        int j = this.widOffset;
        layoutParams.setMargins(paramInt2 * paramInt1 + i + j, 0, j, 0);
        paramView.requestLayout();
    }

    private void setSelectedTxtColor(Context paramContext, int paramInt1, int paramInt2, int paramInt3) {
        TextView[] arrayOfTextView = this.textViews;
        if (arrayOfTextView != null) {
            int i = arrayOfTextView.length;
            for (byte b = 0; b < i; b++) {
                if (b == paramInt3) {
                    this.textViews[b].setTextColor(paramContext.getResources().getColor(paramInt1));
                    this.textViews[b].setTextSize(paramInt2);
                } else {
                    this.textViews[b].setTextColor(paramContext.getResources().getColor(this.txtUnselectedColor));
                    this.textViews[b].setTextSize(this.txtUnselectedSize);
                }
            }
        }
    }

    private void setTitles(Context paramContext, String[] paramArrayOfString, boolean paramBoolean) {
        this.textViews = new TextView[paramArrayOfString.length];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
        layoutParams.weight = 1.0F;
        layoutParams.gravity = 17;
        for (byte b = 0; b < paramArrayOfString.length; b++) {
            TextView textView = new TextView(paramContext);
            textView.setText(paramArrayOfString[b]);
            textView.setGravity(17);
            TextView[] arrayOfTextView = this.textViews;
            arrayOfTextView[b] = textView;
            arrayOfTextView[b].setOnClickListener(new View.OnClickListener() {
                public void onClick(View param1View) {
                    if (NavitationLayout.this.onTitleClickListener != null)
                        NavitationLayout.this.onTitleClickListener.onTitleClick(param1View);
                }
            });
            this.titleLayout.addView((View)textView, (ViewGroup.LayoutParams)layoutParams);
        }
    }

    private void setTitles(Context paramContext, String[] paramArrayOfString, boolean paramBoolean, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3) {
        this.textViews = new TextView[paramArrayOfString.length];
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, -1);
        layoutParams1.weight = 1.0F;
        layoutParams1.gravity = 17;
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(dip2px(paramContext, paramFloat1), -1);
        layoutParams2.setMargins(0, dip2px(paramContext, paramFloat2), 0, dip2px(paramContext, paramFloat3));
        for (byte b = 0; b < paramArrayOfString.length; b++) {
            TextView textView = new TextView(paramContext);
            textView.setText(paramArrayOfString[b]);
            textView.setGravity(17);
            TextView[] arrayOfTextView = this.textViews;
            arrayOfTextView[b] = textView;
            arrayOfTextView[b].setOnClickListener(new View.OnClickListener() {
                public void onClick(View param1View) {
                    if (NavitationLayout.this.onTitleClickListener != null)
                        NavitationLayout.this.onTitleClickListener.onTitleClick(param1View);
                }
            });
            this.titleLayout.addView((View)textView, (ViewGroup.LayoutParams)layoutParams1);
            if (b < paramArrayOfString.length - 1) {
                View view = new View(paramContext);
                view.setBackgroundColor(paramInt);
                this.titleLayout.addView(view, (ViewGroup.LayoutParams)layoutParams2);
            }
        }
    }

    private void setUnselectedTxtColor(Context paramContext, int paramInt1, int paramInt2) {
        TextView[] arrayOfTextView = this.textViews;
        if (arrayOfTextView != null) {
            int i = arrayOfTextView.length;
            for (byte b = 0; b < i; b++) {
                this.textViews[b].setTextColor(paramContext.getResources().getColor(paramInt1));
                this.textViews[b].setTextSize(paramInt2);
            }
        }
    }

    public void setBgLine(Context paramContext, int paramInt1, int paramInt2) {
        paramInt1 = dip2px(paramContext, paramInt1);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, paramInt1);
        View view = new View(paramContext);
        this.bgLine = view;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        this.bgLine.setBackgroundColor(paramContext.getResources().getColor(paramInt2));
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(-1, paramInt1);
        layoutParams1.addRule(12, -1);
        addView(this.bgLine, (ViewGroup.LayoutParams)layoutParams1);
    }

    public void setNavLine(Activity paramActivity, int paramInt1, int paramInt2, int paramInt3) {
        if (this.textViews != null)
            this.navWidth = getScreenWidth(paramActivity) / this.textViews.length;
        paramInt1 = dip2px((Context)paramActivity, paramInt1);
        System.out.println("width:" + this.navWidth);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, paramInt1);
        View view = new View((Context)paramActivity);
        this.navLine = view;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        this.navLine.setBackgroundColor(paramActivity.getResources().getColor(paramInt2));
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(this.navWidth, paramInt1);
        layoutParams1.addRule(12, -1);
        addView(this.navLine, (ViewGroup.LayoutParams)layoutParams1);
        moveBar(this.navLine, this.navWidth, this.widOffset, paramInt3);
    }

    public void setOnNaPageChangeListener(OnNaPageChangeListener paramOnNaPageChangeListener) {
        this.onNaPageChangeListener = paramOnNaPageChangeListener;
    }

    public void setOnTitleClickListener(OnTitleClickListener paramOnTitleClickListener) {
        this.onTitleClickListener = paramOnTitleClickListener;
    }

    public void setViewPager(Context paramContext, String[] paramArrayOfString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean) {
        this.txtUnselectedColor = paramInt1;
        this.txtSelectedColor = paramInt2;
        this.txtUnselectedSize = paramInt3;
        this.txtSelectedSize = paramInt4;
        this.widOffset = dip2px(paramContext, paramInt6);
        setTitles(paramContext, paramArrayOfString, paramBoolean);
        setUnselectedTxtColor(paramContext, paramInt1, paramInt3);
        setSelectedTxtColor(paramContext, paramInt2, paramInt4, paramInt5);
    }

    public void setViewPager(Context paramContext, String[] paramArrayOfString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean, int paramInt7, float paramFloat1, float paramFloat2, float paramFloat3) {
        this.txtUnselectedColor = paramInt1;
        this.txtSelectedColor = paramInt2;
        this.txtUnselectedSize = paramInt3;
        this.txtSelectedSize = paramInt4;
        this.widOffset = dip2px(paramContext, paramInt6);
        setTitles(paramContext, paramArrayOfString, paramBoolean, paramInt7, paramFloat1, paramFloat2, paramFloat3);
        setUnselectedTxtColor(paramContext, paramInt1, paramInt3);
        setSelectedTxtColor(paramContext, paramInt2, paramInt4, paramInt5);
    }

    public static interface OnNaPageChangeListener {
        void onPageScrollStateChanged(int param1Int);

        void onPageScrolled(int param1Int1, float param1Float, int param1Int2);

        void onPageSelected(int param1Int);
    }

    public static interface OnTitleClickListener {
        void onTitleClick(View param1View);
    }
}
