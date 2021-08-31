package com.sinophy.smartbracelet.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
    private static final String spFileName = "app";

    public static Boolean getBoolean(Context paramContext, String paramString) {
        return Boolean.valueOf(paramContext.getSharedPreferences("app", 0).getBoolean(paramString, false));
    }

    public static Boolean getBoolean(Context paramContext, String paramString, Boolean paramBoolean) {
        return Boolean.valueOf(paramContext.getSharedPreferences("app", 0).getBoolean(paramString, paramBoolean.booleanValue()));
    }

    public static int getInt(Context paramContext, String paramString) {
        return paramContext.getSharedPreferences("app", 0).getInt(paramString, -1);
    }

    public static int getInt(Context paramContext, String paramString, int paramInt) {
        return paramContext.getSharedPreferences("app", 0).getInt(paramString, paramInt);
    }

    public static long getLong(Context paramContext, String paramString) {
        return paramContext.getSharedPreferences("app", 0).getLong(paramString, -1L);
    }

    public static long getLong(Context paramContext, String paramString, long paramLong) {
        return paramContext.getSharedPreferences("app", 0).getLong(paramString, paramLong);
    }

    public static String getString(Context paramContext, String paramString) {
        return paramContext.getSharedPreferences("app", 0).getString(paramString, "");
    }

    public static String getString(Context paramContext, String paramString1, String paramString2) {
        return paramContext.getSharedPreferences("app", 0).getString(paramString1, paramString2);
    }

    public static void putBoolean(Context paramContext, String paramString, Boolean paramBoolean) {
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("app", 0).edit();
        editor.putBoolean(paramString, paramBoolean.booleanValue());
        editor.commit();
    }

    public static void putInt(Context paramContext, String paramString, int paramInt) {
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("app", 0).edit();
        editor.putInt(paramString, paramInt);
        editor.commit();
    }

    public static void putLong(Context paramContext, String paramString, long paramLong) {
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("app", 0).edit();
        editor.putLong(paramString, paramLong);
        editor.commit();
    }

    public static void putString(Context paramContext, String paramString1, String paramString2) {
        SharedPreferences.Editor editor = paramContext.getSharedPreferences("app", 0).edit();
        editor.putString(paramString1, paramString2);
        editor.commit();
    }
}
