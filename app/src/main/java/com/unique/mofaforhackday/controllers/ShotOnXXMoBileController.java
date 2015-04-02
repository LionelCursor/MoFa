package com.unique.mofaforhackday.controllers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cursor.common.AppData;
import com.cursor.common.DisplayUtils;
import com.orhanobut.logger.Logger;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.view.photoview.PhotoViewAttacher;

import java.util.ResourceBundle;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;
import static android.widget.RelativeLayout.LayoutParams;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/30
 */
public class ShotOnXXMoBileController extends BaseController {
    /**
     * Bitmap want to be displayed
     */
    private Bitmap mBitmapRaw;


    /**
     * bitmap with frame
     */
    private Bitmap mBitmapWithFrame;
    /**
     * mImageView attached on
     */
    private ImageView mImageView;

    /**
     * get base message
     */
    private Context mContext;
    /**
     * px
     */
    private int mPadding = DisplayUtils.dip2px(7);

    /**
     * px
     */
    private int marginLeftAndRight = DisplayUtils.dip2px(30);

    //px
    private int marginTopAndBottom = DisplayUtils.dip2px(50);

    //px
    private int mBottomLayoutHeight = DisplayUtils.dip2px(50);


    private Paint mPaintBackground = new Paint();
    private int mColorBackground  = 0xfff9f9f9;
    private RelativeLayout mFrameRoot;

    /**
     * flag to show is the imageView was changed by this controller and easy to undo.
     */
    private boolean mIsAttached = false;


    /**
     * a controller of imageVIew
     */
    private PhotoViewAttacher attacher;

    /**
     * controller
     */
    public ShotOnXXMoBileController(Context context) {
        this.mContext = context;
    }


    /**
     * init the attach bitmap and imageView
     *
     * @param bitmap            param
     * @param imageViewAttacher param
     */
    public void attach(Bitmap bitmap, PhotoViewAttacher imageViewAttacher) {
        this.mBitmapRaw = bitmap;
        this.attacher = imageViewAttacher;
        this.mImageView = imageViewAttacher.getImageView();
        init();
    }

    void init(){
        mPaintBackground.setColor(mColorBackground);
    }


//    ImageView mImageViewFrameMain;
//    ImageView mImageViewSlogan;
//    ImageView mImageViewLogo;

    /**
     * add shot on xx mobile frame
     */
    public void attachShotFrame() {
        if (mIsAttached) return;

        Bitmap bitmap = generateBitmap();
        mImageView.setImageBitmap(bitmap);
        attacher.update();

//        wipeOutPhotoView();
//        BitmapDrawable d = (BitmapDrawable) mImageView.getDrawable();
//        changeImageViewContent();
//        mImageViewFrameMain = (ImageView) mFrameRoot.findViewById(R.id.frame_main_image);
//        mImageViewFrameMain.setImageDrawable(d);
//        mImageViewLogo      = (ImageView) mFrameRoot.findViewById(R.id.frame_logo);
//        mImageViewSlogan    = (ImageView) mFrameRoot.findViewById(R.id.frame_slogan);
//        adjustLayout(d);
        mIsAttached = true;
    }


    private Bitmap generateBitmap() {
        int width = mBitmapRaw.getWidth();
        int height = mBitmapRaw.getHeight();
        int maxWidth = DisplayUtils.getsFullScreenWidthInPixels() - marginLeftAndRight * 2 - mPadding * 2;
        int maxHeight = DisplayUtils.getsFullScreenHeightInPixels() - marginLeftAndRight * 2 - mPadding-mBottomLayoutHeight;
        float scaleRaw = ((float) width) / height;
        float scaleNew = ((float) maxWidth) / maxHeight;
        int newWidth = width;
        int newHeight = height;
        float ratio;
        if (width > maxWidth || height > maxHeight) {
            if (scaleRaw > scaleNew) {
                //scale by width
                ratio = maxWidth / ((float) width);

            } else {
                //scale by height
                ratio = maxHeight / ((float) height);
            }
            newWidth = (int) (ratio * width);
            newHeight = (int) (ratio * height);
        }

        Bitmap bitmap = Bitmap.createBitmap(newWidth+mPadding*2,newHeight+mPadding+mBottomLayoutHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(mColorBackground);
        Rect bitmapRect = new Rect(mPadding,mPadding,mPadding+newWidth,mPadding+newHeight);
        canvas.drawBitmap(mBitmapRaw,null,bitmapRect,null);
        Rect rect = new Rect(mPadding,mPadding+bitmapRect.height(),mPadding+ bitmapRect.width(),bitmap.getHeight());
        drawBottomLayout(canvas,rect,MB_TYPE.APPLE);
        return bitmap;
    }

    private void drawBottomLayout(Canvas canvas,Rect rect,MB_TYPE type){
        freshBottom(canvas,rect);
        Resources resources= mContext.getResources();
        Drawable logo = resources.getDrawable(type.type.logo);
        Drawable slogan = resources.getDrawable(type.type.slogan);
        Drawable byMofa = resources.getDrawable(R.drawable.by_mofa);

        logo.setBounds(
                rect.left,generateCenterTop(rect.height(),logo.getIntrinsicHeight(),rect),
                rect.left+logo.getIntrinsicWidth(),generateCenterTop(rect.height(),logo.getIntrinsicHeight(),rect) + logo.getIntrinsicHeight()
        );
        logo.draw(canvas);

        slogan.setBounds(
                generateCenterLeft(rect.width(),slogan.getIntrinsicWidth(),rect),
                generateCenterTop(rect.height(),slogan.getIntrinsicHeight(),rect),
                generateCenterLeft(rect.width(),slogan.getIntrinsicWidth(),rect)+slogan.getIntrinsicWidth(),
                generateCenterTop(rect.height(),slogan.getIntrinsicHeight(),rect)+ slogan.getIntrinsicHeight()
        );
        slogan.draw(canvas);

        byMofa.setBounds(
                rect.right-byMofa.getIntrinsicWidth(),
                generateCenterTop(rect.height(),byMofa.getIntrinsicHeight(),rect),
                rect.right,
                generateCenterTop(rect.height(),byMofa.getIntrinsicHeight(),rect)+byMofa.getIntrinsicHeight()
        );
        byMofa.draw(canvas);

    }

    private int generateCenterTop(int containerLong, int objectLong, Rect rect){
        return rect.top + (containerLong-objectLong)/2;
    }

    private int generateCenterLeft(int containerLong, int objectLong, Rect rect){
        return rect.left+ (containerLong- objectLong)/2;
    }

    private void freshBottom(Canvas canvas,Rect rect){
        canvas.drawRect(rect,mPaintBackground);
    }


    /**
     * delete shot on xx mobile frame
     * and all return past pattern
     */
    public void detachShotFrame() {
        attacher.setZoomable(true);
        mImageView.setImageBitmap(mBitmapRaw);
    }

    /**
     * Frame border
     *
     * @param padding border width in dip
     */
    public void setPadding(int padding) {
        mPadding = DisplayUtils.dip2px(padding);
    }

    public enum MB_TYPE {
        APPLE(
                R.drawable.apple,
                R.drawable.apple_slogan),

        HTC(
                R.drawable.htc,
                R.drawable.htc_slogan),

        HUAWEI(
                R.drawable.huawei,
                R.drawable.huawei_slogan),

        LG(
                R.drawable.lg,
                R.drawable.lg_slogan),

        MEIZU(
                R.drawable.meizu,
                R.drawable.meizu_slogan),

        MOTO(
                R.drawable.moto,
                R.drawable.moto_slogan),

        NOKIA(
                R.drawable.nokia,
                R.drawable.nokia_slogan),

        SAMSUNG(
                R.drawable.samsung,
                R.drawable.samsung_slogan),

        SONY(
                R.drawable.sony,
                R.drawable.sony_slogan),

        VIVO(
                R.drawable.vivo,
                R.drawable.vivo_slagon),

        XIAOMI(
                R.drawable.xiaomi,
                R.drawable.xiaomi_slogan);

        MB_TYPE(int ResId_1, int ResId_2) {
            type = new Mobile(ResId_1, ResId_2);
        }

        public Mobile getType() {
            return type;
        }

        Mobile type;
    }

    static class Mobile {
        int logo;
        int slogan;

        Mobile(int res1, int res2) {
            logo = res1;
            slogan = res2;
        }
    }
}
