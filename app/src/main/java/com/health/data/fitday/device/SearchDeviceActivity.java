package com.health.data.fitday.device;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class SearchDeviceActivity extends BaseActivity {
    Context mContext;
    static String TAG = "SearchDeviceActivity";

    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    @BindView(R.id.v_search)
    SearchLoadingView searchLoadingView;

    @BindView(R.id.lv_bt_dev)
    ListView lv_bt_dev;

    protected int getLayoutId() {
        return R.layout.activity_search_device;
    }

    protected void initData() {

    }

    protected void initView() {
        ButterKnife.bind(this);
        mContext = this;
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                finish();
            }
        });
        this.searchLoadingView.startAnimation();

        mLVdapter = new ListViewAdapter();
        lv_bt_dev.setAdapter(mLVdapter);

        lv_bt_dev.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.w(TAG, "onItemClick on:"+position+" "+mLVdapter.getDevice(position).getAddress());
                Dev.Try_Connect(mLVdapter.getDevice(position),mLVdapter.getDeviceInfo(position),new Dev.ConnectReslutCB(){
                    @Override
                    public void OnConnectReslut(boolean CntExists, BluetoothDevice inBluetoothDevice) {

                        ScanLeDevice(false);

                        if(CntExists) {
                            ScanLeDevice(false);
                            Dev.RemoteDev_CloseManual();
                            Dev.Cache_Connect(mLVdapter.getDevice(position),mLVdapter.getDeviceInfo(position));
                        }
                        finish();
                    }
                    @Override
                    public void OnNewDev(String Event, String ConnectInfo) {
                    }

                });

            }
        });

        actionBarCommon.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                ScanLeDevice(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dev.BTCheckOn_AskResult(this);
        ScanLeDevice(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLVdapter.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.searchLoadingView.stopAnimation();
    }

    @SuppressLint("NewApi")
    private void ScanLeDevice(final boolean enable) {
        if (enable) {
            Dev.StartScan(mContext, mLeBTModScanCBs,10000);
            Log.i(TAG,"Bt_scanLeDevice On ");
        } else {
            Dev.StopScan();
            Log.i(TAG,"Bt_scanLeDevice off ");
        }
        invalidateOptionsMenu();
    }

    int deviceConnect=0;
    public Dev.BTScanCB  mLeBTModScanCBs= new Dev.BTScanCB() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord, final String DevStr) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG,"mLeBTModScanCB onLeScan: "+device.getName());
                    deviceConnect++;
                    if (deviceConnect >= 20) {
                        Dev.StopScan();
                        deviceConnect=0;
                    } else {
                        mLVdapter.addDevice(device);
                    }
                    if (deviceConnect > 0) {
                        lv_bt_dev.setVisibility(View.VISIBLE);
                    }
                    mLVdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onScanErr(int errorCode) {
            Log.i(TAG, "扫描错误");
        }

        @Override
        public void onStartedScan() {
            mLVdapter.clear();
            mLVdapter.notifyDataSetChanged();
            actionBarCommon.getRightTextView().setText(" ");
        }

        @Override
        public void onStoppedScan() {
            Log.i(TAG,"ModCB StoppedScan ");
            ScanLeDevice(false);
            actionBarCommon.getRightTextView().setText("重新扫描");
        }

    };

    private ListViewAdapter mLVdapter = new ListViewAdapter();

    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private ArrayList<String> mLeDevInfo;

        Integer mSelectPosition=0;

        public ListViewAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mLeDevInfo = new ArrayList<String>();
        }

        public void addDevice(BluetoothDevice device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }

        public void addDevice(BluetoothDevice device,String inDevInfo) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
                mLeDevInfo.add(inDevInfo);
            }
        }

        public void setSelectPosition(Integer position)
        {
            this.mSelectPosition = position;
        }

        public BluetoothDevice getSelectDevice() {
            return mSelectPosition == null ? null : mLeDevices.get(mSelectPosition);
        }

        public BluetoothDevice getDevice(int idx) {
            try {
                BluetoothDevice dev= mLeDevices.get(idx);
                return  dev;
            } catch (Exception e) {
                return null;
            }
        }
        public String getDeviceInfo(int idx) {
            try {
                String Info= mLeDevInfo.get(idx);
                return  Info;
            } catch (Exception e) {
                return "";
            }
        }
        public void clear() {
            mLeDevices.clear();
            mLeDevInfo.clear();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_search, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_name =  view.findViewById(R.id.tv_name);
                viewHolder.tv_address =  view.findViewById(R.id.tv_mac);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.tv_name.setText(deviceName);
            } else {
                viewHolder.tv_name.setText("No Name");
            }
            viewHolder.tv_address.setText(device.getAddress());

            return view;
        }
    }

    static class ViewHolder {
        TextView tv_name, tv_address;
    }
}
