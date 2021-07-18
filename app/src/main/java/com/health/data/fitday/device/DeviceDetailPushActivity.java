package com.health.data.fitday.device;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ListView;

import com.health.data.fitday.device.adapter.ComListAdapter;
import com.health.data.fitday.device.adapter.PushListAdapter;
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.contr.L4Command;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class DeviceDetailPushActivity extends BaseActivity {
    @BindView(R.id.lv_detail) ListView listView;
    @BindView(R.id.simple_action_bar) ActionBarCommon actionBarCommon;
    PushListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail_push;
    }

    @Override
    protected void initData() {
        String[] titles = new String[]{"", "微信", "QQ", "LIKEDIN", "FACEBOOK", "TIWTTER", "", "WHATSAPP"};
        boolean[] values = new boolean[]{false, false, false, false, false, false, false, false};
        adapter = new PushListAdapter(this, titles, values);
        listView.setAdapter(adapter);

    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationManagerCompat notification = NotificationManagerCompat.from(this);
        boolean isEnabled = notification.areNotificationsEnabled();
        System.out.println("通知是否打开：" + isEnabled);
        if (!isEnabled) {
            //未打开通知
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("请在“通知”中打开通知权限")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("android.provider.extra.APP_PACKAGE", DeviceDetailPushActivity.this.getPackageName());
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //5.0
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", DeviceDetailPushActivity.this.getPackageName());
                                intent.putExtra("app_uid", DeviceDetailPushActivity.this.getApplicationInfo().uid);
                                startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {  //4.4
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + DeviceDetailPushActivity.this.getPackageName()));
                            } else if (Build.VERSION.SDK_INT >= 15) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", DeviceDetailPushActivity.this.getPackageName(), null));
                            }
                            startActivity(intent);

                        }
                    })
                    .create();
            alertDialog.show();
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }



    }
}