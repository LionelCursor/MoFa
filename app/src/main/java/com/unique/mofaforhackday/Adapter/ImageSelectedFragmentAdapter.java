package com.unique.mofaforhackday.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.unique.mofaforhackday.Fragment.ImageSelectedListFragment;

import java.util.ArrayList;

/**
 * Created by ldx on 2014/8/27.
 */
public class ImageSelectedFragmentAdapter extends FragmentPagerAdapter{
    public ArrayList<Fragment> getList() {
        return list;
    }

    public void setList(ArrayList<Fragment> list) {
        this.list = list;
    }

    private ArrayList<Fragment> list;

    public ImageSelectedFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list){
        super(fm);
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
}
