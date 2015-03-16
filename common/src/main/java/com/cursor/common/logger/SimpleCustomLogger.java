package com.cursor.common.logger;

import android.util.Log;
/**
 * Created by ldx on 2015/2/17.
 * the implementation class which can be extended easily
 * only call android.util.Log
 */
public class SimpleCustomLogger implements LogInterface {
    @Override
    public void d(String tag, String content) {
        Log.d(tag,content);   
    }

    @Override
    public void d(String tag, String content, Throwable tr) {
        Log.d(tag,content,tr);
    }

    @Override
    public void e(String tag, String content) {
        Log.e(tag, content);
    }

    @Override
    public void e(String tag, String content, Throwable tr) {
        Log.e(tag,content,tr);
    }

    @Override
    public void i(String tag, String content) {
        Log.i(tag,content);
    }

    @Override
    public void i(String tag, String content, Throwable tr) {
        Log.i(tag,content,tr);
    }

    @Override
    public void v(String tag, String content) {
        Log.v(tag,content);
    }

    @Override
    public void v(String tag, String content, Throwable tr) {
        Log.v(tag, content, tr);
    }

    @Override
    public void w(String tag, String content) {
        Log.w(tag, content);
    }

    @Override
    public void w(String tag, String content, Throwable tr) {
        Log.w(tag, content, tr);
    }

    @Override
    public void w(String tag, Throwable tr) {
        Log.w(tag, tr);
    }

    @Override
    public void wtf(String tag, String content) {
        Log.wtf(tag, content);
    }

    @Override
    public void wtf(String tag, String content, Throwable tr) {
        Log.wtf(tag,content,tr);
    }

    @Override
    public void wtf(String tag, Throwable tr) {
        Log.wtf(tag,tr);
    }
}
