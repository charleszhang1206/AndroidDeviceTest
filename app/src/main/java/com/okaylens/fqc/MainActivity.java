package com.okaylens.fqc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button my_button1 = null;
    private Button my_button2 = null;
    private Button my_button3 = null;
    private Button my_button4 = null;
    private Button my_button5 = null;
    private Button my_button6 = null;
    private Button my_button7 = null;
    private Button my_button8 = null;
    private Button my_button9 = null;
    private Button my_button10 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_button1 = (Button) findViewById(R.id.button1);
        my_button1.setOnClickListener(this);
        my_button2 = (Button) findViewById(R.id.button2);
        my_button2.setOnClickListener(this);
        my_button3 = (Button) findViewById(R.id.button3);
        my_button3.setOnClickListener(this);
        my_button4 = (Button) findViewById(R.id.button4);
        my_button4.setOnClickListener(this);
        my_button5 = (Button) findViewById(R.id.button5);
        my_button5.setOnClickListener(this);
        my_button6 = (Button) findViewById(R.id.button6);
        my_button6.setOnClickListener(this);
        my_button7 = (Button) findViewById(R.id.button7);
        my_button7.setOnClickListener(this);
        my_button8 = (Button) findViewById(R.id.button8);
        my_button8.setOnClickListener(this);
        my_button9 = (Button) findViewById(R.id.button9);
        my_button9.setOnClickListener(this);
        my_button10 = (Button) findViewById(R.id.button10);
        my_button10.setOnClickListener(this);
    }


    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button1:
                intent.setClass(MainActivity.this, DevicesInfoAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button2:
                intent.setClass(MainActivity.this, MicphoneAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button3:
                intent.setClass(MainActivity.this, SensorsAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button4:
                intent.setClass(MainActivity.this, CameraAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button5:
                intent.setClass(MainActivity.this, TouchAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button6:
                intent.setClass(MainActivity.this, WifiAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button7:
                intent.setClass(MainActivity.this, BluetoothAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button8:
                intent.setClass(MainActivity.this, GpsAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button9:
                intent.setClass(MainActivity.this, FirmwareAct.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.button10:
                intent.setClass(MainActivity.this, AutoAct.class);
                MainActivity.this.startActivity(intent);
                break;
        }
    }
}
