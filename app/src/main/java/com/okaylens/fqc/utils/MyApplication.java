package com.okaylens.fqc.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by charleszhang on 2017/6/7.
 */

class MyApplication extends Application{
    private static Context context;
    public MyApplication() {
        //super.onCreate();
        //context = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    /*
        *获取APP的Context方便其他地方调用
         */
    public static Context getContext(){
        return context;
    }

}
