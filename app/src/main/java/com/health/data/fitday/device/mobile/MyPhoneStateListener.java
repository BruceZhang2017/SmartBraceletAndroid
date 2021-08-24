package com.health.data.fitday.device.mobile;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.health.data.fitday.utils.SpUtils;
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
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d(TAG ,"电话挂断...");
                if (listener != null) {
                    listener.onCallIdle();
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d(TAG ,"正在通话...");
                if (listener != null) {
                    listener.onCallOffHook();
                }
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG ,"电话响铃...");
                if (listener != null) {
                    listener.onCallRinging();
                }
                if (incomingNumber.length() > 0) {
                    boolean b = SpUtils.getBoolean(context,"call");
                    if (b) {
                        L4Command.SendCallInstruction(incomingNumber);
                    }
                }
                break;
        }
        super.onCallStateChanged(state, incomingNumber);
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


