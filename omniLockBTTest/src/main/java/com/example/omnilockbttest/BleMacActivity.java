package com.example.omnilockbttest;

import com.omnilockbt.bt.BLEService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BleMacActivity extends Activity {
    private static final String TAG = "BleMacActivity";

    private TextView mReceiverText;
    private Button mSend;
    private Button mSend1;
    private Button mSend2;
    private Button mSend3;
    private Button mSend4;
    private Button mSend5;
    private Button mSend6;
    private Button mSetBleMac;
    private Button mSetAppId;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        mContext = this;
        mReceiverText = (TextView) findViewById(R.id.receiverText);

        Intent intent = new Intent(this, BLEService.class);
        startService(intent);

        // test
        mSend = (Button) findViewById(R.id.send);
        mSend1 = (Button) findViewById(R.id.send1);
        mSend2 = (Button) findViewById(R.id.send2);
        mSend3 = (Button) findViewById(R.id.send3);
        mSend4 = (Button) findViewById(R.id.send4);
        mSend5 = (Button) findViewById(R.id.send5);
        mSetBleMac = (Button) findViewById(R.id.setBleMac);
        mSetAppId = (Button) findViewById(R.id.setAppId);
        mSetAppId.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                byte[] appid = new byte[] { 0x11, 0x11, 0x11, 0x11 };
                setAppId(appid);
            }

        });
        mSetBleMac.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String bleDeviceMac = "FF:FF:11:00:03:A4";
                setDevMac(bleDeviceMac);
            }

        });

        mSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendAction(BLEService.START_LE_SCAN);
            }

        });

        mSend1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendAction(BLEService.ACTION_GET_UNLOCK_KEY);
            }

        });
        mSend2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendAction(BLEService.ACTION_UNLOCK);
            }

        });
        mSend3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendAction(BLEService.ACTION_QUERY);
            }

        });
        mSend4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendAction(BLEService.ACTION_GET_UNUPLOAD);
            }

        });
        mSend5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                sendAction(BLEService.ACTION_CLEAN_UNUPLOAD);
            }

        });
    }

    private void setAppId(byte[] appid) {
        Intent intent = new Intent();
        intent.setAction(BLEService.ACTION_SET_APP_ID);
        intent.putExtra(BLEService.SET_APP_ID_DATA, appid);
        sendBroadcast(intent);
    }

    private void setDevMac(String bleDeviceMac) {
        Intent intent = new Intent();
        intent.setAction(BLEService.ACTION_SET_LE_MAC);
        intent.putExtra(BLEService.SET_LE_MAC_DATA, bleDeviceMac);
        sendBroadcast(intent);
    }

    private void sendAction(String actionGetUnlockKey) {
        Intent intent = new Intent();
        intent.setAction(actionGetUnlockKey);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        Intent intent = new Intent(this, BLEService.class);
        stopService(intent);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BLEService.RECEIVER_LOCK);
        filter.addAction(BLEService.RECEIVER_UNLOCK);
        filter.addAction(BLEService.RECEIVER_UNLOCK_KEY);
        filter.addAction(BLEService.RECEIVER_QUERY);
        filter.addAction(BLEService.RECEIVER_GET_UNUPLOAD);
        filter.addAction(BLEService.RECEIVER_CLEAN_UNUPLOAD);
        filter.addAction(BLEService.RECEIVER_CONNECT_SUCCESS);
        filter.addAction(BLEService.RECEIVER_CONNECT_FAILURE);
        registerReceiver(mBroadcastReceiver, filter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action: " + action);
            if (action.equals(BLEService.RECEIVER_LOCK)) {
                byte data = intent.getByteExtra(BLEService.RECEIVER_LOCK_RESULT, (byte) 0);
                Log.d(TAG, "hex data: " + data);
                sendAction(BLEService.ACTION_LOCK);
            } else if (action.equals(BLEService.RECEIVER_CONNECT_SUCCESS)) {

            } else if (action.equals(BLEService.RECEIVER_CONNECT_FAILURE)) {

            } else if (action.equals(BLEService.RECEIVER_UNLOCK)) {
                byte unlockResult = intent.getByteExtra(BLEService.RECEIVER_UNLOCK_RESULT, (byte) 0);
            } else if (action.equals(BLEService.RECEIVER_UNLOCK_KEY)) {
                byte key = intent.getByteExtra(BLEService.RECEIVER_UNLOCK_KEY_RESULT, (byte) 0);
            } else if (action.equals(BLEService.RECEIVER_QUERY)) {
                byte LockStatus = intent.getByteExtra(BLEService.RECEIVER_QUERY_LOCKSTATUS, (byte) 0);
                byte batteryCapacity = intent.getByteExtra(BLEService.RECEIVER_QUERY_BATTERYCAPACITY, (byte) 0);
                byte isUnupload = intent.getByteExtra(BLEService.RECEIVER_QUERY_ISUNUPLOAD, (byte) 0);
            } else if (action.equals(BLEService.RECEIVER_GET_UNUPLOAD)) {
                byte[] rideTime = intent.getByteArrayExtra(BLEService.RECEIVER_GET_UNUPLOAD_RIDETIME);
                byte[] userID = intent.getByteArrayExtra(BLEService.RECEIVER_GET_UNUPLOAD_USERID);
            } else if (action.equals(BLEService.RECEIVER_CLEAN_UNUPLOAD)) {
                byte cleanResult = intent.getByteExtra(BLEService.RECEIVER_CLEAN_UNUPLOAD_RESULT, (byte) 0);
            }
        }
    };
}
