package com.okaylens.fqc.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class MicphoneAct extends BaseAty implements View.OnClickListener{
    private Button button1,button2,button3;
    private TextView textView_cur,textView_max;
    private MediaRecorder mp;
    private int maxx = 0;
    File soundFile = null;
    private Timer timer = null;
    private TimerTask task =null;
    private  int value = 0;
    public final static int RESULT_MIC0 = 3;
    public final static int RESULT_MIC1 = 4;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.micphone_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        init();
    }

    private void init() {
        button1 = (Button) findViewById(R.id.button11);
        button2 = (Button) findViewById(R.id.button12);
        button3 = (Button) findViewById(R.id.button13);
        textView_cur = (TextView) findViewById(R.id.textView12);
        textView_max = (TextView) findViewById(R.id.textView13);
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
                }
                //Toast.makeText(MicphoneAct.this, "test btn1", Toast.LENGTH_SHORT).show();

                break;
            case R.id.button12:
                endMic();
                finish();
                break;
            case R.id.button13:
                Intent intent = new Intent();
                //intent.putExtra("result1","test");
                setResult(RESULT_MIC1,intent);
                finish();
                break;
        }
    }

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
                  }
                  //重新调用检测进程
                  startToRec();
                }
            };

    private void startToRec() {
        timer =new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //线程抽象类中的Run（）更新value的值
                //value值放到用于线程之间交流数据的Handle的message中
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
}
