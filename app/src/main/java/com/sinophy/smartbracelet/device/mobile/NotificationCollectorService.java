package com.sinophy.smartbracelet.device.mobile;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.sinophy.smartbracelet.utils.SpUtils;
import com.tjdL4.tjdmain.contr.L4Command;

public class NotificationCollectorService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        System.out.println("RUN HERE");
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String notificationTitle = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String notificationText = extras.getString(Notification.EXTRA_TEXT);
        Log.i("xiaolong", "add" + "-----" + notificationTitle + "-----" + notificationText);
        if (notificationTitle == null || notificationText == null) {
            return;
        }
        boolean wx = SpUtils.getBoolean(getApplicationContext(), "wx");
        boolean qq = SpUtils.getBoolean(getApplicationContext(), "qq");
        boolean likedin = SpUtils.getBoolean(getApplicationContext(), "likedin");
        boolean fb = SpUtils.getBoolean(getApplicationContext(), "fb");
        boolean tiwtter = SpUtils.getBoolean(getApplicationContext(), "tiwtter");
        boolean whatsapp = SpUtils.getBoolean(getApplicationContext(), "whatsapp");
        if (sbn.getPackageName().toLowerCase().contains("qq")){
            if (qq) {
                L4Command.SendPushContent(1,notificationTitle,notificationText);
            }
        }
        if (sbn.getPackageName().toLowerCase().contains("mm")){
            if (wx) {
                L4Command.SendPushContent(2,notificationTitle,notificationText);
            }
        }


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("xiaolong", "remove" + "-----" + sbn.getPackageName());

    }
}


