package com.okaylens.fqc.dao.gps;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Entity(tableName = "gps_tab")
public class GpsTab {

    @PrimaryKey()
    private int uid;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "stars")
    private String stars;


    @ColumnInfo(name = "lng")
    //经度
    private String lng;

    @ColumnInfo(name = "lat")
    //纬度
    private String lat;

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

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}