package com.qianying.bbdc.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

import com.qianying.bbdc.MainActivity;
import com.qianying.bbdc.R;
import com.qianying.bbdc.base.BaseActivity;
import com.qianying.bbdc.widget.CustomTitlebar;
import com.qianying.bbdc.widget.StatusView;

/**
 * Created by Aaron on 2017/7/6.
 */

public class SuccessActivity extends BaseActivity {
    private CustomTitlebar mTitlebar;
    private StatusView statusTel;
    private StatusView statusDeposit;
    private StatusView statusCertification;
    private StatusView statusComplete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initView();

        new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message msg) {
                startActivity(new Intent(SuccessActivity.this, MainActivity.class));
                finish();
                return false;
            }
        }).sendEmptyMessageDelayed(0,3000);
    }

    private void initView(){
        statusTel = (StatusView) findViewById(R.id.status_tel);
        statusDeposit = (StatusView) findViewById(R.id.status_deposit);
        statusCertification = (StatusView) findViewById(R.id.status_certification);
        statusComplete = (StatusView) findViewById(R.id.status_complete);
        mTitlebar = (CustomTitlebar) findViewById(R.id.titlebar);
        mTitlebar.setTitleColor(getResources().getColor(R.color.white));
        mTitlebar.setTitleText("实名认证");
        mTitlebar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        statusTel.setText("手机验证");
        statusTel.setStatus(StatusView.COMPLETE);
        statusTel.setDivider(false);
        statusDeposit.setText("押金充值");
        statusDeposit.setStatus(StatusView.COMPLETE);
        statusCertification.setText("实名认证");
        statusCertification.setStatus(StatusView.COMPLETE);
        statusComplete.setText("注册完成");
        statusComplete.setStatus(StatusView.NEXT);
        statusComplete.setDivider(true);

    }


}
