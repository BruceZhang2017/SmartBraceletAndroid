package com.sinophy.smartbracelet.utils.statusbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.sinophy.smartbracelet.R;

public class StatusBarHeightView extends LinearLayout {
    private int statusBarHeight;

    private int type;

    public StatusBarHeightView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramAttributeSet);
    }

    public StatusBarHeightView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramAttributeSet);
    }

    public StatusBarHeightView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
        super(paramContext, paramAttributeSet, paramInt1);
        init(paramAttributeSet);
    }

    private void init(AttributeSet paramAttributeSet) {
        int i = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (Build.VERSION.SDK_INT >= 19) {
            if (i > 0)
                this.statusBarHeight = getResources().getDimensionPixelSize(i);
        } else {
            this.statusBarHeight = 0;
        }
        if (paramAttributeSet != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.StatusBarHeightView);
            this.type = typedArray.getInt(0, 0);
            typedArray.recycle();
        }
        if (this.type == 1)
            setPadding(getPaddingLeft(), this.statusBarHeight, getPaddingRight(), getPaddingBottom());
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        if (this.type == 0) {
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), paramInt1), this.statusBarHeight);
        } else {
            super.onMeasure(paramInt1, paramInt2);
        }
    }
}
