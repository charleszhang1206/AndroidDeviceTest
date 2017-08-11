package com.okaylens.fqc.ui;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.test.ServiceTestCase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.okaylens.fqc.R;
import com.okaylens.fqc.base.BaseAty;
import com.okaylens.fqc.dao.bluetooth.BlueDB;
import com.okaylens.fqc.dao.bluetooth.BlueTab;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by charleszhang on 2017/5/4.
 */

public class BluetoothAct extends BaseAty implements View.OnClickListener, AdapterView.OnItemClickListener {
    public BluetoothAdapter bluetoothAdapter;
    private TextView textView;
    private Button button1, button2, btn_openbluetooth, btn_closebluetooth, btn_scanbluetooth, btn_socketbluetooth;
    public final static int RESULT_BLUE0 = 13;
    public final static int RESULT_BLUE1 = 14;

    private ListView lvDevices;
    private List<String> bluetoothDevices = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private final UUID MY_UUID = UUID.fromString("abcd1234-ab12-ab12-ab12-abcdef123456");
    private BluetoothSocket clientSocket;
    private BluetoothDevice device;
    private OutputStream os;
    private BlueDB mDb;
    private int connectflag,switchflag,scanflag;
    private static final String TAG = "BluetoothAct";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(R.layout.bluetooth_act);
        init();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice pairedDevice : pairedDevices) {
                bluetoothDevices.add(pairedDevice.getName() + ":" + pairedDevice.getAddress());
                scanflag = 1;
            }
        }else{
            scanflag = 0;
        }
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,bluetoothDevices);
        lvDevices.setAdapter(arrayAdapter);
        lvDevices.setOnItemClickListener(this);
        // 设置广播信息过滤
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);//每搜索到一个设备就会发送一个该广播
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//当全部搜索完后发送该广播
        filter.setPriority(Integer.MAX_VALUE);//设置优先级
        // 注册蓝牙搜索广播接收者，接收并处理搜索结果
        registerReceiver(mReceiver, filter);

        mDb = Room.databaseBuilder(getApplicationContext(), BlueDB.class, "bluetooth_tab")
                .allowMainThreadQueries().build();
    }

    private void init() {
        lvDevices = (ListView) findViewById(R.id.lv_devices);
        //textView = (TextView) findViewById(R.id.textView10);
        button1 = (Button) findViewById(R.id.right_fub_bar_suc_bt);
        button2 = (Button) findViewById(R.id.right_fub_bar_fail_bt);
        btn_openbluetooth = (Button) findViewById(R.id.btn_openbluetooth);
        btn_closebluetooth = (Button) findViewById(R.id.btn_closebluetooth);
        btn_scanbluetooth = (Button) findViewById(R.id.btn_scanbluetooth);
        btn_socketbluetooth = (Button) findViewById(R.id.btn_socketbluetooth);
        button1.setOnClickListener(BluetoothAct.this);
        button2.setOnClickListener(BluetoothAct.this);
        btn_openbluetooth.setOnClickListener(BluetoothAct.this);
        btn_closebluetooth.setOnClickListener(BluetoothAct.this);
        btn_scanbluetooth.setOnClickListener(BluetoothAct.this);
        btn_socketbluetooth.setOnClickListener(BluetoothAct.this);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()) {
            case R.id.btn_openbluetooth:
                if (bluetoothAdapter == null) {
                    Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_SHORT).show();
                }
                /*if (bluetoothAdapter.enable()) {
                    Toast.makeText(this, "蓝牙已经打开...勿重复", Toast.LENGTH_SHORT).show();
                }*/
                if (bluetoothAdapter.disable()) {
                    bluetoothAdapter.enable();
                    Toast.makeText(this, "蓝牙已经打开...", Toast.LENGTH_SHORT).show();
                    switchflag = 1;
                }
                break;
            case R.id.btn_closebluetooth:
                if (bluetoothAdapter == null) {
                    Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_SHORT).show();
                }
                /*if (bluetoothAdapter.disable()) {
                    Toast.makeText(this, "蓝牙已经关闭...勿重复", Toast.LENGTH_SHORT).show();
                }*/
                if (bluetoothAdapter.enable()) {
                    bluetoothAdapter.disable();
                    Toast.makeText(this, "蓝牙已经关闭...", Toast.LENGTH_SHORT).show();
                    switchflag = 0;
                }
                break;
            case R.id.btn_scanbluetooth:
                if (bluetoothAdapter.isDiscovering()){
                    bluetoothAdapter.cancelDiscovery();
                }
                //开启搜素
                bluetoothAdapter.startDiscovery();
                break;
            case R.id.btn_socketbluetooth:


                break;
            case R.id.right_fub_bar_suc_bt:
                Intent intent = new Intent();
                setResult(RESULT_BLUE0, intent);
                queryData();
                finish();
                break;
            case R.id.right_fub_bar_fail_bt:
                Intent intent1 = new Intent();
                setResult(RESULT_BLUE1, intent1);
                finish();
                break;
        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //textView.append(device.getName() + ":" + device.getAddress());
                    bluetoothDevices.add(device.getName() + ":" + device.getAddress());
                    arrayAdapter.notifyDataSetChanged();//更新适配器
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //已搜索完成
                Toast.makeText(context, "搜索完毕...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = arrayAdapter.getItem(position);
        String address = s.substring(s.indexOf(":") + 1).trim();//解析地址
        //主动连接蓝牙服务端
        try {
            if (bluetoothAdapter.isDiscovering()){
                bluetoothAdapter.cancelDiscovery();
            }
            try{
                if (device == null){
                    //获取远程设备
                    device = bluetoothAdapter.getRemoteDevice(address);
                }
                if (clientSocket == null){
                    //创建客户端蓝牙socket
                    clientSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                    //开始连接蓝牙，没有配对则弹出对话框进行配对
                    clientSocket.connect();
                    connectflag = 1;
                    //获得输出流
                    os = clientSocket.getOutputStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (os != null) {
                //往服务器写信息
                os.write("蓝牙信号来了".getBytes("utf-8"));
            }else{
                connectflag = 0;
            }
        } catch (Exception e){

        }
    }


    private void queryData() {
        //传入数据
        BlueTab blueTab = new BlueTab();
        blueTab.setUid((int) (System.currentTimeMillis() % 10000));

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd-HHmmss");
        Date date = new Date(currentTime);
        blueTab.setDatetime(formatter.format(date));

        blueTab.setSwitchflag(switchflag);
        blueTab.setScanflag(scanflag);
        blueTab.setConnnectflag(connectflag);

        if (!mDb.isOpen()) {
            //Toast.makeText(DevicesInfoAct.this, "db is close", Toast.LENGTH_SHORT).show();
            Log.d(TAG, " thread  : db not open" + "");
            //return;
        }
        mDb.beginTransaction();
        //mDb.userDao().insertAll(user);
        mDb.blueDao().insertA(blueTab);
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        //Log.d(TAG, " Insert Db suc !!!!!!!!!!!!!!!!!!! : " + "");

        BlueTab[] bluetabs = null;
        bluetabs = mDb.blueDao().loadAllInfos();
        for (BlueTab bluetab : bluetabs) {
            Log.e(TAG, bluetab.getUid() + " " +
                    bluetab.getDatetime() + " " +
                    bluetab.getSwitchflag()+ " " +
                    bluetab.getScanflag()+ " " +
                    bluetab.getConnnectflag());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mDb.close();
    }
}
