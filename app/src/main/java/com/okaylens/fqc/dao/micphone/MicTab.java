package com.okaylens.fqc.dao.micphone;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;

/**
 * Created by charleszhang on 2017/8/4.
 */


@Entity(tableName = "micphone_tab")
public class MicTab {

    @PrimaryKey()
    private int uid;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "max")
    private float max;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }
}
