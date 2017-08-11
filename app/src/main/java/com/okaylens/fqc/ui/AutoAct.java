package com.okaylens.fqc.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.utils.WifiAdmin;

import java.security.PrivateKey;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class AutoAct extends BaseAty {
    //private Button button1, button2;
    private TextView tv_dev,tv_mic,tv_sensors,tv_cam,tv_touch,tv_wifi,tv_blue,tv_gps,tv_speaker;
    public final static int RESULT_AUTO0 = 19;
    public final static int RESULT_AUTO1 = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.auto_act);
//        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
//        button1.setOnClickListener(AutoAct.this);
//        button2 = (Button) findViewById(R.id.right_fub_bar_fail_bt);
//        button2.setOnClickListener(AutoAct.this);
        init();
        checkDevInfo();
        checkMic();
        checkSensors();
        checkCamera();
        //checkTouch();
        checkWifi();
        checkBlue();
        checkgps();
        //checkSpeaker();
    }

    private void init() {
        tv_dev = (TextView) findViewById(R.id.Auto_devInfo);
        tv_mic = (TextView) findViewById(R.id.Auto_mic);
        tv_sensors = (TextView) findViewById(R.id.Auto_sensors);
        tv_cam = (TextView) findViewById(R.id.Auto_camera);
        //tv_touch = (TextView) findViewById(R.id.Auto_touch);
        tv_wifi = (TextView) findViewById(R.id.Auto_wifi);
        tv_blue = (TextView) findViewById(R.id.Auto_blue);
        tv_gps  = (TextView) findViewById(R.id.Auto_gps);
        //tv_speaker = (TextView) findViewById(R.id.Auto_speaker);
    }

    private void checkDevInfo() {
        //获取im号
        TelephonyManager telephonyManager;
        telephonyManager =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String imestring = telephonyManager.getDeviceId();
        //获取设备名称
        String phoneName = android.os.Build.MODEL ;
        //获取SDK版本
        int phoneSDK = Build.VERSION.SDK_INT ;
        //获取系统版本号
        String phoneVersion = android.os.Build.VERSION.RELEASE ;

        //判断数据


        //textview输出
        tv_dev.append("--ready");
        tv_dev.setBackgroundColor(Color.GREEN);
        //设置deviceinfo反馈
        Intent intent1 = new Intent();
        setResult(RESULT_AUTO0,intent1);
    }

    private void checkMic() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},0);

        } else {

        }
        tv_mic.append("--ready");
        tv_mic.setBackgroundColor(Color.GREEN);
    }

    private void checkSensors() {
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //sensorManager.getDefaultSensor(Sensor.TYPE_ALL);
        if (sensorManager == null){
            tv_sensors.append("--unripe");
        }else {
            tv_sensors.append("--ready");
            tv_sensors.setBackgroundColor(Color.GREEN);
        }
    }

    private void checkCamera() {
        //自动拍照
        tv_cam.append("--prepare");

    }

//    private void checkTouch() {
//        tv_touch.append("--prepare");
//
//    }

    private void checkWifi() {
        //WifiManager mWifiManager = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
        WifiAdmin mWifiAdmin = new WifiAdmin(AutoAct.this);
        mWifiAdmin.closeWifi(AutoAct.this);

        tv_wifi.append("--ready");
        tv_wifi.setBackgroundColor(Color.GREEN);
    }

    private void checkBlue() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            tv_blue.append("--unripe");
        }else{
            tv_blue.append("--ready");
            tv_blue.setBackgroundColor(Color.GREEN);
        }
    }

    private void checkgps() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null){
            tv_gps.append("--unripe");
        }else{
            tv_gps.append("--ready");
            tv_gps.setBackgroundColor(Color.GREEN);
        }
    }

//    private void checkSpeaker() {
//        tv_speaker.append("--prepare");
//    }


}
