package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class SensorsAct extends BaseAty implements View.OnClickListener{
    private Button button1,button2;
    private Button button3,button4,button5,button6,button7;
    private TextView textView;
    public final static int RESULT_SEN0 = 5;
    public final static int RESULT_SEN1 = 6;
    //private MySensorEventListener sensorEventListener;
    private SensorManager sensorManager;
    private Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors_act);
        init();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        vibrator=(Vibrator)getSystemService(this.VIBRATOR_SERVICE);
    }

    private void init() {
        button1 = (Button) findViewById(R.id.button23);
        button2= (Button) findViewById(R.id.button22);
        button3 = (Button) findViewById(R.id.button27);
        button4= (Button) findViewById(R.id.button28);
        button5 = (Button) findViewById(R.id.button26);
        button6= (Button) findViewById(R.id.button25);
        button7 = (Button) findViewById(R.id.button24);
        button1.setOnClickListener(SensorsAct.this);
        button2.setOnClickListener(SensorsAct.this);
        button3.setOnClickListener(SensorsAct.this);
        button4.setOnClickListener(SensorsAct.this);
        button5.setOnClickListener(SensorsAct.this);
        button6.setOnClickListener(SensorsAct.this);
        button7.setOnClickListener(SensorsAct.this);
        textView = (TextView) findViewById(R.id.textView16);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button23:
                Intent intent = new Intent();
                setResult(RESULT_SEN0,intent);
                finish();
                break;
            case R.id.button22:
                Intent intent1 = new Intent();
                setResult(RESULT_SEN1,intent1);
                finish();
                break;
            case R.id.button27:
                accelerometer();
                break;
            case R.id.button28:

                break;
            case R.id.button26:

                break;
            case R.id.button25:

                break;
            case R.id.button24:

                break;
        }


    }

    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //传感器数据变化执行的方法
            float[] v = sensorEvent.values;
            int sValue = 20;
            float x = v[0];//x方向的加速度
            float y = v[1];//y方向
            float z = v[2];//z方向

            if(Math.abs(x)>sValue){
                vibrator.vibrate(300);
            }
            if(Math.abs(y)>sValue){
                vibrator.vibrate(300);
            }
            if(Math.abs(z)>sValue){
                vibrator.vibrate(300);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            textView.setText("x:");
        }

    };

    private void accelerometer() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);

    }
}
