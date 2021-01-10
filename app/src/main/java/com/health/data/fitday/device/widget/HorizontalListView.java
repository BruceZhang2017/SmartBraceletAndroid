package com.health.data.fitday.device.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.LinkedList;
import java.util.Queue;

public class HorizontalListView extends AdapterView<ListAdapter> {
    protected ListAdapter mAdapter;

    public boolean mAlwaysOverrideTouch = true;

    protected int mCurrentX;

    private boolean mDataChanged = false;

    private DataSetObserver mDataObserver = new DataSetObserver() {
        public void onChanged() {
            synchronized (HorizontalListView.this) {
                HorizontalListView.access$002(HorizontalListView.this, true);
                HorizontalListView.this.invalidate();
                HorizontalListView.this.requestLayout();
                return;
            }
        }

        public void onInvalidated() {
            HorizontalListView.this.reset();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }
    };

    private int mDisplayOffset = 0;

    private GestureDetector mGesture;

    private int mLeftViewIndex = -1;

    private int mMaxX = Integer.MAX_VALUE;

    protected int mNextX;

    private GestureDetector.OnGestureListener mOnGesture = (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener() {
        private boolean isEventWithinView(MotionEvent param1MotionEvent, View param1View) {
            Rect rect = new Rect();
            int[] arrayOfInt = new int[2];
            param1View.getLocationOnScreen(arrayOfInt);
            int i = arrayOfInt[0];
            int j = param1View.getWidth();
            int k = arrayOfInt[1];
            rect.set(i, k, j + i, param1View.getHeight() + k);
            return rect.contains((int)param1MotionEvent.getRawX(), (int)param1MotionEvent.getRawY());
        }

        public boolean onDown(MotionEvent param1MotionEvent) {
            return HorizontalListView.this.onDown(param1MotionEvent);
        }

        public boolean onFling(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
            return HorizontalListView.this.onFling(param1MotionEvent1, param1MotionEvent2, param1Float1, param1Float2);
        }

        public void onLongPress(MotionEvent param1MotionEvent) {
            int i = HorizontalListView.this.getChildCount();
            for (byte b = 0; b < i; b++) {
                View view = HorizontalListView.this.getChildAt(b);
                if (isEventWithinView(param1MotionEvent, view)) {
                    if (HorizontalListView.this.mOnItemLongClicked != null) {
                        AdapterView.OnItemLongClickListener onItemLongClickListener = HorizontalListView.this.mOnItemLongClicked;
                        HorizontalListView horizontalListView = HorizontalListView.this;
                        onItemLongClickListener.onItemLongClick(horizontalListView, view, horizontalListView.mLeftViewIndex + 1 + b, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + b));
                    }
                    break;
                }
            }
        }

        public boolean onScroll(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, float param1Float1, float param1Float2) {
            synchronized (HorizontalListView.this) {
                HorizontalListView horizontalListView = HorizontalListView.this;
                horizontalListView.mNextX += (int)param1Float1;
                HorizontalListView.this.requestLayout();
                return true;
            }
        }

        public boolean onSingleTapConfirmed(MotionEvent param1MotionEvent) {
            for (byte b = 0; b < HorizontalListView.this.getChildCount(); b++) {
                View view = HorizontalListView.this.getChildAt(b);
                if (isEventWithinView(param1MotionEvent, view)) {
                    if (HorizontalListView.this.mOnItemClicked != null) {
                        AdapterView.OnItemClickListener onItemClickListener = HorizontalListView.this.mOnItemClicked;
                        HorizontalListView horizontalListView = HorizontalListView.this;
                        onItemClickListener.onItemClick(horizontalListView, view, horizontalListView.mLeftViewIndex + 1 + b, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + b));
                    }
                    if (HorizontalListView.this.mOnItemSelected != null) {
                        AdapterView.OnItemSelectedListener onItemSelectedListener = HorizontalListView.this.mOnItemSelected;
                        HorizontalListView horizontalListView = HorizontalListView.this;
                        onItemSelectedListener.onItemSelected(horizontalListView, view, horizontalListView.mLeftViewIndex + 1 + b, HorizontalListView.this.mAdapter.getItemId(HorizontalListView.this.mLeftViewIndex + 1 + b));
                    }
                    break;
                }
            }
            return true;
        }
    };

    private AdapterView.OnItemClickListener mOnItemClicked;

    private AdapterView.OnItemLongClickListener mOnItemLongClicked;

    private AdapterView.OnItemSelectedListener mOnItemSelected;

    private Queue<View> mRemovedViewQueue = new LinkedList<>();

    private int mRightViewIndex = 0;

    protected Scroller mScroller;

    public HorizontalListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        initView();
    }

    private void addAndMeasureChild(View paramView, int paramInt) {
        ViewGroup.LayoutParams layoutParams1 = paramView.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = layoutParams1;
        if (layoutParams1 == null)
            layoutParams2 = new ViewGroup.LayoutParams(-1, -1);
        addViewInLayout(paramView, paramInt, layoutParams2, true);
        paramView.measure(View.MeasureSpec.makeMeasureSpec(getWidth(), -2147483648), View.MeasureSpec.makeMeasureSpec(getHeight(), -2147483648));
    }

    private void fillList(int paramInt) {
        int i = 0;
        View view = getChildAt(getChildCount() - 1);
        if (view != null)
            i = view.getRight();
        fillListRight(i, paramInt);
        i = 0;
        view = getChildAt(0);
        if (view != null)
            i = view.getLeft();
        fillListLeft(i, paramInt);
    }

    private void fillListLeft(int paramInt1, int paramInt2) {
        while (paramInt1 + paramInt2 > 0) {
            int i = this.mLeftViewIndex;
            if (i >= 0) {
                View view = this.mAdapter.getView(i, this.mRemovedViewQueue.poll(), (ViewGroup)this);
                addAndMeasureChild(view, 0);
                paramInt1 -= view.getMeasuredWidth();
                this.mLeftViewIndex--;
                this.mDisplayOffset -= view.getMeasuredWidth();
            }
        }
    }

    private void fillListRight(int paramInt1, int paramInt2) {
        while (paramInt1 + paramInt2 < getWidth() && this.mRightViewIndex < this.mAdapter.getCount()) {
            View view = this.mAdapter.getView(this.mRightViewIndex, this.mRemovedViewQueue.poll(), (ViewGroup)this);
            addAndMeasureChild(view, -1);
            paramInt1 += view.getMeasuredWidth();
            if (this.mRightViewIndex == this.mAdapter.getCount() - 1)
                this.mMaxX = this.mCurrentX + paramInt1 - getWidth();
            if (this.mMaxX < 0)
                this.mMaxX = 0;
            this.mRightViewIndex++;
        }
    }

    private void initView() {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_0
        //   3: iconst_m1
        //   4: putfield mLeftViewIndex : I
        //   7: aload_0
        //   8: iconst_0
        //   9: putfield mRightViewIndex : I
        //   12: aload_0
        //   13: iconst_0
        //   14: putfield mDisplayOffset : I
        //   17: aload_0
        //   18: iconst_0
        //   19: putfield mCurrentX : I
        //   22: aload_0
        //   23: iconst_0
        //   24: putfield mNextX : I
        //   27: aload_0
        //   28: ldc 2147483647
        //   30: putfield mMaxX : I
        //   33: new android/widget/Scroller
        //   36: astore_1
        //   37: aload_1
        //   38: aload_0
        //   39: invokevirtual getContext : ()Landroid/content/Context;
        //   42: invokespecial <init> : (Landroid/content/Context;)V
        //   45: aload_0
        //   46: aload_1
        //   47: putfield mScroller : Landroid/widget/Scroller;
        //   50: new android/view/GestureDetector
        //   53: astore_1
        //   54: aload_1
        //   55: aload_0
        //   56: invokevirtual getContext : ()Landroid/content/Context;
        //   59: aload_0
        //   60: getfield mOnGesture : Landroid/view/GestureDetector$OnGestureListener;
        //   63: invokespecial <init> : (Landroid/content/Context;Landroid/view/GestureDetector$OnGestureListener;)V
        //   66: aload_0
        //   67: aload_1
        //   68: putfield mGesture : Landroid/view/GestureDetector;
        //   71: aload_0
        //   72: monitorexit
        //   73: return
        //   74: astore_1
        //   75: aload_0
        //   76: monitorexit
        //   77: aload_1
        //   78: athrow
        // Exception table:
        //   from	to	target	type
        //   2	71	74	finally
    }

    private void positionItems(int paramInt) {
        if (getChildCount() > 0) {
            this.mDisplayOffset += paramInt;
            int i = this.mDisplayOffset;
            for (paramInt = 0; paramInt < getChildCount(); paramInt++) {
                View view = getChildAt(paramInt);
                int j = view.getMeasuredWidth();
                view.layout(i, 0, i + j, view.getMeasuredHeight());
                i += view.getPaddingRight() + j;
            }
        }
    }

    private void removeNonVisibleItems(int paramInt) {
        View view;
        for (view = getChildAt(0); view != null && view.getRight() + paramInt <= 0; view = getChildAt(0)) {
            this.mDisplayOffset += view.getMeasuredWidth();
            this.mRemovedViewQueue.offer(view);
            removeViewInLayout(view);
            this.mLeftViewIndex++;
        }
        for (view = getChildAt(getChildCount() - 1); view != null && view.getLeft() + paramInt >= getWidth(); view = getChildAt(getChildCount() - 1)) {
            this.mRemovedViewQueue.offer(view);
            removeViewInLayout(view);
            this.mRightViewIndex--;
        }
    }

    private void reset() {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_0
        //   3: invokespecial initView : ()V
        //   6: aload_0
        //   7: invokevirtual removeAllViewsInLayout : ()V
        //   10: aload_0
        //   11: invokevirtual requestLayout : ()V
        //   14: aload_0
        //   15: monitorexit
        //   16: return
        //   17: astore_1
        //   18: aload_0
        //   19: monitorexit
        //   20: aload_1
        //   21: athrow
        // Exception table:
        //   from	to	target	type
        //   2	14	17	finally
    }

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        return super.dispatchTouchEvent(paramMotionEvent) | this.mGesture.onTouchEvent(paramMotionEvent);
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public View getSelectedView() {
        return null;
    }

    protected boolean onDown(MotionEvent paramMotionEvent) {
        this.mScroller.forceFinished(true);
        return true;
    }

    protected boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_0
        //   3: getfield mScroller : Landroid/widget/Scroller;
        //   6: aload_0
        //   7: getfield mNextX : I
        //   10: iconst_0
        //   11: fload_3
        //   12: fneg
        //   13: f2i
        //   14: iconst_0
        //   15: iconst_0
        //   16: aload_0
        //   17: getfield mMaxX : I
        //   20: iconst_0
        //   21: iconst_0
        //   22: invokevirtual fling : (IIIIIIII)V
        //   25: aload_0
        //   26: monitorexit
        //   27: aload_0
        //   28: invokevirtual requestLayout : ()V
        //   31: iconst_1
        //   32: ireturn
        //   33: astore_1
        //   34: aload_0
        //   35: monitorexit
        //   36: aload_1
        //   37: athrow
        // Exception table:
        //   from	to	target	type
        //   2	27	33	finally
        //   34	36	33	finally
    }

    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return this.mGesture.onTouchEvent(paramMotionEvent);
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_0
        //   3: iload_1
        //   4: iload_2
        //   5: iload_3
        //   6: iload #4
        //   8: iload #5
        //   10: invokespecial onLayout : (ZIIII)V
        //   13: aload_0
        //   14: getfield mAdapter : Landroid/widget/ListAdapter;
        //   17: astore #6
        //   19: aload #6
        //   21: ifnonnull -> 27
        //   24: aload_0
        //   25: monitorexit
        //   26: return
        //   27: aload_0
        //   28: getfield mDataChanged : Z
        //   31: ifeq -> 57
        //   34: aload_0
        //   35: getfield mCurrentX : I
        //   38: istore_2
        //   39: aload_0
        //   40: invokespecial initView : ()V
        //   43: aload_0
        //   44: invokevirtual removeAllViewsInLayout : ()V
        //   47: aload_0
        //   48: iload_2
        //   49: putfield mNextX : I
        //   52: aload_0
        //   53: iconst_0
        //   54: putfield mDataChanged : Z
        //   57: aload_0
        //   58: getfield mScroller : Landroid/widget/Scroller;
        //   61: invokevirtual computeScrollOffset : ()Z
        //   64: ifeq -> 78
        //   67: aload_0
        //   68: aload_0
        //   69: getfield mScroller : Landroid/widget/Scroller;
        //   72: invokevirtual getCurrX : ()I
        //   75: putfield mNextX : I
        //   78: aload_0
        //   79: getfield mNextX : I
        //   82: ifgt -> 98
        //   85: aload_0
        //   86: iconst_0
        //   87: putfield mNextX : I
        //   90: aload_0
        //   91: getfield mScroller : Landroid/widget/Scroller;
        //   94: iconst_1
        //   95: invokevirtual forceFinished : (Z)V
        //   98: aload_0
        //   99: getfield mNextX : I
        //   102: istore_2
        //   103: aload_0
        //   104: getfield mMaxX : I
        //   107: istore_3
        //   108: iload_2
        //   109: iload_3
        //   110: if_icmplt -> 126
        //   113: aload_0
        //   114: iload_3
        //   115: putfield mNextX : I
        //   118: aload_0
        //   119: getfield mScroller : Landroid/widget/Scroller;
        //   122: iconst_1
        //   123: invokevirtual forceFinished : (Z)V
        //   126: aload_0
        //   127: getfield mCurrentX : I
        //   130: aload_0
        //   131: getfield mNextX : I
        //   134: isub
        //   135: istore_2
        //   136: aload_0
        //   137: iload_2
        //   138: invokespecial removeNonVisibleItems : (I)V
        //   141: aload_0
        //   142: iload_2
        //   143: invokespecial fillList : (I)V
        //   146: aload_0
        //   147: iload_2
        //   148: invokespecial positionItems : (I)V
        //   151: aload_0
        //   152: aload_0
        //   153: getfield mNextX : I
        //   156: putfield mCurrentX : I
        //   159: aload_0
        //   160: getfield mScroller : Landroid/widget/Scroller;
        //   163: invokevirtual isFinished : ()Z
        //   166: ifne -> 187
        //   169: new com/health/data/fitday/device/widget/HorizontalListView$2
        //   172: astore #6
        //   174: aload #6
        //   176: aload_0
        //   177: invokespecial <init> : (Lcom/health/data/fitday/device/widget/HorizontalListView;)V
        //   180: aload_0
        //   181: aload #6
        //   183: invokevirtual post : (Ljava/lang/Runnable;)Z
        //   186: pop
        //   187: aload_0
        //   188: monitorexit
        //   189: return
        //   190: astore #6
        //   192: aload_0
        //   193: monitorexit
        //   194: aload #6
        //   196: athrow
        // Exception table:
        //   from	to	target	type
        //   2	19	190	finally
        //   27	57	190	finally
        //   57	78	190	finally
        //   78	98	190	finally
        //   98	108	190	finally
        //   113	126	190	finally
        //   126	187	190	finally
    }

    public void scrollTo(int paramInt) {
        // Byte code:
        //   0: aload_0
        //   1: monitorenter
        //   2: aload_0
        //   3: getfield mScroller : Landroid/widget/Scroller;
        //   6: astore_2
        //   7: aload_0
        //   8: getfield mNextX : I
        //   11: istore_3
        //   12: aload_2
        //   13: iload_3
        //   14: iconst_0
        //   15: iload_1
        //   16: iload_3
        //   17: isub
        //   18: iconst_0
        //   19: invokevirtual startScroll : (IIII)V
        //   22: aload_0
        //   23: invokevirtual requestLayout : ()V
        //   26: aload_0
        //   27: monitorexit
        //   28: return
        //   29: astore_2
        //   30: aload_0
        //   31: monitorexit
        //   32: aload_2
        //   33: athrow
        // Exception table:
        //   from	to	target	type
        //   2	26	29	finally
    }

    public void setAdapter(ListAdapter paramListAdapter) {
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null)
            listAdapter.unregisterDataSetObserver(this.mDataObserver);
        this.mAdapter = paramListAdapter;
        paramListAdapter.registerDataSetObserver(this.mDataObserver);
        reset();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener paramOnItemClickListener) {
        this.mOnItemClicked = paramOnItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener paramOnItemLongClickListener) {
        this.mOnItemLongClicked = paramOnItemLongClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener paramOnItemSelectedListener) {
        this.mOnItemSelected = paramOnItemSelectedListener;
    }

    public void setSelection(int paramInt) {}
}
