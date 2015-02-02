package com.unique.mofaforhackday.Activity;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.view.photoview.PhotoViewAttacher;

import java.io.IOException;
import java.util.List;

public class SetWallpaperActivity extends Activity {
    private Bitmap wallpaper;
    private ImageView image;
    private RelativeLayout button_ok;
    private PhotoViewAttacher attacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_set_wallpaper);
        getWallpaperBitmap();
        button_ok = (RelativeLayout) findViewById(R.id.wallpaper_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Cursor",attacher.getmBaseMatrix().toShortString());
                Log.e("Cursor",attacher.getDrawMatrix().toShortString());
                Log.e("Cursor","width"+wallpaper.getWidth());
                Log.e("Cursor","height"+wallpaper.getHeight());
                setWallPaper(null);
            }
        });
        image = (ImageView) findViewById(R.id.set_wallpaper);
        image.setImageBitmap(wallpaper);
        attacher = new PhotoViewAttacher(image);
        attacher.setZoomable(true);
        attacher.setMediumScale(1.0000f);
        attacher.setMaximumScale(1.0000f);
        attacher.setMinimumScale(1.0000f);
        attacher.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void getWallpaperBitmap(){
        wallpaper = OkActivity.mainCache;
        OkActivity.mainCache = null;
    }

    private void setWallPaper(Bitmap b){
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Log.e("Cursor","min height:"+wallpaperManager.getDesiredMinimumHeight()+"");
        Log.e("Cursor","min width:"+wallpaperManager.getDesiredMinimumWidth());

//        try {
//            wallpaperManager.setBitmap(b);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static final String ACTION_CROP_AND_SET_WALLPAPER =
        "android.service.wallpaper.CROP_AND_SET_WALLPAPER";

    /**
     * Gets an Intent that will launch an activity that crops the given
     * image and sets the device's wallpaper. If there is a default HOME activity
     * that supports cropping wallpapers, it will be preferred as the default.
     * Use this method instead of directly creating a {@link #ACTION_CROP_AND_SET_WALLPAPER}
     * intent.
     *
     * @param imageUri The image URI that will be set in the intent. The must be a content
     *                 URI and its provider must resolve its type to "image/*"
     *
     * @throws IllegalArgumentException if the URI is not a content URI or its MIME type is
     *         not "image/*"
     */
    public Intent getCropAndSetWallpaperIntent(Uri imageUri) {
        if (!ContentResolver.SCHEME_CONTENT.equals(imageUri.getScheme())) {
            throw new IllegalArgumentException("Image URI must be of the "
                    + ContentResolver.SCHEME_CONTENT + " scheme type");
        }

        final PackageManager packageManager = getBaseContext().getPackageManager();
        Intent cropAndSetWallpaperIntent =
                new Intent(ACTION_CROP_AND_SET_WALLPAPER, imageUri);
        cropAndSetWallpaperIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Find out if the default HOME activity supports CROP_AND_SET_WALLPAPER
        Intent homeIntent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolvedHome = packageManager.resolveActivity(homeIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (resolvedHome != null) {
            cropAndSetWallpaperIntent.setPackage(resolvedHome.activityInfo.packageName);

            List<ResolveInfo> cropAppList = packageManager.queryIntentActivities(
                    cropAndSetWallpaperIntent, 0);
            if (cropAppList.size() > 0) {
                return cropAndSetWallpaperIntent;
            }
        }

        // fallback crop activity
        cropAndSetWallpaperIntent.setPackage("com.android.wallpapercropper");
        List<ResolveInfo> cropAppList = packageManager.queryIntentActivities(
                cropAndSetWallpaperIntent, 0);
        if (cropAppList.size() > 0) {
            return cropAndSetWallpaperIntent;
        }
        // If the URI is not of the right type, or for some reason the system wallpaper
        // cropper doesn't exist, return null
        throw new IllegalArgumentException("Cannot use passed URI to set wallpaper; " +
            "check that the type returned by ContentProvider matches image/*");
    }
}
