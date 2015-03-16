package com.unique.mofaforhackday.ui;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cursor.common.DisplayUtils;
import com.cursor.common.logger.Logger;
import com.unique.mofaforhackday.activity.HandleImageActivity;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.view.MoFaTextView;
import com.unique.mofaforhackday.view.interpolator.EaseInOutQuintInterpolator;
import com.unique.mofaforhackday.view.interpolator.easeOutElasticInterpolator;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * USER: ldx
 * EMAIL: danxionglei@foxmail.com
 * PROJECT_NAME: mofaforhackday
 * DATE: 2015/3/11
 */
public class DoubleClickDeleteGuideHandler extends Handler {
    @InjectView(R.id.guide_in_handle_first_text_indicator)
    ImageView mIndicator;
    @InjectView(R.id.guide_in_handle_first_text_text)
    ImageView mImageText;
    @InjectView(R.id.guide_in_handle_first_text_ok)
    ImageButton mImageButtonOk;

    public void handle(HandleImageActivity activity, final MoFaTextView moFaTextView) {
        super.handle(activity);
        final View view = activity.findViewById(R.id.guide_first_text);
        view.setVisibility(View.VISIBLE);
        ButterKnife.inject(view, activity);
//        moFaTextView = activity.getMoFaTextViwReference();
        float targetBottom = moFaTextView.getY() + moFaTextView.getHeight();
        Logger.e("Y:" + moFaTextView.getY());
        Logger.e("getHeight:" + moFaTextView.getHeight());
        int height = DisplayUtils.getsFullScreenHeightInPixels();

        //Indicator
        mIndicator = (ImageView) view.findViewById(R.id.guide_in_handle_first_text_indicator);
        mIndicator.setY(height + 100);
        mIndicator.setVisibility(View.VISIBLE);
        mIndicator.animate().y(targetBottom + DisplayUtils.dip2px(10)).
//                setInterpolator(new easeOutElasticInterpolator()).
        start();

        //mImageText
        mImageText = (ImageView) activity.findViewById(R.id.guide_in_handle_first_text_text);
        mImageText.setY(height + 100);
        mImageText.setVisibility(View.VISIBLE);
        mImageText.animate().y(targetBottom + DisplayUtils.dip2px(40)).
//                setInterpolator(new EaseInOutQuintInterpolator()).
        start();

        //mImageButton
        mImageButtonOk = (ImageButton) activity.findViewById(R.id.guide_in_handle_first_text_ok);
        mImageButtonOk.setY(height + 100);
        mImageButtonOk.setVisibility(View.VISIBLE);
        mImageButtonOk.animate().y(height - DisplayUtils.dip2px(100)).
//                .setInterpolator(new EaseInOutQuintInterpolator())
        start();


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        mImageButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
    }
}
