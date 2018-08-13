package com.example.aviasa100.myandroidproject;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Aviasa100 on 03/04/2018.
 */

class PermissionManager extends ActivityCompat {

        //A method that can be called from any Activity, to check for specific permission
        public static void check(Activity activity, String permission, int requestCode){
            //If requested permission isn't Granted yet
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                //Request permission from user
                ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
            }
        }
    }


