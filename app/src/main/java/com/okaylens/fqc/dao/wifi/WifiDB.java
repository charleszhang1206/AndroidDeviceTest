package com.okaylens.fqc.dao.wifi;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.okaylens.fqc.dao.micphone.MicDao;
import com.okaylens.fqc.dao.micphone.MicTab;

/**
 * Created by charleszhang on 2017/8/8.
 */



@Database(entities = {WifiTab.class}, version = 1, exportSchema = false)
public abstract class WifiDB extends RoomDatabase {
    public abstract WifiDao wifiDao();
}