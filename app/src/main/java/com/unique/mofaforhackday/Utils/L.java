package com.unique.mofaforhackday.Utils;

import android.util.Log;

/**
 * Created by ldx on 2014/9/5.
 * to Log
 */
public class L {
    private static boolean DEBUG = true;
    private static String TAG= "Lei";

    public static void e(String message){
        if (DEBUG){
            Log.e(TAG,message);
        }
    }

    public static void d(String message){
        if (DEBUG){
            Log.d(TAG,message);
        }
    }

    public static void e(String TAG,String message){
        if (DEBUG){
            Log.e(TAG,message);
        }
    }
}
