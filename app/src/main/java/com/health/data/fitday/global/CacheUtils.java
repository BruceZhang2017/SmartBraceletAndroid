package com.health.data.fitday.global;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @项目名： ZhiHuiDemo
 * @包名： huaxa.it.zhihuidemoUtils
 * @类名： CacheUtils
 * @创建者： 黄夏莲
 * @创建时间： 2016年10月3日 ,下午6:09:45
 *
 * @描述：跳转页面
 *
 */
public class CacheUtils
{
    private final static String         sharedPreferencesName   = "zhbj";
    private static SharedPreferences    mpreferences;                       // 静态的，从内存取（只需取1次），比读文件快（每次都要读取）

    private static SharedPreferences getsp(Context context) {
        if (mpreferences == null)
        {
            mpreferences = context.getSharedPreferences(sharedPreferencesName,
                    Context.MODE_PRIVATE);
        }
        return mpreferences;

    }

    /**
     * 通过sp获得boolean类型的数据，没有默认为false
     *
     * @param context
     *            :上下文
     * @param key
     *            ：存储的key
     * @return
     */
    public static boolean getBoolean(Context context, String key)
    {
        // TODO Auto-generated method stub
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);

        mpreferences = getsp(context);
        return mpreferences.getBoolean(key, false);// 第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
    }

    /**
     * 通过sp获得boolean类型的数据
     *
     * @param context
     *            :上下文
     * @param key
     *            ：存储的key
     * @param defValue
     *            ：默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue)
    {
        // TODO Auto-generated method stub
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);
        mpreferences = getsp(context);
        return mpreferences.getBoolean(key, defValue);
    }

    /**
     *
     * 设置Boolean的缓存数据
     *
     * @param context
     *            :上下文
     * @param key
     *            ：缓存对应的key
     * @param value
     *            ：缓存对应的值
     * @return
     */
    public static void setBoolean(Context context, String key, boolean value)
    {
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);
        mpreferences = getsp(context);
        Editor editor = mpreferences.edit();// 获取编辑器
        editor.putBoolean(key, value);
        editor.commit();

    }

    /**
     * 通过sp获得String类型的数据，没有默认为null
     *
     * @param context
     *            :上下文
     * @param key
     *            ：存储的key
     * @return
     */
    public static String getString(Context context, String key)
    {
        // TODO Auto-generated method stub
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);

        mpreferences = getsp(context);
        return mpreferences.getString(key, null);// 第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
    }

    /**
     * 通过sp获得String类型的数据
     *
     * @param context
     *            :上下文
     * @param key
     *            ：存储的key
     * @param defValue
     *            ：默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue)
    {
        // TODO Auto-generated method stub
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);
        mpreferences = getsp(context);
        return mpreferences.getString(key, defValue);
    }

    /**
     *
     * 设置String的缓存数据
     *
     * @param context
     *            :上下文
     * @param key
     *            ：缓存对应的key
     * @param value
     *            ：缓存对应的值
     * @return
     */
    public static void setString(Context context, String key, String value)
    {
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);
        mpreferences = getsp(context);
        Editor editor = mpreferences.edit();// 获取编辑器
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 通过sp获得long类型的数据，没有默认为-1
     *
     * @param context
     *            :上下文
     * @param key
     *            ：存储的key
     * @return
     */
    public static long getLong(Context context, String key)
    {
        // TODO Auto-generated method stub
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);

        mpreferences = getsp(context);
        return mpreferences.getLong(key, -1);// 第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
    }

    /**
     * 通过sp获得long类型的数据
     *
     * @param context
     *            :上下文
     * @param key
     *            ：存储的key
     * @param defValue
     *            ：默认值
     * @return
     */
    public static long getLong(Context context, String key, long defValue)
    {
        // TODO Auto-generated method stub
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);
        mpreferences = getsp(context);
        return mpreferences.getLong(key, defValue);
    }

    /**
     *
     * 设置long的缓存数据
     *
     * @param context
     *            :上下文
     * @param key
     *            ：缓存对应的key
     * @param value
     *            ：缓存对应的值
     * @return
     */
    public static void setLong(Context context, String key, long value)
    {
        // SharedPreferences mpreferences = context.getSharedPreferences(
        // sharedPreferencesName, Context.MODE_PRIVATE);
        mpreferences = getsp(context);
        Editor editor = mpreferences.edit();// 获取编辑器
        editor.putLong(key, value);
        editor.commit();

    }
}

