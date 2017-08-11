package com.okaylens.fqc.dao.wifi;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.okaylens.fqc.dao.micphone.MicTab;

/**
 * Created by charleszhang on 2017/8/8.
 */

@Dao
public interface WifiDao {

    @Query("SELECT * FROM wifi_tab")
    WifiTab[] loadAllInfos();


    @Insert
    void insertA(WifiTab wifiTab);


    @Delete
    void delete(WifiTab wifiTab);
}