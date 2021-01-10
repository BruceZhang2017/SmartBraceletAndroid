package com.health.data.fitday.utils;

import android.content.Context;
import com.health.data.fitday.MyApplication;
import me.drakeet.support.toast.ToastCompat;

public class ToastUtil {
    private static ToastCompat mToast;

    public static void showToast(String paramString) {
        ToastCompat toastCompat = mToast;
        if (toastCompat == null) {
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, 0);
        } else {
            toastCompat.cancel();
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, 0);
        }
        mToast.setDuration(0);
        mToast.setText(paramString);
        mToast.show();
    }

    public static void showToastLong(String paramString) {
        ToastCompat toastCompat = mToast;
        if (toastCompat == null) {
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, 1);
        } else {
            toastCompat.cancel();
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, 1);
        }
        mToast.setDuration(1);
        mToast.setText(paramString);
        mToast.show();
    }
}
