package com.health.data.fitday.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class EditTextUtils {
    public static void clearButtonListener(final EditText et, final View view) {
        if (TextUtils.isEmpty(et.getText().toString())) {
            view.setVisibility(4);
        } else {
            view.setVisibility(0);
        }
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                et.setText("");
                et.requestFocusFromTouch();
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                if (param1Editable.length() == 0) {
                    view.setVisibility(4);
                } else {
                    view.setVisibility(0);
                }
            }

            public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}

            public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
    }
}
