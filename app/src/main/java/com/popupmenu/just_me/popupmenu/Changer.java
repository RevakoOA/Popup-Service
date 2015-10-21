package com.popupmenu.just_me.popupmenu;

import android.content.Context;
import android.content.Intent;

/**
 * Created by just_me on 28.09.15.
 */
public interface Changer {
    public void changeState(Context context, Intent intent);
    public void updateInfo();
}
