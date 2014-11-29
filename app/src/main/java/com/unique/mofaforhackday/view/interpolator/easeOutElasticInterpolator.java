package com.unique.mofaforhackday.view.interpolator;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by ldx on 2014/11/29.
 * Easing function. ElasticInterpolator
 */
public class easeOutElasticInterpolator implements Interpolator{

    @Override
    public float getInterpolation(float input) {
        return (float)(Math.pow(2,-10* input)*Math.sin((input -.3/4)*2*Math.PI/.3));
    }
}
