package com.okaylens.fqc.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.widget.TextView;

import com.okaylens.fqc.R;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class BluetoothAct extends Activity {
    private BluetoothAdapter bluetoothAdapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_act);
        textView = (TextView) findViewById(R.id.textView10);
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
}
