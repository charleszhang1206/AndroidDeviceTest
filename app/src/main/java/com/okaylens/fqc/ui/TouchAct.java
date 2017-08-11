package com.okaylens.fqc.ui;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.custom_view.TouchTestView;
import com.okaylens.fqc.dao.touch.TouchDB;
import com.okaylens.fqc.dao.touch.TouchTab;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class TouchAct extends BaseAty implements View.OnClickListener {
    private Button button1, button2;
    public final static int RESULT_TOU0 = 9;
    public final static int RESULT_TOU1 = 10;
    private TouchDB mDb;
    private static final String TAG = "TouchAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.touch_act);
        //设置窗体始终点亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        btnInit();
        mDb = Room.databaseBuilder(getApplicationContext(), TouchDB.class, "touch_tab")
                .allowMainThreadQueries().build();
    }

    private void btnInit() {
        button1= (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button1.setOnClickListener(TouchAct.this);
        button2= (Button) findViewById(R.id.right_fub_bar_fail_bt);
        button2.setOnClickListener(TouchAct.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_TOU0,intent);
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_TOU1,intent1);
                finish();
                break;
        }
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
                TouchTab touchTab = new TouchTab();
                touchTab.setUid((int) (System.currentTimeMillis()% 10000));

                long currentTime = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                Date date = new Date(currentTime);
                touchTab.setDatetime(formatter.format(date));
                TouchTestView ttv =  new TouchTestView(TouchAct.this);
                //ttv.onTouchEvent(.DOWN);
                touchTab.setX(ttv.getX());
                touchTab.setY(ttv.getY());

                if (!mDb.isOpen()) {
                    //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                mDb.beginTransaction();
                //mDb.userDao().insertAll(user);
                mDb.touchDao().insertA(touchTab);
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

                TouchTab[] touchtabs = null;
                touchtabs = mDb.touchDao().loadAllInfos();
                for (TouchTab touchtab : touchtabs) {
                    Log.e(TAG,  touchtab.getUid() +" " +
                            touchtab.getDatetime() +" " +
                            touchtab.getX()+" " +
                            touchtab.getY()
                    );
                }
            }
//        }).start();
//    }
}
