package com.unique.mofaforhackday.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.unique.mofaforhackday.beans.ColorAdjuster;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ldx on 2014/8/23.
 * Can create a ColorMatrix that give paint
 */
public class ImageAdjuster {

    private static final String TAG = "ImageAdjuster";

    private static final int MSG_FLAG_OK = 0x123;
    private static final int MSG_FLAG_OK_WITH_NO_IMAGEVIEW = 0x124;
    private int Brightness;
    private int Contrast;
    private int Saturation;

    private int Hue;
    private int RedRate;
    private int GreenRate;
    private int BlueRate;

    private static ImageAdjuster mImageAdjuster;

    private ColorMatrix mAllColorMatrix = null;

    private Paint mPaint;


    private ExecutorService threadPool ;
    private DisplayHandler mHandler;
    private ImageAdjustingListener listener;

    private Bitmap mBitmapTemp;
    private ImageView mImageView;

    private ImageAdjuster(){
        this(0,0,0,0,0,0);
    }

    private ImageAdjuster(ColorAdjuster bean){
        this(
                bean.getBrightness()
                ,bean.getContrast()
                ,bean.getSaturation()
                ,bean.getRedRate()
                ,bean.getGreenRate()
                ,bean.getBlueRate());
    }

    private ImageAdjuster(int brightness, int contrast, int saturation,
                         int redRate, int greenRate, int blueRate){
        Brightness = brightness;
        Contrast = contrast;
        Saturation = saturation;
        this.RedRate = redRate;
        this.GreenRate = greenRate;
        this.BlueRate = blueRate;

        mAllColorMatrix = new ColorMatrix();
        mPaint = new Paint();

        threadPool = Executors.newFixedThreadPool(4);
        mHandler = new DisplayHandler();
    }



    public static synchronized ImageAdjuster getInstance(){
        if (mImageAdjuster == null){
            mImageAdjuster = new ImageAdjuster();
            return mImageAdjuster;
        }
        else {
            L.e(mImageAdjuster.toString());
            return mImageAdjuster;
        }
    }

    public void destroy(){
        mImageAdjuster =null;
    }

    private class DisplayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            L.e("handleMessage");
            switch (msg.what){
                case MSG_FLAG_OK:
                    mImageView.setImageBitmap(mBitmapTemp);
                    listener.onAdjustingComplete(mImageView,mBitmapTemp);
                    break;
                case MSG_FLAG_OK_WITH_NO_IMAGEVIEW:
                    L.e("no imageView");
                    listener.onAdjustingComplete(null,mBitmapTemp);
                    break;
                default:
            }
        }
    }

    public void displayImageAdjusted(final Bitmap bitmap,final ImageAdjustingListener listener){
        if (bitmap==null){
            L.e("bitmap null Error");
            return;
        }
        this.listener = listener;
        L.e("listener:"+this.listener.toString());
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                listener.onAdjustingStarted();

                if (mPaint==null){
                    mPaint = new Paint();
                }
                mPaint.setAntiAlias(true);
                setPaintColor();

                int width, height;
                height = bitmap.getHeight();
                width = bitmap.getWidth();

                Bitmap bmp = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);

                Canvas c = new Canvas(bmp);

                c.drawBitmap(bitmap, 0, 0, mPaint);

                mBitmapTemp =bmp;

                mHandler.sendEmptyMessage(MSG_FLAG_OK_WITH_NO_IMAGEVIEW);

            }
        });
    }

    public void displayImageAdjusted(final Bitmap bitmap,ImageView imageView,final ImageAdjustingListener listener){
        if (bitmap==null||imageView==null){
            return;
        }else{
            mImageView = imageView;
        }


        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                listener.onAdjustingStarted();

                if (mPaint==null){
                    mPaint = new Paint();
                }
                mPaint.setAntiAlias(true);
                setPaintColor();

                int width, height;
                height = bitmap.getHeight();
                width = bitmap.getWidth();

                Bitmap bmp = Bitmap.createBitmap(width, height,
                        Bitmap.Config.ARGB_8888);

                Canvas c = new Canvas(bmp);

                c.drawBitmap(bitmap, 0, 0, mPaint);

                mBitmapTemp =bmp;

                mHandler.sendEmptyMessage(MSG_FLAG_OK);

            }
        });
    }

    public void displayImageAdjusted(final Bitmap bitmap,ImageView imageView, final int progress, final ADJUSTER_TYPE type, final ImageAdjustingListener listener){
        L.e("displayImageAdjusted with imageView");
        if (bitmap==null||imageView==null){
            return;
        }else{
            mImageView = imageView;
        }
        if (null == mAllColorMatrix) {
            mAllColorMatrix= new ColorMatrix();
            mAllColorMatrix.reset();
        }
        if (listener !=null){
            this.listener = listener;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    listener.onAdjustingStarted();

                    if (mPaint==null){
                        mPaint = new Paint();
                    }
                    mPaint.setAntiAlias(true);

                    switch (type){
                        case BRIGHTNESS:
                            setBrightness(progress);
                            setPaintColor();
                            break;
                        case CONTRAST:
                            setContrast(progress);
                            setPaintColor();
                            break;
                        case SATURATION:
                            setSaturation(progress);
                            setPaintColor();
                            break;
                        case HUE:
                            setHue(progress);
                            setPaintColor();
                            break;
                        case RED_OFFSET:
                            setRedRate(progress);
                            setPaintColor();
                            break;
                        case GREEN_OFFSET:
                            setGreenRate(progress);
                            setPaintColor();
                            break;
                        case BLUE_OFFSET:
                            setBlueRate(progress);
                            setPaintColor();
                            break;
                        default:
                    }

                    int width, height;
                    height = bitmap.getHeight();
                    width = bitmap.getWidth();

                    Bitmap bmp = Bitmap.createBitmap(width, height,
                            Bitmap.Config.ARGB_8888);

                    Canvas c = new Canvas(bmp);

                    c.drawBitmap(bitmap, 0, 0, mPaint);

                    mBitmapTemp =bmp;

                    mHandler.sendEmptyMessage(MSG_FLAG_OK);

                }
            });
        }
    }

    private void setPaintColor(){
        mPaint.setColorFilter(
                ColorFilterGenetator.adjustColor(getBrightness(),getContrast(),getSaturation(),getHue(),getRedRate(),getGreenRate(),getBlueRate(),mAllColorMatrix)
        );

    }

    /**
     * save attribute to the bean
     * @param bean the bean to save the attribute
     */
    public void save(ColorAdjuster bean){
        bean.setBrightness(getBrightness());
        bean.setContrast(getContrast());
        bean.setSaturation(getSaturation());
        bean.setHue(getHue());
        bean.setRedRate(getRedRate());
        bean.setGreenRate(getGreenRate());
        bean.setBlueRate(getBlueRate());
    }

    /**
     * to get the attribute from the bean
     * @param bean bean store the attribute.
     */
    public void restore(ColorAdjuster bean){
        setBrightness(bean.getBrightness());
        setContrast(bean.getContrast());
        setSaturation(bean.getSaturation());
        setHue(bean.getHue());
        setRedRate(bean.getRedRate());
        setGreenRate(bean.getGreenRate());
        setBlueRate(bean.getBlueRate());
    }

    public int getBrightness() {
        return Brightness;
    }

    public void setBrightness(int brightness) {
        Brightness = brightness;
    }

    public int getContrast() {
        return Contrast;
    }

    public void setContrast(int contrast) {
        Contrast = contrast;
    }

    public int getSaturation() {
        return Saturation;
    }

    public void setSaturation(int saturation) {
        Saturation = saturation;
    }

    public int getHue() {
        return Hue;
    }

    public void setHue(int hue) {
        Hue = hue;
    }

    public int getRedRate() {
        return RedRate;
    }

    public void setRedRate(int redRate) {
        RedRate = redRate;
    }

    public int getGreenRate() {
        return GreenRate;
    }

    public void setGreenRate(int greenRate) {
        GreenRate = greenRate;
    }

    public int getBlueRate() {
        return BlueRate;
    }

    public void setBlueRate(int blueRate) {
        BlueRate = blueRate;
    }




    public interface ImageAdjustingListener{
        void onAdjustingStarted();
        void onAdjustingFailed();
        void onAdjustingComplete(View view,Bitmap result);
        void onAdjustingCancelled();

    }
    public enum ADJUSTER_TYPE{
         RED_OFFSET
        ,GREEN_OFFSET
        ,BLUE_OFFSET
        ,CONTRAST
        ,SATURATION
        ,BRIGHTNESS
        ,HUE
    }


    @Override
    public String toString() {
        return "Brightness:"+getBrightness()
                +";Contrast:"+getContrast()
                +";Saturation:"+getSaturation()
                +":R:"+getRedRate()
                +":G:"+getGreenRate()
                +";B:"+getBlueRate();
    }
}
