package com.unique.mofaforhackday;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by ldx on 2014/8/28.
 *
 */
public class MoFaApplication extends Application{
    ImageLoaderConfiguration config;

    public DisplayImageOptions getOptions() {
        return options;
    }

    DisplayImageOptions options;
    @Override
    public void onCreate() {
        super.onCreate();

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
