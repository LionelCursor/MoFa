package com.unique.mofaforhackday.view;

import android.content.Context;
import android.util.AttributeSet;

import android.widget.AbsSeekBar;
import android.widget.SeekBar;

import com.unique.mofaforhackday.Utils.L;

/**
 * Created by ldx on 2014/9/19.
 */
public class MoFaSeekBar extends SeekBar {

    private OnSeekBarChangeListener mListener;

    public MoFaSeekBar(Context context) {
        super(context);
    }

    public MoFaSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoFaSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }

    public void setProgressAndDisplay(int progress){
        setProgress(progress);
        if (mListener != null){
            mListener.onStopTrackingTouch(this);
        }
    }


    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
        mListener = l;
    }


}
