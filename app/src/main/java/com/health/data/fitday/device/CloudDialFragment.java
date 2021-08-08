package com.health.data.fitday.device;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.health.data.fitday.device.model.DialBean;
import com.health.data.fitday.global.CacheUtils;
import com.health.data.fitday.main.BaseFragment;
import com.health.data.fitday.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CloudDialFragment extends BaseFragment {
    @BindView(R.id.rv_local)
    RecyclerView rvLocal;

    private View mContentView;
    CloudAdapter mAdapter;
    public static List<DialBean> list = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContext = (Activity)getActivity();
        View view = paramLayoutInflater.inflate(R.layout.fragment_cloud_dial, viewGroup, false);
        this.mContentView = view;
        ButterKnife.bind(this, view);
        initView();
        initData();
        return this.mContentView;
    }

    void initView() {

    }

    void initData() {
//        String mac = SpUtils.getString(mContext, "lastDeviceMac");
//        boolean b = false;
//        if (mac != null && mac.length() > 0) {
//            LinkedHashMap<String,String> mapss = new LinkedHashMap<>();
//            mapss = CacheUtils.getMap(mContext, "MyClock");
//            if (mapss != null && mapss.size() > 0) {
//                String value = mapss.get(mac);
//                if (value != null && value.length() > 0) {
//                    String[] arr = value.split("&&&");
//                    int i = 0;
//                    for (String str: arr) {
//                        String[] array = str.split("&&");
//                        mDataset[i] = Integer.parseInt(array[1]);
//                        b = true;
//                        i++;
//                    }
//                }
//            }
//        }
        int type = 1;
        int w = 240;
        int h = 240;
        DialBean bean1 = new DialBean();
        bean1.setDialName("ITIME" + 1);
        int imagename = R.mipmap.d1_240_240;
        bean1.setImage(imagename);
        bean1.setAsset("d1_240_240.bin");
        DialBean bean2 = new DialBean();
        bean2.setDialName("ITIME" + 2);
        int imagename2 = R.mipmap.d2_240_240;
        bean2.setImage(imagename2);
        bean2.setAsset("d2_240_240.bin");
        list.add(bean1);
        list.add(bean2);
        mAdapter = new CloudAdapter(list);
        rvLocal.setAdapter(mAdapter);
        mAdapter.setmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (L4M.GetConnectedMAC() != null && L4M.GetConnectedMAC().length() > 0) {
                    if(L4M.Get_Connect_flag() ==2) {
                        int tag = (int)v.getTag();
                        Intent intent = new Intent(getActivity(), DialUploadActivity.class);
                        intent.putExtra("index", tag);
                        intent.putExtra("cloud", true);
                        getActivity().startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(mContext, "未连接手环", Toast.LENGTH_SHORT).show();
            }
        });
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvLocal.setLayoutManager(mLayoutManager);
    }

}