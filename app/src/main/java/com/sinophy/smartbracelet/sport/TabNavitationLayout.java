package com.sinophy.smartbracelet.sport;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabNavitationLayout extends RelativeLayout {
    private OnNaPageChangeListener onNaPageChangeListener;

    private OnTitleClickListener onTitleClickListener;

    private TextView[] textViews;

    private LinearLayout titleLayout;

    public TabNavitationLayout(Context paramContext) {
        this(paramContext, null);
    }

    public TabNavitationLayout(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public TabNavitationLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.titleLayout = new LinearLayout(paramContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.titleLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.titleLayout.setOrientation(LinearLayout.HORIZONTAL);
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

    private void setSelectedTxtColor(Context paramContext, int paramInt1, int paramInt2, int paramInt3) {
        TextView[] arrayOfTextView = this.textViews;
        if (arrayOfTextView != null) {
            int i = arrayOfTextView.length;
            for (byte b = 0; b < i; b++) {
                if (b == paramInt3) {
                    this.textViews[b].setTextColor(paramContext.getResources().getColor(paramInt1));
                    this.textViews[b].setSelected(true);
                } else {
                    this.textViews[b].setTextColor(paramContext.getResources().getColor(paramInt2));
                    this.textViews[b].setSelected(false);
                }
            }
        }
    }

    private void setTitles(Context paramContext, String[] paramArrayOfString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat, boolean paramBoolean) {
        int i = paramArrayOfString.length;
        this.textViews = new TextView[paramArrayOfString.length];
        for (byte b = 0; b < i; b++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -1);
            layoutParams.weight = 1.0F;
            layoutParams.gravity = 17;
            TextView textView = new TextView(paramContext);
            textView.setText(paramArrayOfString[b]);
            textView.setTextSize(paramInt4);
            textView.setGravity(17);
            TextView[] arrayOfTextView = this.textViews;
            arrayOfTextView[b] = textView;
            arrayOfTextView[b].setOnClickListener(new View.OnClickListener() {
                public void onClick(View param1View) {
                    if (TabNavitationLayout.this.onTitleClickListener != null)
                        TabNavitationLayout.this.onTitleClickListener.onTitleClick(param1View);
                }
            });
            if (b == 0) {
                textView.setBackgroundDrawable(paramContext.getResources().getDrawable(paramInt1));
                layoutParams.setMargins(0, 0, 0, 0);
            } else if (b == i - 1) {
                textView.setBackgroundDrawable(paramContext.getResources().getDrawable(paramInt3));
                layoutParams.setMargins(-dip2px(paramContext, paramFloat), 0, 0, 0);
            } else {
                textView.setBackgroundDrawable(paramContext.getResources().getDrawable(paramInt2));
                layoutParams.setMargins(-dip2px(paramContext, paramFloat), 0, 0, 0);
            }
            this.titleLayout.addView((View)textView, (ViewGroup.LayoutParams)layoutParams);
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

    public void setOnNaPageChangeListener(OnNaPageChangeListener paramOnNaPageChangeListener) {
        this.onNaPageChangeListener = paramOnNaPageChangeListener;
    }

    public void setOnTitleClickListener(OnTitleClickListener paramOnTitleClickListener) {
        this.onTitleClickListener = paramOnTitleClickListener;
    }

    public void setViewPager(Context paramContext, String[] paramArrayOfString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, float paramFloat, boolean paramBoolean) {
        if (paramArrayOfString == null || paramArrayOfString.length == 1) {
            Toast.makeText(paramContext, "至少两个标题才行", Toast.LENGTH_SHORT).show();
            return;
        }
        setTitles(paramContext, paramArrayOfString, paramInt1, paramInt2, paramInt3, paramInt6, paramFloat, paramBoolean);
        setSelectedTxtColor(paramContext, paramInt5, paramInt4, paramInt7);
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
