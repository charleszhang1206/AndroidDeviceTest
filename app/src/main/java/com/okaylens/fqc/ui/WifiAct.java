package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.okaylens.fqc.R;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class WifiAct extends Activity implements View.OnClickListener{
    private TextView textView;
    private Button button1,button2;
    public final static int RESULT_WIFI0 = 11;
    public final static int RESULT_WIFI1 = 12;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        button1 = (Button) findViewById(R.id.button16);
        button2= (Button) findViewById(R.id.button17);
        button1.setOnClickListener(WifiAct.this);
        button2.setOnClickListener(WifiAct.this);
        textView = (TextView) findViewById(R.id.textView9);
        OpenWifi(this,true);
        textView.setText("wifi已经打开！");
    }

    private void OpenWifi(Context context, boolean enabled) {
        WifiManager wifiManager =(WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button16:
                Intent intent = new Intent();
                setResult(RESULT_WIFI0,intent);
                finish();
                break;
            case R.id.button17:
                Intent intent1 = new Intent();
                setResult(RESULT_WIFI1,intent1);
                finish();
                break;
        }
    }
}
