package com.health.data.fitday.device;

import android.app.Activity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class SearchDeviceActivity extends BaseActivity {
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    @BindView(R.id.v_search)
    SearchLoadingView searchLoadingView;

    protected int getLayoutId() {
        return R.layout.activity_search_device;
    }

    protected void initData() {}

    protected void initView() {
        ButterKnife.bind((Activity)this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                SearchDeviceActivity.this.finish();
            }
        });
        this.searchLoadingView.startAnimation();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.searchLoadingView.stopAnimation();
    }
}
