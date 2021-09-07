package com.sinophy.smartbracelet.device;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.CenterPopupView;
import com.sinophy.smartbracelet.R;

public class ProgressPopupView extends CenterPopupView {
    ImageView ivIcon;
    TextView tvTile;

    //注意：自定义弹窗本质是一个自定义View，但是只需重写一个参数的构造，其他的不要重写，所有的自定义弹窗都是这样。
    public ProgressPopupView(@NonNull Context context) {
        super(context);
    }
    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_progress;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        System.out.println("执行这里，舒适化视图");
        ivIcon = (ImageView)findViewById(R.id.iv_icon);
        tvTile = (TextView)findViewById(R.id.tv_title);
    }
    // 设置最大宽度，看需要而定，
    @Override
    protected int getMaxWidth() {
        return super.getMaxWidth();
    }
    // 设置最大高度，看需要而定
    @Override
    protected int getMaxHeight() {
        return super.getMaxHeight();
    }
    // 设置自定义动画器，看需要而定
    @Override
    protected PopupAnimator getPopupAnimator() {
        return super.getPopupAnimator();
    }
    /**
     * 弹窗的宽度，用来动态设定当前弹窗的宽度，受getMaxWidth()限制
     *
     * @return
     */
    protected int getPopupWidth() {
        return 0;
    }

    /**
     * 弹窗的高度，用来动态设定当前弹窗的高度，受getMaxHeight()限制
     *
     * @return
     */
    protected int getPopupHeight() {
        return 0;
    }

    public void setPopTitle(String value) {
        if (tvTile == null) {
            return;
        }
        tvTile.setText(value);
    }

    public void setResource(int value) {
        if (ivIcon == null) {
            return;
        }
        ivIcon.setImageResource(value);
    }

    public void rotate() {
        //ivIcon.animate().rotation(360).setDuration(10000).
    }
}

