package com.unique.mofaforhackday.view.interpolator;

import android.view.animation.Interpolator;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/11
 */
public class EaseInOutQuintInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        if (input<0.5f)
            return 1f/2*(float)Math.pow(input,5);
        return 1f/2*((input-=2)*(float)Math.pow(input, 4)+2);
    }
}
