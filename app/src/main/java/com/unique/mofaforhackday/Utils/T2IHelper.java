package com.unique.mofaforhackday.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by ldx on 2014/9/21.
 * change TextView to ImageView
 */
public class T2IHelper {

    public static void Operate(TextView src ,ImageView target){

        Bitmap tempB;

        tempB = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(tempB);
        src.draw(c);

        ImageView iv = target;
        iv.setImageBitmap(tempB);
        L.e("imageView operatedd");
    }
}
