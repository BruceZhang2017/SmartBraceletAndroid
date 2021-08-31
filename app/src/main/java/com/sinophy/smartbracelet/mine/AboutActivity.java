package com.sinophy.smartbracelet.mine;

import android.app.Activity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class AboutActivity extends BaseActivity {
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    protected void initData() {}

    protected void initView() {
        ButterKnife.bind((Activity)this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        AboutActivity.this.finish();
            }
        });
    }
}
