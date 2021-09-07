package com.sinophy.smartbracelet.device;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cktim.camera2library.camera.MessageEvent;
import com.sinophy.smartbracelet.MyApplication;
import com.sinophy.smartbracelet.device.adapter.ComListAdapter;
import com.sinophy.smartbracelet.device.model.BLEModel;
import com.sinophy.smartbracelet.device.model.DeviceManager;
import com.sinophy.smartbracelet.global.RealmOperationHelper;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.BractletFuncSet;
import com.tjdL4.tjdmain.contr.BractletSedentarySet;
import com.tjdL4.tjdmain.contr.L4Command;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceDetailActivity extends BaseActivity {
    private static String TAG = "DeviceDetailActivity";
    @BindView(R.id.lv_detail) ListView listView;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    @BindView(R.id.tv_device_name) TextView tvName;
    @BindView(R.id.tv_bt) TextView tvBT;
    @BindView(R.id.tv_battery) TextView tvBattery;
    ComListAdapter adapter;
    BractletFuncSet.FuncSetData funcSetData;
    MediaPlayer shootMP;
    String mac;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"", "推送设置", "来电提醒","抬手亮屏", "久坐提醒", "久座提醒时间", "天气推送", "", "闹钟设置", "查找设置", "设备信息", "摇一摇拍照", ""};
        adapter = new ComListAdapter(this, titles);
        listView.setAdapter(adapter);

        L4Command.Brlt_FuncGet(listener);
        L4Command.SedentaryGet(listener);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
        BLEModel model = DeviceManager.getInstance().currentModel;
        if (model == null) {
            return;
        }
        tvName.setText(model.getName());
        String mac = model.getMac();
        System.out.println("当前连接成功的mac:" + L4M.GetConnectedMAC() + "当前设备的mac:" + mac);
        if (L4M.GetConnectedMAC().equals(mac)) {
            tvBT.setText("蓝牙已连接");
            String battery = MyApplication.getInstance().map.get(mac);
            if (battery == null || battery.length() == 0) {
                battery = "0";
            }
            tvBattery.setText("剩余电量" + battery + "%");
            tvBT.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.content_blueteeth_link, null), null, null, null);
            int draw = 0;
            int b = Integer.parseInt(battery);
            if (b < 20) {
                draw = R.mipmap.dianliang1;
            } else if (b < 40) {
                draw = R.mipmap.dianliang2;
            } else if (b < 60) {
                draw = R.mipmap.dianliang3;
            } else {
                draw = R.mipmap.dianliang4;
            }
            tvBattery.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(draw, null), null, null, null);
        } else {
            tvBT.setText("蓝牙未连接");
            tvBattery.setText("剩余电量未知");
            tvBT.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.content_blueteeth_unlink, null), null, null, null);
            tvBattery.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.conten_battery_null, null), null, null, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mac = getIntent().getStringExtra("mac");
        if (mac != null) {
            System.out.println("接收下去的mac值: " + mac);
            this.mac = mac;
            adapter.mac = mac;
        }
    }

    public L4M.BTResultListenr listener = new L4M.BTResultListenr() {
        @Override
        public void On_Result(String TypeInfo, String StrData, Object DataObj) {
            final String tTypeInfo=TypeInfo;
            final String TempStr=StrData;
            final Object TempObj=DataObj;
            Log.e(TAG, "tTypeInfo:" + tTypeInfo + " inTempStr:"+TempStr);

            if(TypeInfo.equals(L4M.ERROR) && StrData.equals(L4M.TIMEOUT)) {
                return;
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(tTypeInfo.equals(L4M.SetSedentary) && TempStr.equals(L4M.OK)) {
                        L4Command.SedentaryGet(null);
                    }
                    if(tTypeInfo.equals(L4M.GetSedentary) && TempStr.equals(L4M.Data)) {
                        BractletSedentarySet.SedentarySetData myDrinkSetData=(BractletSedentarySet.SedentarySetData)TempObj;
                        //获取时分
                        System.out.println("久坐的时间：" + myDrinkSetData.hour + " 分钟：" + myDrinkSetData.minute);
                    }
                    if(tTypeInfo.equals(L4M.SetFunc) && TempStr.equals(L4M.OK)) {
                        L4Command.Brlt_FuncGet(null);
                        System.out.println("取消监听");
                    } else if(tTypeInfo.equals(L4M.GetFunc) && TempStr.equals(L4M.Data)) {
                        funcSetData = (BractletFuncSet.FuncSetData)TempObj;
                        adapter.funcSetData = funcSetData;
                        adapter.notifyDataSetChanged();
                        String mac = L4M.GetConnectedMAC();
                        SpUtils.putString(DeviceDetailActivity.this, "sed",mac + "_" + (funcSetData.mSW_sed ? 1 : 0));
                    }

                    if(tTypeInfo.equals(L4M.RemoteCapOn) && TempStr.equals(L4M.OK)) {
                        System.out.println("打开相机");
                    }
                    if(tTypeInfo.equals(L4M.RemoteCapOFF) && TempStr.equals(L4M.OK)) {
                        System.out.println("关闭相机");
                    }
                    if(tTypeInfo.equals(L4M.RemoteCapTakeCap) && TempStr.equals(L4M.OK)) {
                        System.out.println("启动拍照");
                        L4Command.CameraCap_Respone();//响应
                        EventBus.getDefault().post(new MessageEvent("photo"));
                        shootSound();
                    }
                    if(tTypeInfo.equals(L4M.GetSedentary) && TempStr.equals(L4M.Data)) {
                        BractletSedentarySet.SedentarySetData myDrinkSetData=(BractletSedentarySet.SedentarySetData)TempObj;
                        //获取时分
                        SpUtils.putInt(DeviceDetailActivity.this, "longsit", myDrinkSetData.allminutes);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals("close")) {
            L4Command.CameraOff();
            L4M.SetResultListener(null);
        } else if (messageEvent.getMessage().equals("longsit")) {
            adapter.notifyDataSetChanged();
        } else if (messageEvent.getMessage().equals("deleteDevice")) {
            System.out.println("删除设备: " + mac);
            if (mac != null && mac.length() > 0) {
                RealmOperationHelper.getInstance(Realm.getDefaultInstance()).deleteDevice(BLEModel.class, mac);
            }
        }
    }

    /**
     *   播放系统拍照声音
     */
    public void shootSound()
    {
        AudioManager meng = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = meng.getStreamVolume( AudioManager.STREAM_NOTIFICATION);

        if (volume != 0)
        {
            if (shootMP == null)
                shootMP = MediaPlayer.create(this, Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
            if (shootMP != null)
                shootMP.start();
        }
    }


}