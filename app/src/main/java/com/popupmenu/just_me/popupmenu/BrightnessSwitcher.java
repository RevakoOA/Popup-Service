package com.popupmenu.just_me.popupmenu;

import android.content.Context;
import android.content.Intent;

/**
 * Created by just_me on 28.09.15.
 */
public class BrightnessSwitcher implements Changer {

    //for changing brightness
    private final int LOW_Bright  = 1;
    private final int MED_Bright  = 128;
    private final int HIGH_Bright = 255;
    private final int[] brightness = {LOW_Bright, MED_Bright, HIGH_Bright};
    private int point = 0;

    @Override
    public void changeState(Context context, Intent intent) {
        point++;
        if (point>2)
            point = 0;
        android.provider.Settings.System.putInt(context.getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness[point]);
        if (point == 0)
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchBrightness, R.drawable.emptylight_white);
        if (point == 1)
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchBrightness, R.drawable.halflight_white);
        if (point == 2)
            PopupService.remoteViews.setImageViewResource(R.id.nSwitchBrightness, R.drawable.brightness_high);
        PopupService.notificationManager.notify(PopupService.notificationID, PopupService.notificationBuilder.build());
    }

    @Override
    public void updateInfo() {

    }
}
