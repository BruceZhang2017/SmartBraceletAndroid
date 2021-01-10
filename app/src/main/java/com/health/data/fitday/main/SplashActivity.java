package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.health.data.fitday.mine.LoginActivity;
import com.health.data.fitday.utils.SpUtils;

public class SplashActivity extends Activity {
    private void enterHomeActivity() {
        if (SpUtils.getString((Context)this, "User").length() > 0) {
            startActivity(new Intent((Context)this, HomeActivity.class));
        } else {
            startActivity(new Intent((Context)this, LoginActivity.class));
        }
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        getWindow().setFlags(1024, 1024);
        super.onCreate(paramBundle);
        if (!SpUtils.getBoolean((Context)this, "first_open").booleanValue()) {
            startActivity(new Intent((Context)this, WelcomeGuideActivity.class));
            finish();
            return;
        }
        setContentView(2131427377);
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.enterHomeActivity();
            }
        },  3000L);
    }
}
