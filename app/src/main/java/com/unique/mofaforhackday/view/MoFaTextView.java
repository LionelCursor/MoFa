package com.unique.mofaforhackday.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Utils.L;
import com.unique.mofaforhackday.Utils.gesturedetector.MoveGestureDetector;
import com.unique.mofaforhackday.Utils.gesturedetector.RotateGestureDetector;


/**
 * Created by ldx on 2014/9/2.
 *
 * In real, it's actually an imageView. I'm sorry if it mislead you, haha~~
 * OK, now it's not only a view, but a relativeLayout.
 * shit code.
 *
 */

//TODO-BUG:scale/shove/rotate tremble when it relative to itself.
public class MoFaTextView extends TextView {

    private final static String TAG = "MoFaTextView";

    private float ROTATE_RADIO = 1.5f;

    private Context context;
    private AssetManager assetManager;

    /**
     * TextView and it's attribute.
     */
    private ORIENTATION mOrientation= ORIENTATION.HORIZONTAL;
    private String mRawText = "";

    /**
     * Attributes of ImageView or Bitmap
     */
    private float mRotationDegrees = 0.f;
    private float mFocusX = 0.f;
    private float mFocusY = 0.f;
    private int mImageHeight, mImageWidth;

    private RotateGestureDetector mRotateDetector;
    private MoveGestureDetector mMoveDetector;

    public enum MOFA_TYPEFACE{

    }
    public MoFaTextView(Context context) {
        this(context, null);
    }

    public MoFaTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MoFaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;
        this.setMoFaText("");
        this.setTextSize(50);
        this.setPadding(10,0,10,0);
        this.setTextColor(0xffffffff);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.textview_background));
        this.mOrientation = ORIENTATION.HORIZONTAL;

        assetManager = context.getApplicationContext().getAssets();

        // Setup Gesture Detectors
        mRotateDetector = new RotateGestureDetector(context.getApplicationContext(), new RotateListener());
        mMoveDetector 	= new MoveGestureDetector(context.getApplicationContext(), new MoveListener());
        this.post(new Runnable() {
            @Override
            public void run() {
                SelfCenter();
            }
        });
    }



    public void SelfCenter(){
        this.setOnTouchListener(new OnTouchListener());
        // Determine the center of the screen to center 'earth'
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();

        mFocusX = display.getWidth()/2f;
        mFocusY = display.getHeight()/2f;

        mImageHeight = this.getHeight();
        mImageWidth = this.getWidth();

        // View is scaled and translated by matrix, so scale and translate initially
        float ImageCenterX = (mImageWidth)/2f;
        float ImageCenterY = (mImageHeight)/2f;

        this.setTranslationX(mFocusX - ImageCenterX);
        this.setTranslationY(mFocusY - ImageCenterY);
    }


    //onTouchEvent's priority is higher than OnTouchListener's onTouch
    private void display() {
        mImageWidth = this.getWidth();
        mImageHeight = this.getHeight();
        this.setTranslationX(mFocusX - mImageWidth/2f);
        this.setTranslationY(mFocusY - mImageHeight/2f);
        this.setPivotX(mImageWidth);
        this.setPivotY(mImageHeight);
        this.setRotation(mRotationDegrees);
    }

    public class OnTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mRotateDetector.onTouchEvent(event);
            mMoveDetector.onTouchEvent(event);

            //use all the attribute to change and display the ImageView.
            display();
            return true;
        }
    }


    public void setMoFaText(CharSequence text) {
        super.setText(text);
        if (mOrientation == ORIENTATION.VERTICAL) {
            return;
        }
        mRawText = text.toString();
        L.e(text.toString());
    }

    //attr of TextView
    public void setTypeface(String typeface){
        Typeface typeFace = Typeface.createFromAsset(assetManager, typeface);
        this.setTypeface(typeFace);
    }

    public MoFaTextView setOrientation(ORIENTATION orientation){
        this.mOrientation = orientation;
        if (orientation  == ORIENTATION.VERTICAL){
            setVertical();
        }else{
            setHorizontal();
        }
        return this;
    }

    public void setHorizontal(){
        this.mOrientation = ORIENTATION.HORIZONTAL;
        //TODO-make Horizontal
        this.setMoFaText(mRawText);
    }

    public void setVertical(){
        this.mOrientation = ORIENTATION.VERTICAL;
        //TODO-make Vertical
        StringBuilder s = new StringBuilder(this.getText());
        for (int i = s.length()-1;i>0;i--){
            s.insert(i,"\n");
        }
        this.setMoFaText(s);
    }
    public enum ORIENTATION{
        HORIZONTAL,
        VERTICAL
    }
    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            float t = detector.getRotationDegreesDelta();
            if (t>0) {
                mRotationDegrees -= t*ROTATE_RADIO;
            }
            return true;
        }
    }

    private class MoveListener extends MoveGestureDetector.SimpleOnMoveGestureListener {
        @Override
        public boolean onMove(MoveGestureDetector detector) {
            PointF d = detector.getFocusDelta();
            mFocusX += d.x;
            mFocusY += d.y;
            return true;
        }
    }

}
