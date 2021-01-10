package com.health.data.fitday.mine;

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
import com.health.data.fitday.main.BaseActivity;
import com.sinophy.smartbracelet.R;

import java.util.ArrayList;
import java.util.List;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class HelpCenterActivity extends BaseActivity {
    @BindView(2131231197)
    ActionBarCommon actionBarCommon;

    private HelpCenterAdapter adapter;

    @BindView(2131230955)
    EditText etFeedback;

    @BindView(2131231063)
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

    @OnClick({2131230855, 2131230841})
    public void onClick(View paramView) {
        int i = paramView.getId();
        if (i != 2131230841) {
            if (i == 2131230855)
                Toast.makeText((Context)this, "请输入问题描述", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
