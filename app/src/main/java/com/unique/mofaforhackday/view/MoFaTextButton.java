package com.unique.mofaforhackday.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.unique.mofaforhackday.R;

/**
 * Created by ldx on 2014/12/10.
 */
public class MoFaTextButton extends TextView {
    private final static String TAG = "MoFaTextButton";
    private int pressedColor = 0xFFFDDE93;

    public MoFaTextButton(Context context) {
        super(context);
    }

    public MoFaTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoFaTextButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MoFaTextButton);
        pressedColor = a.getColor(R.styleable.MoFaTextButton_pressed_color,pressedColor);
        Log.e(TAG,"pressedColor: "+pressedColor);
    }
    public void colorReset(){
        this.setTextColor(0xffffffff);
    }

    public void colorPressed(){
        this.setTextColor(0xfffdde93);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.setTextColor(pressedColor);
                break;
            default:
        }
        return super.onTouchEvent(event);
    }
}
