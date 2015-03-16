package com.cursor.common;

import android.content.Context;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/15
 */
public class DisplayUtils {
    public static float sDensity = 0;
    public static int sFullScreenHeight = 0;
    public static int sFullScreenWidth = 0;

    public static Context sContext;

    public static void init(Context context){
        sContext = context;
        sFullScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        sFullScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        sDensity = context.getResources().getDisplayMetrics().density;
    }

    public static int getsFullScreenHeightInPixels(){
        return sFullScreenHeight;
    }

    public static int getsFullScreenWidthInPixels(){
        return sFullScreenWidth;
    }

    public static float getsDensity(){
        return  sDensity;
    }

    public static int dip2px(float dpValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = sContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
