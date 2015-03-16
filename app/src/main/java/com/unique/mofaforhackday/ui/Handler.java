package com.unique.mofaforhackday.ui;

import com.unique.mofaforhackday.activity.HandleImageActivity;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/11
 */
public abstract class Handler {
    //In reality, I don't know what to do.(~_~)
    public HandleImageActivity mActivity;

    public void handle(HandleImageActivity activity){
        mActivity = activity;
    }
}
