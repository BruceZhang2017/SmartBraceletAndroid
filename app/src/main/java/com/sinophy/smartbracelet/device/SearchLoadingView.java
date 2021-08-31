package com.sinophy.smartbracelet.device;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.Timer;
import java.util.TimerTask;

public class SearchLoadingView extends View {
    private static final int POINT_DISTANCE = 10;

    private int centerX;

    private int centerY;

    private int[] colors = new int[] { 440061845, 859492245, 1715130261, -1724199019, -868561003, -12922987 };

    private Paint mPaint;

    private int mPointCount = 6;

    private int mPointRadius = 4;

    private float mPointStartX;

    private float mPointY;

    private float mWidth;

    private int start = 0;

    private TimerTask task = new TimerTask() {
        public void run() {
            if (start > mPointCount)
                start = 0;
            postInvalidate();
            start += 1;
        }
    };

    private final Timer timer = new Timer();

    public SearchLoadingView(Context paramContext) {
        this(paramContext, null);
    }

    public SearchLoadingView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public SearchLoadingView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaint.setStrokeWidth(4.0F);
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        byte b = 0;
        while (true) {
            int i = this.mPointCount;
            if (b < i) {
                int j = this.start;
                this.mPaint.setColor(this.colors[(j + b) % i]);
                float f = this.mPointStartX;
                i = this.mPointRadius;
                paramCanvas.drawCircle(f + ((i * 2 + 10) * b), this.mPointY, i, this.mPaint);
                b++;
                continue;
            }
            break;
        }
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        paramInt1 /= 2;
        this.centerX = paramInt1;
        paramInt3 = paramInt2 / 2;
        this.centerY = paramInt3;
        paramInt2 = this.mPointCount;
        float f = ((paramInt2 - 1) * this.mPointRadius * 2 + (paramInt2 - 1) * 10);
        this.mWidth = f;
        this.mPointStartX = paramInt1 - f / 2.0F;
        this.mPointY = paramInt3;
    }

    public void startAnimation() {
        this.timer.schedule(this.task, 1000L, 200L);
    }

    public void stopAnimation() {
        this.timer.cancel();
    }
}
