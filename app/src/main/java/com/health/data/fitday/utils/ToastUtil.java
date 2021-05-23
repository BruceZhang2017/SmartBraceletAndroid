package com.health.data.fitday.utils;

import android.content.Context;
import com.health.data.fitday.MyApplication;
import me.drakeet.support.toast.ToastCompat;

public class ToastUtil {
    private static ToastCompat mToast;

    public static void showToast(String paramString) {
        ToastCompat toastCompat = mToast;
        if (toastCompat == null) {
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, ToastCompat.LENGTH_SHORT);
        } else {
            toastCompat.cancel();
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, ToastCompat.LENGTH_SHORT);
        }
        mToast.setDuration(ToastCompat.LENGTH_SHORT);
        mToast.setText(paramString);
        mToast.show();
    }

    public static void showToastLong(String paramString) {
        ToastCompat toastCompat = mToast;
        if (toastCompat == null) {
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, ToastCompat.LENGTH_LONG);
        } else {
            toastCompat.cancel();
            mToast = ToastCompat.makeText((Context)MyApplication.getInstance(), paramString, ToastCompat.LENGTH_LONG);
        }
        mToast.setDuration(ToastCompat.LENGTH_LONG);
        mToast.setText(paramString);
        mToast.show();
    }
}
