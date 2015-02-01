package com.unique.mofaforhackday.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Utils.DefaultFontInflator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ldx on 2014/12/9.
 * @author ldx
 */
public class AboutUsActivity extends SwipeBackActivity {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_about_us);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        initActionBar();
        setList();
    }

    private void setList(){
        ListView list = (ListView) findViewById(R.id.list_about_us);
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                getData(),
                R.layout.about_us_list_item,
                new String[]{"image","text1","text2","text3"},
                new int[]{R.id.about_us_head_icon,R.id.text1, R.id.text2 , R.id.text3});
        list.setAdapter(adapter);
    }

    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>(4);
        Map<String,Object> mapApp = new HashMap<String, Object>();
        mapApp.put("image", R.drawable.mofa);
        mapApp.put("text1", "新浪微博：@mofa应用");
        mapApp.put("text2", "Email:aikenlovesea@gmail.com");
        mapApp.put("text3", "App");
        list.add(mapApp);
        Map<String,Object> mapChenGang = new HashMap<String, Object>();
        mapChenGang.put("image",R.drawable.cg);
        mapChenGang.put("text1","新浪微博：@设计师Jacob");
        mapChenGang.put("text2","Email:gangchen@hustunique.com");
        mapChenGang.put("text3","Designer");
        list.add(mapChenGang);
        Map<String,Object> mapLei = new HashMap<String, Object>();
        mapLei.put("image",R.drawable.lei);
        mapLei.put("text1","GitHub:LionelCursor/MoFa.git");
        mapLei.put("text2","Email:danxionglei@hustunique.com");
        mapLei.put("text3","Android");
        list.add(mapLei);
        Map<String,Object> mapLiuChange = new HashMap<String, Object>();
        mapLiuChange.put("image",R.drawable.chang);
        mapLiuChange.put("text1","新浪微博：@liuchangchang");
        mapLiuChange.put("text2","Email:changliu@hustunique.com");
        mapLiuChange.put("text3","Android");
        list.add(mapLiuChange);
        return list;
    }

    TextView viceText;
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
//            viceText.getPaint().setFakeBoldText(true);
            DefaultFontInflator.apply(this, viceText);
//            Button confirm = (Button) view.findViewById(R.id.confirm);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(view, layout);
            viceText.setText("我们");
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
