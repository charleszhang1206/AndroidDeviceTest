package com.okaylens.fqc.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.okaylens.fqc.R;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class TouchAct extends Activity implements View.OnClickListener{
    private Button button1,button2;
    public final static int RESULT_TOU0 = 9;
    public final static int RESULT_TOU1 = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_act);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button29:
                Intent intent = new Intent();
                setResult(RESULT_TOU0,intent);
                finish();
                break;
            case R.id.button30:
                Intent intent1 = new Intent();
                setResult(RESULT_TOU1,intent1);
                finish();
                break;

        }
    }
}
