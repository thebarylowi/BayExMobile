package com.bayex.bayex.Utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.bayex.bayex.MainActivity;

public class Permissions {

    //TODO add request before app start
    public Permissions(){
        addPermission(String.valueOf(Manifest.permission.WRITE_EXTERNAL_STORAGE));
        addPermission(String.valueOf(Manifest.permission.SYSTEM_ALERT_WINDOW));


    }

    public void addPermission(String permission){
        if (ContextCompat.checkSelfPermission(MainActivity.mainActivity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            Log.d("Permissions", "permission not granted yet");
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.mainActivity,
                    permission)) {

                Log.d("Permissions", "show permission");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                Log.d("Permissions", "automatic permission");
                ActivityCompat.requestPermissions(MainActivity.mainActivity,
                        new String[]{permission},1);

            }
        }
    }
}
