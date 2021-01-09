package com.health.data.tjddemo;

import android.app.Application;
import android.content.Context;

import com.tjd.tjdmain.devices.btv2.BTManager;
import com.tjdL4.tjdmain.L4M;


public class Applct extends Application {
	private static final String TAG = "Applct";
	
	private static Applct meInstance = null;
	private Context context;

	@Override
    public void onCreate() 
    {    	
    	super.onCreate();
    	
    	meInstance = this;
		context = getApplicationContext();

		Init_data();

    }


	public Context getContext()
	{
		return context;
	}

	public static Applct getInstance() 
	{
		return meInstance;
	}

	public static Applct getMe() 
	{
		return meInstance;
	}
	//=================================================================================
	private void Init_data()
	{
		//两者用一个
		//L4M.InitData(this);   //sdk内部数据库 (建议使用者自己做存储,sdk内部存储的数据或许不是你们想要的格式)
		L4M.InitData(this,1,-2);//外部存储方式，所有数据都是临时的，需要使用者自己创建数据库

	}

}


