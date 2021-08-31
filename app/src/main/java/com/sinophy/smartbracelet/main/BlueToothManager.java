package com.sinophy.smartbracelet.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.sinophy.smartbracelet.R;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class BlueToothManager {
    private static final int REQUEST_CODE_OPEN_GPS = 1;

    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;

    private Context mContext;

    public BlueToothManager(Application paramApplication, Context paramContext) {
        this.mContext = paramContext;
        BleManager.getInstance().init(paramApplication);
        BleManager.getInstance().enableLog(true).setReConnectCount(1, 5000L).setConnectOverTime(20000L).setOperateTimeout(5000);
        System.out.println("是否支持蓝牙：" + BleManager.getInstance().isSupportBle());
        System.out.println("蓝牙是否开启：" + BleManager.getInstance().isBlueEnable());
    }

    public boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager)this.mContext.getSystemService(Context.LOCATION_SERVICE);
        return (locationManager == null) ? false : locationManager.isProviderEnabled("gps");
    }

    public void checkPermissions() {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            Context context = this.mContext;
            Toast.makeText(context, context.getString(R.string.please_open_blue), Toast.LENGTH_SHORT).show();
            return;
        }
        String[] arrayOfString = new String[1];
        arrayOfString[0] = "android.permission.ACCESS_FINE_LOCATION";
        ArrayList<String> arrayList = new ArrayList();
        int i = arrayOfString.length;
        for (byte b = 0; b < i; b++) {
            String str = arrayOfString[b];
            if (ContextCompat.checkSelfPermission(this.mContext, str) == 0) {
                onPermissionGranted(str);
            } else {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            arrayOfString = arrayList.<String>toArray(new String[arrayList.size()]);
            ActivityCompat.requestPermissions((Activity)this.mContext, arrayOfString, 2);
        }
    }

    public void connect(BleDevice paramBleDevice) {
        BleManager.getInstance().connect(paramBleDevice, new BleGattCallback() {
            public void onConnectFail(BleDevice param1BleDevice, BleException param1BleException) {}

            public void onConnectSuccess(BleDevice param1BleDevice, BluetoothGatt param1BluetoothGatt, int param1Int) {
                System.out.println(""+ param1BleDevice.getName());
            }

            public void onDisConnected(boolean param1Boolean, BleDevice param1BleDevice, BluetoothGatt param1BluetoothGatt, int param1Int) {}

            public void onStartConnect() {}
        });
    }

    public void onPermissionGranted(String paramString) {
        byte b;
        if (paramString.equals("android.permission.ACCESS_FINE_LOCATION")) {
            b = 0;
        } else {
            b = -1;
        }
        if (b == 0)
            if (Build.VERSION.SDK_INT >= 23 && !checkGPSIsOpen()) {
                (new AlertDialog.Builder(this.mContext)).setTitle(R.string.notifyTitle).setMessage(R.string.gpsNotifyMsg).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        ((Activity)BlueToothManager.this.mContext).finish();
                    }
                }).setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                        ((Activity)BlueToothManager.this.mContext).startActivityForResult(intent, 1);
                    }
                }).setCancelable(false).show();
            } else {
                setScanRule();
                startScan();
            }
    }

    public void setScanRule() {
        BleScanRuleConfig bleScanRuleConfig = (new BleScanRuleConfig.Builder()).setScanTimeOut(10000L).build();
        BleManager.getInstance().initScanRule(bleScanRuleConfig);
    }

    public void startScan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            public void onLeScan(BleDevice param1BleDevice) {
                super.onLeScan(param1BleDevice);
            }

            public void onScanFinished(List<BleDevice> param1List) {
                System.out.println(""+ param1List.size());
            }

            public void onScanStarted(boolean param1Boolean) {
                String str;
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = (new StringBuilder()).append("");
                if (param1Boolean) {
                    str = "YES";
                } else {
                    str = "NO";
                }
                printStream.println(stringBuilder.append(str).toString());
            }

            public void onScanning(BleDevice param1BleDevice) {
                if (param1BleDevice.getName() == null || param1BleDevice.getName().length() == 0)
                    return;
                System.out.println("设备名称："+ param1BleDevice.getName() + " MAC" + param1BleDevice.getMac());
                BlueToothManager.this.stopScan();
                BlueToothManager.this.connect(param1BleDevice);
            }
        });
    }

    public void stopScan() {
        BleManager.getInstance().cancelScan();
    }
}
