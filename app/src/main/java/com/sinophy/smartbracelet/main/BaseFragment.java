package com.sinophy.smartbracelet.main;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    protected Activity mContext;

    public void doOpenCamera() {}

    public void doWriteSDCard() {}

    public boolean hasPermission(String... paramVarArgs) {
        int i = paramVarArgs.length;
        for (byte b = 0; b < i; b++) {
            String str = paramVarArgs[b];
            if (ContextCompat.checkSelfPermission((Context)getActivity(), str) != 0)
                return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {}

    public void requestPermission(int paramInt, String... paramVarArgs) {
        if (Build.VERSION.SDK_INT >= 23)
            requestPermissions(paramVarArgs, paramInt);
    }
}
