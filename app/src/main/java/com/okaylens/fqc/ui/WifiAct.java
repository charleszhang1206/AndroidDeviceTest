package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.okaylens.fqc.R;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class WifiAct extends Activity {
    private TextView textView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_act);
        textView = (TextView) findViewById(R.id.textView9);
        OpenWifi(this,true);
        textView.setText("wifi已经打开！");
    }

    private void OpenWifi(Context context, boolean enabled) {
        WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }
}
