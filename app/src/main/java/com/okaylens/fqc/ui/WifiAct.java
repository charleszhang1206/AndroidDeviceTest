package com.okaylens.fqc.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.wifi.WifiDB;
import com.okaylens.fqc.dao.wifi.WifiTab;
import com.okaylens.fqc.utils.WifiAdmin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class WifiAct extends BaseAty implements View.OnClickListener{
    private ListView mListView;
    private Button btn_openwifi,btn_closewifi,btn_checkwifi,btn_scanwifi,button1,button2;
    public final static int RESULT_WIFI0 = 11;
    public final static int RESULT_WIFI1 = 12;
    public int level;
    protected String ssid;
    protected WifiAdmin mWifiAdmin;
    private List<ScanResult> mWifiList;
    private int connectflag;
    private WifiDB mDb;
    private static final String TAG = "WifiAct";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.wifi_act);
        init();
        mWifiAdmin = new WifiAdmin(WifiAct.this);
        //broadcast
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver,filter);

        mDb = Room.databaseBuilder(getApplicationContext(), WifiDB.class, "wifi_tab")
                .allowMainThreadQueries().build();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WifiAct.this);
                ssid = mWifiList.get(position).SSID;
                alert.setTitle(ssid);
                alert.setMessage("请输入密码：");
                final EditText et_password = new EditText(WifiAct.this);
                final SharedPreferences preferences =getSharedPreferences("wifi_passpord",Context.MODE_PRIVATE);
                et_password.setText(preferences.getString(ssid,""));
                alert.setView(et_password);
                alert.setPositiveButton("连接", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pw = et_password.getText().toString();
                        if (pw == null || pw.length() < 8 ){
                            Toast.makeText(WifiAct.this,"密码至少8位",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SharedPreferences.Editor editor =preferences.edit();
                        editor.putString(ssid,pw);
                        editor.commit();
                        mWifiAdmin.addNetwork(mWifiAdmin.CreatWifiInfo(ssid,et_password.getText().toString(),3));
                    }
                });
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();
            }
        });

    }

    private void init() {
        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button2= (Button) findViewById(R.id.right_fub_bar_fail_bt);
        btn_openwifi= (Button) findViewById(R.id.btn_openwifi);
        btn_closewifi= (Button) findViewById(R.id.btn_closewifi);
        btn_checkwifi= (Button) findViewById(R.id.btn_checkwifi);
        btn_scanwifi= (Button) findViewById(R.id.btn_scan_wifi);
        button1.setOnClickListener(WifiAct.this);
        button2.setOnClickListener(WifiAct.this);
        btn_openwifi.setOnClickListener(WifiAct.this);
        btn_closewifi.setOnClickListener(WifiAct.this);
        btn_checkwifi.setOnClickListener(WifiAct.this);
        btn_scanwifi.setOnClickListener(WifiAct.this);
        mListView = (ListView) findViewById(R.id.wifiList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_WIFI0,intent);
                queryData();

                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_WIFI1,intent1);
                finish();
                break;
            case R.id.btn_openwifi:
                mWifiAdmin.openWifi(WifiAct.this);
                break;
            case R.id.btn_closewifi:
                mWifiAdmin.closeWifi(WifiAct.this);
                break;
            case R.id.btn_checkwifi:
                mWifiAdmin.checkState(WifiAct.this);
                break;
            case  R.id.btn_scan_wifi:
                mWifiAdmin.statScan(WifiAct.this);
                mWifiList = mWifiAdmin.getmWifiList();
                if (mWifiList != null){
                    mListView.setAdapter(new MyAdapter(this,mWifiList));
                    //new Utility().setListViewHeightBasedOnChrilden(mListView);
                }
                break;
            default:
                break;
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //ConnectivityManager主要管理和网络连接相关的操作 
            ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //NetworkInfo类包含了对wifi和mobile两种网络模式连接的详细描述,通过其getState()方法获取的State对象则代表着 成功和失败
            NetworkInfo wifiInfo = manager.getActiveNetworkInfo();
            //manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null) {
                if (wifiInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    String wifiSSID = wifiManager.getConnectionInfo().getSSID();
                    Toast.makeText(context, wifiSSID + "连接成功", Toast.LENGTH_SHORT).show();
                    connectflag = 1;
                }else {
                    connectflag = 0;
                }
            }
        }
    };

    private class MyAdapter extends BaseAdapter {
        LayoutInflater inflater;
        List<ScanResult> list ;

        public MyAdapter(Context context, List<ScanResult> list){
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.wifi_listitem,null);
            ScanResult scanResult = list.get(position);
            TextView wifi_ssid = (TextView) view.findViewById(R.id.ssid);
            ImageView wifi_level = (ImageView) view.findViewById(R.id.wifi_level);
            wifi_ssid.setText(scanResult.SSID);
            level = WifiManager.calculateSignalLevel(scanResult.level,5);
            if (scanResult.capabilities.contains("WEP") || scanResult.capabilities.contains("PSK")
                || scanResult.capabilities.contains("EAP")){
                wifi_level.setImageResource(R.mipmap.wifi_signal_lock);
            }else{
                wifi_level.setImageResource(R.mipmap.wifi_signal_open);
            }
            wifi_level.setImageLevel(level);
            //判断信号强弱，显示不同图标
            return view;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mDb.close();
    }

    private void queryData() {
                //传入数据
                WifiTab wifiTab = new WifiTab();
                wifiTab.setUid((int) (System.currentTimeMillis()% 10000));

                long currentTime = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
                Date date = new Date(currentTime);
                wifiTab.setDatetime(formatter.format(date));

                wifiTab.setConnnectflag(connectflag);
                wifiTab.setScanflag(mWifiAdmin.startScanFlag(WifiAct.this));
                wifiTab.setSwitchflag(mWifiAdmin.checkStateFlag(WifiAct.this));

                if (!mDb.isOpen()) {
                    //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, " thread  : db not open" + "");
                    //return;
                }
                mDb.beginTransaction();
                //mDb.userDao().insertAll(user);
                mDb.wifiDao().insertA(wifiTab);
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
                //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

                WifiTab[] wifitabs = null;
                wifitabs = mDb.wifiDao().loadAllInfos();
                for (WifiTab wifitab : wifitabs) {
                    Log.e(TAG,  wifitab.getUid() +" " +
                            wifitab.getDatetime() +" " +
                            wifitab.getConnnectflag()+" " +
                            wifitab.getScanflag()+" " +
                            wifitab.getSwitchflag());
                }
    }

    //scrollbar和listview焦点冲突问题
//    private class Utility {
//
//        public void setListViewHeightBasedOnChrilden(ListView listView){
//            ListAdapter listAdapter = listView.getAdapter();
//            if (listAdapter == null){
//                return;
//            }
//           int totalHeight = 0;
//            for (int i = 0; i < listAdapter.getCount(); i++){
//                View listItem = listAdapter.getView(i,null,listView);
//                listItem.measure(0,0);
//                totalHeight += listItem.getMeasuredHeight();
//            }
//            ViewGroup.LayoutParams params =listView.getLayoutParams();
//            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//            listView.setLayoutParams(params);
//        }
//    }
}
