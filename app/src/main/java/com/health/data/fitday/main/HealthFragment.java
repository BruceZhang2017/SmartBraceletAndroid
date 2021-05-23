package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.health.data.fitday.device.SearchDeviceActivity;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.sinophy.smartbracelet.R;

import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class HealthFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";

    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    private View mContentView;

    private void initData() {
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                Intent intent = new Intent((Context)HealthFragment.this.mContext, SearchDeviceActivity.class);
                HealthFragment.this.mContext.startActivity(intent);
                ((HomeActivity)HealthFragment.this.mContext).startSearchingDevice();
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this, this.mContentView);
        RefreshLayout refreshLayout = (RefreshLayout)this.mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader((RefreshHeader)new ClassicsHeader(this.mContentView.getContext()));
        refreshLayout.setRefreshFooter((RefreshFooter)new ClassicsFooter(this.mContentView.getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh(RefreshLayout param1RefreshLayout) {
                param1RefreshLayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore(RefreshLayout param1RefreshLayout) {
                param1RefreshLayout.finishLoadMore(2000);
            }
        });
    }

    public static HealthFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        HealthFragment healthFragment = new HealthFragment();
        healthFragment.setArguments(bundle);
        return healthFragment;
    }

    @OnClick({})
    public void onClick(View paramView) {
        paramView.getId();
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mContext = (Activity)getActivity();
        this.mContentView = paramLayoutInflater.inflate(R.layout.fragment_health, paramViewGroup, false);
        initView();
        initData();
        return this.mContentView;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }
}
