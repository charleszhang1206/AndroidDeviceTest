package com.okaylens.fqc.dao.camera;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.okaylens.fqc.dao.wifi.WifiDao;
import com.okaylens.fqc.dao.wifi.WifiTab;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Database(entities = {CameraTab.class}, version = 1, exportSchema = false)
public abstract class CameraDB extends RoomDatabase {
    public abstract CameraDao cameraDao();
}