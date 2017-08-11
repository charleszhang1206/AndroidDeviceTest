package com.okaylens.fqc.dao.deviceInfo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by charleszhang on 2017/8/3.
 */


@Database(entities = {DevTab.class}, version = 2, exportSchema = false)
public abstract class DevDB extends RoomDatabase {
    public abstract DevDao devDao();
}
