package com.health.data.fitday.device;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;
import com.tjdL4.tjdmain.contr.L4Command;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceFoundActivity extends BaseActivity {
    final static String TAG = "DeviceFoundActivity";
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;
    @BindView(R.id.btn_found)
    Button btnFound;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_found;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_found})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            finish();
        } else {
            find();
        }
    }

    private void find() {
        if (L4M.Get_Connect_flag() != 2) {
            Toast.makeText(this, "设备未连接", Toast.LENGTH_SHORT).show();
            return;
        }
        //查找
        L4Command.FindDev();
//监听
        L4M.SetResultListener(new L4M.BTResultListenr() {
            @Override
            public void On_Result(String TypeInfo, String StrData, Object DataObj) {
                final String tTypeInfo=TypeInfo;
                final String TempStr=StrData;
                final Object TempObj=DataObj;

                Log.e(TAG, "inTempStr:"+TempStr);
                if(TypeInfo.equals(L4M.ERROR) && StrData.equals(L4M.TIMEOUT)) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(tTypeInfo.equals(L4M.FindDev) && TempStr.equals(L4M.OK)) {
                            Toast.makeText(DeviceFoundActivity.this, "设备已经找到", Toast.LENGTH_SHORT).show();
                            DeviceFoundActivity.this.finish();
                        }

                    }
                });
            }

        });
    }
}