package com.health.data.fitday.mine;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.health.data.fitday.main.BaseActivity;
import java.util.ArrayList;
import java.util.List;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class AccountAndSafeActivity extends BaseActivity {
    @BindView(2131231197)
    ActionBarCommon actionBarCommon;

    private AccountSafeAdapter adapter;

    @BindView(2131231061)
    ListView listView;

    private List<AccountSafeBean> mList = new ArrayList<>();

    protected int getLayoutId() {
        return 2131427361;
    }

    protected void initData() {
        AccountSafeBean accountSafeBean = new AccountSafeBean();
        accountSafeBean.setKey("手机号");
                accountSafeBean.setKeyDesc("");
        this.mList.add(accountSafeBean);
        accountSafeBean = new AccountSafeBean();
        accountSafeBean.setKey("邮件地址");
                accountSafeBean.setKeyDesc("");
        this.mList.add(accountSafeBean);
        accountSafeBean = new AccountSafeBean();
        accountSafeBean.setKey("微信绑定");
                accountSafeBean.setValue("未绑定");
        this.mList.add(accountSafeBean);
        accountSafeBean = new AccountSafeBean();
        accountSafeBean.setKey("QQ绑定");
                accountSafeBean.setValue("未绑定");
        this.mList.add(accountSafeBean);
        accountSafeBean = new AccountSafeBean();
        accountSafeBean.setKey("密码修改");
                accountSafeBean.setKeyDesc("");
        this.mList.add(accountSafeBean);
        accountSafeBean = new AccountSafeBean();
        accountSafeBean.setKey("账号保护");
                accountSafeBean.setKeyDesc("防止他人恶意登录，建议开启");
                        accountSafeBean.setSafe(false);
        this.mList.add(accountSafeBean);
        AccountSafeAdapter accountSafeAdapter = new AccountSafeAdapter(this.mList, (Context)this);
        this.adapter = accountSafeAdapter;
        this.listView.setAdapter((ListAdapter)accountSafeAdapter);
    }

    protected void initView() {
        ButterKnife.bind((Activity)this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        AccountAndSafeActivity.this.finish();
            }
        });
    }
}
