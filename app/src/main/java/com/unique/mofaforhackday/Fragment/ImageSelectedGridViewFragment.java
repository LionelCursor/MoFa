package com.unique.mofaforhackday.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.unique.mofaforhackday.Activity.HandleImageActivity;
import com.unique.mofaforhackday.Activity.ImageSelectedActivity;
import com.unique.mofaforhackday.MoFaApplication;
import com.unique.mofaforhackday.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ldx on 2014/8/25.
 */
public class ImageSelectedGridViewFragment extends Fragment {

    private static final boolean DEBUG = true;

    private GridView gridView;
    private ArrayList<HashMap<String, Object>> dataList;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            this.dataList = ImageSelectedListFragment.BucketDataList;
    }

    public GridView getGridView() {
        return gridView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.gc();
        View rootView = inflater.inflate(R.layout.fragment_image_selected_gridview, null);
        gridView = (GridView) rootView.findViewById(R.id.image_selected_gridView);
        GridViewAdapter adapter = new GridViewAdapter(inflater, dataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new MyOnClickListener());
        return rootView;
    }

    private class MyOnClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), HandleImageActivity.class);
//            intent.setType("ImageSelected");
            intent.putExtra(ImageSelectedActivity.INTENT_EXTRA_NAME_IMAGE_SELECTED, (String) dataList.get(position).get(ImageSelectedActivity.KEY_SRC_DATA_PATH));
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.ani_static);
            getActivity().finish();
        }
    }

    private class GridViewAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ArrayList<HashMap<String, Object>> list;
        ImageLoadingListener listener;

        private GridViewAdapter(LayoutInflater inflater, ArrayList<HashMap<String, Object>> list) {
            this.inflater = inflater;
            this.list = list;
            this.listener = new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    if (failReason.getType().equals(FailReason.FailType.OUT_OF_MEMORY)){
                        ImageLoader.getInstance().displayImage(imageUri,(ImageView)view,((MoFaApplication)getActivity().getApplication()).getOptions(),listener);
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            };
        }

        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_gridview, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.item_gridView_imageView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ImageLoader.getInstance().displayImage(
                    "file://" + list.get(position).get(ImageSelectedActivity.KEY_SRC_DATA_PATH)
                    , holder.imageView
                    , ((MoFaApplication) getActivity().getApplication()).getOptions()
                    , listener

            );
            return convertView;
        }


        @Override
        public void notifyDataSetInvalidated() {
            super.notifyDataSetInvalidated();
        }

        class ViewHolder {
            ImageView imageView;
        }
    }


}
