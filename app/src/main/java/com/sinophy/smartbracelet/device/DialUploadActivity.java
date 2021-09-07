package com.sinophy.smartbracelet.device;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sinophy.smartbracelet.device.model.DialBean;
import com.sinophy.smartbracelet.global.CacheUtils;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.mine.MessageEvent;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.lxj.xpopup.XPopup;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.BaseContents;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.ctrls.DialPushManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DialUploadActivity extends BaseActivity {
    @BindView(R.id.iv_dial_big) ImageView ivDial;
    @BindView(R.id.tv_dial_name_big) TextView tvDialName;
    @BindView(R.id.tv_dial_file_size) TextView tvFileSize;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    final static String TAG = "DialUploadActivity";
    ProgressPopupView popupView;
    int index = 0;
    String fileName = "";
    File outFile;
    DialBean currentBean;
    boolean cloud;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dial_upload;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        actionBarCommon.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    private void upload() {
        if(L4M.Get_Connect_flag()!=2) {
            Toast.makeText(DialUploadActivity.this, "未连接设备，请先连接设备", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Dev.IsSupportFunc(BaseContents.DEV_SUPPORT_Dial_Push)) {
            Toast.makeText(DialUploadActivity.this, "设备不支持表盘升级", Toast.LENGTH_SHORT).show();
            return;
        }
        DialPushManager manager=new DialPushManager(DialUploadActivity.this);
        manager.startDialPush(outFile.getAbsolutePath(), new DialPushManager.OnDialPushListener() {
            @Override
            public void OnErr(String EventStr, String ErrInfo)
            {
                if(EventStr.equals(DialPushManager.CONNECT)) {
                    switch (ErrInfo)
                    {
                        case DialPushManager.WRONGCONNECTION:
                            Log.e(TAG,"蓝牙连接不正常");
                            break;
                        case DialPushManager.ARESYNCHRONIZED:
                            Log.e(TAG,"正在同步数据,稍后再试!");
                            break;
                        case DialPushManager.CONNECTLATER:
                            break;
                    }
                }
            }

            @Override
            public void OnStart(String EventStr) {
                if (EventStr.equals(DialPushManager.STARTCAN))
                {
                    Log.w(TAG,"DialPush Can--->");
                }
                else if (EventStr.equals(DialPushManager.STARTNO))
                {
                    Log.w(TAG,"Can't push dial--->");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupView = new ProgressPopupView(DialUploadActivity.this);
                        new XPopup.Builder(DialUploadActivity.this)
                                .asCustom(popupView)
                                .show();
                        new Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        //要执行的任务
                                        popupView.setResource(R.mipmap.conten_icon_refresh);
                                        popupView.setPopTitle("正在同步至手环，请稍后...");
                                    }
                                }, 500);
                    }
                });
            }

            @Override
            public void OnProgress(int total,int index)
            {
                float progress = (index*1.0f / total * 100.0F);
                String mPro= String.format(Locale.getDefault(), "%s",progress).replace(",",".");
                float mProgress=Float.parseFloat(mPro);
                Log.w(TAG,"mProgress---> "+mProgress);
            }

            @Override
            public void OnFail(String EventStr) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupView.setResource(R.mipmap.content_icon_fail);
                        popupView.setPopTitle("推送失败！");
                        new Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        //要执行的任务
                                        popupView.dismiss();
                                    }
                                }, 1000);
                    }
                });

            }

            @Override
            public void OnSucc(String EventStr) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupView.setResource(R.mipmap.content_icon_success);
                        popupView.setPopTitle("推送成功！");
                        uploadsuccess();
                        new Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        //要执行的任务
                                        popupView.dismiss();
                                        finish();
                                    }
                                }, 1000);
                    }
                });
            }

        });
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        index = getIntent().getIntExtra("index", 0);
        cloud = getIntent().getBooleanExtra("cloud", false);
        if (cloud) {
            currentBean = CloudDialFragment.list.get(index);
        } else {
            String mac = SpUtils.getString(this, "lastDeviceMac");
            if (mac != null && mac.length() > 0) {
                LinkedHashMap<String,String> mapss = new LinkedHashMap<>();
                mapss = CacheUtils.getMap(this, "MyClock");
                if (mapss != null && mapss.size() > 0) {
                    String value = mapss.get(mac);
                    if (value != null && value.length() > 0) {
                        String[] arr = value.split("&&&");
                        String str = arr[index];
                        String[] array = str.split("&&");
                        currentBean = new DialBean();
                        currentBean.setDialName(array[0]);
                        currentBean.setImage(Integer.parseInt(array[1]));
                        currentBean.setAsset(array[2]);
                    }
                }
            }
        }
        ivDial.setImageResource(currentBean.getImage());
        tvDialName.setText(currentBean.getDialName());
        fileName = currentBean.getAsset();
        try {
            InputStream in = getAssets().open(fileName);
            outFile = new File(getCacheDir(), fileName);
            OutputStream out = new FileOutputStream(outFile);

            try {
                int len;
                byte[] buff = new byte[1024];
                while ((len = in.read(buff)) > 0) {
                    out.write(buff, 0, len);
                }
            } finally {
                // close in & out
            }
            long theRealFileSizeInBytes = outFile.length();
            tvFileSize.setText("文件大小：" + theRealFileSizeInBytes / 1024 + "K");
        } catch (IOException exc) {

        }

        if (cloud) {
            actionBarCommon.getRightTextView().setText("下载并使用");
        }
    }

    private void uploadsuccess() {
        if (cloud == false) {
            return;
        }
        String mac = SpUtils.getString(this, "lastDeviceMac");
        boolean b = false;
        if (mac != null && mac.length() > 0) {
            LinkedHashMap<String,String> mapss = new LinkedHashMap<>();
            mapss = CacheUtils.getMap(this, "MyClock");
            if (mapss != null && mapss.size() > 0) {
                String value = mapss.get(mac);
                if (value != null && value.length() > 0) {
                    if (value.contains(currentBean.getDialName())) { // 已经加过该设备，则不用重复添加
                        return;
                    }
                    mapss.put(mac,  value + "&&&" + currentBean.getDialName() + "&&" + currentBean.getImage() + "&&" + currentBean.getAsset());
                    CacheUtils.setMap(this, "MyClock", mapss);
                } else {
                    mapss.put(mac,  currentBean.getDialName() + "&&" + currentBean.getImage() + "&&" + currentBean.getAsset());
                    CacheUtils.setMap(this, "MyClock", mapss);
                }
            } else {
                mapss.put(mac,  currentBean.getDialName() + "&&" + currentBean.getImage() + "&&" + currentBean.getAsset());
                CacheUtils.setMap(this, "MyClock", mapss);
            }
            EventBus.getDefault().post(new MessageEvent("clockrefresh"));
        }
    }
}