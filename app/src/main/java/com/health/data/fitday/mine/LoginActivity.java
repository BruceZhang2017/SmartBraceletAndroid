package com.health.data.fitday.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import com.health.data.fitday.device.UserBean;
import com.health.data.fitday.main.BaseActivity;
import com.health.data.fitday.main.HomeActivity;
import com.health.data.fitday.utils.SpUtils;
import com.health.data.fitday.utils.Validator;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sahooz.library.countrypicker.Country;
import com.sahooz.library.countrypicker.PickActivity;
import com.sinophy.smartbracelet.R;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.btn_country)
    Button countryButton;

    @BindView(R.id.ib_show)
    ImageButton eyeImageButton;

    @BindView(R.id.btn_login)
    Button loginButton;

    @BindView(R.id.et_phone)
    EditText phoneEditText;

    @BindView(R.id.et_pwd)
    EditText pwdEditText;

    private void chooseCountry() {
        startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), 111);
    }

    private void login() {
        String str1 = this.phoneEditText.getText().toString();
        if (str1 == null || str1.length() == 0 || !Validator.isMobile(str1) || !Validator.isEmail(str1)) {
            Toast.makeText((Context)this, "请输入有效手机号或邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        String str2 = this.pwdEditText.getText().toString();
        if (str2 == null || str2.length() == 0) {
            Toast.makeText((Context)this, "请输入密码", Toast.LENGTH_SHORT).show();
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
        final KProgressHUD hud = KProgressHUD.create((Context)this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setSize(100, 100).setDimAmount(0.5F).show();
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                hud.dismiss();
                Intent intent = new Intent((Context)LoginActivity.this, HomeActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        }, 1000);
    }

    @OnClick({R.id.btn_country, R.id.btn_login, R.id.ib_show})
    public void OnClick(View paramView) {
        int i = paramView.getId();
        switch (i) {
            case R.id.btn_country:
                chooseCountry();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.ib_show:
                ImageButton imageButton = this.eyeImageButton;
                imageButton.setSelected(imageButton.isSelected() ^ true);
                if (this.eyeImageButton.isSelected()) {
                    this.pwdEditText.setTransformationMethod((TransformationMethod)HideReturnsTransformationMethod.getInstance());
                    this.eyeImageButton.setImageResource(R.mipmap.icon_visible);
                } else {
                    this.pwdEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
                    this.eyeImageButton.setImageResource(R.mipmap.icon_invicible);
                }
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
            Country country = Country.fromJson(paramIntent.getStringExtra("country"));
            this.countryButton.setText(country.name);
        }
    }
}
