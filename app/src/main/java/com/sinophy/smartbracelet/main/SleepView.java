package com.sinophy.smartbracelet.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SleepView extends View {
    private Paint mPaint = new Paint();
    private int[] values = new int[3];

    public SleepView(Context context) {
        this(context,null);
    }

    public SleepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#EB517B"));
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = getWidth();
        float h = getHeight();
        if (values[0] > 0) {
            mPaint.setColor(Color.parseColor("#EB517B"));
            mPaint.setStrokeWidth(10);
            float v = h / 12 / 60;
            float y = (12 * 60 - values[0]) * v;
            canvas.drawLine(f / 3 / 2,y,f / 3 / 2, h,mPaint);
        }
        if (values[1] > 0) {
            mPaint.setColor(Color.parseColor("#EAB055"));
            mPaint.setStrokeWidth(10);
            float v = h / 12 / 60;
            float y = (12 * 60 - values[1]) * v;
            canvas.drawLine(f / 2,y,f / 2, h,mPaint);
        }
        if (values[2] > 0) {
            mPaint.setColor(Color.parseColor("#5AC1C1"));
            mPaint.setStrokeWidth(10);
            float v = h / 12 / 60;
            float y = (12 * 60 - values[2]) * v;
            canvas.drawLine(f * 5 / 6,y,f * 5 / 6, h,mPaint);
        }
        mPaint.setColor(Color.parseColor("#33000000"));
        mPaint.setStrokeWidth(1);
        canvas.drawLine(0,h - 1,f, h - 1,mPaint);
    }

    public void refreshUI(int[] values) {
        this.values = values;
        invalidate();
    }
}
