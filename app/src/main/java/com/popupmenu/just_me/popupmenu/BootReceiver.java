package com.popupmenu.just_me.popupmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by just_me on 11.09.15.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //context.startService(new Intent(context, PopupService.class));
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, PopupService.class);
            context.startService(serviceIntent);
        }
    }
}
