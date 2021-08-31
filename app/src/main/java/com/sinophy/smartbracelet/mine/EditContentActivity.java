package com.sinophy.smartbracelet.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.sinophy.smartbracelet.main.BaseActivity;
import com.sinophy.smartbracelet.utils.EditTextUtils;
import com.sinophy.smartbracelet.utils.Utils;
import com.sinophy.smartbracelet.R;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

public class EditContentActivity extends BaseActivity {
    @BindView(R.id.simple_action_bar)
    ActionBarCommon actionBarCommon;

    @BindView(R.id.et_content)
    EditText contentEditText;

    @BindView(R.id.ib_delete)
    ImageButton deleteButton;

    @BindView(R.id.tv_desc)
    TextView descTextView;

    private void checkName(Editable paramEditable, EditText paramEditText) {
        String str2 = paramEditable.toString().trim();
        String str1 = stringFilter(str2);
        if (!str2.equals(str1)) {
            paramEditText.setText(str1);
            paramEditText.setSelection(paramEditText.getText().toString().length());
            return;
        }
        this.descTextView.setText("还可以输入" + (10 - str2.length()) + "个字");
        if (Utils.getTextLength(str2) > 20) {
            paramEditText.setText(getStringLength(str2, 20));
            paramEditText.setSelection(paramEditText.getText().toString().length());
        }
    }

    private String getStringLength(String paramString, int paramInt) {
        return paramString.substring(0, paramInt);
    }

    public static String stringFilter(String paramString) throws PatternSyntaxException {
        return Pattern.compile("[^a-zA-Z\\u4E00-\\u9FA5]").matcher(paramString).replaceAll("").trim();
    }

    protected int getLayoutId() {
        return R.layout.activity_edit_content;
    }

    protected void initData() {
        EditTextUtils.clearButtonListener(this.contentEditText, (View)this.deleteButton);
        this.contentEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                EditContentActivity editContentActivity = EditContentActivity.this;
                editContentActivity.checkName(param1Editable, editContentActivity.contentEditText);
            }

            public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}

            public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
    }

    protected void initView() {
        ButterKnife.bind((Activity)this);
        this.actionBarCommon.setOnLeftIconClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                System.out.println("点击事件");
                        EditContentActivity.this.finish();
            }
        });
        this.actionBarCommon.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            public void onClick(View param1View) {
                String str = EditContentActivity.this.contentEditText.getText().toString().trim();
                if (str == null || str.length() == 0) {
                    Toast.makeText((Context)EditContentActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("result", str);
                EditContentActivity.this.setResult(2, intent);
                EditContentActivity.this.finish();
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Intent intent = getIntent();
        String str1 = intent.getStringExtra("title");
        String str2 = intent.getStringExtra("value");
        this.actionBarCommon.getTitleTextView().setText(str1);
        if (str2 != null && str2.length() > 0 && !str2.equals("未设置"))
        this.contentEditText.setText(str2);
    }
}
