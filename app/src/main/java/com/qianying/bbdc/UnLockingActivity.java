package com.qianying.bbdc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.qianying.bbdc.base.BaseActivity;
import com.qianying.bbdc.http.HttpResponse;
import com.qianying.bbdc.http.MyHttpUtils;
import com.qianying.bbdc.model.MapLocation;
import com.qianying.bbdc.model.NetEntity;
import com.qianying.bbdc.model.RegInfo;
import com.qianying.bbdc.model.TokenInfo;
import com.qianying.bbdc.useBk.EndUseBkActivity;
import com.qianying.bbdc.util.CodeUtil;
import com.qianying.bbdc.util.LocationHelper;
import com.qianying.bbdc.xutils3.MyCallBack;
import com.qianying.bbdc.xutils3.X;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 扫描后的解锁页面
 * Created by Vinsen on 17/5/19.
 */

public class UnLockingActivity extends BaseActivity {

    private static final int test = 1000;
    private MapLocation mapLocation;

    private RegInfo regInfo;
    private TokenInfo tokenInfo;

    private String client_id;
    private String state;
    private String url;
    private String access_token;
    private String SN;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case test:
                    startActivity(new Intent(mContext, EndUseBkActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_unlocking);

        Bundle bundle = getIntent().getExtras();
        SN = bundle.getString("SN");

        regInfo = RegInfo.newInstance();
        tokenInfo = TokenInfo.newInstance();

        mapLocation= LocationHelper.loadMapLocation(mContext);

        loadScan();

//        mHandler.sendEmptyMessageDelayed(test, 2000);

//        MyHttpUtils.userBike(mapLocation.getLatitude(), mapLocation.getLongitude(), "2", new HttpResponse() {
//            @Override
//            public void onGetData(String data) {
//                super.onGetData(data);
//                Log.e("--usebike--",data);
//            }
//
//            @Override
//            public void onError(String error) {
//                super.onError(error);
//            }
//
//            @Override
//            public void onErrorCode(int errorCode) {
//                super.onErrorCode(errorCode);
//            }
//        });

    }


    /**
     * 扫码解锁接口
     * */
    private void loadScan(){
        client_id = regInfo.getApp_key();
        state = regInfo.getSeed_secret();
        url = regInfo.getSource_url();
        access_token = tokenInfo.getAccess_token();
        JSONObject json = new JSONObject();
        try {
            json.put("client_id",client_id);
            json.put("state",state);
            json.put("access_token",access_token);
            json.put("action","scanCode");
            json.put("sn",SN);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        X.Post(url, json, new MyCallBack<String>() {
            @Override
            protected void onFailure(String message) {
                CodeUtil.isCode(UnLockingActivity.this,message);
                startActivity(new Intent(UnLockingActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onSuccess(NetEntity entity) {
                Message msg = new Message();
                msg.what= 101;
                mHandler.sendMessageDelayed(msg,2000);
            }
        });
    }
}
