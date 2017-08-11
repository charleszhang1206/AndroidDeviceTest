package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;

public class MainActivity extends BaseAty implements View.OnClickListener {
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
    private Button my_button11 = null;
    private Button my_button12 = null;
    //result code
    public final static int RESULT_DEV0 = 1;
    public final static int RESULT_DEV1 = 2;
    public final static int RESULT_MIC0 = 3;
    public final static int RESULT_MIC1 = 4;
    public final static int RESULT_SEN0 = 5;
    public final static int RESULT_SEN1 = 6;
    public final static int RESULT_CAM0 = 7;
    public final static int RESULT_CAM1 = 8;
    public final static int RESULT_TOU0 = 9;
    public final static int RESULT_TOU1 = 10;
    public final static int RESULT_WIFI0 = 11;
    public final static int RESULT_WIFI1 = 12;
    public final static int RESULT_BLUE0 = 13;
    public final static int RESULT_BLUE1 = 14;
    public final static int RESULT_GPS0 = 15;
    public final static int RESULT_GPS1 = 16;
    public final static int RESULT_SPE0 = 17;
    public final static int RESULT_SPE1 = 18;
    public final static int RESULT_AUTO0 = 19;
    public final static int RESULT_AUTO1 = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.activity_main);

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
        my_button11 = (Button) findViewById(R.id.button15);
        my_button11.setOnClickListener(this);
        my_button12 = (Button) findViewById(R.id.button14);
        my_button12.setOnClickListener(this);
    }


    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.button1:
                intent.setClass(MainActivity.this, DevicesInfoAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button2:
                intent.setClass(MainActivity.this, MicphoneAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button3:
                intent.setClass(MainActivity.this, SensorsAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button4:
                intent.setClass(MainActivity.this, CameraAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button5:
                intent.setClass(MainActivity.this, TouchAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button6:
                intent.setClass(MainActivity.this, WifiAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button7:
                intent.setClass(MainActivity.this, BluetoothAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button8:
                intent.setClass(MainActivity.this, GpsAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button9:
                intent.setClass(MainActivity.this, FirmwareAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button10:
                intent.setClass(MainActivity.this, AutoAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button15:
                intent.setClass(MainActivity.this, SpeakerAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
            case R.id.button14:
                intent.setClass(MainActivity.this, HeadSetAct.class);
                MainActivity.this.startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_DEV0:
                my_button1.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_DEV1:
                my_button1.setBackgroundColor(Color.RED);
                break;
            case RESULT_MIC0:
                my_button2.setBackgroundColor(Color.GREEN);
                my_button2.setEnabled(false);
                break;
            case RESULT_MIC1:
                my_button2.setBackgroundColor(Color.RED);
                break;
            case RESULT_SEN0:
                my_button3.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_SEN1:
                my_button3.setBackgroundColor(Color.RED);
                break;
            case RESULT_CAM0:
                my_button4.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_CAM1:
                my_button4.setBackgroundColor(Color.RED);
                break;
            case RESULT_TOU0:
                my_button5.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_TOU1:
                my_button5.setBackgroundColor(Color.RED);
                break;
            case RESULT_WIFI0:
                my_button6.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_WIFI1:
                my_button6.setBackgroundColor(Color.RED);
                break;
            case RESULT_BLUE0:
                my_button7.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_BLUE1:
                my_button7.setBackgroundColor(Color.RED);
                break;
            case RESULT_GPS0:
                my_button8.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_GPS1:
                my_button8.setBackgroundColor(Color.RED);
                break;
            case RESULT_SPE0:
                my_button11.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_SPE1:
                my_button11.setBackgroundColor(Color.RED);
                break;
            case RESULT_AUTO0:
                my_button1.setBackgroundColor(Color.GREEN);
                break;
            case RESULT_AUTO1:
                my_button1.setBackgroundColor(Color.RED);
                break;
            default:
                break;
        }
    }
}
