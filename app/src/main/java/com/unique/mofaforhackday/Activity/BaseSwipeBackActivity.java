package com.unique.mofaforhackday.activity;

import com.umeng.analytics.MobclickAgent;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ldx on 2015/2/3.
 * added for Umeng static
 */
public class BaseSwipeBackActivity extends SwipeBackActivity {
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
