package com.health.data.fitday.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class HeartView extends View {
    private Paint mPaint = new Paint();
    private int[] values = new int[24];

    public HeartView(Context context) {
        this(context,null);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#EEEEEE"));
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置两端的类型
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 24; i++) {
            float f = getWidth() / 24;
            mPaint.setColor(Color.parseColor("#EEEEEE"));
            mPaint.setStrokeWidth(4);
            canvas.drawLine(i * f + 5,0,i * f + 5, getHeight(),mPaint);
        }
        for (int i = 0; i < 24; i++) {
            if (values[i] > 0) {
                System.out.println("绘制对应的值：" + i);
                mPaint.setColor(Color.parseColor("#EB516B"));
                mPaint.setStrokeWidth(4);
                float f = getWidth() / 24;
                float h = getHeight();
                float v = h / 100;
                float y = h / 2 - v * values[i] / 4;
                canvas.drawLine(i * f + 5,y,i * f + 5, y + v * values[i] / 2,mPaint);
            }
        }
    }

    public void refreshUI(int[] values) {
        this.values = values;
        invalidate();
    }
}
