package com.unique.mofaforhackday.Activity;


import android.app.ActionBar;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.unique.mofaforhackday.R;

import org.w3c.dom.Text;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ImageSelectedDetailActivity extends SwipeBackActivity{
    TextView viceText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initActionBar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setTintColor(Color.parseColor("#4886ba"));
        }

    }

    private void initContentView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_image_selected_detail);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

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
            String folder = (String) getIntent().getCharSequenceExtra("folder");
            viceText.setText(folder);
            viceText.setEms(10);
            viceText.setGravity(Gravity.CENTER);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollToFinishActivity();
                }
            });
        }
    }

}
