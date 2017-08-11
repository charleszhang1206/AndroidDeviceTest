package com.okaylens.fqc.dao.bluetooth;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by charleszhang on 2017/8/8.
 */

@Database(entities = {BlueTab.class}, version = 1, exportSchema = false)
public abstract class BlueDB extends RoomDatabase {
    public abstract BlueDao blueDao();
}
