package com.health.data.fitday.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.health.data.fitday.device.model.UserBean;
import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.main.HomeActivity;
import com.health.data.fitday.utils.SpUtils;
import com.health.data.fitday.utils.Validator;
import com.sinophy.smartbracelet.R;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.btn_country)
    CountryCodePicker picker;

    @BindView(R.id.ib_show)
    Button codeButton;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.et_phone)
    EditText phoneEditText;

    @BindView(R.id.et_pwd)
    EditText pwdEditText;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        picker.changeDefaultLanguage(CountryCodePicker.Language.CHINESE_SIMPLIFIED);
    }

    private void login() {
        String str1 = this.phoneEditText.getText().toString();
        if (str1 == null || str1.length() == 0 || !(Validator.isMobile(str1) || Validator.isEmail(str1))) {
            Toast.makeText((Context)this, "请输入有效手机号或邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        String str2 = this.pwdEditText.getText().toString();
        if (str2 == null || str2.length() == 0) {
            Toast.makeText((Context)this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!str2.equals("1234")) {
            Toast.makeText((Context)this, "请输入验证码'1234'", Toast.LENGTH_SHORT).show();
            return;
        }
        UserBean userBean = new UserBean();
        if (Validator.isMobile(str1)) {
            userBean.setMobile(str1);
        } else if (Validator.isEmail(str1)) {
            userBean.setEmail(str1);
        }
        userBean.setCountry("中国");
        SpUtils.putString((Context)this, "User", (new Gson()).toJson(userBean));
        Intent intent = new Intent((Context)LoginActivity.this, HomeActivity.class);
        LoginActivity.this.startActivity(intent);
        LoginActivity.this.finish();

//        final KProgressHUD hud = KProgressHUD.create((Context)this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setSize(100, 100).setDimAmount(0.5F).show();
//        (new Handler()).postDelayed(new Runnable() {
//            public void run() {
//                hud.dismiss();
//                Intent intent = new Intent((Context)LoginActivity.this, HomeActivity.class);
//                LoginActivity.this.startActivity(intent);
//                LoginActivity.this.finish();
//            }
//        }, 1000);
    }

    @OnClick({R.id.btn_login, R.id.ib_show})
    public void OnClick(View paramView) {
        int i = paramView.getId();
        switch (i) {
            case R.id.btn_login:
                login();
                break;
            case R.id.ib_show:
                System.out.println("获取验证码");
                break;
        }
    }

    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    protected void initData() {}

    protected void initView() {
        ButterKnife.bind((Activity)this);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if (paramInt1 == 111 && paramInt2 == -1) {
            //Country country = Country.fromJson(paramIntent.getStringExtra("country"));
            //this.countryButton.setText(country.name);
        }
    }
}
