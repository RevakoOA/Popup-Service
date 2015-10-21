package com.popupmenu.just_me.popupmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by just_me on 07.09.15.
 */
public class SwitchListener extends BroadcastReceiver {
    public final static String action = "action";
    public final static String[] actions = new String[] {"torch", "wi-fi", "network", "brightness", "battery", "settings"};
    private final static String TAG = "TAG SwitchListener";
    public static Changer[] changers = new Changer[] {new TorchSwitcher(), new WiFiSwitcher(), new NetSwitcher(), new BrightnessSwitcher(), };

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive()");
        switch (intent.getStringExtra(action)){
            case "torch":
                changers[0].changeState(context, intent);
                break;
            case "wi-fi":
                changers[1].changeState(context, intent);
                break;
            case "network":
                changers[2].changeState(context, intent);
                break;
            case "brightness":
                changers[3].changeState(context, intent);
                break;
        }
    }

}

