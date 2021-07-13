package com.health.data.fitday.main;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
import com.health.data.fitday.device.model.DeviceManager;
import com.health.data.fitday.device.model.UserBean;
import com.health.data.fitday.global.RealmOperationHelper;
import com.health.data.fitday.main.widget.AlphaTabsIndicator;
import com.health.data.fitday.utils.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.BracltBatLevel;
import com.tjdL4.tjdmain.contr.BrltUserParaSet;
import com.tjdL4.tjdmain.contr.Health_TodayPedo;
import com.tjdL4.tjdmain.contr.L4Command;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

public class HomeActivity extends BaseActivity {
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final String TAG = "HomeActivity";
    private AlphaTabsIndicator alphaTabsIndicator;
    ArrayList<Fragment> arrayList;
    //public BlueToothManager bleManager;
    private boolean bReadUserinfoOnlyOnce = false; // 只读取用户信息一次
    private long exitTime = 0L;
    private int step = 0, getup = 0, deep = 0, shellow = 0;
    private String preDate = "";
    private String preValue = "";
    KProgressHUD hud;
    boolean bShowHUD = false;
    private ViewPager viewPager;
    private TextView tvTitle;

    protected int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PermissionUtil.checkPermissions(this);
        Test_InMainActivity();
        L4M.registerBTStReceiver(this, DataReceiver);
    }

    protected void initData() {
        //bleManager = new BlueToothManager((Application)MyApplication.getInstance(), (Context)this);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        L4M.unregisterBTStReceiver(this,DataReceiver);
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
        L4M.SetResultListener(new L4M.BTResultListenr() {
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {
                Log.e(TAG, "【SetResultListener】 TypeInfo:" + TypeInfo + " StrData: " + StrData);
                if(TypeInfo.equals(L4M.SetLanguage) && StrData.equals(L4M.OK)) {
                    Log.e(TAG, "Language:" + StrData);
                    if (bReadUserinfoOnlyOnce == false) {
                        bReadUserinfoOnlyOnce = true;
                        getUserInfo();
                    }
                }
            }
        });

        L4M.SetResultToDBListener(new L4M.BTResultToDBListenr(){
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {
                Log.e(TAG, "【SetResultToDBListener】TypeInfo:" + TypeInfo + " StrData: " + StrData);
            }

            @Override
            public void On_ProgressResult(String TypeInfo, int datTotal, int datIdx, String StrData, Object DataObj) {
                //返回数据
                Log.w(TAG," TypeInfo:"+TypeInfo+"  Tatal:"+datTotal+"  index:"+datIdx+"  StrData:"+StrData);
                bShowHUD = false;
                final String[] split = StrData.replace("[", "").replace("]", "").split(",");
                if (TypeInfo.equals("PEDO_TIME_HISTORY")) {
                    String[] arr = split;
                    int value = Integer.parseInt(arr[2]);
                    if (datIdx == 1) {
                        step = value;
                    } else {
                        step += value;
                    }
                    if (datTotal == datIdx) {
                        Health_TodayPedo.TodayStepPageData stepData= Health_TodayPedo.GetHealth_Data(L4M.GetConnectedMAC(),getCurrentDate());
                        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                        if (fragment != null) {
                            fragment.refreshUI(stepData);
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() { //要执行的任务
                                L4Command.GetHeart1();
                            }
                        }, 1000);
                    }

                } else if (TypeInfo.equals("HEART_HISTORY")) {
                    if (datTotal == datIdx) {
                        String[] arr = split;
                        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                        if (fragment != null) {
                            fragment.refreshUI(arr[2]);
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() { //要执行的任务
                                L4Command.CommSleepTime(0,3000);
                            }
                        }, 1000);
                    }
                } else if (TypeInfo.equals("SLEEP_TIME_HISTORY")) {
                    if (hud != null && hud.isShowing()) {
                        hud.dismiss();
                        hud = null;
                    }
                    String[] arr = split;
                    if (datTotal == 1) {
                        return;
                    }
                    if (datIdx > 1) {
                        String date = arr[1];
                        if (preValue.equals("0")) {
                            getup += getGapMinutes(preDate, date);
                        } else if (preValue.equals("1")) {
                            shellow += getGapMinutes(preDate, date);
                        } else {
                            deep += getGapMinutes(preDate, date);
                        }
                    }
                    if (datIdx == datTotal) {
                        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                        if (fragment != null) {
                            fragment.refreshUI(getup + shellow + deep);
                        }
                        new Handler().postDelayed(new Runnable() {
                            public void run() { //要执行的任务
                                L4Command.GetBloodPrs();
                            }
                        }, 1000);
                    }
                    preDate = arr[1];
                    preValue = arr[2];
                } else if (TypeInfo.equals("BLDPRESS_HISTORY")) {
                    String[] arr = split;
                    if (datIdx == datTotal) {
                        HealthFragment fragment = (HealthFragment)(arrayList.get(0));
                        if (fragment != null) {
                            fragment.refreshUI(arr[2] + "/" + arr[3]);
                        }
                        boolean isOxy=Dev.IsFunction(1);
                        if (isOxy) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() { //要执行的任务
                                    L4Command.GetBldOxyGen();
                                }
                            }, 1000);
                        }
                        if (hud != null && hud.isShowing()) {
                            hud.dismiss();
                            hud = null;
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
                bShowHUD = true;
            } else if (Info.contains("BT_BLE_Connected")) {
                new Handler().postDelayed(new Runnable() {
                    public void run() { //要执行的任务
                        L4Command.LanguageZH();
                    }
                }, 3000);
                addDeviceToDB(); // 连接成功后，将设备保持至数据库。
            } else if (Info.contains("close")) {

            }
        }
    };

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format( new Date());
        return time;
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
        L4Command.Brlt_UserParaGet(new L4M.BTResultListenr() {
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj)
            {
                final String tTypeInfo=TypeInfo;
                final String TempStr=StrData;
                final Object TempObj=DataObj;

                Log.e(TAG, "inTempStr:"+TempStr);
                if(TypeInfo.equals(L4M.ERROR) && StrData.equals(L4M.TIMEOUT)) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        if (tTypeInfo.equals(L4M.GetUserPara) && TempStr.equals(L4M.Data)) {
                            BrltUserParaSet.UserParaSetData my = (BrltUserParaSet.UserParaSetData) TempObj;
                            Log.e(TAG," 年龄 "+my.mAge
                                    +" 体重 "+my.mWeight
                                    +" 身高 "+my.mHeight
                                    +" 性别 "+my.mGender);
                            readBattery();
                            L4Command.CommPedoTime(0,5000);
                            saveUserInfo(my); // 将用户信息保存到数据中
                        }

                    }
                });
            }

        });
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

    private void readBattery() {
        L4Command.BatLevel(new L4M.BTResultListenr() {
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {
                final String tTypeInfo = TypeInfo;
                final String TempStr = StrData;
                final Object TempObj = DataObj;

                if (TypeInfo.equals(L4M.ERROR) && StrData.equals(L4M.TIMEOUT)) {
                    return;
                }

                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tTypeInfo.equals(L4M.GetBatLevel) && TempStr.equals(L4M.Data)) {
                            BracltBatLevel.BatLevel myBatLevel= (BracltBatLevel.BatLevel)TempObj;
                            int batlevel=myBatLevel.batlevel;
                            System.out.println("当前设备的电量为" + batlevel);
                            MyApplication.getInstance().map.put(L4M.GetConnectedMAC(), batlevel + "");
                        }
                    }
                });
            }
        });
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
    }
}
