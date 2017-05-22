package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;

import java.util.List;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class GpsAct extends BaseAty {
    //private double latitude = 0.0;
    //private double longitude = 0.0;
    /*private TextView textView11;
    private LocationManager locationManager;
    private String locationProvider;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initGPS();
        //获取显示地理位置信息的TextView  
        textView11 = (TextView)findViewById(R.id.textView11);
        //获取地理位置管理器  
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        if(!providers.contains(LocationManager.GPS_PROVIDER)) {
           Toast.makeText(this,"请打开GPS",Toast.LENGTH_LONG).show();
        }else {
            //如果是GPS  
            locationProvider = LocationManager.GPS_PROVIDER;
            Location location = locationManager.getLastKnownLocation(locationProvider);

            if (location != null) {
                //不为空,显示地理位置经纬度  
                showLocation(location);
            } else {
                Toast.makeText(this, "无法获取位置信息", Toast.LENGTH_SHORT).show();
            }

            //Location location = locationManager.getLastKnownLocation(locationProvider);
        }
            //监视地理位置变化  
            //locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
            private void showLocation (final Location location) {
                String locationStr = "维度：" + location.getLatitude() + "\n"
                        + "经度：" + location.getLongitude();
                textView11.setText(locationStr);
            }
    /*private void initGPS(){
        LocationManager locationmanager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS已经打开！", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled",true);
        this.sendBroadcast(intent);
        String provider = Settings.Secure.getString(getContentResolver(),Settings.Secure.LOCATION_MODE);
        if (provider.contains("GPS")) {//gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            this.sendBroadcast(poke);

        }
    }
    */
}
