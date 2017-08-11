package com.okaylens.fqc.dao.deviceInfo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by charleszhang on 2017/8/3.
 */

@Dao
public interface DevDao {

    @Query("SELECT * FROM devicesinfo_tab")
    DevTab[] loadAllInfos();

    @Insert
    void insertA(DevTab devtab);


    @Delete
    void delete(DevTab user);

}
