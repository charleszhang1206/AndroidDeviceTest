package com.okaylens.fqc.dao.touch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Database(entities = {TouchTab.class}, version = 1, exportSchema = false)
public abstract class TouchDB extends RoomDatabase {
    public abstract TouchDao touchDao();
}