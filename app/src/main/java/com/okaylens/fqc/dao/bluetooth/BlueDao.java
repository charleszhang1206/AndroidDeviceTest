package com.okaylens.fqc.dao.bluetooth;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;

import com.okaylens.fqc.dao.micphone.MicTab;
import com.okaylens.fqc.dao.wifi.WifiDao;
import com.okaylens.fqc.dao.wifi.WifiTab;

/**
 * Created by charleszhang on 2017/8/8.
 */



@Dao
public interface BlueDao {

    @Query("SELECT * FROM bluetooth_tab")
    BlueTab[] loadAllInfos();


    @Insert
    void insertA(BlueTab blueTab);


    @Delete
    void delete(BlueTab blueTab);
}
