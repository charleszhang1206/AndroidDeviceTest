package com.okaylens.fqc;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class DevicesInfoAct extends Activity {
    private TextView textView2;
    private String imestring;
    private TextView textView10;
    private TextView textView4;
    private TextView textView9;
    String phoneName;
    String phoneVersion;
    int phoneSDK;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicesinfo_act);
        //获取im号
        TelephonyManager telephonyManager;
        telephonyManager =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        imestring = telephonyManager.getDeviceId();
        textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setText(imestring);
        //获取设备名称
        phoneName = android.os.Build.MODEL ;
        textView10 = (TextView)findViewById(R.id.textView4);
        textView10.setText(phoneName);
        //获取SDK版本
        phoneSDK = Build.VERSION.SDK_INT ;
        textView9 = (TextView)findViewById(R.id.textView6);
        textView9.setText(Integer.toString(phoneSDK));
        //获取系统版本号
        phoneVersion = android.os.Build.VERSION.RELEASE ;
        textView4 = (TextView)findViewById(R.id.textView8);
        textView4.setText(phoneVersion);

        textView = (TextView) findViewById(R.id.textView);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
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
                textView.setText("电池的状态：" + statusString
                        + "\n健康值: " + healthString
                        + "\n电池剩余容量： " + level
                        //+ "\n电池的最大值：" + scale
                        //+ "\n小图标：" + icon_small
                        //+ "\n充电方式：" + plugged
                        + "\n充电方式: " + acString
                        + "\n电池的电压：" + voltage
                        + "\n电池的温度：" + (float) temperature * 0.1
                        + "\n电池的类型：" + technology);
            }
        }

    };
    protected void onPause() {
        super.onPause();
        // 解除注册监听
        unregisterReceiver(mBroadcastReceiver);
    }

}
