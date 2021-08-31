package com.sinophy.smartbracelet.device;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sinophy.smartbracelet.global.CacheUtils;
import com.sinophy.smartbracelet.main.BaseFragment;
import com.sinophy.smartbracelet.utils.SpUtils;
import com.sinophy.smartbracelet.R;
import com.tjdL4.tjdmain.L4M;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalDialFragment extends BaseFragment {

    @BindView(R.id.rv_local)
    RecyclerView rvLocal;
    @BindView(R.id.tv_null)
    TextView tvNull;

    private View mContentView;
    CustomAdapter mAdapter;
    int[] mDataset;
    RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mContext = (Activity)getActivity();
        View view = paramLayoutInflater.inflate(R.layout.fragment_local_dial, viewGroup, false);
        this.mContentView = view;
        ButterKnife.bind(this, view);
        initView();
        initData();
        return this.mContentView;
    }

    void initView() {

    }

    void initData() {
        String mac = SpUtils.getString(mContext, "lastDeviceMac");
        boolean b = false;
        if (mac != null && mac.length() > 0) {
            LinkedHashMap<String,String> mapss = new LinkedHashMap<>();
            mapss = CacheUtils.getMap(mContext, "MyClock");
            if (mapss != null && mapss.size() > 0) {
                String value = mapss.get(mac);
                if (value != null && value.length() > 0) {
                    String[] arr = value.split("&&&");
                    mDataset = new int[arr.length];
                    int i = 0;
                    for (String str: arr) {
                        String[] array = str.split("&&");
                        mDataset[i] = Integer.parseInt(array[1]);
                        b = true;
                        i++;
                    }
                }
            }
        }
        if (mDataset != null && mDataset.length > 0) {
            tvNull.setVisibility(View.INVISIBLE);
            mAdapter = new CustomAdapter(mDataset);
            rvLocal.setAdapter(mAdapter);
            mAdapter.setmOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int)v.getTag();
                    Intent intent = new Intent(getActivity(), DialUploadActivity.class);
                    intent.putExtra("index", tag);
                    getActivity().startActivity(intent);
                }
            });
        } else {
            tvNull.setVisibility(View.VISIBLE);
        }
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvLocal.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onResume() {
        super.onResume();
        String mac = SpUtils.getString(mContext, "lastDeviceMac");
        boolean b = false;
        if (mac != null && mac.length() > 0) {
            LinkedHashMap<String,String> mapss = new LinkedHashMap<>();
            mapss = CacheUtils.getMap(mContext, "MyClock");
            if (mapss != null && mapss.size() > 0) {
                String value = mapss.get(mac);
                if (value != null && value.length() > 0) {
                    String[] arr = value.split("&&&");
                    mDataset = new int[arr.length];
                    int i = 0;
                    for (String str: arr) {
                        String[] array = str.split("&&");
                        mDataset[i] = Integer.parseInt(array[1]);
                        b = true;
                        i++;
                    }
                }
            }
        }
        if (mDataset != null && mDataset.length > 0) {
            tvNull.setVisibility(View.INVISIBLE);
            mAdapter = new CustomAdapter(mDataset);
            rvLocal.setAdapter(mAdapter);
            mAdapter.setmOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (L4M.GetConnectedMAC() != null && L4M.GetConnectedMAC().length() > 0) {
                        if(L4M.Get_Connect_flag() ==2) {
                            int tag = (int)v.getTag();
                            Intent intent = new Intent(getActivity(), DialUploadActivity.class);
                            intent.putExtra("index", tag);
                            getActivity().startActivity(intent);
                            return;
                        }
                    }
                    Toast.makeText(mContext, "未连接手环", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}