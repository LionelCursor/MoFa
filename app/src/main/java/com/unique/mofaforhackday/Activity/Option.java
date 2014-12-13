package com.unique.mofaforhackday.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.unique.mofaforhackday.R;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class Option extends SwipeBackActivity implements View.OnClickListener {
    private ImageButton optionOut, optionFeedback, optionShare;
    private UMSocialService mController;

    private FeedbackAgent agent;
    private ImageButton back;

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
        setContentView(R.layout.activity_option);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }



        setActionBarLayout();
        init();
        addShare();
        optionOut.setOnClickListener(this);
        optionFeedback.setOnClickListener(this);
        optionShare.setOnClickListener(this);
        back.setOnClickListener(this);
    }
    private void init() {
        optionOut = (ImageButton) findViewById(R.id.option_back);
        optionFeedback = (ImageButton) findViewById(R.id.option_feedback);
        optionShare = (ImageButton) findViewById(R.id.option_share);
        agent = new FeedbackAgent(this);
    }

    public void setActionBarLayout() {
        ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater mInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(R.layout.vice_actionbar, null);
            back = (ImageButton) view.findViewById(R.id.back);
            TextView viceText = (TextView) view.findViewById(R.id.vice_text);
            viceText.getPaint().setFakeBoldText(true);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(view, layout);
            viceText.setText("设置");
        }
    }

    private void addShare() {
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.setShareContent("用mofa处理的图片效果好棒哦～快来应用市场下载试试吧～～   http://www.wandoujia.com/apps/com.unique.mofaforhackday");
        mController.getConfig().removePlatform(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE);

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        qqSsoHandler.addToSocialSDK();

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        qZoneSsoHandler.addToSocialSDK();
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
               case R.id.back:
                finish();
                overridePendingTransition(R.anim.ani_static,R.anim.out_to_right);
                break;
            case R.id.option_back:
                //imitatePressHome(this);
                startActivity(new Intent(this, AboutUsActivity.class));
                overridePendingTransition(R.anim.in_from_right,R.anim.ani_static);
                break;
            case R.id.option_feedback:
                agent.startFeedbackActivity();
                break;
            case R.id.option_share:
                mController.openShare(this, false);
                break;
        }
    }

    /**
     * 模拟按home键
     * 程序退到后台运行
     * @param context
     */
    private void imitatePressHome(Context context)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }
}
