package com.health.data.fitday.device;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.mine.HelpCenterActivity;
import com.lxj.xpopup.XPopup;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.BaseContents;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.ctrls.DialPushManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                if(L4M.Get_Connect_flag()!=2) {
                    Toast.makeText(DialUploadActivity.this, "未连接设备，请先连接设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Dev.IsSupportFunc(BaseContents.DEV_SUPPORT_Dial_Push)) {
                    Toast.makeText(DialUploadActivity.this, "设备不支持表盘升级", Toast.LENGTH_SHORT).show();
                    return;
                }
                DialPushManager manager=new DialPushManager(DialUploadActivity.this);
                manager.startDialPush(outFile.getPath(), new DialPushManager.OnDialPushListener() {
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
                        popupView = new ProgressPopupView(DialUploadActivity.this);
                        popupView.setResource(R.mipmap.conten_icon_refresh);
                        popupView.setPopTitle("正在同步至手环，请稍后...");
                        new XPopup.Builder(DialUploadActivity.this)
                                .asCustom(popupView)
                                .show();
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

                    @Override
                    public void OnSucc(String EventStr) {
                        popupView.setResource(R.mipmap.content_icon_success);
                        popupView.setPopTitle("推送成功！");
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
        });
    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        index = getIntent().getIntExtra("index", 0);
        if (index == 0) {
            ivDial.setImageResource(R.mipmap.preview_watch1);
            fileName = "preview_watch1_ClkResPKG.bin";
        } else if (index == 1) {
            ivDial.setImageResource(R.mipmap.preview_watch2);
            fileName = "preview_watch2_ClkResPKG.bin";
        } else if (index == 2) {
            ivDial.setImageResource(R.mipmap.preview_watch3);
            fileName = "preview_watch3_ClkResPKG.bin";
        } else {
            ivDial.setImageResource(R.mipmap.preview_watch4);
            fileName = "preview_watch4_ClkResPKG.bin";
        }
        tvDialName.setText("ITIME - " + (index + 1));
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
    }
}