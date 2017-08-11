package com.okaylens.fqc.dao.touch;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Entity(tableName = "touch_tab")
public class TouchTab {

    @PrimaryKey()
    private int uid;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "x")
    private float x;

    @ColumnInfo(name = "y")
    private float y;

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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}