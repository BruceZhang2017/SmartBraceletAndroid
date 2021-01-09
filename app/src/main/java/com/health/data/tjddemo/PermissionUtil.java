package com.health.data.tjddemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;


public class PermissionUtil {
	
	private static final String TAG = "PermissionUtil";
	


    public static final int MY_PERMISSIONS_REQUEST_CODE=10;
    static String[] permissions=new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION};


    static ArrayList<String> pms=new ArrayList<>();


    public static void checkPermissions(Activity cext){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }
        pms.clear();
        for (int i=0;i<permissions.length;i++){
            if(ContextCompat.checkSelfPermission(cext,permissions[i])!= PackageManager.PERMISSION_GRANTED){
                pms.add(permissions[i]);
            }
        }

        if(pms.size()>0){
            String[]  ps=pms.toArray(new String[pms.size()]);
            ActivityCompat.requestPermissions( cext,ps,MY_PERMISSIONS_REQUEST_CODE);
        }


    }


}
