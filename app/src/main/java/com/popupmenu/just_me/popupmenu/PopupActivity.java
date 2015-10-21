package com.popupmenu.just_me.popupmenu;


import android.content.Context;
import android.content.Intent;

import android.hardware.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PopupActivity extends AppCompatActivity {


    private final static String TAG = "TAG PopupActivity";
    private static Context context = null;
    private Button startServiceButton = null;
    private boolean serviceIsRunning = false;
    private Button switchFlash;
    private boolean isFlash = false;
    private Camera camera = null;
    private Camera.Parameters p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "start OnCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        startServiceButton = (Button) findViewById(R.id.startService);
        switchFlash = (Button) findViewById(R.id.switchflash);

        startServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serviceIsRunning) {
                    startService(new Intent(context, PopupService.class));
                    serviceIsRunning = true;
                } else {
                    stopService(new Intent(context, PopupService.class));
                    serviceIsRunning = false;
                }
            }
        });

        context = this;
        switchFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFlash) {
                    holdCamera();
                    runFlashLight();
                    isFlash = true;
                } else {
                    stopFlashLight();
                    releaseCamera();
                    isFlash = false;
                }
            }
        });



        Log.d(TAG, "end OnCreate()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popupmain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void runFlashLight(){
        if (camera == null)
            return;
        Log.d(TAG, "Camera is ready");

        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
    }
    private void stopFlashLight() {
        if (camera == null){
            return;
        }
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

    public static Context getContext(){
        return context;
    }



}



