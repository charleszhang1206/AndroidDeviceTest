package com.okaylens.fqc.dao.touch;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Dao
public interface TouchDao {

    @Query("SELECT * FROM touch_tab")
    TouchTab[] loadAllInfos();


    @Insert
    void insertA(TouchTab touchTab);


    @Delete
    void delete(TouchTab touchTab);
}

