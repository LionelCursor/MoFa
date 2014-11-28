package com.unique.mofaforhackday.Activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.unique.mofaforhackday.R;


public class Option extends Activity implements View.OnClickListener {
    private ImageButton optionOut, optionFeedback, optionShare;
    private UMSocialService mController;

    private FeedbackAgent agent;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_option);
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
//            Button confirm = (Button) view.findViewById(R.id.confirm);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(view, layout);
            viceText.setText("设置");
//            confirm.setVisibility(View.GONE);
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
                break;
            case R.id.option_back:
                System.exit(0);
                break;
            case R.id.option_feedback:
                agent.startFeedbackActivity();
                break;
            case R.id.option_share:
                mController.openShare(this, false);
                break;
        }
    }
}
