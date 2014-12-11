package com.unique.mofaforhackday.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.unique.mofaforhackday.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ldx on 2014/12/9.
 * @author ldx
 */
public class AboutUsActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initActionBar();
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
            viceText.getPaint().setFakeBoldText(true);
//            Button confirm = (Button) view.findViewById(R.id.confirm);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(view, layout);
            viceText.setText("About us");
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
