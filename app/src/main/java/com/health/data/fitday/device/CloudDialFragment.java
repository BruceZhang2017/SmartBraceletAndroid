package com.health.data.fitday.device;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.health.data.fitday.main.BaseFragment;
import com.sinophy.smartbracelet.R;

import butterknife.ButterKnife;

public class CloudDialFragment extends BaseFragment {

    private View mContentView;

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

    }

}