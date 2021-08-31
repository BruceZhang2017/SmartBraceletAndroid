package com.sinophy.smartbracelet.mine;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class HelpCenterActivity extends BaseActivity {
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    private HelpCenterAdapter adapter;

    @BindView(R.id.et_feedback)
    EditText etFeedback;

    @BindView(R.id.lv_common_question)
    ListView listView;

    private List<HelpCenterBean> mList = new ArrayList<>();

    private String numToString(int paramInt) {
        return (paramInt != 0) ? ((paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 3) ? ((paramInt != 4) ? "" : "五") : "四") : "三") : "二") : "一";
    }

    protected int getLayoutId() {
        return R.layout.activity_help_center;
    }

    protected void initData() {
        for (byte b = 0; b < 5; b++) {
            HelpCenterBean helpCenterBean = new HelpCenterBean();
            helpCenterBean.setTitle("常见问题"+ numToString(b));
            this.mList.add(helpCenterBean);
        }
        HelpCenterAdapter helpCenterAdapter = new HelpCenterAdapter(this.mList, (Context)this);
        this.adapter = helpCenterAdapter;
        this.listView.setAdapter((ListAdapter)helpCenterAdapter);
    }

    protected void initView() {
        ButterKnife.bind((Activity)this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        HelpCenterActivity.this.finish();
            }
        });
    }

    @OnClick({R.id.btn_submit, R.id.btn_cancel})
    public void onClick(View paramView) {
        int i = paramView.getId();
        if (i != R.id.btn_cancel) {
            if (i == R.id.btn_submit)
                Toast.makeText((Context)this, "请输入问题描述", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
