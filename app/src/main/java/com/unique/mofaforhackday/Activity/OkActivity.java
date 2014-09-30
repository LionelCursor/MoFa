package com.unique.mofaforhackday.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.unique.mofaforhackday.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by ldx on 2014/9/28.
 */
public class OkActivity extends Activity {
    private UMSocialService mController;
    private Bitmap mBitmapBlurBackground;
    private Bitmap mBitmapMain;

    private WallpaperManager wallpaperManager;
    private Handler mHandler;

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
                        mController.setShareMedia(new UMImage(getBaseContext(), "/sdcard/mofa/" + mBitmapMain.toString() + ".png"));
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
                    saveMyBitmap(mBitmapMain, mBitmapMain.toString());
            }
        }.start();


    }

    private void setUmeng(){

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.setShareContent("[来自mofa艺术]");
        mController.getConfig().removePlatform(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE);

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        qqSsoHandler.addToSocialSDK();

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        qZoneSsoHandler.addToSocialSDK();
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

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
                mController.openShare(OkActivity.this,false);
            }
        });
        (findViewById(R.id.ok_set_mobile_background)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            wallpaperManager = WallpaperManager.getInstance(OkActivity.this);
                            wallpaperManager.setBitmap(mBitmapMain);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    ;
                }.start();

                final ProgressDialog mProgressDialog = new ProgressDialog(
                        OkActivity.this);
                mProgressDialog.setIcon(R.drawable.ic_launcher);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                mProgressDialog.setContentView(R.layout.layout_progress);

                new Thread(new Runnable() {
                    public void run() {
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);// 告诉主线程执行任务
                        mProgressDialog.cancel();
                    }
                }).start();

            }
        });
        (findViewById(R.id.ok_main_page)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(OkActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });
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
}
