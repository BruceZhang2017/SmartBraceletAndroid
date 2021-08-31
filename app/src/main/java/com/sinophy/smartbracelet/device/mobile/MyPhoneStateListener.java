package com.sinophy.smartbracelet.device.mobile;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.sinophy.smartbracelet.utils.SpUtils;
import com.tjdL4.tjdmain.contr.L4Command;

public class MyPhoneStateListener extends PhoneStateListener {
    private static final String TAG = "MyPhoneStateListener";
    protected CallListener listener;
    public Context context;
    /**
     * 返回电话状态
     *
     * CALL_STATE_IDLE 无任何状态时
     * CALL_STATE_OFFHOOK 接起电话时
     * CALL_STATE_RINGING 电话响铃时
     */
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG ,"电话挂断..." + incomingNumber);
                if (listener != null) {
                    listener.onCallIdle();
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG ,"正在通话..." + incomingNumber);
                if (listener != null) {
                    listener.onCallOffHook();
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG ,"电话响铃... : " + incomingNumber);
                if (listener != null) {
                    listener.onCallRinging();
                }
                boolean b = SpUtils.getBoolean(context,"call");
                Log.d(TAG ,"电话监听功能开启... : " + b);
                if (b) {
                    String num = (incomingNumber == null || incomingNumber.length() == 0) ? "未知号码" : incomingNumber;
                    L4Command.SendCallInstruction(num);
                }
                break;
        }
    }

    //回调
    public void setCallListener(CallListener callListener) {
        this.listener = callListener;
    }

    //回调接口
    public interface CallListener {
        void onCallIdle();
        void onCallOffHook();
        void onCallRinging();
    }
}


