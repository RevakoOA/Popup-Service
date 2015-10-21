package com.popupmenu.just_me.popupmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by just_me on 06.09.15.
 */
public class NetSwitcher extends BroadcastReceiver implements Changer {
    private final static String TAG = "TAG NetSwitcher";
    private static boolean isActiveNetwork;

    @Override
    public void onReceive(Context context, Intent intent) {
        updateInfo();
    }

    @Override
    public void changeState(Context context, Intent intent) {
        try {
            Log.d(TAG, "changing state starts");
            final ConnectivityManager connectivityManager = PopupService.connectivityManager;
            final Class cmClass = connectivityManager.getClass();
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            isActiveNetwork = (Boolean)method.invoke(connectivityManager);
            final Field conmanField = cmClass.getDeclaredField("mService");
            conmanField.setAccessible(true);
            final Object connectivityManagerObject = conmanField.get(connectivityManager);

            final Class connectivityManagerClass = Class.forName(connectivityManagerObject.getClass().getName());

            final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            //So, ConnectivityManager -> Class -> Field -> Object -> Class(again) -> Method
            setMobileDataEnabledMethod.invoke(connectivityManagerObject, !isActiveNetwork);

            Log.d(TAG, "changing state ends");
        }catch (Exception e){
            //Maybe it may break)
            //But actually it works
            //And there is no another way to do that...
        }
    }

    @Override
    public void updateInfo() {
        try {
            final ConnectivityManager connectivityManager = PopupService.connectivityManager;
            final Class cmClass = connectivityManager.getClass();
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            isActiveNetwork = (Boolean) method.invoke(connectivityManager);
            if (isActiveNetwork) {
                PopupService.remoteViews.setImageViewResource(R.id.nSwitchNetwork, R.drawable.import_export_blue);
                Log.d(TAG, "Blue IE set");
            }
            else {
                PopupService.remoteViews.setImageViewResource(R.id.nSwitchNetwork, R.drawable.import_export_white);
                Log.d(TAG, "White IE set");
            }
            PopupService.notificationManager.notify(PopupService.notificationID, PopupService.notificationBuilder.build());
        }catch (Exception e){
            Log.d(TAG, "Shit happens)");
        }
    }
}
