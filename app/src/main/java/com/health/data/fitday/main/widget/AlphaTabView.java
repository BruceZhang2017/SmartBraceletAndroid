package com.health.data.fitday.main.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.health.data.fitday.R;

public class AlphaTabView extends View {
    private boolean isShowPoint;

    private boolean isShowRemove;

    private float mAlpha;

    private int mBadgeBackgroundColor = -65536;

    private int mBadgeNumber;

    private Context mContext;

    private Paint.FontMetricsInt mFmi;

    private Rect mIconAvailableRect = new Rect();

    private Rect mIconDrawRect = new Rect();

    private Bitmap mIconNormal;

    private Bitmap mIconSelected;

    private int mPadding = 5;

    private Paint mSelectedPaint = new Paint();

    private String mText;

    private Rect mTextBound;

    private int mTextColorNormal = -6710887;

    private int mTextColorSelected = -12140517;

    private Paint mTextPaint;

    private int mTextSize = 12;

    public AlphaTabView(Context paramContext) {
        this(paramContext, (AttributeSet)null);
        this.mContext = paramContext;
    }

    public AlphaTabView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
        this.mContext = paramContext;
    }

    public AlphaTabView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.mContext = paramContext;
        this.mTextSize = (int)TypedValue.applyDimension(2, this.mTextSize, getResources().getDisplayMetrics());
        this.mPadding = (int)TypedValue.applyDimension(1, this.mPadding, getResources().getDisplayMetrics());
        TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AlphaTabView);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)typedArray.getDrawable(2);
        if (bitmapDrawable != null)
            this.mIconNormal = bitmapDrawable.getBitmap();
        bitmapDrawable = (BitmapDrawable)typedArray.getDrawable(3);
        if (bitmapDrawable != null)
            this.mIconSelected = bitmapDrawable.getBitmap();
        Bitmap bitmap = this.mIconNormal;
        if (bitmap != null) {
            Bitmap bitmap1 = this.mIconSelected;
            if (bitmap1 != null)
                bitmap = bitmap1;
            this.mIconSelected = bitmap;
        } else {
            Bitmap bitmap1 = this.mIconSelected;
            if (bitmap1 != null)
                bitmap = bitmap1;
            this.mIconNormal = bitmap;
        }
        this.mText = typedArray.getString(4);
        this.mTextSize = typedArray.getDimensionPixelSize(5, this.mTextSize);
        this.mTextColorNormal = typedArray.getColor(6, this.mTextColorNormal);
        this.mTextColorSelected = typedArray.getColor(7, this.mTextColorSelected);
        this.mBadgeBackgroundColor = typedArray.getColor(0, this.mBadgeBackgroundColor);
        this.mPadding = (int)typedArray.getDimension(1, this.mPadding);
        typedArray.recycle();
        initText();
    }

    private Rect availableToDrawRect(Rect paramRect, Bitmap paramBitmap) {
        float f1 = 0.0F;
        float f2 = 0.0F;
        float f3 = paramRect.width() * 1.0F / paramBitmap.getWidth();
        float f4 = paramRect.height() * 1.0F / paramBitmap.getHeight();
        if (f3 > f4) {
            f1 = (paramRect.width() - paramBitmap.getWidth() * f4) / 2.0F;
        } else {
            f2 = (paramRect.height() - paramBitmap.getHeight() * f3) / 2.0F;
        }
        int i = (int)(paramRect.left + f1 + 0.5F);
        int j = (int)(paramRect.top + f2 + 0.5F);
        int k = (int)(paramRect.right - f1 + 0.5F);
        int m = (int)(paramRect.bottom - f2 + 0.5F);
        this.mIconDrawRect.set(i, j, k, m);
        return this.mIconDrawRect;
    }

    private float dp2px(Context paramContext, float paramFloat) {
        return (int)(paramFloat * (paramContext.getResources().getDisplayMetrics()).density);
    }

    private void drawBadge(Canvas paramCanvas) {
        int i = getMeasuredWidth() / 14;
        int j = getMeasuredHeight() / 9;
        if (i < j)
            j = i;
        i = this.mBadgeNumber;
        if (i > 0) {
            String str;
            float f;
            Bitmap bitmap;
            Paint paint = new Paint();
            paint.setColor(this.mBadgeBackgroundColor);
            paint.setAntiAlias(true);
            i = this.mBadgeNumber;
            if (i > 99) {
                str = "99+";
            } else {
                str = String.valueOf(i);
            }
            if (j / 1.5F == 0.0F) {
                f = 5.0F;
            } else {
                f = j / 1.5F;
            }
            i = (int)dp2px(this.mContext, j);
            if (str.length() == 1) {
                j = (int)dp2px(this.mContext, j);
                bitmap = Bitmap.createBitmap(j, i, Bitmap.Config.ARGB_8888);
            } else if (str.length() == 2) {
                j = (int)dp2px(this.mContext, (j + 5));
                bitmap = Bitmap.createBitmap(j, i, Bitmap.Config.ARGB_8888);
            } else {
                j = (int)dp2px(this.mContext, (j + 8));
                bitmap = Bitmap.createBitmap(j, j, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(bitmap);
            canvas.drawRoundRect(new RectF(0.0F, 0.0F, j, i), 50.0F, 50.0F, paint);
            paint = new Paint();
            paint.setColor(-1);
            paint.setTextSize(dp2px(this.mContext, f));
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            canvas.drawText(str, j / 2.0F, i / 2.0F - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2.0F, paint);
            paramCanvas.drawBitmap(bitmap, (getMeasuredWidth() / 10) * 6.0F, dp2px(this.mContext, 5.0F), null);
            bitmap.recycle();
        } else if (i != 0 && this.isShowPoint) {
            Paint paint = new Paint();
            paint.setColor(this.mBadgeBackgroundColor);
            paint.setAntiAlias(true);
            float f2 = (getMeasuredWidth() / 10) * 6.0F;
            float f1 = dp2px(getContext(), 5.0F);
            if (j > 10)
                j = 10;
            float f3 = dp2px(getContext(), j);
            paramCanvas.drawOval(new RectF(f2, f1, f2 + f3, f1 + f3), paint);
        }
    }

    private void initText() {
        if (this.mText != null) {
            this.mTextBound = new Rect();
            Paint paint = new Paint();
            this.mTextPaint = paint;
            paint.setTextSize(this.mTextSize);
            this.mTextPaint.setAntiAlias(true);
            this.mTextPaint.setDither(true);
            paint = this.mTextPaint;
            String str = this.mText;
            paint.getTextBounds(str, 0, str.length(), this.mTextBound);
            this.mFmi = this.mTextPaint.getFontMetricsInt();
        }
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public int getBadgeNumber() {
        return this.mBadgeNumber;
    }

    public boolean isShowPoint() {
        return this.isShowPoint;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        int i = (int)Math.ceil((this.mAlpha * 255.0F));
        Bitmap bitmap = this.mIconNormal;
        if (bitmap != null && this.mIconSelected != null) {
            Rect rect = availableToDrawRect(this.mIconAvailableRect, bitmap);
            this.mSelectedPaint.reset();
            this.mSelectedPaint.setAntiAlias(true);
            this.mSelectedPaint.setFilterBitmap(true);
            this.mSelectedPaint.setAlpha(255 - i);
            paramCanvas.drawBitmap(this.mIconNormal, null, rect, this.mSelectedPaint);
            this.mSelectedPaint.reset();
            this.mSelectedPaint.setAntiAlias(true);
            this.mSelectedPaint.setFilterBitmap(true);
            this.mSelectedPaint.setAlpha(i);
            paramCanvas.drawBitmap(this.mIconSelected, null, rect, this.mSelectedPaint);
        }
        if (this.mText != null) {
            this.mTextPaint.setColor(this.mTextColorNormal);
            this.mTextPaint.setAlpha(255 - i);
            paramCanvas.drawText(this.mText, this.mTextBound.left, (this.mTextBound.bottom - this.mFmi.bottom / 2), this.mTextPaint);
            this.mTextPaint.setColor(this.mTextColorSelected);
            this.mTextPaint.setAlpha(i);
            paramCanvas.drawText(this.mText, this.mTextBound.left, (this.mTextBound.bottom - this.mFmi.bottom / 2), this.mTextPaint);
        }
        if (!this.isShowRemove)
            drawBadge(paramCanvas);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        if (this.mText != null || (this.mIconNormal != null && this.mIconSelected != null)) {
            Rect rect;
            paramInt2 = getPaddingLeft();
            paramInt1 = getPaddingTop();
            int i = getPaddingRight();
            int j = getPaddingBottom();
            int k = getMeasuredWidth();
            int m = getMeasuredHeight();
            k = k - paramInt2 - i;
            j = m - paramInt1 - j;
            String str = this.mText;
            if (str != null && this.mIconNormal != null) {
                i = this.mTextBound.height();
                m = this.mPadding;
                this.mIconAvailableRect.set(paramInt2, paramInt1, paramInt2 + k, paramInt1 + j - i + m);
                paramInt2 = (k - this.mTextBound.width()) / 2 + paramInt2;
                paramInt1 = this.mIconAvailableRect.bottom + this.mPadding;
                rect = this.mTextBound;
                rect.set(paramInt2, paramInt1, rect.width() + paramInt2, this.mTextBound.height() + paramInt1);
            } else if (rect == null) {
                this.mIconAvailableRect.set(paramInt2, paramInt1, paramInt2 + k, paramInt1 + j);
            } else if (this.mIconNormal == null) {
                paramInt2 = (k - this.mTextBound.width()) / 2 + paramInt2;
                paramInt1 = (j - this.mTextBound.height()) / 2 + paramInt1;
                rect = this.mTextBound;
                rect.set(paramInt2, paramInt1, rect.width() + paramInt2, this.mTextBound.height() + paramInt1);
            }
            return;
        }
        throw new IllegalArgumentException("tabText tabIconSelected);
    }

    public void removeShow() {
        this.mBadgeNumber = 0;
        this.isShowPoint = false;
        this.isShowRemove = true;
        invalidate();
    }

    public void setIconAlpha(float paramFloat) {
        if (paramFloat >= 0.0F && paramFloat <= 1.0F) {
            this.mAlpha = paramFloat;
            invalidateView();
            return;
        }
        throw new IllegalArgumentException("0.0 - 1.0");
    }

    public void showNumber(int paramInt) {
        this.isShowRemove = false;
        this.isShowPoint = false;
        this.mBadgeNumber = paramInt;
        if (paramInt > 0) {
            invalidate();
        } else {
            this.isShowRemove = true;
            invalidate();
        }
    }

    public void showPoint() {
        this.isShowRemove = false;
        this.mBadgeNumber = -1;
        this.isShowPoint = true;
        invalidate();
    }
}
