package com.unique.mofaforhackday.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.umeng.fb.FeedbackAgent;
import com.unique.mofaforhackday.R;

/**
 * Created by ldx at 2014/8/
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final boolean DEBUG = true;

    private static final int LOAD_IMAGE = 1;

    private ImageButton mImageButtonPic;
    private ImageButton mImageButtonCam;
    private ImageButton mImageButtonWord;
    private ImageButton mImageButtonSet;
    private ImageButton mImageButtonFeedback;

    private FeedbackAgent agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //full screen Content View
        initContentView();

        findView();

        startCam();
        feedBack();
        imageSelected();
    }

    private void initContentView(){
        // NO_TITLE go away the title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_main);
    }

    private void findView(){
        mImageButtonCam = (ImageButton)findViewById(R.id.imageButton_cam);
        mImageButtonFeedback = (ImageButton) findViewById(R.id.imageButton_feeback);
        mImageButtonWord = (ImageButton)findViewById(R.id.imageButton_word);
        mImageButtonSet = (ImageButton)findViewById(R.id.imageButton_set);
        mImageButtonPic = (ImageButton)findViewById(R.id.imageButton_pic);
    }




    //init the Camera
    private void startCam(){
        mImageButtonCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), LOAD_IMAGE);
            }
        });
    }

    //Umeng
    private void feedBack(){
        agent = new FeedbackAgent(MainActivity.this);
        agent.sync();

        mImageButtonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) Log.e(TAG,"FeedBack is clicked");
                agent.startFeedbackActivity();
            }
        });
    }

    private void imageSelected(){
        mImageButtonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ImageSelectedActivity.class));
                //Main activity is not finished.
            }
        });
    }

    //use Media Camera to load a photo.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode ==Activity.RESULT_OK){
            switch (requestCode){
                case LOAD_IMAGE:
                    break;
                default:
            }
        }
    }
}
