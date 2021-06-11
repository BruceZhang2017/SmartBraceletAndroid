package com.health.data.fitday.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.tjdL4.tjdmain.Dev;
import com.tjdL4.tjdmain.L4M;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.health.data.fitday.mine.AboutActivity;
import com.health.data.fitday.mine.AccountAndSafeActivity;
import com.health.data.fitday.mine.DataRecordActivity;
import com.health.data.fitday.mine.HelpCenterActivity;
import com.health.data.fitday.mine.LoginActivity;
import com.health.data.fitday.mine.UserInfoActivity;
import com.health.data.fitday.utils.SpUtils;
import com.sinophy.smartbracelet.R;

public class MineFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";

    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.tv_watch_battery)
    TextView tvBattery;
    @BindView(R.id.tv_watch_ble)
    TextView tvBLE;
    @BindView(R.id.iv_user_head)
    ImageView ivHead;

    private View mContentView;

    private void initView() {
        ButterKnife.bind(this, this.mContentView);
        if (L4M.Get_Connect_flag() == 2) {
            tvBLE.setText("设备已连接");
            setDrawableLeft(tvBLE, R.mipmap.content_blueteeth_link);
        }
    }

    private void setDrawableLeft(TextView attention, int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        attention.setCompoundDrawables(drawable, null, null, null);
    }

    public static MineFragment newInstance(String paramString) {
        Bundle bundle = new Bundle();
        bundle.putString("title", paramString);
        MineFragment mineFragment = new MineFragment();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @OnClick({R.id.btn_exit, R.id.btn_account_safe, R.id.btn_user_info, R.id.btn_about, R.id.btn_help, R.id.btn_record, R.id.btn_update})
    public void onClick(View paramView) {
        Intent intent;
        switch (paramView.getId()) {
            case R.id.btn_user_info:
                intent = new Intent((Context)this.mContext, UserInfoActivity.class);
                this.mContext.startActivity(intent);
                break;
            case R.id.btn_update:
                Toast.makeText((Context)this.mContext, "已经是最新固件", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_record:
                intent = new Intent((Context)this.mContext, DataRecordActivity.class);
                this.mContext.startActivity(intent);
                break;
            case R.id.btn_help:
                intent = new Intent((Context)this.mContext, HelpCenterActivity.class);
                this.mContext.startActivity(intent);
                break;
            case R.id.btn_exit:
                new AlertView("提示", "您确定退出该账号", "取消", new String[]{"确定"}, null, this.mContext,
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 0) {
                            if (L4M.Get_Connect_flag() == 2) {
                                Dev.RemoteDev_CloseManual(); // 手动断开
                            }
                            Intent intent = new Intent(MineFragment.this.mContext, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }).show();
                break;
            case R.id.btn_account_safe:
                intent = new Intent((Context)this.mContext, AccountAndSafeActivity.class);
                this.mContext.startActivity(intent);
                break;
            case R.id.btn_about:
                intent = new Intent((Context)this.mContext, AboutActivity.class);
                this.mContext.startActivity(intent);
                break;
            default:
                break;
        }

    }

    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.mContext = (Activity)getActivity();
        this.mContentView = paramLayoutInflater.inflate(R.layout.fragment_mine, paramViewGroup, false);
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
