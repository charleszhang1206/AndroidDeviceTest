package com.okaylens.fqc.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;


import com.okaylens.fqc.R;

import java.io.IOException;
import java.security.PrivateKey;

/**
 * Created by charleszhang on 2017/6/7.
 */

public class PlayerManager {
    private static PlayerManager playerManager;
    private MediaPlayer mediaPlayer;
    private PlayCallback callback;
    private String filePath;
    private Context context;


    public static PlayerManager getPlayerManager() {
        if (playerManager == null) {
            synchronized (PlayerManager.class) {
                playerManager = new PlayerManager();
            }
        }
        return playerManager;
    }

    private PlayerManager() {
        this.context = MyApplication.getContext();
        mediaPlayer = new MediaPlayer();
    }

    public PlayerManager(Context context) {
        //this.context = MyApplication.getContext();
        this.context = context;
        mediaPlayer = new MediaPlayer();
    }


    /*
    * 是否正在播放
    * @return 正在播放返回true,否则返回false;
    * */
    public boolean isPlaying() {

        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    /*
    播放回调接口
    * */
    public interface PlayCallback {
        //音乐准备完毕
        void onPrepared();

        //播放完毕
        void onComplete();

        //音乐停止播放
        void onstop();
    }

    /*
    * 播放音乐
    * @param path 音乐文件路径
    * @param callback 播放回调函数
    * */
    public void play(String path, final PlayCallback callback) {
        this.filePath = path;
        this.callback = callback;
        try {
            mediaPlayer.reset();
            //mediaPlayer.(context, R.raw.test);
            mediaPlayer.setDataSource(context, Uri.parse(path));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    callback.onPrepared();
                    mediaPlayer.start();
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 停止播放
    * */
    public void stop() {
        if (isPlaying()) {
            mediaPlayer.stop();
            callback.onstop();
        }
    }



}
