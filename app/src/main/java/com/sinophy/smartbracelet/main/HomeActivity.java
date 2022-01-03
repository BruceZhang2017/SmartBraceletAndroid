package com.sinophy.smartbracelet.main;

import static com.tjdL4.tjdmain.Dev.L4UI_DATA_SyncProgress;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sinophy.smartbracelet.main.widget.AlphaTabsIndicator;
import com.sinophy.smartbracelet.MyApplication;
import com.sinophy.smartbracelet.PermissionUtil;
import com.sinophy.smartbracelet.device.DeviceSwitchActivity;
import com.sinophy.smartbracelet.device.SearchDeviceActivity;
import com.sinophy.smartbracelet.device.mobile.MyPhoneStateListenService;
import com.sinophy.smartbracelet.device.mobile.MyPhoneStateListener;
import com.sinophy.smartbracelet.device.mobile.NotificationCollectorService;
import com.sinophy.smartbracelet.device.model.BLEModel;
import com.sinophy.smartbracelet.device.model.DeviceManager;
import com.sinophy.smartbracelet.device.model.UserBean;
import com.sinophy.smartbracelet.global.RealmOperationHelper;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.utils.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinophy.smartbracelet.R;
import com.tjd.tjdmain.icentre.ICC_Contents;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.BracltBatLevel;
import com.tjdL4.tjdmain.contr.BrltUserParaSet;
import com.tjdL4.tjdmain.contr.Health_HeartBldPrs;
import com.tjdL4.tjdmain.contr.Health_Sleep;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.realm.Realm;

public class HomeActivity extends BaseActivity {
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final String TAG = "HomeActivity";
    private AlphaTabsIndicator alphaTabsIndicator;
    ArrayList<Fragment> arrayList;
    private boolean bReadUserinfoOnlyOnce = false; // 只读取用户信息一次
    private long exitTime = 0L;
    private int step = 0, getup = 0, deep = 0, shellow = 0;
    private String preDate = "";
    private String preValue = "";
    KProgressHUD hud;
    private ViewPager viewPager;
    private TextView tvTitle;
    static final String startTime="22:00:00";
    static final String endTime="10:00:00";
    private boolean bSingleFlag = false;
    private int readDataProgress = 0; // 读取数据的进度
    private String progress = ""; // 同步数据的进度
    private boolean bbbb = false;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    /** Key code constant: Play/Pause media key. */
    public static final int KEYCODE_MEDIA_PLAY_PAUSE= 85;
    /** Key code constant: Stop media key. */
    public static final int KEYCODE_MEDIA_STOP      = 86;
    /** Key code constant: Play Next media key. */
    public static final int KEYCODE_MEDIA_NEXT      = 87;
    /** Key code constant: Play Previous media key. */
    public static final int KEYCODE_MEDIA_PREVIOUS  = 88;
    /** Key code constant: Rewind media key. */
    public static final int KEYCODE_MEDIA_REWIND    = 89;
    /** Key code constant: Fast Forward media key. */
    public static final int KEYCODE_MEDIA_FAST_FORWARD = 90;

    protected int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PermissionUtil.checkPermissions(this);
        L4M.registerBTStReceiver(this, DataReceiver);
        telephony();
        registerPhoneStateListener();
        registerNotificationListener();
        initReceiver();
        startService(new Intent(this, NotificationCollectorService.class));
        toggleNotificationListenerService();
    }

    //检测通知监听服务是否被授权
    public boolean isNotificationListenerEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(this);
        if (packageNames.contains(context.getPackageName())) {
            return true;
        }
        return false;
    }

    // 将接收通知内容的服务关闭后，再开启
    private void toggleNotificationListenerService() {
        if (!isNotificationListenerEnabled(this)) {
            return;
        }
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(
                new ComponentName(this, NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

        pm.setComponentEnabledSetting(
                new ComponentName(this.getApplicationContext(), NotificationCollectorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    void initReceiver() //放在 onCreate
    {
        IntentFilter IntentFilter_a = new IntentFilter();
        IntentFilter_a.addAction(ICC_Contents.ToUi);
        registerReceiver(DataReceiver2, IntentFilter_a);
    }

    void unReceiver() //放在 onDestroy
    {
        unregisterReceiver(DataReceiver2);
    }


    private void registerPhoneStateListener() {
        Intent intent = new Intent(this,  MyPhoneStateListenService.class);
        intent.setAction(MyPhoneStateListenService.ACTION_REGISTER_LISTENER);
        startService(intent);
    }

    private void registerNotificationListener() {

    }

    protected void initData() {
        config();
        Test_InMainActivity();
    }

    void config() {
        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_PEDO,myUpDateUiCb);
        Dev.EnUpdateUiListener(myUpDateUiCb, 1);
        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_SLEEP,myUpDateUiCbSleep);
        Dev.EnUpdateUiListener(myUpDateUiCbSleep, 1);
        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_HEARTRATE,myUpDateUiCbHrt);
        Dev.EnUpdateUiListener(myUpDateUiCbHrt, 1);
        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_BLOODPRESS,myUpDateUiCbBldPrs);
        Dev.EnUpdateUiListener(myUpDateUiCbBldPrs, 1);
        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_HEALTH,myUpDateUiCb2);
        Dev.EnUpdateUiListener(myUpDateUiCb2, 1);
        Dev.SetUpdateUiListener(Dev.L4UI_PageDATA_BLDOXYGEN,myUpDateUiCb3);
        Dev.EnUpdateUiListener(myUpDateUiCb3, 1);
    }

    void unconfig() {
        Dev.EnUpdateUiListener(myUpDateUiCb, 0);
        Dev.EnUpdateUiListener(myUpDateUiCbHrt, 0);
        Dev.EnUpdateUiListener(myUpDateUiCbSleep, 0);
        Dev.EnUpdateUiListener(myUpDateUiCbBldPrs, 0);
        Dev.EnUpdateUiListener(myUpDateUiCb2, 0);
        Dev.EnUpdateUiListener(myUpDateUiCb3, 0);
    }

    protected void initView() {
        viewPager = (ViewPager)findViewById(R.id.mViewPager);
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager());
        viewPager.setAdapter((PagerAdapter)homeAdapter);
        viewPager.addOnPageChangeListener(homeAdapter);
        viewPager.setOffscreenPageLimit(2);
        AlphaTabsIndicator alphaTabsIndicator = (AlphaTabsIndicator)findViewById(R.id.alphaIndicator);
        this.alphaTabsIndicator = alphaTabsIndicator;
        alphaTabsIndicator.setViewPager(viewPager);

        if (L4M.GetConnectedMAC() != null && L4M.GetConnectedMAC().length() > 0) {

        }
        ImageButton ibSearch = (ImageButton)findViewById(R.id.ib_search);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BLEModel> list = DeviceManager.getInstance().models;
                if (list.size() > 0) {
                    Intent intent = new Intent(HomeActivity.this, DeviceSwitchActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HomeActivity.this, SearchDeviceActivity.class);
                    startActivity(intent);
                }

            }
        });
        tvTitle = (TextView)findViewById(R.id.tv_title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (L4M.GetConnectedMAC() != null && L4M.GetConnectedMAC().length() > 0) {
            if(L4M.Get_Connect_flag()!=2) { // 当前未连接成功
                System.out.println("mac:" + L4M.GetConnectedMAC());
                Dev.Connect(L4M.GetConnectedMAC(), L4M.GetConnecteddName());

            }
        }
        checkBlue();
        if (bbbb == false) {
            readDBData();
            bbbb = true;
        }
    }

    void readDBData() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pedoData(getCurrentDate(), true);
                Hrt(getCurrentDate(), true);
                sleep(getYestodayDate(), true);
                BldPrs(getCurrentDate(), true);
                oxy();
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L4M.unregisterBTStReceiver(this,DataReceiver);
        //销毁
        unconfig();
        unReceiver();

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        //if (paramInt1 == 1 && bleManager.checkGPSIsOpen()) {
            //bleManager.setScanRule();
            //bleManager.startScan();
       // }
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4 && paramKeyEvent.getAction() == 0) {
            if (System.currentTimeMillis() - this.exitTime > 2000L) {
                ToastUtil.showToast("双击退出应用");
                this.exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    public final void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
        if (paramInt == 2 && paramArrayOfint.length > 0)
            for (paramInt = 0; paramInt < paramArrayOfint.length; paramInt++) {
                if (paramArrayOfint[paramInt] == 0) {
                    //bleManager.onPermissionGranted(paramArrayOfString[paramInt]);
                }
            }
    }

    private class HomeAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private List<Fragment> fragments;

        private String[] titles;

        public HomeAdapter(FragmentManager param1FragmentManager) {
            super(param1FragmentManager);
            arrayList = new ArrayList();
            this.fragments = arrayList;
            String[] arrayOfString = new String[3];
            arrayOfString[0] = getString(R.string.health_head);
            //arrayOfString[1] = "运动";
            arrayOfString[1] = "设备";
            arrayOfString[2] = "我的";
            this.titles = arrayOfString;
            arrayList.add(HealthFragment.newInstance(this.titles[0]));
            //arrayList.add(SportFragment.newInstance(this.titles[1]));
            arrayList.add(DeviceFragment.newInstance(this.titles[1]));
            arrayList.add(MineFragment.newInstance(this.titles[2]));
        }

        public int getCount() {
            return this.fragments.size();
        }

        public Fragment getItem(int param1Int) {
            return this.fragments.get(param1Int);
        }

        public void onPageScrollStateChanged(int param1Int) {

        }

        public void onPageScrolled(int param1Int1, float param1Float, int param1Int2) {

        }

        public void onPageSelected(int param1Int) {
            if (param1Int == 0) {
                tvTitle.setText(R.string.health_head);
            } else if (param1Int == 1) {
                tvTitle.setText("设备");
                DeviceFragment fragment = (DeviceFragment)(arrayList.get(1));
                if (fragment != null) {
                    fragment.refreshUI();
                }
            } else if (param1Int == 2) {
                tvTitle.setText("我的");
                MineFragment fragment = (MineFragment)(arrayList.get(2));
                if (fragment != null) {
                    fragment.refreshUI();
                }
            }
        }
    }

    public void Test_InMainActivity() {
        L4M.SetResultListener(listener);

        L4M.SetResultToDBListener(new L4M.BTResultToDBListenr(){
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {

            }

            @Override
            public void On_ProgressResult(String TypeInfo, int datTotal, int datIdx, String StrData, Object DataObj) {
                //返回数据
                Log.w(TAG," TypeInfo:"+TypeInfo+"  Tatal:"+datTotal+"  index:"+datIdx+"  StrData:"+StrData);
                if (TypeInfo.equals("BLDOXY_HISTORY")) {
                    StrData = StrData.replace("[", "").replace("]", "");
                    String[] array = StrData.split(",");
                    if (array.length >= 3) {
                        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                        if (fragment != null) {
                            fragment.refreshUIForOxy(array[2]);
                        }
                    }
                }
            }
        });
    }

    private L4M.BTStReceiver DataReceiver = new L4M.BTStReceiver() {
        @Override
        public void  OnRec(String InfType ,String Info) {
            if (Info.contains("Connecting")) {
                if (hud == null) {
                    hud = KProgressHUD.create(HomeActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(1)
                            .setSize(200, 200);
                    hud.show();
                }
            } else if (Info.contains("BT_BLE_Connected")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() { //要执行的任务
                        L4Command.LanguageEN();
                    }
                }, 3000);
                addDeviceToDB(); // 连接成功后，将设备保持至数据库。
                config();
            } else if (Info.contains("close")) {
                unconfig();
                if (hud != null) {
                    hud.dismiss();
                    hud = null;
                }
            }
        }
    };

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format( new Date());
        return time;
    }

    private String getYestodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format( new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
        return time;
    }

    private static int timeToTimestamp(String startDate) {
        long start = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(startDate).getTime();
        } catch (Exception e) {

        }
        int minutes = (int)(start / 1000);
        return minutes;
    }

    private static int getGapMinutes(String startDate, String endDate) {
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(startDate).getTime();
            end = df.parse(endDate).getTime();
        } catch (Exception e) {

        }
        int minutes = (int) ((end - start) / (1000 * 60));
        return minutes;
    }

    private void getUserInfo() {
        L4Command.Brlt_UserParaGet(listener);
    }

    // 保存或更新用户信息
    private void saveUserInfo(BrltUserParaSet.UserParaSetData my) {
        UserBean userBean = new UserBean();
        userBean.setWeight(my.mWeight);
        userBean.setHeight(my.mHeight);
        userBean.setSex(my.mGender);
        userBean.setMobile("13888888888");
        userBean.setBirthday(L4M.GetUser_Birthday());
        userBean.setNickname(L4M.GetUserName());
        RealmOperationHelper.getInstance(Realm.getDefaultInstance()).add(userBean);
        Log.i(TAG, "将用户信息保存至数据库中");
    }

    // 读取电量逻辑
    private void readBattery() {
        L4Command.BatLevel(listener);
    }

    // 添加连接成功的设备至数据库
    private void addDeviceToDB() {
        BLEModel bleModel = new BLEModel();
        BluetoothDevice device = Dev.getRemoteBTDev();
        bleModel.setMac(L4M.GetConnectedMAC());
        bleModel.setBond(true);
        bleModel.setName(device.getName());
        bleModel.setImageName("produce_image_no.2");
        bleModel.setFirmwareVersion(Dev.get_SWVerCode());
        bleModel.setHardwareVersion(Dev.get_HWVerCode());
        RealmOperationHelper.getInstance(Realm.getDefaultInstance()).add(bleModel);
        Log.e(TAG, "将连接成功的设备保存至数据");
        DeviceManager.getInstance().currentModel = bleModel;
        SpUtils.putString(this, "lastDeviceMac", L4M.GetConnectedMAC());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DeviceManager.getInstance().initializeDevices();
                    }
                });
            }
        }, 1000);


    }

    Dev.UpdateUiListenerImpl myUpDateUiCb = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData) {
            final int dataType=ParaA;
            Log.e(TAG,"Foot ParaA:"+ParaA);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pedoData(getCurrentDate(), true);
                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCbHrt = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG,"Heart ParaA:"+ParaA);
                    Hrt(getCurrentDate(), true);
                    L4Command.GetBloodPrs();
                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCbSleep = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG,"Sleep ParaA:"+ParaA);
                    sleep(getYestodayDate(), true);
                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCbBldPrs = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG,"Blood ParaA:"+ParaA);
                    BldPrs(getCurrentDate(), true);
                }
            });
        }
    };

    private void pedoData(String dateStr, boolean LoadDB) {
        if (LoadDB) {
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_TodayPedo.TodayStepPageData todayData = Health_TodayPedo.GetHealth_Data(tempAddr, dateStr);
                Log.e(TAG, "  步数  " + todayData.step
                        + "  距离 " + todayData.distance
                        + "  距离单位  " + todayData.distanceUnit
                        + "  能量  " +todayData.energy
                        + "  心率  " +todayData.heart
                        + "  血压  " +todayData.bloodPrs);
                HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                if (fragment != null) {
                    fragment.refreshUIForSport(todayData);
                }
            }
        }
    }

    private void Hrt(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            System.out.println("[Hrt]设备mac地址: " + tempAddr + "日期：" + dateStr);
            if (tempAddr!=null){
                Health_HeartBldPrs.HeartPageData mHeartData = Health_HeartBldPrs.GetHeart_Data(tempAddr, dateStr);
                Log.e(TAG, "心率  " + mHeartData.HeartRate);
                List<Health_HeartBldPrs.HrtDiz> HrtRateDizList = mHeartData.mHrtDiz;
                String current = getCurrentDate();
                if (HrtRateDizList != null) {
                    int[] values = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                    for (int i = 0; i < HrtRateDizList.size(); i++) {
                        Health_HeartBldPrs.HrtDiz mHrtRateDiz = HrtRateDizList.get(i);
                        if (equalToTwoTime(current, mHrtRateDiz.mMsrTime)) {
                            int hour = hourInTime(mHrtRateDiz.mMsrTime);
                            values[hour] = Integer.parseInt(mHrtRateDiz.mHrtRate);
                            Log.e(TAG, "心率详细数据  时间  " + mHrtRateDiz.mMsrTime+" 心率值  "+mHrtRateDiz.mHrtRate);
                        }
                    }
                    HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                    if (fragment != null) {
                        fragment.refreshUIForHeart(values);
                    }
                }
                HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                if (fragment != null) {
                    fragment.refreshUIForHeart(mHeartData.HeartRate);
                }
            }

        }
    }

    private void sleep(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            if (tempAddr!=null){
                Health_Sleep.HealthSleepData sleepData= Health_Sleep.GetSleep_Data(tempAddr,dateStr,startTime,endTime);
                Log.e(TAG, "睡眠  质量  " + sleepData.sleelLevel
                        + "  清醒  " + sleepData.awakeHour+":"+sleepData.awakeMinute
                        + "  浅睡  " + sleepData.lightHour+":"+sleepData.lightMinute
                        + "  深睡  " + sleepData.deepHour+":"+sleepData.deepMinute
                        + "  总时长  " + sleepData.sumHour+":"+sleepData.sumMinute);
                HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                if (fragment != null) {
                    fragment.refreshUIForSleep(sleepData.sumHour+":"+sleepData.sumMinute);
                    int ah = Integer.parseInt(sleepData.awakeHour);
                    int am = Integer.parseInt(sleepData.awakeMinute);
                    int lh = Integer.parseInt(sleepData.lightHour);
                    int lm = Integer.parseInt(sleepData.lightMinute);
                    int dh = Integer.parseInt(sleepData.deepHour);
                    int dm = Integer.parseInt(sleepData.deepMinute);
                    fragment.refreshUIForSleep(new int[]{ah * 60 + am, lh * 60 + lm, dh * 60 + dm});
                }
            }

        }
    }

    private void BldPrs(String dateStr, boolean LoadDB) {
        if (LoadDB){
            String tempAddr = L4M.GetConnectedMAC();
            System.out.println("[血压]设备mac地址: " + tempAddr);
            if (tempAddr!=null){
                Health_HeartBldPrs.BloodPrsData mBldPrsData = Health_HeartBldPrs.GetBloodPrs_Data(tempAddr, dateStr);
                List<Health_HeartBldPrs.BldPrsDiz>   BldPrsDizList = mBldPrsData.mBldPrsDiz;
                Log.e(TAG, "血压 " +mBldPrsData.BloodPrs);
                HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                if (fragment != null) {
                    fragment.refreshUIBlood(mBldPrsData.BloodPrs);
                }
                return;
            }
        }
        String b = SpUtils.getString(this, "blood");
        if (b == null || b.length() == 0) {
            return;
        }
        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
        if (fragment != null) {
            fragment.refreshUIBlood(b);
        }
        return;
    }

    private void oxy() {
        String b = SpUtils.getString(this, "oxy");
        if (b == null || b.length() == 0) {
            return;
        }
        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
        if (fragment != null) {
            fragment.refreshUIForOxy(b);
        }
    }

    Dev.UpdateUiListenerImpl myUpDateUiCb2 = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData) {
            final int    TempPara=ParaA;
            final String TempStrData=StrData;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"L4UI_DATA_SyncProgress ="+TempStrData);

                    if(TempPara== L4UI_DATA_SyncProgress && TempStrData.equals("1")) {

                    }
                    else if(TempPara== L4UI_DATA_SyncProgress && TempStrData.equals("100")) {
                        if (progress.equals(TempStrData)) {
                            refreshData();
                        }
                    }
                    progress = TempStrData; // 当前进度
                }
            });
        }
    };

    Dev.UpdateUiListenerImpl myUpDateUiCb3 = new Dev.UpdateUiListenerImpl() {
        @Override
        public void UpdateUi(int ParaA, String StrData) {
            final int    TempPara=ParaA;
            final String TempStrData=StrData;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"L4UI_DATA_BOOLDOxgen ="+TempStrData);
                }
            });
        }
    };

    L4M.BTResultListenr listener = new L4M.BTResultListenr() {
        @Override
        public void On_Result(String TypeInfo, String StrData, Object DataObj) {
            final String tTypeInfo = TypeInfo;
            final String TempStr = StrData;
            final Object TempObj = DataObj;
            Log.i(TAG, "tTypeInfo: " + tTypeInfo + " TempStr: " + TempStr);
            if (TypeInfo.equals(L4M.ERROR) && StrData.equals(L4M.TIMEOUT)) {
                return;
            }

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bReadUserinfoOnlyOnce == false) {
                        bReadUserinfoOnlyOnce = true;
                        getUserInfo();
                    }
                    if (tTypeInfo.equals(L4M.GetBatLevel) && TempStr.equals(L4M.Data)) {
                        BracltBatLevel.BatLevel myBatLevel= (BracltBatLevel.BatLevel)TempObj;
                        int batlevel=myBatLevel.batlevel;
                        System.out.println("当前设备的电量为" + batlevel);
                        MyApplication.getInstance().map.put(L4M.GetConnectedMAC(), batlevel + "");
                        DeviceFragment fragment = (DeviceFragment)(arrayList.get(1));
                        if (fragment != null) {
                            fragment.refreshUI();
                        }
                        refreshData();
                    } else if (tTypeInfo.equals(L4M.GetUserPara) && TempStr.equals(L4M.Data)) {
                        BrltUserParaSet.UserParaSetData my = (BrltUserParaSet.UserParaSetData) TempObj;
                        Log.e(TAG," 年龄 "+my.mAge
                                +" 体重 "+my.mWeight
                                +" 身高 "+my.mHeight
                                +" 性别 "+my.mGender);
                        readBattery();
                        saveUserInfo(my); // 将用户信息保存到数据中
                    } else if (TempStr.startsWith("5A0925")) {
                        String target = TempStr.substring(8, 16);
                        int t = Integer.parseInt(target, 16);
                        SpUtils.putInt(HomeActivity.this, "goal", t);
                        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                        if (fragment != null && t > 0) {
                            fragment.refreshUIGoal(t + "");
                        }
                        Locale locale = getResources().getConfiguration().locale;
                        String language = locale.getLanguage();
                        System.out.println("当前手机系统语言：" + language);
                        L4Command.LanguageSwitch(language);
                    } else if (TempStr.startsWith("5A05270107")) {
                        controlMusic(KEYCODE_MEDIA_PLAY_PAUSE);
                    } else if (TempStr.startsWith("5A052702E5")) {
                        controlMusic(KEYCODE_MEDIA_NEXT);
                    } else if (TempStr.startsWith("5A052703BB")) {
                        controlMusic(KEYCODE_MEDIA_PREVIOUS);
                    }
                }
            });
        }
    };

    // 如果蓝牙未开启，则弹框提示用户开启蓝牙
    public void checkBlue() {
        BluetoothAdapter blueadapter=BluetoothAdapter.getDefaultAdapter();
        //支持蓝牙模块
        if (blueadapter != null){
            if (blueadapter.isEnabled()){
                List<BLEModel> list = DeviceManager.getInstance().models;
                boolean b = PermissionUtil.checkPermissionLocation(this);
                if ((list == null || list.size() == 0) && bSingleFlag == false && b) {
                    bSingleFlag = true;
                    Intent intent = new Intent(HomeActivity.this, SearchDeviceActivity.class);
                    startActivity(intent);
                }
            } else {
                new AlertDialog.Builder(this).setTitle("蓝牙功能尚未打开，是否打开蓝牙")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (turnOnBluetooth()){
                                    Toast tst = Toast.makeText(HomeActivity.this, "打开蓝牙成功", Toast.LENGTH_SHORT);
                                    tst.show();
                                }
                                else {
                                    Toast tst = Toast.makeText(HomeActivity.this, "打开蓝牙失败！！", Toast.LENGTH_SHORT);
                                    tst.show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();
            }
        }else{//不支持蓝牙模块
            Toast tst = Toast.makeText(this, "该设备不支持蓝牙或没有蓝牙模块", Toast.LENGTH_SHORT);
            tst.show();
        }
    }

    // 判断蓝牙是否开启
    public static boolean turnOnBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }

    private void refreshData() {
        System.out.println("同步数据：" + readDataProgress);
        if (readDataProgress == 0) {
            readDataProgress = 1;
            L4Command.GetPedo1(); // 获取步数
            return;
        }

        if (readDataProgress == 1) {
            L4Command.GetSleep1();
            readDataProgress += 1;
            return;
        }

        if (readDataProgress == 2) {
            if (hud != null) {
                hud.dismiss();
                hud = null;
            }
            L4Command.GetHeart1();
            readDataProgress += 1;

            Handler handlerD = new Handler();
            handlerD.postDelayed(new Runnable() {
                @Override
                public void run() {
                    L4Command.GetBloodPrs();
                }
            }, 6000);

            Handler handlerE = new Handler();
            handlerE.postDelayed(new Runnable() {
                @Override
                public void run() {
                    L4Command.GetBldOxyGen();
                }
            }, 8000);
            Handler handlerF = new Handler();
            handlerF.postDelayed(new Runnable() {
                @Override
                public void run() {
                    L4Command.TargetStepGet(listener);
                }
            }, 10000);
            return;
        }
    }

    public void refreshAllData() {
        readDataProgress = 0;
        refreshData();
    }

    private boolean equalToTwoTime(String time1, String time2) {
        System.out.print("时间对比" + time1 + " " + time2);
        if (time1 == null || time1.length() < 10) {
            return false;
        }
        if (time2 == null || time2.length() < 10) {
            return false;
        }
        if (time1.substring(0, 10).equals(time2.substring(0, 10))) {
            return true;
        }
        return false;
    }

    private int hourInTime(String time) {
        if (time == null || time.length() < 13) {
            return  0;
        }
        return Integer.parseInt(time.substring(11, 13));
    }

    private void telephony() {
        //获得相应的系统服务
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        System.out.println("TelephonyManager不为空" + (tm != null ? "YES" : "NO"));
        if(tm != null) {
            try {
                MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener();
                myPhoneStateListener.context = this;
                myPhoneStateListener.setCallListener(new MyPhoneStateListener.CallListener() {
                    @Override
                    public void onCallIdle() {
                    }

                    @Override
                    public void onCallOffHook() {
                    }

                    @Override
                    public void onCallRinging() {
                        //走接口查询号码信息
                        System.out.println("有电话打入");
                    }
                });
                // 注册来电监听
                tm.listen(myPhoneStateListener, MyPhoneStateListener.LISTEN_CALL_STATE);
            } catch(Exception e) {
                // 异常捕捉
            }
        }
    }

    // 找手机使用到的广播
    private BroadcastReceiver DataReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //final String action = intent.getAction();
            try {
                String msgData1 = intent.getStringExtra(ICC_Contents.ToUi_D1);
                if(msgData1==null)
                    return;

                if(msgData1!=null) {
                    if(msgData1.contains("FindPhone_Ring")) {
                        initMediaPlayer();
                        mediaPlayer.start();
                        showDialog();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void initMediaPlayer() {
        try {
            AssetFileDescriptor fd = getAssets().openFd("Alarm.mp3");
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare(); //让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialog() {
        (new android.app.AlertDialog.Builder(this)).setTitle("提示").setMessage("查找手机成功").setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }).setCancelable(false).show();
    }

    private void controlMusic(int keyCode) {
        long eventTime = SystemClock.uptimeMillis();
        KeyEvent key = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0);
        dispatchMediaKeyToAudioService(key);
        dispatchMediaKeyToAudioService(KeyEvent.changeAction(key, KeyEvent.ACTION_UP));
    }

    private void dispatchMediaKeyToAudioService(KeyEvent event) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            try {
                audioManager.dispatchMediaKeyEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
