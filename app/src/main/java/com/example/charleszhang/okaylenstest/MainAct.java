package com.example.charleszhang.okaylenstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by charleszhang on 2017/5/2.
 */

public class MainAct extends Activity{
    private Button btn_auto;
    private Button btn_auto1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainact);
        btn_auto = (Button) findViewById(R.id.button10);

        btn_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainAct.this,SensorsAct.class);
                startActivity(intent);
            }
        });
    }
}
