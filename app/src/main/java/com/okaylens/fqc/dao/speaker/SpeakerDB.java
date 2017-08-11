package com.okaylens.fqc.dao.speaker;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Database(entities = {SpeakerTab.class}, version = 1, exportSchema = false)
public abstract class SpeakerDB extends RoomDatabase {
    public abstract SpeakerDao speakerDao();
}