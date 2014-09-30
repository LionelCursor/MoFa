package com.unique.mofaforhackday.Fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.unique.mofaforhackday.Activity.ImageSelectedActivity;
import com.unique.mofaforhackday.MoFaApplication;
import com.unique.mofaforhackday.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ldx on 2014/8/25.
 */
public class ImageSelectedListFragment extends Fragment {
    private static final String TAG = "ImageSelectedListFragment";
    private boolean DEBUG = true;

    private static String KEY_TOTAL_NUMBER = "total";

    private ListView listView;

    private ArrayList<HashMap<String,Object>> dataList;
    private ArrayList<HashMap<String,Object>> SrcList;
    @SuppressLint("ValidFragment")
    public ImageSelectedListFragment() {
        this.dataList = ImageSelectedActivity.AlbumList;
        this.SrcList = ImageSelectedActivity.dataList;
        AddNumKey();
    }


    private void AddNumKey(){
        int total =0;
        for (HashMap<String,Object> map:dataList){
            for (HashMap<String,Object> srcMap:SrcList){
                if (srcMap.get(ImageSelectedActivity.KEY_BUCKET_ID).equals(map.get(ImageSelectedActivity.KEY_BUCKET_ID)))
                    total++;
                if (srcMap.equals(map))
                    break;
            }
            map.put(KEY_TOTAL_NUMBER,total);
            total =0;
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater,  final ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_image_selected_listview,null);

        listView = (ListView)rootView.findViewById(R.id.fragment_image_listView);

        BucketAdapterUIL adapter = new BucketAdapterUIL(inflater,dataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ImageItemClickListener());
        return rootView;
    }
    static ArrayList<HashMap<String,Object>> BucketDataList;
    public class ImageItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ArrayList<HashMap<String,Object>> Srclist  = ((ImageSelectedActivity)getActivity()).getAllDataList();
            BucketDataList = new ArrayList<HashMap<String, Object>>();
            Integer i = (Integer)dataList.get(position).get(ImageSelectedActivity.KEY_BUCKET_ID);
            for (HashMap<String,Object> map:Srclist){
                if (map.get(ImageSelectedActivity.KEY_BUCKET_ID ).equals(i)){
                    BucketDataList.add(map);
                }
            }
            ((ImageSelectedActivity)getActivity()).getmPager().setVisibility(View.GONE);
//            Fragment f = new ImageSelectedAlbumClickedFragment(BucketDataList);
            Fragment f= new ImageSelectedGridViewFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content,f).commit();

        }
    }

    /**
     * My Own Adapter to load images,but it's sometimes throw OOM
     * So I use UIL
     */
    public class BucketAdapterUIL extends BaseAdapter{
        private ArrayList<HashMap<String,Object>> list;

        private LayoutInflater inflater;
        ImageLoadingListener listener;

        public BucketAdapterUIL(LayoutInflater inflater, ArrayList<HashMap<String, Object>> list) {
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
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;

            if(convertView==null){
                convertView = inflater.inflate(R.layout.item_album_listview, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.item_album_listView_imageView);
                holder.title = (TextView)convertView.findViewById(R.id.item_album_listView_TextView);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.title.setText(dataList.get(position).get(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                    +"("
                    +dataList.get(position).get(KEY_TOTAL_NUMBER)
                    +")"
            );
//            holder.imageView.setTag(dataList.get(position).get(MediaStore.Images.Media.DATA));

            ImageLoader.getInstance().displayImage(
                    "file:///" + dataList.get(position).get(MediaStore.Images.Media.DATA)
                    ,holder.imageView
                    ,((MoFaApplication)getActivity().getApplication()).getOptions()
                    ,listener
                    );
            return convertView;
        }

        class ViewHolder{
            ImageView imageView;
            TextView title;
        }
    }


}
