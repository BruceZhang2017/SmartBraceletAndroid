package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.health.data.fitday.mine.AboutActivity;
import com.health.data.fitday.mine.AccountAndSafeActivity;
import com.health.data.fitday.mine.DataRecordActivity;
import com.health.data.fitday.mine.HelpCenterActivity;
import com.health.data.fitday.mine.UserInfoActivity;
import com.health.data.fitday.utils.SpUtils;

public class MineFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";

    @BindView(2131230844)
    Button btnExit;

    @BindView(2131231032)
    ImageView ivHead;

    private View mContentView;

    private void initView() {
        ButterKnife.bind(this, this.mContentView);
    }

    public static MineFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        MineFragment mineFragment = new MineFragment();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @OnClick({2131230844, 2131230840, 2131230859, 2131230839, 2131230845, 2131230849, 2131230858})
    public void onClick(View paramView) {
        System.out.println(");
        switch (paramView.getId()) {
            default:
                return;
            case 2131230859:
                intent = new Intent((Context)this.mContext, UserInfoActivity.class);
                this.mContext.startActivity(intent);
            case 2131230858:
                Toast.makeText((Context)this.mContext, "已经是最新固件", 0).show();
            case 2131230849:
                intent = new Intent((Context)this.mContext, DataRecordActivity.class);
                this.mContext.startActivity(intent);
            case 2131230845:
                intent = new Intent((Context)this.mContext, HelpCenterActivity.class);
                this.mContext.startActivity(intent);
            case 2131230844:
                (new AlertView.Builder()).setContext((Context)this.mContext).setStyle(AlertView.Style.Alert).setTitle(").setCancelText(").setDestructive(new String[] { "}).setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(Object param1Object, int param1Int) {}
            }).build().show();
            case 2131230840:
                intent = new Intent((Context)this.mContext, AccountAndSafeActivity.class);
                this.mContext.startActivity(intent);
            case 2131230839:
                break;
        }
        Intent intent = new Intent((Context)this.mContext, AboutActivity.class);
        this.mContext.startActivity(intent);
    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mContext = (Activity)getActivity();
        this.mContentView = paramLayoutInflater.inflate(2131427405, paramViewGroup, false);
        initView();
        return this.mContentView;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onResume() {
        super.onResume();
        System.out.println("我的页面");
                String str = SpUtils.getString((Context)this.mContext, "UserHead");
        if (str != null && str.length() > 0)
            this.ivHead.setImageURI(Uri.parse(str));
    }
}
