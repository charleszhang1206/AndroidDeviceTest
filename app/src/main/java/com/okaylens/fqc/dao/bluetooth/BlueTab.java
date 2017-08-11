package com.okaylens.fqc.dao.bluetooth;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by charleszhang on 2017/8/8.
 */


@Entity(tableName = "bluetooth_tab")
public class BlueTab {

    @PrimaryKey()
    private int uid;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "scanflag")
    //0/1表示连接与否
    private int scanflag;

    @ColumnInfo(name = "connnectflag")
    //0/1表示连接与否
    private int connnectflag;

    @ColumnInfo(name = "switchflag")
    //0/1表示连接与否
    private int switchflag;

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

    public int getScanflag() {
        return scanflag;
    }

    public void setScanflag(int scanflag) {
        this.scanflag = scanflag;
    }

    public int getConnnectflag() {
        return connnectflag;
    }

    public void setConnnectflag(int connnectflag) {
        this.connnectflag = connnectflag;
    }

    public int getSwitchflag() {
        return switchflag;
    }

    public void setSwitchflag(int switchflag) {
        this.switchflag = switchflag;
    }
}