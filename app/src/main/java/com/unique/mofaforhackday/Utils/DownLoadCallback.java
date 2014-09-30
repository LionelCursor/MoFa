package com.unique.mofaforhackday.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.unique.mofaforhackday.Activity.HandleImageActivity;
import com.unique.mofaforhackday.R;

import java.io.File;
import java.util.concurrent.FutureTask;

/**
 * Created by ldx on 2014/9/28.
 */
public class DownLoadCallback implements FutureCallback<File> {
    private Context mContext;
    private String mString;
    public DownLoadCallback(Context c,String name){
        mContext = c;
        mString = name;
    }
    @Override
    public void onCompleted(Exception e, File file) {
        if (e != null) {
            Toast.makeText(mContext, "下载失败了哎~", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(mContext, "下载成功了呐~", Toast.LENGTH_SHORT).show();
        final File f = mContext.getFileStreamPath(mString);
        Typeface typeface = Typeface.createFromFile(f);
    }
}
