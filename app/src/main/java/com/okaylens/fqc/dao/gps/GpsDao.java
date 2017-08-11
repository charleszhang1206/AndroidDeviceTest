package com.okaylens.fqc.dao.gps;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Dao
public interface GpsDao {

    @Query("SELECT * FROM gps_tab")
    GpsTab[] loadAllInfos();


    @Insert
    void insertA(GpsTab gpsTab);


    @Delete
    void delete(GpsTab gpsTab);
}
