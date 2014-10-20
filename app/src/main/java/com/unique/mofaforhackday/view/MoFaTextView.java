package com.unique.mofaforhackday.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Utils.gesturedetector.MoveGestureDetector;
import com.unique.mofaforhackday.Utils.gesturedetector.RotateGestureDetector;
/**
 * Created by ldx on 2014/9/2.
 * <p/>
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
    private ORIENTATION mOrientation = ORIENTATION.HORIZONTAL;
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

    private Drawable mBackgroundDrawable;
    private OnFocusedListener mFocusedListener;
    private float mAlpha =1;

    public enum MOFA_TYPEFACE {

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
        this.setPadding(15, 0, 15, 0);
        this.setTextColor(0xffffffff);
        mBackgroundDrawable = context.getResources().getDrawable(R.drawable.textview_background);
        this.mOrientation = ORIENTATION.HORIZONTAL;

        assetManager = context.getApplicationContext().getAssets();


        // Setup Gesture Detectors
        mMoveDetector = new MoveGestureDetector(context.getApplicationContext(), new MoveListener());
        this.setOnTouchListener(new OnTouchListener());

        setOnFocusedListener(new OnFocusedListener() {
            @Override
            public void onFocused(View view) {

            }
        });


        this.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }
    public void setMoFaAlpha(float alpha){
        mAlpha = alpha;
        this.setAlpha(mAlpha);
    }

    public void SelfCenter() {
        this.setAlpha(mAlpha);
        // Determine the center of the screen to center 'earth'
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();

        mFocusX = display.getWidth() / 2f;
        mFocusY = display.getHeight() / 2f;

        mImageWidth = getWidth();
        mImageHeight = getHeight();

        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        // View is scaled and translated by matrix, so scale and translate initially
        float ImageCenterX = (mImageWidth) / 2f;
        float ImageCenterY = (mImageHeight) / 2f;
        setTranslationX(mFocusX - ImageCenterX - left);
        setTranslationY(mFocusY - ImageCenterY - top);
    }


    //onTouchEvent's priority is higher than OnTouchListener's onTouch
    private void display() {
        mImageWidth = this.getWidth();
        mImageHeight = this.getHeight();
        this.setTranslationX(mFocusX - mImageWidth / 2f);
        this.setTranslationY(mFocusY - mImageHeight / 2f);
        this.setRotation(mRotationDegrees);
    }

    public void setTranslation(float deltaX, float deltaY) {
        mFocusX += deltaX;
        mFocusY += deltaY;
        display();
    }

    public void setDeltaRotate(float deltaDegree) {
        //TODO-rotate-layout
        mRotationDegrees -= deltaDegree;
        display();
    }

    public void setRotate(float Degree) {
        this.mRotationDegrees = Degree;
        display();
    }

    /**
     * make it on focused
     * invoked by TouchEvent DOWN
     */
    public interface OnFocusedListener{
        public void onFocused(View view);
    }

    public void setOnFocusedListener(OnFocusedListener listener){
        this.mFocusedListener = listener;
    }

    public class OnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundDrawable(mBackgroundDrawable);
                    if(mFocusedListener!=null){
                        mFocusedListener.onFocused(v);
                    }

                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundDrawable(null);
                    break;
                default:
            }
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
        this.setAlpha(0);
    }

    //attr of TextView
    public void setTypeface(String typeface) {
        Typeface typeFace = Typeface.createFromAsset(assetManager, typeface);
        this.setTypeface(typeFace);
    }



    public void setHorizontal() {
        if (mOrientation == ORIENTATION.HORIZONTAL){
            return;
        }
        this.setLineSpacing(0f,1f);
        this.mOrientation = ORIENTATION.HORIZONTAL;
        this.setMoFaText(mRawText);
    }

    public void setVertical() {
        if (mOrientation == ORIENTATION.VERTICAL){
            return;
        }
        this.setLineSpacing(0f,0.8f);
        this.mOrientation = ORIENTATION.VERTICAL;
        StringBuilder s = new StringBuilder(this.getText());
        for (int i = s.length() - 1; i > 0; i--) {
            s.insert(i, "\n");
        }
        this.setMoFaText(s);
    }

    public enum ORIENTATION {
        HORIZONTAL,
        VERTICAL
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            float t = detector.getRotationDegreesDelta();
            if (t > 0) {
                mRotationDegrees -= t * ROTATE_RADIO;
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
