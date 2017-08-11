package com.okaylens.fqc.dao.sensors;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Entity(tableName = "sensor_tab")
public class SensorTab {

    @PrimaryKey()
    private int uid;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "accelerx")
    private String accelerx;

    @ColumnInfo(name = "accelery")
    private String accelery;

    @ColumnInfo(name = "accelerz")
    private String accelerz;

    @ColumnInfo(name = "magx")
    private String magx;

    @ColumnInfo(name = "magy")
    private String magy;

    @ColumnInfo(name = "magz")
    private String magz;

    @ColumnInfo(name = "gyrx")
    private String gyrx;

    @ColumnInfo(name = "gyry")
    private String gyry;

    @ColumnInfo(name = "gyrz")
    private String gyrz;

    @ColumnInfo(name = "distance")
    private String distance;

    @ColumnInfo(name = "acc")
    private String acc;

    @ColumnInfo(name = "lux")
    private String lux;

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

    public String getAccelerx() {
        return accelerx;
    }

    public void setAccelerx(String accelerx) {
        this.accelerx = accelerx;
    }

    public String getAccelery() {
        return accelery;
    }

    public void setAccelery(String accelery) {
        this.accelery = accelery;
    }

    public String getAccelerz() {
        return accelerz;
    }

    public void setAccelerz(String accelerz) {
        this.accelerz = accelerz;
    }

    public String getMagx() {
        return magx;
    }

    public void setMagx(String magx) {
        this.magx = magx;
    }

    public String getMagy() {
        return magy;
    }

    public void setMagy(String magy) {
        this.magy = magy;
    }

    public String getMagz() {
        return magz;
    }

    public void setMagz(String magz) {
        this.magz = magz;
    }

    public String getGyrx() {
        return gyrx;
    }

    public void setGyrx(String gyrx) {
        this.gyrx = gyrx;
    }

    public String getGyry() {
        return gyry;
    }

    public void setGyry(String gyry) {
        this.gyry = gyry;
    }

    public String getGyrz() {
        return gyrz;
    }

    public void setGyrz(String gyrz) {
        this.gyrz = gyrz;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getLux() {
        return lux;
    }

    public void setLux(String lux) {
        this.lux = lux;
    }
}
