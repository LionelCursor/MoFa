package com.cursor.common;

import android.content.Context;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/15
 */
public class AppData {
    private static Context sAppContext;

    public static Context getContext(){
        return sAppContext;
    }

    public static void init(Context context){
        sAppContext = context;
    }
}
