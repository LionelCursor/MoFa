package com.unique.mofaforhackday.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.unique.mofaforhackday.Config;
import com.unique.mofaforhackday.MoFaApplication;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Utils.DefaultFontInflator;
import com.unique.mofaforhackday.activity.HandleImageActivity;
import com.unique.mofaforhackday.activity.ImageSelectedActivity;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ldx on 2014/9/29.
 */
public class ImageSelectedRecommendedFragment extends Fragment {

    private static final boolean DEBUG = true;

    private GridView gridView;
    private ArrayList<HashMap<String, Object>> dataList;

    @SuppressLint("ValidFragment")
    public ImageSelectedRecommendedFragment(ArrayList<HashMap<String, Object>> list) {
        this.dataList = list;
    }

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList = ImageSelectedActivity.recommendedList;
        sharedPreferences = getActivity().getSharedPreferences(Config.PREFERENCE_NAME_IMAGE, Context.MODE_PRIVATE);
    }

    public ImageSelectedRecommendedFragment() {
        this.dataList = new ArrayList<HashMap<String, Object>>();
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
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            String name = handleString((String) dataList.get(position).get(ImageSelectedActivity.KEY_SRC_DATA_PATH));
            boolean hasFile = hasFile(Config.SDCARD_MOFA + name);
            if (!hasFile||!isFile(Config.SDCARD_MOFA + name)) {
                if (!hasFile(Config.SDCARD_MOFA)) {
                    File f = new File(Config.SDCARD_MOFA);
                    f.mkdirs();
                }
                if (ImageSelectedActivity.isNetworkConnected(getActivity())) {
                    if (ImageSelectedActivity.isWifi(getActivity())) {
                        DownLoadImageFilesWithIon((String) dataList.get(position).get(ImageSelectedActivity.KEY_SRC_DATA_PATH));
                    } else {
                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                        RelativeLayout layout = (RelativeLayout) inflater
                                .inflate(R.layout.layout_dialog, null);
                        DefaultFontInflator.applyRecursive(getActivity(), layout);
                        final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
                        dialog.show();
                        dialog.getWindow().setContentView(layout);
                        ((TextView) layout.findViewById(R.id.dialog_text)).setText("现在不是WiFi状态，真下载吗？");
                        // 取消按钮
                        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        // 确定按钮
                        Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
                        btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DownLoadImageFilesWithIon((String) dataList.get(position).get(ImageSelectedActivity.KEY_SRC_DATA_PATH));
                                dialog.dismiss();
                            }
                        });

                    }
                } else {
                    Toast.makeText(getActivity(), "美美的图片联网就可以下载哦~亲~~", Toast.LENGTH_SHORT).show();
                }
            } else {
                Intent intent = new Intent(getActivity(), HandleImageActivity.class);
                intent.putExtra(ImageSelectedActivity.INTENT_EXTRA_NAME_IMAGE_SELECTED, name);
                intent.putExtra("network", true);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.ani_static);
            }
        }
    }

    private boolean hasFile(String url) {
        try {
            File f = new File(url);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private boolean isFile(String url) {
        try {
            File f = new File(url);
            if (!f.exists() || !f.isFile()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public void DownLoadImageFilesWithIon(String url) {
        showDialog();
        showTempImage(url);
        final TextView textView = ((TextView) getActivity().findViewById(R.id.download_image_textView));
        final String name = handleString(url);
        url = Config.url + name;
        Log.e("Cursor", "ImageSelectedRecommendedFragment:" + url);
        Ion.with(this)
                .load(url)
                .progressBar((ProgressBar) getActivity().findViewById(R.id.download_image_progress_bar))
                .progressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long l, long l2) {
                        double percent = ((double) l) / l2;
                        NumberFormat format = NumberFormat.getInstance();
                        format.setMaximumFractionDigits(2);
                        textView.setText("" + format.format(percent * 100) + "%");
                    }
                })
                .write(new File(Config.SDCARD_MOFA + name))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        Intent intent = new Intent(getActivity(), HandleImageActivity.class);
                        intent.putExtra(ImageSelectedActivity.INTENT_EXTRA_NAME_IMAGE_SELECTED, name);
                        intent.putExtra("network", true);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
    }

    private void showTempImage(String url) {
        ImageView imageView = getRoundedImage();
        ImageLoader.getInstance().
                displayImage(
                        url
                        , imageView
                        , ((MoFaApplication) getActivity().getApplication()).getOptions()
                );
    }

    private ImageView getRoundedImage() {
        return (ImageView) getActivity().findViewById(R.id.rounded_imageView);
    }

    //handle url to have exact name
    public static String handleString(String url) {
        StringBuilder stringBuilder = new StringBuilder(url);
        return stringBuilder.substring(19);
    }

    private void dismissDialog() {
        RelativeLayout dialog = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout_dialog_fullscreen);
        dialog.setVisibility(View.GONE);
    }


    private void showDialog() {
        RelativeLayout dialog = (RelativeLayout) getActivity().findViewById(R.id.relativeLayout_dialog_fullscreen);
        dialog.setVisibility(View.VISIBLE);
        dialog.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
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
                    if (failReason.getType().equals(FailReason.FailType.OUT_OF_MEMORY)) {
                        ImageLoader.getInstance().displayImage(imageUri, (ImageView) view, ((MoFaApplication) getActivity().getApplication()).getOptions(), listener);
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
                    "" + list.get(position).get(ImageSelectedActivity.KEY_SRC_DATA_PATH)
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
