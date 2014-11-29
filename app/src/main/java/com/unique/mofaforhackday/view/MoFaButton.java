package com.unique.mofaforhackday.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.unique.mofaforhackday.view.cropper.cropwindow.handle.Handle;
import com.unique.mofaforhackday.view.interpolator.easeOutElasticInterpolator;


/**
 * Created by ldx on 2014/11/29.
 * Used In Main Activity
 * When it was pressed it will be shrunk, and enlarge when pointer up
 */
public class MoFaButton extends ImageView {
    private static final String TAG = "MoFaButton";
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
    ButtonHandler handler;
    private void init(){
        initAnimShrinkSet();
        initAnimRecover();
        handler = new ButtonHandler();
    }


    private void initAnimRecover(){
        animRecover = new AnimatorSet();
        ObjectAnimator animRecoverX = ObjectAnimator.ofFloat(this,"scaleX",1f,1/.8f);
        ObjectAnimator animRecoverY = ObjectAnimator.ofFloat(this,"scaleY",1f,1/.8f);
        animRecover.playTogether(animRecoverX,animRecoverY);
        animRecover.setDuration(400);
        animRecover.setInterpolator(new easeOutElasticInterpolator());
    }

    private void initAnimShrinkSet(){
        animShrink = new AnimatorSet();
        ObjectAnimator animShrinkX = ObjectAnimator.ofFloat(this,"scaleX",1f,0.8f);
        ObjectAnimator animShrinkY = ObjectAnimator.ofFloat(this,"scaleY",1f,0.8f);
        animShrink.playTogether(animShrinkX,animShrinkY);
        animShrink.setDuration(50);
        animShrink.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                animShrink.start();
                break;
            case MotionEvent.ACTION_UP:
                animShrink.cancel();
                animRecover.start();
                break;
            case MotionEvent.ACTION_CANCEL:
                animRecover.start();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
        }
        return super.onTouchEvent(event);
    }
    class ButtonHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x123:
                    MoFaButton.super.performClick();
                    break;
                default:
            }
        }
    }
    @Override
    public boolean performClick() {
        handler.sendEmptyMessageDelayed(0x123,200);
        return true;
    }

    public void shrink(float ratio){

    }

    public void enlarge(float ratio){

    }

}
