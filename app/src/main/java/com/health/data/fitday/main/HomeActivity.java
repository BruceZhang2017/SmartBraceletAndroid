package com.health.data.fitday.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.health.data.fitday.MyApplication;
import com.health.data.fitday.PermissionUtil;
import com.health.data.fitday.device.DeviceSwitchActivity;
import com.health.data.fitday.device.SearchDeviceActivity;
import com.health.data.fitday.device.model.BLEModel;
import com.health.data.fitday.device.model.DStepModel;
import com.health.data.fitday.device.model.DeviceManager;
import com.health.data.fitday.device.model.UserBean;
import com.health.data.fitday.global.RealmOperationHelper;
import com.health.data.fitday.global.RunUtils;
import com.health.data.fitday.main.widget.AlphaTabsIndicator;
import com.health.data.fitday.utils.SpUtils;
import com.health.data.fitday.utils.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.BracltBatLevel;
import com.tjdL4.tjdmain.contr.BrltUserParaSet;
import com.tjdL4.tjdmain.contr.Health_BldoxyGen;
import com.tjdL4.tjdmain.contr.Health_HeartBldPrs;
import com.tjdL4.tjdmain.contr.Health_Sleep;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

import static com.tjdL4.tjdmain.Dev.L4UI_DATA_BLOODPRESS;
import static com.tjdL4.tjdmain.Dev.L4UI_DATA_HEARTRATE;
import static com.tjdL4.tjdmain.Dev.L4UI_DATA_PEDO;
import static com.tjdL4.tjdmain.Dev.L4UI_DATA_SLEEP;
import static com.tjdL4.tjdmain.Dev.L4UI_DATA_SyncProgress;

public class HomeActivity extends BaseActivity {
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final String TAG = "HomeActivity";
    private AlphaTabsIndicator alphaTabsIndicator;
    ArrayList<Fragment> arrayList;
    //public BlueToothManager bleManager;
    private boolean bReadUserinfoOnlyOnce = false; // 只读取用户信息一次
    private boolean bReadHeartOnlyOnce = false; // 只读取用户信息一次
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

    protected int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PermissionUtil.checkPermissions(this);
        L4M.registerBTStReceiver(this, DataReceiver);
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
        readDBData();
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
            }
        }, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L4M.unregisterBTStReceiver(this,DataReceiver);
        //销毁
        unconfig();
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
            arrayOfString[0] = "健康";
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
                tvTitle.setText("健康");
            } else if (param1Int == 1) {
                tvTitle.setText("设备");
            } else if (param1Int == 2) {
                tvTitle.setText("我的");
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
        DeviceManager.getInstance().initializeDevices();
        DeviceManager.getInstance().currentModel = bleModel;
        SpUtils.putString(this, "lastDeviceMac", L4M.GetConnectedMAC());
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
                if (bReadHeartOnlyOnce == false) {
                    bReadHeartOnlyOnce = true;

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
            }
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
                        if (fragment != null) {
                            fragment.refreshUIGoal("目标 | " + t + "步");
                        }
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
}
