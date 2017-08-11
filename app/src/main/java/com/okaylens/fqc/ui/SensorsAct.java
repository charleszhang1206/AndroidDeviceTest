package com.okaylens.fqc.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.micphone.MicTab;
import com.okaylens.fqc.dao.sensors.SensorDB;
import com.okaylens.fqc.dao.sensors.SensorTab;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class SensorsAct extends BaseAty implements View.OnClickListener{
    private Button button1,button2;
    private Button button3,button4,button5,button6,button7;
    private TextView textView,textView1,textView2,textView3,textView4;
    public final static int RESULT_SEN0 = 5;
    public final static int RESULT_SEN1 = 6;
    private SensorManager sensorManager;
    private static final String TAG = "SensorsAct";
    private SensorDB mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.sensors_act);
        init();
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //vibrator=(Vibrator)getSystemService(this.VIBRATOR_SERVICE);
        mDb = Room.databaseBuilder(getApplicationContext(), SensorDB.class, "sensor_tab").allowMainThreadQueries().build();
    }

    private void init() {
        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button2= (Button) findViewById(R.id.right_fub_bar_fail_bt);
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
        textView = (TextView) findViewById(R.id.textView20);
        textView1 = (TextView) findViewById(R.id.textView19);
        textView2 = (TextView) findViewById(R.id.textView18);
        textView3 = (TextView) findViewById(R.id.textView17);
        textView4 = (TextView) findViewById(R.id.textView16);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_SEN0,intent);
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
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
                //Log.e("Fooo", "gyroscope clicked: ");
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
            Log.e("Fooo", "onSensorChanged: " + sensorEvent.sensor.toString());
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
                    textView.setBackgroundColor(Color.GREEN);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    //单位 弧度/秒
                    float[] v1 = sensorEvent.values;
                    float x1 = v1[0];//x方向的加速度
                    float y1 = v1[1];//y方向
                    float z1 = v1[2];//z方向

                    textView1.setText("x:"+ x1
                            +"\ny:"+y1
                            +"\nz:"+z1);
                    textView1.setBackgroundColor(Color.GREEN);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    //单位是微特斯拉（micro-Tesla），用uT表示。单位也可以是高斯（Gauss），1Tesla=10000Gauss
                    //返回 x、y、z 三轴的环境磁场数据
                    float[] v2 = sensorEvent.values;
                    float x2 = v2[0];//x方向的加速度
                    float y2 = v2[1];//y方向
                    float z2 = v2[2];//z方向

                    textView2.setText("x:"+ x2
                            +"\ny:"+y2
                            +"\nz:"+z2);
                    textView2.setBackgroundColor(Color.GREEN);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    float[] v3 = sensorEvent.values;
                    float distance = v3[0];
                    textView3.setText("distance："+ distance);
                    textView3.setBackgroundColor(Color.GREEN);
                    break;
                case Sensor.TYPE_LIGHT:
                    //光线精度
                    float acc = sensorEvent.accuracy;
                    //光线强度
                    float lux = sensorEvent.values[0];
                    textView4.setText("光线精度："+ acc + "\n光线强度:" + lux);
                    textView4.setBackgroundColor(Color.GREEN);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();
    }

    private void queryData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
                //传入数据
                SensorTab sensorTab = new SensorTab();
                sensorTab.setUid((int) (System.currentTimeMillis()% 10000));
                //解析时间
                long currentTime = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                Date date = new Date(currentTime);
                sensorTab.setDatetime(formatter.format(date));
        //解析加速度传感器XYZ
        if (textView.getText() != null) {
            String[] splitacc = textView.getText().toString().split("\n");
            sensorTab.setAccelerx(splitacc[0]);
            sensorTab.setAccelery(splitacc[1]);
            sensorTab.setAccelerz(splitacc[2]);
        }
                //解析重力传感器XYZ
        if (textView1.getText() == null) {
            sensorTab.setGyrx("-");
            sensorTab.setGyry("-");
            sensorTab.setGyrz("-");
        }else {
            String[] splitgyr = textView1.getText().toString().split("\n");
            if(splitgyr.length >=3) {
                sensorTab.setGyrx(splitgyr[0]);
                sensorTab.setGyry(splitgyr[1]);
                sensorTab.setGyrz(splitgyr[2]);
            } else {
                sensorTab.setGyrx("-");
                sensorTab.setGyry("-");
                sensorTab.setGyrz("-");
            }



        }
                //解析磁力传感器XYZ
        if (textView2.getText() == null) {
            sensorTab.setMagx("-");
            sensorTab.setMagy("0");
            sensorTab.setMagz("0");
        }else {
            String[] splitmag = textView2.getText().toString().split("\n");
            if(splitmag.length >= 3) {
                sensorTab.setMagx(splitmag[0]);
                sensorTab.setMagy(splitmag[1]);
                sensorTab.setMagz(splitmag[2]);
            } else {
                sensorTab.setMagx("-");
                sensorTab.setMagy("-");
                sensorTab.setMagz("-");
            }
        }
                //距离传感器
        if (textView3.getText() == null) {
            sensorTab.setDistance("-");
        }else {
            sensorTab.setDistance(textView3.getText().toString());
        }
        //光照传感器
        if (textView4.getText() == null) {

        }else {
            String[] splitlight = textView4.getText().toString().split("\n");
            if(splitlight.length >= 2) {
                sensorTab.setAcc(splitlight[0]);
                sensorTab.setLux(splitlight[1]);
            } else {
                sensorTab.setAcc("-");
                sensorTab.setLux("0");
            }

        }

                if (!mDb.isOpen()) {
                    //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                mDb.beginTransaction();
                mDb.sensorDao().insertA(sensorTab);
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

                SensorTab[] sensorTabs = null;
                sensorTabs = mDb.sensorDao().loadAllInfos();
                for (SensorTab  sensortab: sensorTabs) {
                    Log.e(TAG,  sensortab.getUid() +" " +
                            sensortab.getDatetime() +" "+
                            sensortab.getAccelerx()+" "+
                            sensortab.getAccelery()+" "+
                            sensortab.getAccelerz());
                }
//            }
//        }).start();
    }
}
