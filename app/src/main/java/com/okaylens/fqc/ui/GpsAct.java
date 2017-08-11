package com.okaylens.fqc.ui;

import android.Manifest;
import android.app.Activity;
import android.app.usage.UsageEvents;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.icu.text.StringPrepParseException;
import android.icu.text.UFormat;
import android.location.GnssStatus;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.gps.GpsDB;
import com.okaylens.fqc.dao.gps.GpsTab;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class GpsAct extends BaseAty implements View.OnClickListener{

    private static final String TAG = "GpsAct";
    private TextView tv_gps, tv_satellites;
    private Button button1, button2;
    private LocationManager locationManager;
    private String provider;
    private StringBuilder sb;
    public final static int RESULT_GPS0 = 15;
    public final static int RESULT_GPS1 = 16;
    private GpsDB mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.gps_act);


        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button2 = (Button) findViewById(R.id.right_fub_bar_fail_bt);
        button1.setOnClickListener(GpsAct.this);
        button2.setOnClickListener(GpsAct.this);
        tv_satellites = (TextView) findViewById(R.id.textView9);
        tv_gps = (TextView) findViewById(R.id.textView11);

        mDb = Room.databaseBuilder(getApplicationContext(), GpsDB.class, "gps_tab")
                .allowMainThreadQueries().build();

        turnGPSon();
        //获取地理位置管理器  
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;

//        List<String> list = locationManager.getProviders(true);
//
//        if (list.contains(LocationManager.GPS_PROVIDER)) {
//            //是否为GPS位置控制器
//            provider = LocationManager.GPS_PROVIDER;
//            Log.e(TAG, "GPS Location");
//
//
//        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
//            //是否为网络位置控制器
//            provider = LocationManager.NETWORK_PROVIDER;
//            Log.e(TAG, "Network Location");
//
//        } else {
//            Toast.makeText(this, "请检查网络或GPS是否打开", Toast.LENGTH_LONG).show();
//            Log.e(TAG, "No Location");
//            return;
//        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        updateMsg(location);
//        if (location != null) {
//            //获取当前位置，这里只用到了经纬度
//            String string = "纬度为：" + location.getLatitude() + "\n经度为："
//                    + location.getLongitude();
//            tv_gps.setText(string);
//        }


        //绑定定位事件，监听位置是否改变
        //第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
        //第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器
        locationManager.requestLocationUpdates(provider, 2000, 2, locationListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationManager.registerGnssStatusCallback(new GnssStatus.Callback() {
                @Override
                public void onFirstFix(int ttffMillis) {
                    super.onFirstFix(ttffMillis);
                }

                @Override
                public void onSatelliteStatusChanged(GnssStatus status) {
                    super.onSatelliteStatusChanged(status);
                }

                @Override
                public void onStarted() {
                    super.onStarted();
                }

                @Override
                public void onStopped() {
                    super.onStopped();
                }
            });
        } else {
            locationManager.addGpsStatusListener(new GpsStatus.Listener() {
                @Override
                public void onGpsStatusChanged(int event) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GpsStatus status = locationManager.getGpsStatus(null);//取当前状态
                    int count = updateGpsStatus(status);
                    tv_satellites.setText("当前星颗数=" + count);
                }
            });
        }

    }

    private int updateGpsStatus(GpsStatus status) {
        //StringBuilder sb2 = new StringBuilder("");
        int maxSatellites = status.getMaxSatellites();
        Iterator<GpsSatellite> it = status.getSatellites().iterator();
        int count = 0;
        while (it.hasNext() && count <= maxSatellites) {
            GpsSatellite s = it.next();
            if (s.getSnr() != 0)//只有信躁比不为0的时候才算搜到了星
            {
                count++;
            }
        }
        return count;
    }


    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onStatusChanged: " + arg0);
        }

        @Override
        public void onProviderEnabled(String arg0) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onProviderEnabled: " + arg0);
        }

        @Override
        public void onProviderDisabled(String arg0) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onProviderDisabled: " + arg0);
        }

        @Override
        public void onLocationChanged(Location arg0) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onLocationChanged: " + arg0);
            // 更新当前经纬度
            String locInfo = updateMsg(arg0);
            tv_gps.setText(null);
            tv_gps.setText(locInfo);
        }
    };

    private String updateMsg(Location location) {
        sb = null;
        sb = new StringBuilder("位置信息：\n");
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            sb.append("纬度：" + lat + "\n经度：" + lng);

            if (location.hasAccuracy()) {
                sb.append("\n精度：" + location.getAccuracy());
            }
            if (location.hasAltitude()) {
                sb.append("\n海拔：" + location.hasAltitude());
            }
            if (location.hasBearing()) {
                sb.append("\n方向：" + location.hasBearing());
            } else {
                sb.append("没有位置信息!");
            }
        }
        return sb.toString();
    }

    //关闭时解除监听器
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


    private void turnGPSon() {
//        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
//        intent.putExtra("enabled", true);
//        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_MODE);
//        if (provider.contains("GPS")) {//gps is disabled
//            final Intent poke = new Intent();
//            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
//            poke.setData(Uri.parse("3"));
//            this.sendBroadcast(poke);
//        }


        LocationManager mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_GPS0, intent);
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_GPS1, intent1);
                finish();
                break;
        }
    }


    private void queryData() {
        //传入数据
        GpsTab gpsTab = new GpsTab();
        gpsTab.setUid((int) (System.currentTimeMillis()% 10000));

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
        Date date = new Date(currentTime);
        gpsTab.setDatetime(formatter.format(date));


        String[] info =  tv_gps.getText().toString().split("\n");
        String[] stars =  tv_satellites.getText().toString().split("\n");
        if(info.length >= 3) {
            gpsTab.setLat(info[1]);
            gpsTab.setLng(info[2]);
        }
        if(stars.length >= 2) {
            gpsTab.setStars(stars[1]);
        }
        if (!mDb.isOpen()) {
            //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
            Log.d(TAG, " thread  : db not open" + "");
            //return;
        }
        mDb.beginTransaction();
        //mDb.userDao().insertAll(user);
        mDb.gpsDao().insertA(gpsTab);
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

        GpsTab[] gpstabs = null;
        gpstabs = mDb.gpsDao().loadAllInfos();
        for (GpsTab gpstab : gpstabs) {
            Log.e(TAG,  gpstab.getUid() +" " +
                    gpstab.getDatetime() +" " +
                    gpstab.getStars()+" " +
                    gpstab.getLat()+" " +
                    gpstab.getLng());
        }
    }
}
