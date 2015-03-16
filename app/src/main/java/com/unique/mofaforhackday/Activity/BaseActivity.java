package com.unique.mofaforhackday.activity;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by ldx on 2015/2/3.
 * Base activity.
 * added for Umeng
 */
public class BaseActivity extends Activity{
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
