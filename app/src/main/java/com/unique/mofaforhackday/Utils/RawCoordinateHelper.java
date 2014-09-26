package com.unique.mofaforhackday.Utils;

import android.graphics.PointF;

/**
 * Created by ldx on 2014/9/24.
 */
public class RawCoordinateHelper {
    /**
     * Known Point Relative Origin
     */
    private float mRawX,mRawY;
    /**
     * Known Point Relative View
     */
    private float mKnownX,mKnownY;

    public RawCoordinateHelper(float mRawX, float mRawY, float mKnownX1, float mKnownY1){
        this.mRawX = mRawX;
        this.mRawY = mRawY;
        this.mKnownX = mKnownX1;
        this.mKnownY = mKnownY1;
    }

    public void set(float mRawX, float mRawY, float mKnownX1, float mKnownY1){
        this.mRawX = mRawX;
        this.mRawY = mRawY;
        this.mKnownX = mKnownX1;
        this.mKnownY = mKnownY1;
    }

    /**
     * Required Point Relative View
     * @param UnRawX coordinate X relative View
     * @param UnRawY coordinate Y relative View
     * @return Point contains Required Point's Raw coordinate
     */
    public PointF Operate(float UnRawX,float UnRawY){
        float VecX = UnRawX - mKnownX;
        float VecY = UnRawY - mKnownY;
        float requiredRawX = mRawX + VecX;
        float requiredRawY = mRawY + VecY;
        return new PointF(requiredRawX,requiredRawY);
    }
}
