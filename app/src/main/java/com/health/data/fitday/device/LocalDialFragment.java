package com.health.data.fitday.device;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.health.data.fitday.main.BaseFragment;
import com.sinophy.smartbracelet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalDialFragment extends BaseFragment {

    @BindView(R.id.rv_local)
    RecyclerView rvLocal;

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
        mDataset = new int[]{R.mipmap.preview_watch1, R.mipmap.preview_watch2, R.mipmap.preview_watch3, R.mipmap.preview_watch4};
        mAdapter = new CustomAdapter(mDataset);
        rvLocal.setAdapter(mAdapter);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvLocal.setLayoutManager(mLayoutManager);
        mAdapter.setmOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int)v.getTag();
                Intent intent = new Intent(getActivity(), DialUploadActivity.class);
                intent.putExtra("index", tag);
                getActivity().startActivity(intent);
            }
        });
    }

}