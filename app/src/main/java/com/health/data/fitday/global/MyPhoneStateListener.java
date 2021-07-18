package com.health.data.fitday.global;

import android.content.Context;

import android.telephony.PhoneStateListener;

import android.util.Log;

import com.tjdL4.tjdmain.contr.L4Command;

public class MyPhoneStateListener extends PhoneStateListener {

    Context context;

    @Override

    public void onCallStateChanged(int state,String incomingNumber){
        System.out.println("incomingNumber:" + incomingNumber);
        L4Command.SendCallInstruction(incomingNumber);
    }

}
