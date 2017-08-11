package com.okaylens.fqc.dao.speaker;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Dao
public interface SpeakerDao {

    @Query("SELECT * FROM speaker_tab")
    SpeakerTab[] loadAllInfos();


    @Insert
    void insertA(SpeakerTab speakerTab);


    @Delete
    void delete(SpeakerTab speakerTab);
}
