package com.okaylens.fqc.dao.camera;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;

import com.okaylens.fqc.dao.micphone.MicTab;

/**
 * Created by charleszhang on 2017/8/8.
 */



@Dao
public interface CameraDao {

    @Query("SELECT * FROM camera_tab")
    CameraTab[] loadAllInfos();


    @Insert
    void insertA(CameraTab cameraTab);


    @Delete
    void delete(CameraTab cameraTab);
}

