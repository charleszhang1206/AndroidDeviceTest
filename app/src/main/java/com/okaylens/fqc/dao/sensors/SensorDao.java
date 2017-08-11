package com.okaylens.fqc.dao.sensors;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by charleszhang on 2017/8/8.
 */



@Dao
public interface SensorDao {

    @Query("SELECT * FROM sensor_tab")
    SensorTab[] loadAllInfos();


    @Insert
    void insertA(SensorTab sensorTab);


    @Delete
    void delete(SensorTab sensorTab);
}
