package com.sinophy.smartbracelet.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sinophy.smartbracelet.global.StatusBarUtil;
import com.sinophy.smartbracelet.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutId());
        initView();
        initData();
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        super.onStop();
    }

}
