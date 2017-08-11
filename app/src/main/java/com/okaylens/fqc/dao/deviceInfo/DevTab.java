package com.okaylens.fqc.dao.deviceInfo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;

/**
 * Created by charleszhang on 2017/8/3.
 */


@Entity(tableName = "devicesinfo_tab")
public class DevTab {

    @PrimaryKey()
    private int uid;

    @ColumnInfo(name = "datetime")
    private String datetime;


    @ColumnInfo(name = "imei")
    private String  imei;


    @ColumnInfo(name = "devicename")
    private String  devicename;


    @ColumnInfo(name = "sdkversion")
    private String sdkversion;

    @ColumnInfo(name = "systemversion")
    private String systemversion;



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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getSdkversion() {
        return sdkversion;
    }

    public void setSdkversion(String sdkversion) {
        this.sdkversion = sdkversion;
    }

    public String getSystemversion() {
        return systemversion;
    }

    public void setSystemversion(String systemversion) {
        this.systemversion = systemversion;
    }
}
