package com.okaylens.fqc.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.deviceInfo.DevDB;
import com.okaylens.fqc.dao.deviceInfo.DevDao;
import com.okaylens.fqc.dao.deviceInfo.DevTab;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class DevicesInfoAct extends BaseAty implements View.OnClickListener {
    private static final String TAG = "DevicesInfoAct";

    private TextView textView, textView1, textView2, textView3, textView4, textView5,
            textView6, textView7, textView8, textView9;
    private Button button1, button2;
    private String imestring;
    String phoneName;
    String phoneVersion;
    int phoneSDK;
    int sign;
    public final static int RESULT_DEV0 = 1;
    public final static int RESULT_DEV1 = 2;
    private DevDB mDb = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.devicesinfo_act);
        init();
        getDeviceInfo();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);

        checkInfo();
        operateDB();
    }


    private void init() {
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView2);
        textView2 = (TextView) findViewById(R.id.textView3);
        textView3 = (TextView) findViewById(R.id.textView4);
        textView4 = (TextView) findViewById(R.id.textView5);
        textView5 = (TextView) findViewById(R.id.textView6);
        textView6 = (TextView) findViewById(R.id.textView7);
        textView7 = (TextView) findViewById(R.id.textView8);
        textView8 = (TextView) findViewById(R.id.textView14);
        textView8 = (TextView) findViewById(R.id.textView15);
        textView9 = (TextView) findViewById(R.id.textView14);
        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button2 = (Button) findViewById(R.id.right_fub_bar_fail_bt);
        button1.setOnClickListener(DevicesInfoAct.this);
        button2.setOnClickListener(DevicesInfoAct.this);

    }


    private void getDeviceInfo() {
        //获取im号
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imestring = telephonyManager.getDeviceId();
        textView.setText("IMEI:" + imestring);
        textView.setBackgroundColor(Color.GREEN);
        sign = sign + 1;
        //获取设备名称
        phoneName = android.os.Build.MODEL;
        textView1.setText("设备名称：" + phoneName);
        textView1.setBackgroundColor(Color.GREEN);
        sign = sign + 1;
        //获取SDK版本
        phoneSDK = Build.VERSION.SDK_INT;
        textView2.setText("SDK版本：" + Integer.toString(phoneSDK));
        textView2.setBackgroundColor(Color.GREEN);
        sign = sign + 1;
        //获取系统版本号
        phoneVersion = android.os.Build.VERSION.RELEASE;
        textView3.setText("系统版本号：" + phoneVersion);
        textView3.setBackgroundColor(Color.GREEN);
        sign = sign + 1;
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
// TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
// 得到电池状态：
// BatteryManager.BATTERY_STATUS_CHARGING：充电状态。
// BatteryManager.BATTERY_STATUS_DISCHARGING：放电状态。
// BatteryManager.BATTERY_STATUS_NOT_CHARGING：未充满。
// BatteryManager.BATTERY_STATUS_FULL：充满电。
// BatteryManager.BATTERY_STATUS_UNKNOWN：未知状态。
                int status = intent.getIntExtra("status", 0);
// 得到健康状态：
// BatteryManager.BATTERY_HEALTH_GOOD：状态良好。
// BatteryManager.BATTERY_HEALTH_DEAD：电池没有电。
// BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE：电池电压过高。
// BatteryManager.BATTERY_HEALTH_OVERHEAT：电池过热。
// BatteryManager.BATTERY_HEALTH_UNKNOWN：未知状态。
                int health = intent.getIntExtra("health", 0);
                // boolean类型
                //boolean present = intent.getBooleanExtra("present", false);
                // 得到电池剩余容量
                int level = intent.getIntExtra("level", 0);
                // 得到电池最大值。通常为100。
                int scale = intent.getIntExtra("scale", 0);
                // 得到图标ID
                //int icon_small = intent.getIntExtra("icon-small", 0);
                // 充电方式：　BatteryManager.BATTERY_PLUGGED_AC：AC充电。　BatteryManager.BATTERY_PLUGGED_USB：USB充电。
                int plugged = intent.getIntExtra("plugged", 0);
                // 得到电池的电压(福特)
                int voltage = intent.getIntExtra("voltage", 0);
                // 得到电池的温度,0.1度单位。例如 表示197的时候，意思为19.7度
                int temperature = intent.getIntExtra("temperature", 0);
                // 得到电池的类型
                String technology = intent.getStringExtra("technology");
                // 得到电池状态
                String statusString = "";
                // 根据状态id，得到状态字符串
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        statusString = "unknown";
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        statusString = "charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        statusString = "discharging";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        statusString = "not charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        statusString = "full";
                        break;
                }
                //得到电池的寿命状态
                String healthString = "";
                //根据状态id，得到电池寿命
                switch (health) {
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        healthString = "unknown";
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        healthString = "good";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        healthString = "overheat";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        healthString = "dead";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        healthString = "voltage";
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        healthString = "unspecified failure";
                        break;
                }
                //得到充电模式
                String acString = "";
                //根据充电状态id，得到充电模式
                switch (plugged) {
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        acString = "plugged ac";
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB:
                        acString = "plugged usb";
                        break;
                }
                //显示电池信息
                textView4.setText("电池的状态：" + statusString);
                textView4.setBackgroundColor(Color.GREEN);
                sign = sign + 1;
                textView5.setText("健康值: " + healthString);
                textView5.setBackgroundColor(Color.GREEN);
                sign = sign + 1;
                textView6.setText("电池剩余容量： " + level);
                textView6.setBackgroundColor(Color.GREEN);
                sign = sign + 1;
                textView7.setText("充电方式: " + acString);
                textView7.setBackgroundColor(Color.GREEN);
                sign = sign + 1;
                textView8.setText("电池的电压：" + voltage);
                textView8.setBackgroundColor(Color.GREEN);
                sign = sign + 1;
                textView9.setText("电池的温度：" + (float) temperature * 0.1);
                textView9.setBackgroundColor(Color.GREEN);
                sign = sign + 1;
                //textView10.setText( "电池的类型：" + technology);
                //textView10.setBackgroundColor(Color.GREEN);

                /*textView.setText("电池的状态：" + statusString
                        + "\n健康值: " + healthString
                        + "\n电池剩余容量： " + level
                        //+ "\n电池的最大值：" + scale
                        //+ "\n小图标：" + icon_small
                        //+ "\n充电方式：" + plugged
                        + "\n充电方式: " + acString
                        + "\n电池的电压：" + voltage
                        + "\n电池的温度：" + (float) temperature * 0.1
                        + "\n电池的类型：" + technology);
                        */
            }
        }

    };


    protected void onPause() {
        super.onPause();
        // 解除注册监听
        unregisterReceiver(mBroadcastReceiver);
    }

    private void checkInfo() {
        if (sign == 4) {
            button1.setBackgroundColor(Color.GREEN);
            button2.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_DEV0, intent);
                //query DB
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_DEV1, intent1);
                finish();
                break;
        }
    }

    private void operateDB() {
        mDb = Room.databaseBuilder(getApplicationContext(), DevDB.class, "devicesinfo_tab").allowMainThreadQueries().build();
        DevTab devtab = new DevTab();
        //传入数据
        devtab.setUid((int) (System.currentTimeMillis()% 10000));

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
        Date date = new Date(currentTime);
        devtab.setDatetime(formatter.format(date));
        devtab.setDevicename(phoneName);
        devtab.setSdkversion(Integer.toString(phoneSDK));
        devtab.setImei(imestring);
        devtab.setSystemversion(phoneVersion);
        if (!mDb.isOpen()) {
            //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
            Log.d(TAG, " thread  : db not open" + "");
            //return;
        }
        mDb.beginTransaction();
        //mDb.userDao().insertAll(user);
        mDb.devDao().insertA(devtab);
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

    }

    private void queryData() {
        Log.d(TAG, " onQueryData : " + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                DevTab[] devtabs = null;
                devtabs = mDb.devDao().loadAllInfos();
                for (DevTab devtab : devtabs) {
                    Log.e(TAG,  devtab.getUid() + " " +
                            devtab.getDatetime() +" " +
                            devtab.getDevicename() +" " +
                            devtab.getImei() +" " +
                            devtab.getSdkversion() +" " +
                            devtab.getSystemversion()
                    );
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();
    }
}
