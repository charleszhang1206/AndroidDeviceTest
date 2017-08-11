package com.okaylens.fqc.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.dao.camera.CameraDB;
import com.okaylens.fqc.dao.camera.CameraTab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class CameraAct extends Activity implements View.OnClickListener{
    private Button button;
    private Button button1,button2;
    public final static int RESULT_CAM0 = 7;
    public final static int RESULT_CAM1 = 8;
    private CameraDB mDb;
    private static final String TAG = "CameraAct";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button2= (Button) findViewById(R.id.right_fub_bar_fail_bt);
        button1.setOnClickListener(CameraAct.this);
        button2.setOnClickListener(CameraAct.this);

        mDb = Room.databaseBuilder(getApplicationContext(), CameraDB.class, "camera_tab")
                .allowMainThreadQueries().build();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)){// 检测sd是否可用  
                Log.e("TestFile","SD card is not avaiable/writeable right now.");
                return;
            }
            //Calendar calendar = Calendar.getInstance(Locale.CHINA);
            String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis()) + ".jpg";
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  

            FileOutputStream b = null;
            File file = new File("/sdcard/myImage/");
            file.mkdirs();// 创建文件夹  
            String fileName = "/sdcard/myImage/"+name;
            try{
                b = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,b);// 把数据写入文件  
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally{
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);// 将图片显示在ImageView里  
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_CAM0,intent);
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_CAM1,intent1);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                //传入数据
                CameraTab cameraTab = new CameraTab();
                cameraTab.setUid((int) (System.currentTimeMillis()% 10000));

                long currentTime = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                Date date = new Date(currentTime);
                cameraTab.setDatetime(formatter.format(date));

                String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis()) + ".jpg";
                String fileName = "/sdcard/myImage/"+name;
                cameraTab.setPicture(fileName);
                if (!mDb.isOpen()) {
                    //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                mDb.beginTransaction();
                //mDb.userDao().insertAll(user);
                mDb.cameraDao().insertA(cameraTab);
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

                CameraTab[] camtabs = null;
                camtabs = mDb.cameraDao().loadAllInfos();
                for (CameraTab camtab : camtabs) {
                    Log.e(TAG,  camtab.getUid() +" " +
                            camtab.getDatetime() +" " +
                            camtab.getPicture()
                    );
                }
            }
        }).start();
    }
}
