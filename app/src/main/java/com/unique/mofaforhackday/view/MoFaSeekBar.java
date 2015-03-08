package com.unique.mofaforhackday.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.ViewTreeObserver;
import android.widget.SeekBar;

/**
 * Created by ldx on 2014/9/19.
 */
public class MoFaSeekBar extends SeekBar {

    private OnSeekBarChangeListener mListener;

    private boolean isDrawCustomBackground;
    private Paint mBackgroundLittleRoundDotPaint;


    public MoFaSeekBar(Context context) {
        super(context, null);
        init();
    }

    public MoFaSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoFaSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        isDrawCustomBackground = true;
        mBackgroundLittleRoundDotPaint = new Paint();
        mBackgroundLittleRoundDotPaint.setAntiAlias(true);
        mBackgroundLittleRoundDotPaint.setColor(0xffffffff);
        mBackgroundLittleRoundDotPaint.setStyle(Paint.Style.FILL);
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                MoFaSeekBar.this.post(new Runnable() {
                    @Override
                    public void run() {
                        getMeasuredHeight();
                    }
                });
            }
        });
    }



    /** set if draw little circle for background*/
    public void setDrawCustomBackground(boolean bool){
        isDrawCustomBackground = bool;
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


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        if (isDrawCustomBackground){
            int width = getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
            canvas.save();
            canvas.translate(getPaddingLeft(), getPaddingTop()+getMeasuredHeight()/2f);
            for(int i= 0;i<getMax();i++){
                canvas.drawCircle(0,0,10,mBackgroundLittleRoundDotPaint);
                canvas.translate(((float)width)/getMax(),0);
            }
            canvas.drawCircle(0,0,10,mBackgroundLittleRoundDotPaint);
            canvas.restore();
        }
        super.onDraw(canvas);
    }
}
