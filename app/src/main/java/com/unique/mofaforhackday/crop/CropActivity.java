package com.unique.mofaforhackday.crop;

import android.annotation.TargetApi;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unique.mofaforhackday.activity.BaseActivity;
import com.unique.mofaforhackday.activity.OkActivity;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.view.cropper.CropImageView;

import java.io.IOException;

public class CropActivity extends BaseActivity {
    public final String TAG = "CropActivity";
    private Bitmap raw_bitmap;
    private CropImageView cropImageView;
    private RelativeLayout mRoot;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_crop);
        mRoot = (RelativeLayout)findViewById(R.id.crop_activity_root);
        raw_bitmap = getBitmap(OkActivity.mainCache);
        cropImageView = createCropper(this,raw_bitmap);
        addViewOnRoot(mRoot, cropImageView, raw_bitmap.getWidth(), raw_bitmap.getHeight());
        setCropper(cropImageView,raw_bitmap,getIntent());
        SaveOnClick();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        break;
                }
            }
        };
    }

    private void SaveOnClick(){
        findViewById(R.id.crop_activity_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wpm = WallpaperManager.getInstance(CropActivity.this);
                try {
                    wpm.setBitmap(cropImageView.getCroppedImage());
                    Toast.makeText(CropActivity.this,"壁纸设置成功",Toast.LENGTH_SHORT).show();
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            overridePendingTransition(R.anim.ani_static,R.anim.out_to_right);
                        }
                    },700);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(CropActivity.this,"您的手机不支持此功能，请在gallery中手动设置",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.ani_static,R.anim.out_to_right);
    }

    /**
     * set bitmap and adjust the aspect of cropper
     * @param cropImageView cropper
     * @param source bitmap
     */
    private void setCropper(CropImageView cropImageView, Bitmap source, Intent intent){
        Point scaledPoint = ScaleForScreen(source.getWidth(),source.getHeight());
        cropImageView.setImageBitmap(source);
        int aspectX = intent.getIntExtra(CropExtras.KEY_ASPECT_X,scaledPoint.x);
        int aspectY = intent.getIntExtra(CropExtras.KEY_ASPECT_Y,scaledPoint.y);
        cropImageView.setAspectRatio(aspectX, aspectY);
        cropImageView.setFixedAspectRatio(true);
        cropImageView.setShadowOverlay(getResources().getColor(R.color.translucent_setWallPaper_cropper_shadow_overlay));
    }

    /**
     * add view on center of root layout
     * @param root root layout
     * @param Child cropper here
     * @param width bitmap advised width
     * @param height bitmap advised height
     */
    private void addViewOnRoot(ViewGroup root,View Child,int width,int height){
        Point scaledPoint = ScaleForScreen(width,height);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(scaledPoint.x,scaledPoint.y);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        lp.setMargins(0,0,0,0);
        root.addView(Child,0,lp);
    }

    private Point ScaleForScreen(int width,int height){
        Point displaySize = getDefaultDisplaySize(new Point());
        int displayWidth = displaySize.x;
        int displayHeight = displaySize.y;
        float bitmapRatio = width/(float)height;
        float displayRatio = displayWidth/(float)displayHeight;
        if (width>displayWidth||height>displayHeight){
            if (bitmapRatio>displayRatio){
                float scale = displayWidth/(float) width;
                height *= scale;
                return new Point(displayWidth,height);
            }else{
                float scale = displayHeight/(float) height;
                width *= scale;
                return new Point(width,displayHeight);
            }
        }
        return new Point(width,height);
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private Point getDefaultDisplaySize(Point size) {
        Display d = getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            d.getSize(size);
        } else {
            size.set(d.getWidth(), d.getHeight());
        }
        return size;
    }

    private CropImageView createCropper(Context context,Bitmap bitmap){
        return new CropImageView(context);
    }
    private static Bitmap getBitmap(Bitmap b){
        return b;
    }

}
