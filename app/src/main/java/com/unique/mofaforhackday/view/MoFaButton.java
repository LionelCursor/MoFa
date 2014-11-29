package com.unique.mofaforhackday.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;


/**
 * Created by ldx on 2014/11/29.
 * Used In Main Activity
 * When it was pressed it will be shrunk, and enlarge when pointer up
 */
public class MoFaButton extends ImageButton{

    private AnimatorSet animShrink;
    private AnimatorSet animRecover;
    public MoFaButton(Context context) {
        super(context);
        init();
    }

    public MoFaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoFaButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        initAnimShrinkSet();
        initAnimRecover();
    }

    private void initAnimRecover(){
        animRecover = new AnimatorSet();
        ObjectAnimator animRecoverX = ObjectAnimator.ofFloat(this,"scaleX",0.8f,1f);
        ObjectAnimator animRecoverY = ObjectAnimator.ofFloat(this,"scaleY",0.8f,1f);
        animRecover.playTogether(animRecoverX,animRecoverY);
        animRecover.setInterpolator(new DecelerateInterpolator());
    }

    private void initAnimShrinkSet(){
        animShrink = new AnimatorSet();
        ObjectAnimator animShrinkX = ObjectAnimator.ofFloat(this,"scaleX",1f,0.8f);
        ObjectAnimator animShrinkY = ObjectAnimator.ofFloat(this,"scaleY",1f,0.8f);
        animShrink.playTogether(animShrinkX,animShrinkY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:

                break;
            default:

        }
        return true;
    }

    @Override
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
    }

    public void shrink(float ratio){

    }

    public void enlarge(float ratio){

    }

}
