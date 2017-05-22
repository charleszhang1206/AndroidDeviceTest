package com.okaylens.fqc.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.okaylens.fqc.R;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class BluetoothAct extends Activity implements View.OnClickListener{
    private BluetoothAdapter bluetoothAdapter;
    private TextView textView;
    private Button button1,button2;
    public final static int RESULT_BLUE0 = 13;
    public final static int RESULT_BLUE1 = 14;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        textView = (TextView) findViewById(R.id.textView10);
        button1 = (Button) findViewById(R.id.button20);
        button2= (Button) findViewById(R.id.button21);
        button1.setOnClickListener(BluetoothAct.this);
        button2.setOnClickListener(BluetoothAct.this);
        openBlueTooth();

    }

    private void openBlueTooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            textView.setText("蓝牙不可用");
        }
        String Address = bluetoothAdapter.getAddress();
        String Name    = bluetoothAdapter.getName();
        if(bluetoothAdapter.disable()){
            bluetoothAdapter.enable();
            textView.setText("蓝牙已打开"+
                    "\n"+Address+
                    "\n"+Name);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button20:
                Intent intent = new Intent();
                setResult(RESULT_BLUE0,intent);
                finish();
                break;
            case R.id.button21:
                Intent intent1 = new Intent();
                setResult(RESULT_BLUE1,intent1);
                finish();
                break;
        }
    }
}
