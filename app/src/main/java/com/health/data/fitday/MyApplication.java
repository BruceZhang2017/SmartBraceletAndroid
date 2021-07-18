package com.health.data.fitday;

import android.app.Application;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.multidex.MultiDex;

import com.health.data.fitday.global.MyPhoneStateListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tjdL4.tjdmain.L4M;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MyApplication extends Application {
	private static final String TAG = "MyApplication";
	public Map<String, String> map = new HashMap<String, String>();
	private static MyApplication meInstance = null;
	private Context context;

	@Override
    public void onCreate() {
    	super.onCreate();
    	
    	meInstance = this;
		context = getApplicationContext();
		MultiDex.install(this);
		Init_data();
		Realm.init(context);
		RealmConfiguration config = new RealmConfiguration.Builder().name("bracelet").build();
		Realm.setDefaultConfiguration(config);
		CrashReport.initCrashReport(getApplicationContext(), "7680174387", false);

		monitorphone();
	}


	public Context getContext()
	{
		return context;
	}

	public static MyApplication getInstance()
	{
		return meInstance;
	}

	public static MyApplication getMe()
	{
		return meInstance;
	}
	//=================================================================================
	private void Init_data() {
		//两者用一个
		L4M.InitData(this);   //sdk内部数据库 (建议使用者自己做存储,sdk内部存储的数据或许不是你们想要的格式)
		//L4M.InitData(this,1,-2);//外部存储方式，所有数据都是临时的，需要使用者自己创建数据库

	}

	private void monitorphone() {
		MyPhoneStateListener phoneListener=new MyPhoneStateListener(); //我们派生的类

		TelephonyManager telephonyManager

				=(TelephonyManager)getSystemService(TELEPHONY_SERVICE);

		telephonyManager.listen(phoneListener,

				PhoneStateListener.LISTEN_CALL_STATE);
	}

}


