package com.unique.mofaforhackday.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.unique.mofaforhackday.Config;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Utils.DefaultFontInflator;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class Option extends BaseSwipeBackActivity implements View.OnClickListener {
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


        reflect();
    }

    /**
     * I want to change Activity's onPostCreate() to make all Activity's statusBar blue.
     */
    private void reflect(){

    }

    private void init() {
        optionOut = (ImageButton) findViewById(R.id.option_about_us);
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
            DefaultFontInflator.apply(this, viceText);
//            viceText.getPaint().setFakeBoldText(true);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(view, layout);
            viceText.setText("设置");
        }
    }

    private void addShare() {
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.setShareContent("用mofa处理的图片效果好棒哦～快来应用市场下载试试吧～～");
        mController.setAppWebSite("http://www.wandoujia.com/apps/com.unique.mofaforhackday");

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setTargetUrl(Config.SHARE_URL);
        qqShareContent.setAppWebSite(Config.SHARE_URL);
        qqShareContent.setTitle("[来自mofa艺术]");
        qqShareContent.setShareContent("用mofa处理的图片效果好棒哦～快来应用市场下载试试吧～～");
        qqSsoHandler.addToSocialSDK();
        mController.setShareMedia(qqShareContent);


        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, "1101518130",
                "t1kIusoT4DwBin6X");
        QZoneShareContent qZone = new QZoneShareContent();
        qZone.setTitle("[来自mofa艺术]");
        qZone.setTargetUrl(Config.SHARE_URL);
        qZone.setShareContent("用mofa处理的图片效果好棒哦～快来应用市场下载试试吧～～");
        qZoneSsoHandler.addToSocialSDK();
        mController.setShareMedia(qZone);

        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());

        String appId = "wx07deedae03518a47";
        String appSecret = "c5805cb9071fce9c4bbe55a80c15aece";
        //添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this,appId,appSecret);
        wxHandler.addToSocialSDK();

        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置title
        weixinContent.setTitle("[来自mofa艺术]");
        //设置分享内容跳转URL
        weixinContent.setTargetUrl(Config.SHARE_URL);
        //设置分享图片
        weixinContent.setShareContent("用mofa处理的图片效果好棒哦～快来应用市场下载试试吧～～");

        mController.setShareMedia(weixinContent);
        // 支持微信朋友圈

        UMWXHandler wxCircleHandler = new UMWXHandler(this,appId,appSecret);

        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //设置微信朋友圈分享内容
        CircleShareContent circleMedia = new CircleShareContent();
        //设置朋友圈title
        circleMedia.setTitle("[来自mofa艺术]");
//        circleMedia.setShareImage(umImage);
        circleMedia.setShareContent("用mofa处理的图片效果好棒哦～快来应用市场下载试试吧～～");
        circleMedia.setTargetUrl(Config.SHARE_URL);
        mController.setShareMedia(circleMedia);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
               case R.id.back:
                finish();
                overridePendingTransition(R.anim.ani_static,R.anim.out_to_right);
                break;
            case R.id.option_about_us:
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
            default:
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
