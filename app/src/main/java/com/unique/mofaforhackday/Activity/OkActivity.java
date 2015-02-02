package com.unique.mofaforhackday.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.unique.mofaforhackday.Config;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.crop.CropActivity;
import com.unique.mofaforhackday.crop.CropExtras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * Created by ldx on 2014/9/28.
 * Screen after MainActivity
 */
public class OkActivity extends Activity {
    private UMSocialService mController;
    private Bitmap mBitmapBlurBackground;
    private Bitmap mBitmapMain;

    public static Bitmap mainCache;
    public static Bitmap blurCache;

    private WallpaperManager wallpaperManager;
    private Handler mHandler;
    private Uri SavedUri;


    /**
     * the format of saved photo's name
     * with no .png
     */
    public String SAVE_NAME_MOFA = "mofa_%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_ok);
        getStaticBitmap();
        setBackground();
        setMain();
        setStuffButton();
        setUmeng();
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(getApplicationContext(), "壁纸设置成功",
                                Toast.LENGTH_LONG).show();
                        break;
                    case 22:
                        Toast.makeText(getApplicationContext(),
                                "成功保存到" + "mofa",
                                Toast.LENGTH_SHORT).show();
                        mController.setShareMedia(new UMImage(getBaseContext(), "/sdcard/mofa/" + getSavedPhotoName() + ".png"));
                        break;
                    case 33:
                        Toast.makeText(getApplicationContext(),
                                "保存出错了",
                                Toast.LENGTH_SHORT).show();

                        break;
                    default:
                }
                super.handleMessage(msg);
            }
        };

        // 新线程
        new Thread() {
            public void run() {
                if (mBitmapMain == null)
                    return;
                else
                    saveMyBitmap(mBitmapMain, getSavedPhotoName());
            }
        }.start();
    }
    private String wrapPNG(String nameWithNoPHG){
        return nameWithNoPHG + ".png";
    }
    private void writeInContentProvider(String path){
        ContentValues values = new ContentValues();
        ContentResolver resolver = getBaseContext().getContentResolver();
        values.put(MediaStore.Images.ImageColumns.DATA,path);
        values.put(MediaStore.Images.ImageColumns.TITLE,wrapPNG(getSavedPhotoName()));
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME,wrapPNG(getSavedPhotoName()));
        long time = getTime();
        values.put(MediaStore.Images.ImageColumns.DATE_ADDED,time);
        values.put(MediaStore.Images.ImageColumns.DATE_TAKEN,time*1000);
        values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED,time);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE,"image/png");

        SavedUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    //can be used
    private void scanFile(String path){
        MediaScannerConnection.scanFile(
                getApplicationContext(),
                new String[]{path},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Toast.makeText(OkActivity.this, "DEBUG-This picture was added", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
    private String getSavedPhotoPath(){
        return  "/sdcard/mofa/"+ wrapPNG(getSavedPhotoName());
    }
    private String getSavedPhotoName(){
        String[] strings = mBitmapMain.toString().split("@");
        return String.format(SAVE_NAME_MOFA,strings[strings.length-1]);
    }
    private void setUmeng(){
        UMImage umImage = new UMImage(this,mBitmapMain);
        Log.LOG = true;
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.setShareContent("[来自mofa艺术]");
        mController.setShareImage(umImage);
        mController.setAppWebSite(Config.SHARE_URL);

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        qqSsoHandler.addToSocialSDK();

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareImage(umImage);
        qqShareContent.setTargetUrl(Config.SHARE_URL);
        qqShareContent.setAppWebSite(Config.SHARE_URL);
        qqShareContent.setTitle("[来自mofa艺术]");
        mController.setShareMedia(qqShareContent);

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        qZoneSsoHandler.addToSocialSDK();

        QZoneShareContent qZone = new QZoneShareContent();
        qZone.setTitle("[来自mofa艺术]");
        qZone.setShareImage(umImage);
        qZone.setTargetUrl(Config.SHARE_URL);
        qZone.setShareContent("Art is long, and time is fleeting. ");
        mController.setShareMedia(qZone);
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig() .setSsoHandler(new TencentWBSsoHandler());


        String appId = "wx07deedae03518a47";
        String appSecret = "c5805cb9071fce9c4bbe55a80c15aece";
//        添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this,appId,appSecret);
        wxHandler.addToSocialSDK();

        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置title
        weixinContent.setTitle("[来自mofa艺术]");
        //设置分享内容跳转URL
        weixinContent.setTargetUrl(Config.SHARE_URL);
        //设置分享图片
        weixinContent.setShareImage(umImage);
        mController.setShareMedia(weixinContent);
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this,appId,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        //设置朋友圈title
        circleMedia.setTitle("[来自mofa艺术]");
        circleMedia.setShareImage(umImage);
        circleMedia.setTargetUrl(Config.SHARE_URL);
        mController.setShareMedia(circleMedia);
    }

    // 保存到SD卡
    private void saveMyBitmap(Bitmap bitmap, String bitName) {
        File cache = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/mofa/");
        if (!cache.exists()) {
            cache.mkdirs();
        }
        File f = new File("/sdcard/mofa/" + bitName + ".png");

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 1, fOut);// 把100调低
            fOut.flush();
            fOut.close();
            //Here write in content Provider
            writeInContentProvider(getSavedPhotoPath());
            mHandler.sendEmptyMessage(22);
        } catch (FileNotFoundException e) {
            mHandler.sendEmptyMessage(33);
            e.printStackTrace();
        } catch (IOException e) {
            mHandler.sendEmptyMessage(33);
            e.printStackTrace();
        }
    }
    private void setStuffButton() {
        (findViewById(R.id.ok_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.openShare(OkActivity.this, false);
            }
        });
        (findViewById(R.id.ok_set_mobile_background)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentCropAndSetWallpaper();
                //解决方法二：自己写
//                Intent intent = new Intent(OkActivity.this,SetWallpaperActivity.class);
//                mainCache = mBitmapMain;
//                new Thread() {
//                    public void run() {
//                        try {
//                            wallpaperManager = WallpaperManager.getInstance(OkActivity.this);
//                            wallpaperManager.setBitmap(mBitmapMain);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }


//                }.start();
            }
        });
        (findViewById(R.id.ok_main_page)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressWarnings("deprecation")
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
    private static final String IMAGE_TYPE = "image/*";
    private void IntentCropAndSetWallpaper(){
        Intent cropAndSetWallpaperIntent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WallpaperManager wpm = WallpaperManager.getInstance(getApplicationContext());
                    try {
                        cropAndSetWallpaperIntent = wpm.getCropAndSetWallpaperIntent(SavedUri);
                        startActivity(cropAndSetWallpaperIntent);
//                        finish();
                        return;
                    } catch (ActivityNotFoundException anfe) {
                        // ignored; fallthru to existing crop activity
                    } catch (IllegalArgumentException iae) {
                        // ignored; fallthru to existing crop activity
                    }
                }else{

                int width = getWallpaperDesiredMinimumWidth();
                int height = getWallpaperDesiredMinimumHeight();
                Point size = getDefaultDisplaySize(new Point());
                float spotlightX = (float) size.x / width;
                float spotlightY = (float) size.y / height;
                cropAndSetWallpaperIntent = new Intent(CropExtras.CROP_ACTION)
                        .setClass(this,CropActivity.class)
                        .setDataAndType(SavedUri, IMAGE_TYPE)
                        .addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
                        .putExtra(CropExtras.KEY_OUTPUT_X, width)
                        .putExtra(CropExtras.KEY_OUTPUT_Y, height)
                        .putExtra(CropExtras.KEY_ASPECT_X, width)
                        .putExtra(CropExtras.KEY_ASPECT_Y, height)
                        .putExtra(CropExtras.KEY_SPOTLIGHT_X, spotlightX)
                        .putExtra(CropExtras.KEY_SPOTLIGHT_Y, spotlightY)
                        .putExtra(CropExtras.KEY_SCALE, true)
                        .putExtra(CropExtras.KEY_SCALE_UP_IF_NEEDED, true)
                        .putExtra(CropExtras.KEY_RETURN_DATA,true)
                        .putExtra(CropExtras.KEY_SHOW_WHEN_LOCKED,true)
                        .putExtra(CropExtras.KEY_SET_AS_WALLPAPER, true);
                    mainCache = mBitmapMain;
                    try {
                        startActivity(cropAndSetWallpaperIntent );
                        overridePendingTransition(R.anim.in_from_right,R.anim.ani_static);
//                finish();
                    }catch (Exception e){
                        e.printStackTrace();

                    }
    }}

    private long getTime(){
        return System.currentTimeMillis()/1000;
    }
    private void setBackground() {
        ((ImageView) (findViewById(R.id.ok_blur))).setImageBitmap(mBitmapBlurBackground);
    }

    private void setMain() {
        ImageView imageView = (ImageView) findViewById(R.id.ok_main_imageView);
        imageView.setImageBitmap(mBitmapMain);
    }

    private void getStaticBitmap() {
        mBitmapMain = HandleImageActivity.sBitmapMain;
        mBitmapBlurBackground = HandleImageActivity.sBitmapBlur;
        resetStatic();
    }

    private void resetStatic() {
        HandleImageActivity.sBitmapBlur = null;
        HandleImageActivity.sBitmapMain = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode) ;
        if(ssoHandler != null){
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
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
