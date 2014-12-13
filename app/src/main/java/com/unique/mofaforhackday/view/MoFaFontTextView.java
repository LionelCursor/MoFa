package com.unique.mofaforhackday.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ldx on 2014/12/13.
 */
public class MoFaFontTextView extends TextView {
    public MoFaFontTextView(Context context) {
        super(context);
    }

    public MoFaFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoFaFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(Typeface.createFromAsset(context.getAssets(),"font/zhunyuan.ttf"));
    }
}
