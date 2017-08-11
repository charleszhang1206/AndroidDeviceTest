package com.okaylens.fqc.dao.micphone;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by charleszhang on 2017/8/4.
 */


@Dao
public interface MicDao {

    @Query("SELECT * FROM micphone_tab")
    MicTab[] loadAllInfos();


    @Insert
    void insertA(MicTab micTab);


    @Delete
    void delete(MicTab micTab);
}
