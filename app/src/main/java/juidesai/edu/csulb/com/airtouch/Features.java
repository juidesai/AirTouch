package juidesai.edu.csulb.com.airtouch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;

/**
 * Created by jmd19 on 4/7/2017.
 */

public class Features {

    Context ctx;
    AudioManager myAudioManager;
    SharedPreferences.Editor sfe;

    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean flashflag;
    Boolean isFlashAvailable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public Features(Context ctx){
        this.ctx = ctx;
        flashflag = false;
        sfe = ctx.getSharedPreferences("AirTouch",Context.MODE_PRIVATE).edit();
        myAudioManager = (AudioManager)ctx.getSystemService(Context.AUDIO_SERVICE);

        mCameraManager = (CameraManager) ctx.getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void setfeature(String type, int pos){
        sfe.putInt(type,pos-1);
        sfe.commit();
    }

    public void setflashfeature(String type, boolean pos){
        sfe.putBoolean(type,pos);
        sfe.commit();
    }

    public void usefeature(String type){
        SharedPreferences sf = ctx.getSharedPreferences("AirTouch",Context.MODE_PRIVATE);
        int pos = sf.getInt(type,0);
        if(pos==0)
        {
            wifi();
        }
        else if(pos==1){
            silent();
        }
        else if(pos==2){
            general();
        } else if(pos==3){
            vibrate();
        }
        else if(pos==4){
            flashlight();
        }
        else if(pos==5){
            MobileData();
        }
        else if(pos==6){
            Bluetooth();
        }
    }

    private void wifi(){
        ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            // Do whatever
            wifi.setWifiEnabled(false);
        }
        else{
            wifi.setWifiEnabled(true);
        }
    }

    private void silent(){
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    private void general(){
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    private void vibrate(){
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    private void flashlight() {
        if (flashflag == false) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCameraManager.setTorchMode(mCameraId, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flashflag = true;
        } else {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCameraManager.setTorchMode(mCameraId, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }            flashflag = false;
        }
    }


    private void MobileData()
    {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
        ctx.startActivity(intent);
    }

    private void Bluetooth(){
        Intent intentOpenBluetoothSettings = new Intent();
        intentOpenBluetoothSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentOpenBluetoothSettings.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
        ctx.startActivity(intentOpenBluetoothSettings);
    }


    public void flashon(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flashoff(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }            flashflag = false;

    }

}
