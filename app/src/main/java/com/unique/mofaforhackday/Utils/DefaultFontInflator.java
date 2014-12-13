package com.unique.mofaforhackday.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ldx on 2014/12/13.
 * transfer the typeface of textViews .
 */
public class DefaultFontInflator {
    public static void apply(TextView view, Typeface typeface) {
        view.setTypeface(typeface);
    }

    public static void apply(Context c, View... views) {
        for (View view : views) {
            if (view instanceof TextView) {
                apply((TextView) view, Typeface.createFromAsset(c.getAssets(), "font/zhunyuan.ttf"));
            }
        }
    }
    public static void applyRecursive(Context c,final View v)
    {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    applyRecursive(c,child);
                }
            } else if (v instanceof TextView) {
                apply(c,v);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }
}
