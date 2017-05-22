package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

    private SensorManager sensorManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //vibrator=(Vibrator)getSystemService(this.VIBRATOR_SERVICE);
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
                sensorManager.unregisterListener(listener);
                accelerometer();
                break;
            case R.id.button28:
                sensorManager.unregisterListener(listener);
                gyroscope();
                break;
            case R.id.button26:
                sensorManager.unregisterListener(listener);
                magnetic();
                break;
            case R.id.button25:
                sensorManager.unregisterListener(listener);
                proximity();
                break;
            case R.id.button24:
                sensorManager.unregisterListener(listener);
                ambientlight();
                break;
        }


    }




    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            switch (sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    //传感器数据变化执行的方法
                    float[] v = sensorEvent.values;
                    float x = v[0];//x方向的加速度
                    float y = v[1];//y方向
                    float z = v[2];//z方向

                    textView.setText("x:"+ x
                            +"\ny:"+y
                            +"\nz:"+z);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    //单位 弧度/秒
                    float[] v1 = sensorEvent.values;
                    float x1 = v1[0];//x方向的加速度
                    float y1 = v1[1];//y方向
                    float z1 = v1[2];//z方向

                    textView.setText("x:"+ x1
                            +"\ny:"+y1
                            +"\nz:"+z1);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    //单位是微特斯拉（micro-Tesla），用uT表示。单位也可以是高斯（Gauss），1Tesla=10000Gauss
                    //返回 x、y、z 三轴的环境磁场数据
                    float[] v2 = sensorEvent.values;
                    float x2 = v2[0];//x方向的加速度
                    float y2 = v2[1];//y方向
                    float z2 = v2[2];//z方向

                    textView.setText("x:"+ x2
                            +"\ny:"+y2
                            +"\nz:"+z2);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    float[] v3 = sensorEvent.values;
                    float distance = v3[0];
                    textView.setText("距离："+ distance);
                    break;
                case Sensor.TYPE_LIGHT:
                    //光线精度
                    float acc = sensorEvent.accuracy;
                    //光线强度
                    float lux = sensorEvent.values[0];
                    textView.setText("光线精度："+ acc + "\n光线强度" + lux);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

    };


    private void accelerometer() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void gyroscope() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void magnetic() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void proximity() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void ambientlight() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
    }
}
