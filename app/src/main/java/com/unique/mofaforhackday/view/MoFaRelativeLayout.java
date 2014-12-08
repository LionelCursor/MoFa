package com.unique.mofaforhackday.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by ldx on 2014/12/1.
 */
public class MoFaRelativeLayout extends RelativeLayout {
    public MoFaRelativeLayout(Context context) {
        super(context);
    }

    public MoFaRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoFaRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    ArrayList<OnInterceptTouchListener> interceptTouchListeners;

    public interface OnInterceptTouchListener {
        public void InterceptTouchListener(MotionEvent event);
    }

    public void addOnInterceptTouchListener(OnInterceptTouchListener listener) {
        if (interceptTouchListeners == null) {
            interceptTouchListeners = new ArrayList<OnInterceptTouchListener>(1);
        }
        interceptTouchListeners.add(listener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (interceptTouchListeners != null) {
            for (OnInterceptTouchListener ls : interceptTouchListeners) {
                ls.InterceptTouchListener(ev);
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
