package com.qianying.bbdc.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qianying.bbdc.MyApp;

import org.xutils.x;

/**
 * Created by Vinsen on 17/5/16.
 */

public class BaseActivity extends AppCompatActivity {

    public static boolean isForeground = false;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        x.view().inject(this);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().hide();
        } else {
            if (getActionBar() != null)
                getActionBar().hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

}
