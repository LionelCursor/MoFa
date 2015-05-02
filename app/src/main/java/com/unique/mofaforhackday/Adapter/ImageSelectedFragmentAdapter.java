package com.unique.mofaforhackday.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.unique.mofaforhackday.R;

import java.util.ArrayList;

/**
 * Created by ldx on 2014/8/27.
 */
public class ImageSelectedFragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public ArrayList<Fragment> getList() {
        return list;
    }

    public void setList(ArrayList<Fragment> list) {
        this.list = list;
    }

    private ArrayList<Fragment> list;

    public ImageSelectedFragmentAdapter(FragmentManager fm, Context mContext, ArrayList<Fragment> list){
        super(fm);
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return mContext.getResources().getString(R.string.image_selected_Recommended);
        }else if (position ==1){
            return mContext.getResources().getString(R.string.image_selected_Album);
        }else{
            return "";
        }
    }
}
