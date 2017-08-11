package com.okaylens.fqc.ui;

import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.bluetooth.BlueDB;
import com.okaylens.fqc.dao.speaker.SpeakerDB;
import com.okaylens.fqc.dao.speaker.SpeakerTab;
import com.okaylens.fqc.utils.PlayerManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.media.AudioManager.MODE_NORMAL;

/**
 * Created by charleszhang on 2017/6/6.
 */

public class SpeakerAct extends BaseAty implements View.OnClickListener,PlayerManager.PlayCallback{
    private Button button1,button2,button3;
    private TextView textView_speaker;
    public final static int RESULT_SPE0 = 17;
    public final static int RESULT_SPE1 = 18;
    public PlayerManager playerManager;
    public static final String TAG = "SpeakerAct";
    private HeadsetReceiver mHeadsetReceiver;
    private SpeakerDB mDb;
    private String startime,inserttime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.speakeract);
        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button1.setOnClickListener(SpeakerAct.this);
        button2 = (Button) findViewById(R.id.right_fub_bar_fail_bt);
        button2.setOnClickListener(SpeakerAct.this);
        button3 = (Button) findViewById(R.id.btnspeaker);
        button3.setOnClickListener(SpeakerAct.this);
        textView_speaker = (TextView) findViewById(R.id.textView22);

        mHeadsetReceiver = new HeadsetReceiver();
        //注册耳机插入广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(mHeadsetReceiver, intentFilter);
        //注册二级拔出广播
        IntentFilter intentFilter1 = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(headsetPlugReceiver,intentFilter1);

        mDb = Room.databaseBuilder(getApplicationContext(), SpeakerDB.class, "speaker_tab")
                .allowMainThreadQueries().build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_SPE0,intent);
                if (playerManager != null) {
                    playerManager.stop();
                    queryData();
                    finish();
                }else {
                    textView_speaker.setText("请先开始播放音乐！");
                }

                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_SPE1,intent1);
                if (playerManager != null) {
                    playerManager.stop();
                    finish();
                }else {
                    textView_speaker.setText("请先开始播放音乐！");
                }
                break;

            case R.id.btnspeaker:
                //开始播放时间
                long startTime = System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                Date date = new Date(startTime);
                startime = format.format(date);

                playerManager = new PlayerManager(this);
                //audioManager.setMode(MODE_NORMAL);
                //audioManager.setSpeakerphoneOn(true);
                //playerManager = PlayerManager.getPlayerManager();
                Uri uri  = Uri.parse("android.resource://" + getPackageName() + "/"+ R.raw.test);
                // playerManager.play("//storage//emulate//0//MIUI//music//mp3//jj.mp3",this);
                playerManager.play(uri.toString(),this);
                textView_speaker.setText("请插入耳机...");
                button3.setEnabled(false);
                //耳机拔出的监听
                //registerReceiver(mHeadsetReceiver,new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
                //registerReceiver(mHeadsetReceiver,new IntentFilter(Intent.ACTION_HEADSET_PLUG));
                break;
        }
    }

    @Override
    public void onPrepared() {
        //开始播放之前
        Toast.makeText(this,"播放开始...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onComplete() {
        //播放完成
    }

    @Override
    public void onstop() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //playerManager.stop();
        unregisterReceiver(mHeadsetReceiver);
        mDb.close();
    }


    public class HeadsetReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state",0);
                if (intent.hasExtra("state")){
                    if (state == 0){
                        //耳机未插入
                        Log.v(TAG,"耳机未插入");
                        //
                        //textView_speaker.setText("耳机未插入");
                    }else if (state == 1){
                        //耳机已经插入
                        long currentTime = System.currentTimeMillis();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                        Date date = new Date(currentTime);
                        inserttime = format.format(date);

                        textView_speaker.setText("检测到耳机插入...");
                        Toast.makeText(context,"耳机已经插入...",Toast.LENGTH_SHORT).show();
                        Log.v(TAG,"耳机已插入");
                    }
                }
            }
        }
    }

    private BroadcastReceiver headsetPlugReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)){
                textView_speaker.setText("耳机未插入");
            }
        }
    };

    private void queryData() {
        //传入数据
        SpeakerTab speakerTab = new SpeakerTab();
        speakerTab.setUid((int) (System.currentTimeMillis()% 10000));

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
        Date date = new Date(currentTime);
        speakerTab.setDatetime(formatter.format(date));

        speakerTab.setMusicname("test.mps");
        speakerTab.setStarttime(startime);
        speakerTab.setInserttime(inserttime);

        if (!mDb.isOpen()) {
            //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
            Log.d(TAG, " thread  : db not open" + "");
            //return;
        }
        mDb.beginTransaction();
        //mDb.userDao().insertAll(user);
        mDb.speakerDao().insertA(speakerTab);
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

        SpeakerTab[] speakertabs = null;
        speakertabs = mDb.speakerDao().loadAllInfos();
        for (SpeakerTab speakertab : speakertabs) {
            Log.e(TAG,  speakertab.getUid() +" " +
                    speakertab.getDatetime() +" " +
                    speakertab.getMusicname()+" " +
                    speakertab.getStarttime()+" " +
                    speakertab.getInserttime());
        }
    }

}
