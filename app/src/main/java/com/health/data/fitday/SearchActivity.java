package com.health.data.fitday;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;


import java.util.ArrayList;

public class SearchActivity extends Activity implements View.OnClickListener{

    private Button btn_search;
    public Activity mContext;
    private ListView lv_bt_dev;
    public static final String TAG="MainActivity";
    private TextView tv_BtName;
    private TextView tv_BtAddr;
    private TextView tv_BtSt;
    private ImageButton Ibtn_left;
    @Override
    protected void onResume() {
        super.onResume();

        if(L4M.Get_Connect_flag()!=2)
        {
            Dev.BTCheckOn_AskResult(this);
            ScanLeDevice(true);
        }
        UpdateUi_BtConInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mLVdapter.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Dev.StopScan();
        unReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initReceiver();

    }

    private void initView() {
        mContext=this;
        btn_search=findViewById(R.id.btn_search);
        tv_BtName =findViewById(R.id.tv_BtName);
        tv_BtAddr =  findViewById(R.id.tv_BtAddr);
        tv_BtSt = findViewById(R.id.tv_BtSt);
        Ibtn_left=findViewById(R.id.Ibtn_left);
        Ibtn_left.setOnClickListener(this);

        btn_search.setOnClickListener(this);
        lv_bt_dev = findViewById(R.id.lv_bt_dev);
        mLVdapter = new ListViewAdapter();
        lv_bt_dev.setAdapter(mLVdapter);

        lv_bt_dev.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.w(TAG, "onItemClick on:"+position+" "+mLVdapter.getDevice(position).getAddress());
                //Dev.Try_Connect(mLVdapter.getDevice(position),new Dev.ConnectReslutCB(){
                Dev.Try_Connect(mLVdapter.getDevice(position),/*"DC123456789a"*/mLVdapter.getDeviceInfo(position),new Dev.ConnectReslutCB(){
                    @Override
                    public void OnConnectReslut(boolean CntExists, BluetoothDevice inBluetoothDevice) {

                        ScanLeDevice(false);

                        if(CntExists)
                        {
                            ScanLeDevice(false);
                            UpdateUi_BtConInfo();
                            Dev.RemoteDev_CloseManual();
                            //Dev.Cache_Connect(mLVdapter.getDevice(position));
                            Dev.Cache_Connect(mLVdapter.getDevice(position),/*"DC123456789a"*/mLVdapter.getDeviceInfo(position));//lh new InfoCode

                        }
                    }
                    @Override
                    public void OnNewDev(String Event, String ConnectInfo) {
                    }

                });

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search:
                ScanLeDevice(true);
                break;
            case R.id.Ibtn_left:
                finish();
                break;
        }
    }

    private ListViewAdapter mLVdapter = new ListViewAdapter();

    public class ListViewAdapter extends BaseAdapter
    {
        private ArrayList<BluetoothDevice> mLeDevices;
        private ArrayList<String> mLeDevInfo;

        Integer mSelectPosition=0;

        public ListViewAdapter()
        {
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

        public BluetoothDevice getSelectDevice()
        {
            return mSelectPosition == null ? null : mLeDevices.get(mSelectPosition);
        }

        public BluetoothDevice getDevice(int idx)
        {
            try
            {
                BluetoothDevice dev= mLeDevices.get(idx);
                return  dev;
            }
            catch (Exception e)
            {
                return null;
            }
        }
        public String getDeviceInfo(int idx)
        {
            try
            {
                String Info= mLeDevInfo.get(idx);
                return  Info;
            }
            catch (Exception e)
            {
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
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null)
            {
                view = LayoutInflater.from(mContext).inflate(R.layout.vw_search_ble_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_name =  view.findViewById(R.id.tv_name);
                viewHolder.tv_address =  view.findViewById(R.id.tv_address);
                view.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
                viewHolder.tv_name.setText(deviceName);
                viewHolder.tv_address.setText(device.getAddress());

            return view;
        }
    }

    static class ViewHolder
    {
        TextView tv_name, tv_address;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void ScanLeDevice(final boolean enable)
    {
        if (enable)
        {

            String []FilterNamas=new String[]{"Lefun","F1","W3"};
            Dev.SetScanFilterLi(0,FilterNamas);
            Dev.SetScanFilter(1,"TJDR");
            Dev.StartScan(mContext, mLeBTModScanCBs,10000);
            Log.i(TAG,"Bt_scanLeDevice On ");
        }
        else
        {

            Dev.StopScan();
            Log.i(TAG,"Bt_scanLeDevice off ");
        }
        invalidateOptionsMenu();
    }

    int deviceConnect=0;
    public Dev.BTScanCB  mLeBTModScanCBs= new Dev.BTScanCB()
    {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord, final String DevStr)
        {
            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.i(TAG,"mLeBTModScanCB onLeScan: "+device.getName());
                    deviceConnect++;
                    if (deviceConnect>=20)
                    {
                        Dev.StopScan();
                        deviceConnect=0;
                    }
                    else
                    {
                        mLVdapter.addDevice(device);
                    }

                    mLVdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onScanErr(int errorCode)
        {
        }

        @Override
        public void onStartedScan()
        {
            mLVdapter.clear();
            mLVdapter.notifyDataSetChanged();
        }

        @Override
        public void onStoppedScan()
        {
            Log.i(TAG,"ModCB StoppedScan ");
            ScanLeDevice(false);
        }

    };


    private void initReceiver() // 放在 onCreate
    {
        L4M.registerBTStReceiver(this,DataReceiver);
    }

    private void unReceiver() // 放在 onDestroy
    {
        L4M.unregisterBTStReceiver(this,DataReceiver);
    }

    private L4M.BTStReceiver DataReceiver = new L4M.BTStReceiver() {
        @Override
        public void  OnRec(String InfType ,String Info)
        {

            try {
                if(Info==null)
                    return;

                if (Info.contains("Connecting")) {
                    UpdateUi_BtConInfo();
                }
                else if (Info.contains("BT_BLE_Connected"))
                {
                    UpdateUi_BtConInfo();
                }
                else if (Info.contains("close"))
                {
                    UpdateUi_BtConInfo();
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private  int UpdateUi_BtConInfo()
    {
        if(L4M.Get_Connect_flag()==1) //正在连接
        {
            tv_BtName.setText(L4M.GetConnecteddName());
            tv_BtAddr.setText(L4M.GetConnectedMAC());
            tv_BtSt.setText("Connecting");
            Log.i(TAG,"Connecting");
        }
        else if(L4M.Get_Connect_flag()==2)//已连接
        {
            tv_BtName.setText(L4M.GetConnecteddName());
            tv_BtAddr.setText(L4M.GetConnectedMAC());
            tv_BtSt.setText("Connected");
            Log.i(TAG,"Connected");
        }
        else//未连接
        {
            tv_BtName.setText(L4M.GetConnecteddName());
            tv_BtAddr.setText(L4M.GetConnectedMAC());
            tv_BtSt.setText("not connected");
            Log.i(TAG,"not connected");
        }
        return L4M.Get_Connect_flag();
    }
}
