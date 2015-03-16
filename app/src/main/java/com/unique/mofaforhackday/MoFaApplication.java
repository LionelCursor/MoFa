package com.unique.mofaforhackday;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.cursor.common.CommonApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.unique.mofaforhackday.Utils.MoFaFileUtils;

import java.io.File;

/**
 * Created by ldx on 2014/8/28.
 *
 */
public class MoFaApplication extends CommonApplication{
    ImageLoaderConfiguration config;
    /**
     * indicating whether the app was launched at first time
     * and will be applied to {@code false} when the MainActivity finished
     */
    boolean firstIn = false;

    public DisplayImageOptions getOptions() {
        return options;
    }

    DisplayImageOptions options;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = getSharedPreferences(getString(R.string.action_settings),MODE_PRIVATE);
        firstIn = sp.getBoolean(getString(R.string.FIRST_IN),true);
        File file = new File(Config.SDCARD_MOFA);
        if (firstIn){
//            MoFaFileUtils.delRecommendedFiles();
//            file.mkdirs();
        }
        if (!file.exists()){
            file.mkdirs();
        }

        config = new ImageLoaderConfiguration.Builder(this)
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .denyCacheImageMultipleSizesInMemory()
//                .threadPriority()
//                .threadPoolSize()
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().clearMemoryCache();
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.drawable.image_default)
                .showImageForEmptyUri(R.drawable.image_default)
                .showImageOnFail(R.drawable.image_default)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//                .memoryCache()
                .cacheInMemory(true)
                .considerExifParams(true)
//                .resetViewBeforeLoading(true)
//                .displayer(new FadeInBitmapDisplayer(100))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

}
