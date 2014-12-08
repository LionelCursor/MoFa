package com.unique.mofaforhackday.Fragment;


import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by ldx on 2014/8/25.
 */
public class ImageSelectedAlbumClickedFragment extends ImageSelectedGridViewFragment {


    GestureDetector mGestureDetector;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        ImageLoader.getInstance().clearMemoryCache();
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }


}
