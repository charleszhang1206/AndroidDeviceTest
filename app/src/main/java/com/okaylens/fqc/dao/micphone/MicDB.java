package com.okaylens.fqc.dao.micphone;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by charleszhang on 2017/8/4.
 */


@Database(entities = {MicTab.class}, version = 1, exportSchema = false)
public abstract class MicDB extends RoomDatabase {
    public abstract MicDao micDao();
}
