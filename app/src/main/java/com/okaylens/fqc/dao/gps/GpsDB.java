package com.okaylens.fqc.dao.gps;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Database(entities = {GpsTab.class}, version = 1, exportSchema = false)
public abstract class GpsDB extends RoomDatabase {
    public abstract GpsDao gpsDao();
}