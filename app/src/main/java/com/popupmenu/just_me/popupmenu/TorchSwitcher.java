package com.popupmenu.just_me.popupmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.util.Log;

/**
 * Created by just_me on 28.08.15.
 */
public class TorchSwitcher implements Changer{

    private final String TAG = "TAG TorchList";

    private boolean isFlash = false;
    private Camera camera = null;
    private Camera.Parameters p = null;

    @Override
    public void changeState(Context context, Intent intent) {
        Log.d(TAG, "Received!");

        if (!isFlash){
            holdCamera();
            runFlashLight();
            isFlash = true;
        }else {
            stopFlashLight();
            releaseCamera();
            isFlash = false;
        }
    }

    @Override
    public void updateInfo() {
        //TODO do it normal!!!
        isFlash = false;
        PopupService.remoteViews.setImageViewResource(R.id.nSwitchLight, R.drawable.white_bulb);
    }

    private void runFlashLight(){
        if (camera == null)
            return;
        Log.d(TAG, "Camera is ready");
        PopupService.remoteViews.setImageViewResource(R.id.nSwitchLight, R.drawable.blue_bulb);
        PopupService.notificationManager.notify(PopupService.notificationID, PopupService.notificationBuilder.build());
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
    }
    private void stopFlashLight() {
        if (camera == null){
            return;
        }
        PopupService.remoteViews.setImageViewResource(R.id.nSwitchLight, R.drawable.white_bulb);
        PopupService.notificationManager.notify(PopupService.notificationID, PopupService.notificationBuilder.build());
        Log.d(TAG, "Camera stoped");
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.startPreview();

    }

    private int holdCamera(){
        try {

            Log.d(TAG, "Device has "+Integer.toString(Camera.getNumberOfCameras())+" camera(s)");
            camera = Camera.open(); // attempt to get a Camera instance
            if (camera == null){
                Log.d(TAG, "camera is null");
            }else{
                Log.d(TAG, "we catch it!");
            }

        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.d(TAG, "We have exception on Camera.open()");
            return 1;
        }
        p = camera.getParameters();

        return 0;
    }

    private void releaseCamera(){
        if (camera != null){
            camera.release();
        }
    }
}
