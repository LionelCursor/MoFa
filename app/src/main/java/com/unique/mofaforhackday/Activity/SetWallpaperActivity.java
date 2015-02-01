package com.unique.mofaforhackday.Activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.view.photoview.PhotoViewAttacher;

public class SetWallpaperActivity extends Activity {
    private Bitmap wallpaper;
    private ImageView image;
    private Button button;
    private PhotoViewAttacher attacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);
        getWallpaperBitmap();
        image = (ImageView) findViewById(R.id.set_wallpaper);

        button = (Button) findViewById(R.id.set_wallpaper_ok);
        image.setImageBitmap(wallpaper);
        attacher = new PhotoViewAttacher(image);
        attacher.setZoomable(false);
    }

    private void getWallpaperBitmap(){
        wallpaper = OkActivity.mainCache;
        OkActivity.mainCache = null;
    }

}
