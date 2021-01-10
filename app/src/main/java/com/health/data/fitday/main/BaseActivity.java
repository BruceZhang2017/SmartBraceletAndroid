package com.health.data.fitday.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(getLayoutId());
        setTransparentStatusBar((Activity)this);
        initView();
        initData();
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

    public void setTransparentStatusBar(Activity paramActivity) {
        if (Build.VERSION.SDK_INT >= 21) {
            paramActivity.getWindow().getDecorView().setSystemUiVisibility(1280);
            paramActivity.getWindow().setStatusBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19) {
            WindowManager.LayoutParams layoutParams = paramActivity.getWindow().getAttributes();
            layoutParams.flags = 0x4000000 | layoutParams.flags;
        }
    }
}
