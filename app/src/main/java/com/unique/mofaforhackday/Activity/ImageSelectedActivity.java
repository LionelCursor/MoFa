package com.unique.mofaforhackday.Activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.unique.mofaforhackday.Adapter.ImageSelectedFragmentAdapter;
import com.unique.mofaforhackday.Config;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Fragment.*;
import com.unique.mofaforhackday.Utils.L;
import com.unique.mofaforhackday.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ldx on 2014/8/25.
 * To Select Photo in Phone.There are two indicator for User, which are MoFa and Album.
 * In MoFa , GridView
 * In Album , First ListView ,After Click GridView. When you need to go back to ListView ,press KeyEvent.KEYCODE_BACK
 */
public class ImageSelectedActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, ActionBar.TabListener {

    private static final String TAG = "ImageSelectedActivity";
    private static final boolean DEBUG = true;

    /**
     * HashMap of Image has 6 Key:
     */
    public static String KEY_BUCKET_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
    public static String KEY_BUCKET_ID = MediaStore.Images.Media.BUCKET_ID;
    public static String KEY_SRC_NAME = MediaStore.Images.Media.DISPLAY_NAME;
    //    public static String KEY_SRC_ID = MediaStore.Images.Media._ID;
    public static String KEY_SRC_DATA_PATH = MediaStore.Images.Media.DATA;
//    public static String KEY_THUMBNAIL_DATA_PATH = "Thumbnails_data_path";

    public static String MOFA_BUCKET_NAME = "mofa";

    public static String INTENT_EXTRA_NAME_IMAGE_SELECTED = "ImageSelected";

    private ViewPager mPager;
    private int keyCode;
    private KeyEvent event;
    private TextView viceText;

    public ArrayList<Fragment> getFragmentList() {
        return fragmentList;
    }

    private ArrayList<Fragment> fragmentList;

    //    public static final int INDEX_MOFA = 0;
    public static final int INDEX_RECOMMENDED = 0;
    public static final int INDEX_ALBUM = 1;
    public static final int INDEX_ALBUM_CLICKED = 2;


    public ArrayList<HashMap<String, Object>> getAllDataList() {
        return dataList;
    }

    static public ArrayList<HashMap<String, Object>> dataList;
    static public ArrayList<HashMap<String, Object>> AlbumList;
    private ArrayList<HashMap<String, Object>> mofaList;
    static public ArrayList<HashMap<String, Object>> recommendedList;

    public static int DATA_STATE_UNFINISHED = 0;
    public static int DATA_STATE_FINISHED = 1;
    private ContentResolver cr = null;

    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            , MediaStore.Images.Media.BUCKET_ID
            , MediaStore.Images.Media.DISPLAY_NAME
//            , MediaStore.Images.Media._ID
            , MediaStore.Images.Media.DATA
    };


    public int getData_state() {
        return data_state;
    }

    public void setData_state(int data_state) {
        this.data_state = data_state;
    }

    private int data_state = DATA_STATE_UNFINISHED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView();

//        initViewPager();
        initActionBar();

        initData();
    }
    private void initActionBar(){
        ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(R.layout.vice_actionbar, null);
            ImageButton back = (ImageButton) view.findViewById(R.id.back);
            viceText = (TextView) view.findViewById(R.id.vice_text);
            viceText.getPaint().setFakeBoldText(true);
//            Button confirm = (Button) view.findViewById(R.id.confirm);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(view, layout);
            viceText.setText(R.string.image_selected);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
//            confirm.setVisibility(View.GONE);
        }
//        initTab();
    }

    private class BackClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (mPager.getVisibility() == View.GONE) {
                returnClickedAlbum();
            }
        }
    }

    private void initData() {
        dataList = new ArrayList<HashMap<String, Object>>();
        AlbumList = new ArrayList<HashMap<String, Object>>();
//        mofaList = new ArrayList<HashMap<String, Object>>();
        recommendedList = new ArrayList<HashMap<String, Object>>();
        setRecommendedList();
        getLoaderManager().initLoader(0, null, this);
    }

    private void setRecommendedList() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_1);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_2);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_3);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_4);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_5);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_6);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_7);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_8);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_9);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_10);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_11);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_12);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_13);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_14);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_15);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_16);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_17);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_18);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_19);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_20);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_21);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_22);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_23);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_24);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_25);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_26);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_27);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_28);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_29);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_30);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_31);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_32);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_33);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_34);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_35);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_36);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_37);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_38);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_39);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_40);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_41);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_42);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_43);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_44);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_45);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_46);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_47);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_48);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_49);
        recommendedList.add(map);
        map = new HashMap<String, Object>();
//        map.put(KEY_SRC_DATA_PATH, Config.IMAGE_50);
//        recommendedList.add(map);
    }

    private void initContentView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setTintColor(Color.parseColor("#4886ba"));
        }
        setContentView(R.layout.activity_image_selector);
    }

    public ViewPager getmPager() {
        return mPager;
    }

    private void initViewPager() {
        mPager = (ViewPager) findViewById(R.id.image_selected_viewpager);
        fragmentList = new ArrayList<Fragment>();
//        fragmentList.add(INDEX_MOFA, new ImageSelectedMoFaFragment(mofaList));
        fragmentList.add(INDEX_RECOMMENDED, new ImageSelectedRecommendedFragment(/*recommendedList*/));
        fragmentList.add(INDEX_ALBUM, new ImageSelectedListFragment(/*AlbumList,dataList*/));
        //给ViewPager设置适配器
        mPager.setAdapter(new ImageSelectedFragmentAdapter(getSupportFragmentManager(), this, fragmentList));
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页


        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setTextColor(0xFFFFFFFF);
        tabs.setDividerColor(0xFFFFFFFF);
        tabs.setIndicatorColor(0xFFFFFFFF);
        tabs.setTextSize(45);
        tabs.setIndicatorHeight(10);
        tabs.setDividerColor(0x00000000);
        tabs.setShouldExpand(true);
        tabs.setViewPager(mPager);
    }


    public void returnClickedAlbum() {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().getFragments().get(2)).commit();
        mPager.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mPager.getVisibility() == View.GONE) {
                returnClickedAlbum();
            }
            RelativeLayout relativeLayout  =(RelativeLayout)findViewById(R.id.relativeLayout_dialog_fullscreen);
            if (relativeLayout.getVisibility()== View.VISIBLE){
                relativeLayout.setVisibility(View.GONE);
            }
            return true;
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,
                null,
                null,
                null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        new ImageDataAsyncTask(this).execute(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        data_state = DATA_STATE_UNFINISHED;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        int c = Integer.valueOf((String) tab.getTag());
        if (mPager != null) {
            switch (c) {
                case 0:
                    if (mPager.getVisibility() == View.GONE)
                        returnClickedAlbum();
                    mPager.setCurrentItem(0);
                    break;
                case 1:
                    if (mPager.getVisibility() == View.GONE)
                        returnClickedAlbum();
                    mPager.setCurrentItem(1);
                    break;
                default:
            }
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        int c = Integer.valueOf((String) tab.getTag());
        if (mPager != null) {
            switch (c) {
                case 0:
                    if (mPager.getVisibility() == View.GONE)
                        returnClickedAlbum();
                    mPager.setCurrentItem(0);
                    break;
                case 1:
                    if (mPager.getVisibility() == View.GONE)
                        returnClickedAlbum();
                    mPager.setCurrentItem(1);
                    break;
                default:
            }
        }
    }

    private class MyOnPageChangListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i2) {

        }

        @Override
        public void onPageSelected(int i) {
            if (getActionBar() != null)
                getActionBar().setSelectedNavigationItem(i);
            L.e(TAG, "Page." + i + " " + "was selected.");
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    private class ImageDataAsyncTask extends AsyncTask<Cursor, Object, Object> {

        Context context;

        public ImageDataAsyncTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Cursor[] params) {
            Cursor data = params[0];
            String bucket_display_name;
            int bucket_id;
            String display_name;
//            int _id;
            String path;

            HashMap<String, Object> map;
            /**
             * this index is used to ensure if AlbumList have the bucket map
             * and AlbumList.set(index,object);
             */
            int index;

            int bucket_display_nameColumn = data.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            int bucket_idColumn = data.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
            int display_nameColumn = data.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
//            int _idColumn = data.getColumnIndex(MediaStore.Images.Media._ID);
            int dataColumn = data.getColumnIndex(MediaStore.Images.Media.DATA);
            int con = 0;

            if (data.moveToLast()) {
                dataList.clear();
                AlbumList.clear();
//                mofaList.clear();
                do {
//                    _id = data.getInt(_idColumn);
                    path = data.getString(dataColumn);
                    display_name = data.getString(display_nameColumn);
                    bucket_display_name = data.getString(bucket_display_nameColumn);
                    bucket_id = data.getInt(bucket_idColumn);

                    map = new HashMap<String, Object>();
                    map.put(MediaStore.Images.Media.DATA, path);
                    map.put(MediaStore.Images.Media.DISPLAY_NAME, display_name);
                    map.put(MediaStore.Images.Media.BUCKET_ID, bucket_id);
                    map.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, bucket_display_name);
                    dataList.add(map);

//                    if (bucket_display_name.equals(MOFA_BUCKET_NAME)) {
//                        mofaList.add(map);
//                    }

                    if ((index = haveMapValue(AlbumList, bucket_id)) < 0) {
                        AlbumList.add(map);
                    } else {
                        AlbumList.set(index, map);
                    }

                } while (data.moveToPrevious());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            initViewPager();
            data_state = DATA_STATE_FINISHED;
        }

        private int haveMapValue(ArrayList<HashMap<String, Object>> list, Object o) {
            int size = list.size();
            for (int index = 0; index < size; index++) {
                if (list.get(index).containsValue(o)) {
                    return index;
                }
            }
            return -1;
        }


        private void addSQLThumnails(Context context, ArrayList<HashMap<String, Object>> srcList) {

            cr = getContentResolver();


            String[] projection = {MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                    MediaStore.Images.Thumbnails.DATA};
            StringBuilder selection = new StringBuilder(MediaStore.Images.Thumbnails.IMAGE_ID + " IN (");
            for (HashMap<String, Object> map : srcList) {
                selection.append(map.get(MediaStore.Images.Media._ID));
                selection.append(",");
            }
            selection.deleteCharAt(selection.length() - 1);
            selection.append(")");

            Cursor cur = cr.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, projection, selection.toString(), null, null);
            if (DEBUG) Log.i(TAG, selection.toString());
            if (DEBUG) Log.i(TAG, MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI + "");

            if (cur.moveToFirst()) {
//                int _id;
                int image_id;
                String image_path;
//                int _idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails._ID);
                int image_idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
                int dataColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

                int index;
                do {
                    // Get the field values
//                    _id = cur.getInt(_idColumn);
                    image_id = cur.getInt(image_idColumn);
                    image_path = cur.getString(dataColumn);

                    // Do something with the values.
                    index = haveMapValue(srcList, image_id);
//                    TempList.remove(index);
//                    srcList.get(index).put(KEY_THUMBNAIL_DATA_PATH,image_path);
                } while (cur.moveToNext());


                if (DEBUG) Log.e(TAG, "AddThumbnailFinished");
                if (DEBUG) Log.i("Lei", cur.getCount() + "");
            }
        }
    }
}
