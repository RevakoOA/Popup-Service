package com.popupmenu.just_me.popupmenu;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by just_me on 02.09.15.
 */
public class WiFiSwitcher extends BroadcastReceiver implements Changer, Runnable{
    private final static String TAG = "TAG WiFi listener";
    private static boolean isWifiEnabled;

    @Override
    public void changeState(Context context, Intent intent) {
        Log.d(TAG, "Received!");
        isWifiEnabled = PopupService.wifiManager.isWifiEnabled();
        new Thread(this).start();
    }

    @Override
    public void updateInfo() {
        isWifiEnabled = PopupService.wifiManager.isWifiEnabled();
        if (isWifiEnabled)
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchWifi, R.drawable.blue_wifi);
        else
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchWifi, R.drawable.white_wifi);
        PopupService.notificationManager.notify(PopupService.notificationID, PopupService.notificationBuilder.build());
    }

    @Override
    public void run() {
        changeWifiState();
    }

    public void changeWifiState(){
        PopupService.wifiManager.setWifiEnabled(isWifiEnabled = !isWifiEnabled);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wm = PopupService.wifiManager;
        if (wm.getWifiState() == WifiManager.WIFI_STATE_DISABLING || wm.getWifiState() == WifiManager.WIFI_STATE_DISABLED)
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchWifi, R.drawable.white_wifi);
        if (wm.getWifiState() == WifiManager.WIFI_STATE_ENABLING || wm.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchWifi, R.drawable.blue_wifi);
        PopupService.notificationManager.notify(PopupService.notificationID, PopupService.notificationBuilder.build());
    }
}
