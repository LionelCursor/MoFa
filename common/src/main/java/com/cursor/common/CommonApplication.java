package com.cursor.common;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/15
 */
public class CommonApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AppData.init(this);
        DisplayUtils.init(this);
    }
}
