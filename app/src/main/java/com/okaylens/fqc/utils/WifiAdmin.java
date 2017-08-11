package com.okaylens.fqc.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charleszhang on 2017/6/13.
 */

public class WifiAdmin {
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    //扫描的网络连接列表
    private List<ScanResult> mWifiList;
    //网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    WifiManager.WifiLock mWifiLock;
    //private Context context;

    public WifiAdmin(Context context) {
        //this.context = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }


    //打开wifi方法
    public void openWifi(Context context) {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        } else if (mWifiManager.getWifiState() == 2) {
            Toast.makeText(context, "Wifi正在开启，请稍候...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Wifi已经开启,不用再开了", Toast.LENGTH_SHORT).show();
        }
    }

    //关闭wifi方法
    public void closeWifi(Context context) {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        } else if (mWifiManager.getWifiState() == 0) {
            Toast.makeText(context, "Wifi正在关闭，请稍后...", Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 1) {
            Toast.makeText(context, "Wifi已经关闭，不用再关了", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "请重新关闭", Toast.LENGTH_SHORT).show();
        }
    }


    //检查当前Wifi状态
    public void checkState(Context context) {
        if (mWifiManager.getWifiState() == 0) {
            Toast.makeText(context, "Wifi正在关闭", Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 1) {
            Toast.makeText(context, "Wifi已经关闭", Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 2) {
            Toast.makeText(context, "Wifi正在开启", Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 3) {
            Toast.makeText(context, "Wifi已经开启", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "没有获取到WiFi状态", Toast.LENGTH_SHORT).show();
        }
    }


    //返回状态值给数据库
    public int checkStateFlag(Context context) {
        if (mWifiManager.getWifiState() == 0) {
            //Toast.makeText(context, "Wifi正在关闭", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (mWifiManager.getWifiState() == 1) {
            //Toast.makeText(context, "Wifi已经关闭", Toast.LENGTH_SHORT).show();
            return 0;
        } else if (mWifiManager.getWifiState() == 2) {
            //Toast.makeText(context, "Wifi正在开启", Toast.LENGTH_SHORT).show();
            return 1;
        } else if (mWifiManager.getWifiState() == 3) {
            //Toast.makeText(context, "Wifi已经开启", Toast.LENGTH_SHORT).show();
            return 1;
        } else {
            //Toast.makeText(context, "没有获取到WiFi状态", Toast.LENGTH_SHORT).show();
            return 2;
        }
    }

    //锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }


    //解锁WifiLock
    public void releaseWifiLock() {
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    //创建一个WifiLock
    public void craetWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("Test");
    }

    //得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    //指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        //索引大于配置好的是网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
    }

    //得到配置好的网络
    public void statScan(Context context) {
        mWifiManager.startScan();
        //得到扫描结果
        List<ScanResult> results = mWifiManager.getScanResults();
        //得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        if (results == null) {
            if (mWifiManager.getWifiState() == 3) {
                Toast.makeText(context, "当前区域没有无线网络", Toast.LENGTH_SHORT).show();
            } else if (mWifiManager.getWifiState() == 2) {
                Toast.makeText(context, "WiFi正在开启，请稍后重新点击扫描", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "WiFi没有开启，无法扫描", Toast.LENGTH_SHORT).show();
            }
        } else {
            mWifiList = new ArrayList<>();
            for (ScanResult result : results) {
                if (result.SSID == null || result.SSID.length() == 0 ||
                        result.capabilities.contains("[IBSS]")) {
                    continue;
                }
                boolean found = false;
                for (ScanResult item : mWifiList) {
                    if (item.SSID.equals(result.SSID) && item.capabilities.equals(result.capabilities)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    mWifiList.add(result);
                }
            }
        }
    }

    //scanflag数据库内容
    public int startScanFlag(Context context) {
        mWifiManager.startScan();
        //得到扫描结果
        List<ScanResult> results = mWifiManager.getScanResults();
        //得到配置好的网络连接
        //mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        if (results == null) {
           return 0;
        } else {
           return 1;
        }
    }

    //得到网络列表
    public List<ScanResult> getmWifiList() {
        return mWifiList;
    }

    //查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + new Integer(i + 1).toString() + ":");
        }
        return stringBuilder;
    }

    //得到Mac地址
    public String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    //得到接入点的BSSID
    public String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    //得到IP地址
    public int getIPAdress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    //得到连接的ID
    public int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    //添加一个网络并连接
    public void addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean ret = mWifiManager.enableNetwork(wcgID, true);
        System.out.print("a--" + wcgID);
        System.out.print("b--" + ret);
    }

    //断开指定ID网络
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }

    public void removeWifi(int netId) {
        disconnectWifi(netId);
        mWifiManager.removeNetwork(netId);
    }


    public WifiConfiguration CreatWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1)//WIFI_CIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2)//WIFI_CIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3)//WIFICIPHER_WPA   
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);     
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }

        return config;
    }

    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }
}
