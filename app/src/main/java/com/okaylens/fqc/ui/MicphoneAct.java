package com.okaylens.fqc.ui;

import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.micphone.MicDB;
import com.okaylens.fqc.dao.micphone.MicTab;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class MicphoneAct extends BaseAty implements View.OnClickListener{
    private Button button1,button2,button3;
    private TextView textView_cur,textView_max,tv_timer;
    private MediaRecorder mp;
    private int maxx = 0;
    File soundFile = null;
    private Timer timer = null;
    private TimerTask task =null;
    private  int value = 0;
    private  double second = 10;
    public final static int RESULT_MIC0 = 3;
    public final static int RESULT_MIC1 = 4;
    private MicDB mDb;
    private static final String TAG = "MicphoneAct";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.micphone_act);
        init();
        operateDB();
    }



    private void init() {
        button1 = (Button) findViewById(R.id.button11);
        button2 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button3 = (Button) findViewById(R.id.right_fub_bar_fail_bt);
        textView_cur = (TextView) findViewById(R.id.textView12);
        textView_max = (TextView) findViewById(R.id.textView13);
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        button1.setOnClickListener(MicphoneAct.this);
        button2.setOnClickListener(MicphoneAct.this);
        button3.setOnClickListener(MicphoneAct.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button11:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},0);

                } else {
                    startMic();
                    button1.setBackgroundColor(Color.GRAY);
                    button1.setEnabled(false);
                    button1.setText("请将最大声音提高至10000");
                    //handler.postDelayed(runnable,10*1000);
                }
                //Toast.makeText(MicphoneAct.this, "test btn1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_fub_bar_suc_bt:
                endMic();
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent = new Intent();
                //intent.putExtra("result1","test");
                setResult(RESULT_MIC1,intent);
                finish();
                break;
        }
    }



//    //倒计时10S
//    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            handler.postDelayed(this,10*1000);
//        }
//    };

    private void startMic() {
        //开始录音
        if(mp == null){
            //输出目录
            File file = new File(Environment.getExternalStorageDirectory(),"micTest");
            if(!file.exists()){
                file.mkdirs();
            }
            //输出文件
            soundFile = new File(file,System.currentTimeMillis()+".amr");
            if (!soundFile.exists()){
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            mp = new MediaRecorder();
            //初始化MediaRecorder
            mp.setAudioSource(MediaRecorder.AudioSource.MIC);
            //输出格式
            mp.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mp.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //绑定文件。将录制内容自动保存至soundFile中
            mp.setOutputFile(soundFile.getAbsolutePath());
            try {
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (IllegalStateException e){
                e.printStackTrace();
            }
            Toast.makeText(MicphoneAct.this,"开始录音...",Toast.LENGTH_SHORT).show();
            //调用线程来获取当前音频振幅 start to record
            startToRec();
        }
    }

            private Handler mhandler = new Handler(){
              public void  handleMessage(Message msg){
                  //handle获得的信息在主线程使用，更新当前textview显示内容
                    textView_cur.setText(""+msg.arg1);
                  if (maxx < msg.arg1){
                      //显示最大值
                      maxx = msg.arg1;
                      textView_max.setText(""+maxx);
                      if (maxx > 10000){
                          second = -1;
                      }
                  }
                  if(second > 0) {
                      String seconds = String.format("%.1f",second);
                      tv_timer.setText("剩余" + seconds + "秒");
                      //重新调用检测进程
                      startToRec();
                  }
                  else if (second == -1) {
                      button2.setBackgroundColor(Color.GREEN);
                      button3.setEnabled(false);
                  }
                  else if (second < 0) {
                      button3.setBackgroundColor(Color.RED);
                      button2.setEnabled(false);
                      tv_timer.setText("时间结束...");
                  }
                }
            };

    private void startToRec() {
        timer =new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //线程抽象类中的Run（）更新value的值
                //value值放到用于线程之间交流数据的Handle的message中
                second = second - 0.1;
                value = mp.getMaxAmplitude();
                Message message = mhandler.obtainMessage();
                message.arg1 = value;
                mhandler.sendMessage(message);
            }
        };
        //timer设置100毫秒后执行task线程——会自动调用task的start（）函数
        timer.schedule(task,100);
    }

    private void endMic() {
        //结束录音
        if (mp!=null){
            Toast.makeText(MicphoneAct.this,"录音结束...",Toast.LENGTH_SHORT).show();
            timer.cancel();
            //停止录音
            mp.stop();
            //释放资源部在绑定soundFile文件
            mp.release();
            //结束后删除文件
            soundFile.delete();
            mp = null;
            Intent intent = new Intent();
            //intent.putExtra("result1","test");
            setResult(RESULT_MIC0,intent);
        }
    }

    private void operateDB() {
        mDb = Room.databaseBuilder(getApplicationContext(), MicDB.class, "micphone_tab")
                .allowMainThreadQueries().build();
        //MicTab micTab = new MicTab();
    }

    private void queryData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //传入数据
                MicTab micTab = new MicTab();
                micTab.setUid((int) (System.currentTimeMillis()% 10000));

                long currentTime = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                Date date = new Date(currentTime);
                micTab.setDatetime(formatter.format(date));

                micTab.setMax(maxx);
                if (!mDb.isOpen()) {
                    //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                mDb.beginTransaction();
                //mDb.userDao().insertAll(user);
                mDb.micDao().insertA(micTab);
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

                MicTab[] mictabs = null;
                mictabs = mDb.micDao().loadAllInfos();
                for (MicTab mictab : mictabs) {
                    Log.e(TAG,  mictab.getUid() +" " +
                            mictab.getDatetime() +" " +
                            mictab.getMax());
                }
            }
        }).start();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDb.close();
    }
}
