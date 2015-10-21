package com.popupmenu.just_me.popupmenu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created by just_me on 27.08.15.
 */
public class PopupService extends Service {

    static NotificationManager notificationManager;
    static WifiManager wifiManager;
    static ConnectivityManager connectivityManager;
    static TelephonyManager telephonyManager = null;

    //for access to notification
    //9 - cause I like this number
    static NotificationCompat.Builder notificationBuilder;
    static RemoteViews remoteViews;
    final static int notificationID = 9;
    private final static String TAG = "TAG PopupService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "start OnStartCommand");

        startNotification();
        return START_STICKY;
    }

    private void getManagers(){
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }
    private void startNotification(){
        getManagers();

        remoteViews = new RemoteViews(getPackageName(), R.layout.notificationlayout);

        startListener();

        notificationBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this);
        Notification notification = notificationBuilder.setSmallIcon(R.drawable.white_bulb)
                .setContentTitle("Popup Notification")
                .setContentText("Created by Just_Me")
                .setContent(remoteViews)
                .setPriority(Notification.PRIORITY_MAX)
                .build();

        int apiVersion = Build.VERSION.SDK_INT;

        if (apiVersion < Build.VERSION_CODES.HONEYCOMB) {
            startForeground(notificationID, notification);
        }else if (apiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            startForeground(notificationID, notificationBuilder.build());
        }
        for (int i = 0; i < SwitchListener.changers.length; i++){
            SwitchListener.changers[i].updateInfo();
        }
    }

    private void startListener(){
        //Torch
        Intent switchIntent = new Intent(this, SwitchListener.class);
        switchIntent.putExtra("action", "torch");
        PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(this, 0, switchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nSwitchLight, pendingSwitchIntent);
        //WiFi
        switchIntent = new Intent(this, SwitchListener.class);
        switchIntent.putExtra("action", "wi-fi");
        pendingSwitchIntent = PendingIntent.getBroadcast(this, 1, switchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nSwitchWifi, pendingSwitchIntent);
        //Network
        switchIntent = new Intent(this, SwitchListener.class);
        switchIntent.putExtra("action", "network");
        pendingSwitchIntent = PendingIntent.getBroadcast(this, 2, switchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nSwitchNetwork, pendingSwitchIntent);
        //Brightness
        switchIntent = new Intent(this, SwitchListener.class);
        switchIntent.putExtra("action", "brightness");
        pendingSwitchIntent = PendingIntent.getBroadcast(this, 3, switchIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.nSwitchBrightness, pendingSwitchIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
