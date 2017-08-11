package com.okaylens.fqc.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.okaylens.fqc.R;

/**
 * Created by TomLi on 2017/5/5.
 */

public class BaseAty extends AppCompatActivity{

    /*
    * 设置横屏和隐藏标题栏绑定XML文件
    * */
    protected void initTitle(int layoutResID){
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(layoutResID);
        getSupportActionBar().hide();

    }
    /**
     * startActivity
     * @param cls
     * @param bundle
     */
    protected void $startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity
     * @param cls
     */
    protected void $startActivity(Class<?> cls) {
        $startActivity(cls, null);
    }

    /**
     * startActivityForResult
     * @param cls
     * @param bundle
     * @param requestCode
     */
    protected void $startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult
     * @param cls
     * @param requestCode
     */
    protected void $startActivityForResult(Class<?> cls, int requestCode) {
        $startActivityForResult(cls, null, requestCode);
    }

    /**
     * getIntentExtra
     * @return
     */
    protected Bundle $getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = null;
        if (null != intent)
            bundle = intent.getExtras();
        return bundle;
    }

    /**
     * showMessage
     * @param msg
     */
    protected void $showMessage(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * showMessage
     * @param resId
     */
    protected void $showMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
