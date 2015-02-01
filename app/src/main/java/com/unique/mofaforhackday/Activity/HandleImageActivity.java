package com.unique.mofaforhackday.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.unique.mofaforhackday.Config;
import com.unique.mofaforhackday.R;
import com.unique.mofaforhackday.Utils.DefaultFontInflator;
import com.unique.mofaforhackday.Utils.ImageAdjuster;
import com.unique.mofaforhackday.Utils.L;
import com.unique.mofaforhackday.beans.ColorAdjuster;
import com.unique.mofaforhackday.beans.Modifitation;
import com.unique.mofaforhackday.view.MoFaSeekBar;
import com.unique.mofaforhackday.view.MoFaSlidingDrawer;
import com.unique.mofaforhackday.view.MoFaTextButton;
import com.unique.mofaforhackday.view.MoFaTextView;
import com.unique.mofaforhackday.view.SwitchButton;
import com.unique.mofaforhackday.view.cropper.CropImageView;
import com.unique.mofaforhackday.view.photoview.PhotoViewAttacher;

import java.io.File;
import java.util.ArrayList;

import static com.unique.mofaforhackday.Utils.ImageAdjuster.ADJUSTER_TYPE;

/**
 * Created by ldx on 2014/8/29.
 * main Activity to operate image
 */
public class HandleImageActivity extends Activity {
    private final static String TAG = "HandleImageActivity";
    private ImageView mMainImageView;
    private PhotoViewAttacher attacher;
    private Bitmap mSrcBitmap;
    private Bitmap mOperatingBitmap;
    private Bitmap bitmapStepNow;
    private ImageLoadingListener ImageLoadinglistener;
    /**
     * if the bitmap is grayed or is being grayed.
     */
    private boolean gray = false;
    private AsyncTask<Bitmap, Void, Bitmap> quseThread;
    private int SUM_STEP_SAVE = 0;
    private int NOW_STEP_DISPLAY = 0;
    private ArrayList<Modifitation> mStepMemory = null;

    private ImageButton abandon;
    private ImageButton save;
    private ImageButton imageButtonForward;
    private ImageButton imageButtonBackward;


    /**
     * The WrapSlidingDrawer global views                              *
     */
    private Modifitation mModifitation = null;

    private MoFaSlidingDrawer wrapSlidingDrawer;
    /**
     * Main Work Station
     * A relativeLayout contains handledImage and BlurredBackground
     */
    private RelativeLayout mainLayout;
    private MoFaTextView textView;
    private ArrayList<MoFaTextView> TextList;
    private static int SHADOWCOLOR = 0x3f000000;
    private float mShadowRadius, mShadowX, mShadowY;
    SharedPreferences sharedPreferences;

    ImageView background;
    Bitmap mBitmapBlur;
    static Bitmap sBitmapBlur;
    static Bitmap sBitmapMain;

    private ImageButton wenziButton;
    private ImageButton filterButton;
    private ImageButton GaussButton;
    private ImageButton AdjustButton;
    private ImageButton xuhuaButton;
    private ImageButton editButton;

    ImageButton word_imageButton_color;
    ImageButton word_imageButton_font;
    ImageButton word_imageButton_shadow;
    ImageButton word_imageButton_textsize;
    ImageButton word_imageButton_tmd;
    ImageButton word_imageButton_hor_ver;
    ImageButton word_imageButton_move;
    ImageButton word_imageButton_rotate;


    private ImageButton font01Button;
    private ImageButton font02Button;
    private ImageButton font03Button;
    private ImageButton font04Button;
    private ImageButton font05Button;
    private ImageButton font06Button;
    private ImageButton font07Button;
    private ImageButton font08Button;
    private ImageButton font09Button;
    private ImageButton font10Button;
    private ImageButton font11Button;
    private ImageButton font12Button;
    private ImageButton font13Button;
    private ImageButton font14Button;
    private ImageButton font15Button;
    private ImageButton font16Button;
    private ImageButton font17Button;
    private ImageButton font18Button;
    private ImageButton font19Button;
    private ImageButton font20Button;
    private ImageButton font21Button;
    private ImageButton font22Button;
    private ImageButton font23Button;
    private ImageButton font24Button;
    private ImageButton font25Button;
    private ImageButton font26Button;
    private ImageButton font27Button;
    private ImageButton font28Button;
    private ImageButton font29Button;
    private ImageButton font30Button;
    private RelativeLayout morenButton;
    private RelativeLayout miaowuButton;
    private RelativeLayout daofengButton;
    private RelativeLayout shangheiButton;
    private RelativeLayout yueheiButton;
    /**
     * 顶级Layout
     */
    private RelativeLayout rBlurDetailLayout;
    private RelativeLayout rEditTextLayout;
    private RelativeLayout rFilterDetailLayout;
    private RelativeLayout rAdjustDetailLayout;
    private RelativeLayout rXuhuaDetailLayout;
    private RelativeLayout rBianjiDetailLayout;
    /**
     * EditText Details
     */
    private EditText mEditTextInWord;
    private RelativeLayout rFontDetailLayout;
    private RelativeLayout rTextSizeDetailLayout;
    private RelativeLayout rTouMingDetailLayout;
    private HorizontalScrollView rColorDetailLayout;
    private LinearLayout rShadowDetailLayout;
    private HorizontalScrollView rEnglishFontTypeLayout;
    private HorizontalScrollView rChineseFontTypeLayout;
    /**
     * Adjust Detail layout
     */
    private MoFaSeekBar SaturationseekBar = null;
    private MoFaSeekBar BrightnessseekBar = null;
    private MoFaSeekBar ContrastseekBar = null;
    private MoFaSeekBar BlueSeekBar = null;
    private MoFaSeekBar GreenSeekBar = null;
    private MoFaSeekBar RedSeekBar = null;
    private MoFaSeekBar RuihuaSeekBar = null;
    private MoFaSeekBar shadowSeekBar = null;
    private MoFaSeekBar wenheduSeekbar = null;
    private LinearLayout mAdjustDetailRGBLayout = null;
    private RelativeLayout mAdjustDetailBrightnessLayout;
    private RelativeLayout mAdjustDetailContrastLayout;
    private RelativeLayout mAdjustDetailSaturationLayout;

    MoFaTextButton ButtonBrightness;
    MoFaTextButton ButtonContrast;
    MoFaTextButton ButtonSaturation;
    MoFaTextButton ButtonRGB;
//    private LinearLayout mAdjustDetailTiaoseLayout = null;
//    private LinearLayout mAdjustDetailDetailLayout = null;
//    private ImageButton TiaoseButton = null;
//    private ImageButton RGBButton = null;
//    private ImageButton DetailButton = null;
    private AdjustSeekBarChangeListener adjustseekbarChangeListener = null;
    /**
     * Blur
     */
    private int progress_blur;
    //threadPool to make thread in an List.
    private MoFaSeekBar BlurSeekBar;

    /**
     * Bianji
     */
    private HorizontalScrollView scrollViewCrop;
    private HorizontalScrollView scrollViewRotate;
    private CropImageView cropImageView;
    private float mRotateDegree;
    private float mRotateXDegree;
    private float mRotateYDegree;

    private Matrix mDrawMatrix;
    private Matrix mDisplayMatrix;
    private boolean mCropping= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.gc();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
        setContentView(R.layout.activity_handle_image);

        CreateSrcBitmap();
        SaveAndAbandonClickListener();
        makeStep();
        initMain();
        initTextView();
        setWrapSlidingMenu();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void makeStep() {
        mStepMemory = new ArrayList<Modifitation>();
        mModifitation = new Modifitation();
        mStepMemory.clear();
        imageButtonForward = ((ImageButton) findViewById(R.id.forward));
        imageButtonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e("NOW:" + NOW_STEP_DISPLAY + ";" + "SUM:" + SUM_STEP_SAVE);
                if (NOW_STEP_DISPLAY < SUM_STEP_SAVE) {
                    imageButtonForward.setClickable(false);
                    final Modifitation modifitation = mStepMemory.get(NOW_STEP_DISPLAY);

                    switch (modifitation.getType()) {
                        case ADJUST:
                            ColorAdjuster bean = modifitation.getColorAdjusterBean();
                            ImageAdjuster.getInstance().restore(bean);
                            ImageAdjuster.getInstance()
                                    .displayImageAdjusted(
                                            mOperatingBitmap
                                            , new ImageAdjuster.ImageAdjustingListener() {
                                                @Override
                                                public void onAdjustingStarted() {

                                                }

                                                @Override
                                                public void onAdjustingFailed() {

                                                }

                                                @Override
                                                public void onAdjustingComplete(View view, Bitmap result) {
                                                    mOperatingBitmap = result;

                                                    NOW_STEP_DISPLAY++;
                                                    imageButtonForward.setClickable(true);

                                                    mMainImageView.setImageBitmap(mOperatingBitmap);
                                                }

                                                @Override
                                                public void onAdjustingCancelled() {

                                                }
                                            });
                            break;
                        case BLUR:
                            BlurAsyncTask task = new BlurAsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                    BlurSeekBar.setProgress(modifitation.getBlurProgress());
                                }

                                @Override
                                protected void onPostExecute(Bitmap bitmap) {
                                    mOperatingBitmap = bitmap;
                                    NOW_STEP_DISPLAY++;
                                    imageButtonForward.setClickable(true);
                                    mMainImageView.setImageBitmap(mOperatingBitmap);
                                }
                            };
                            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, mOperatingBitmap);
                            break;
                        case WORD:
                            break;
                        default:
                    }
                }
            }
        });

        imageButtonBackward = ((ImageButton) findViewById(R.id.backward));
        imageButtonBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButtonBackward.setClickable(false);

                if (NOW_STEP_DISPLAY == 0) {
                    mOperatingBitmap = mSrcBitmap;

                    mMainImageView.setImageBitmap(mOperatingBitmap);
                    imageButtonBackward.setClickable(true);
                    allTopLayoutGONE();
                    return;
                }


                for (int i = 0; i < NOW_STEP_DISPLAY; i++) {
                    if (0 == i) {
                        mOperatingBitmap = mSrcBitmap;

                        if (i == NOW_STEP_DISPLAY - 1) {
                            mMainImageView.setImageBitmap(mOperatingBitmap);
                            imageButtonBackward.setClickable(true);
                            allTopLayoutGONE();
                            if (NOW_STEP_DISPLAY > 0) {
                                NOW_STEP_DISPLAY--;
                            }
                        }

                    } else {
                        final Modifitation modifitation = mStepMemory.get(i - 1);

                        switch (modifitation.getType()) {
                            case ADJUST:
                                ColorAdjuster bean = modifitation.getColorAdjusterBean();
                                ImageAdjuster.getInstance().restore(bean);
                                final int finalI1 = i;
                                ImageAdjuster.getInstance()
                                        .displayImageAdjusted(
                                                mOperatingBitmap
                                                , new ImageAdjuster.ImageAdjustingListener() {
                                                    @Override
                                                    public void onAdjustingStarted() {
                                                    }

                                                    @Override
                                                    public void onAdjustingFailed() {
                                                    }

                                                    @Override
                                                    public void onAdjustingComplete(View view, Bitmap result) {
                                                        mOperatingBitmap = result;
                                                        //SoGaSNie
                                                        //Thread error ,number is changed.
                                                        if (finalI1 == NOW_STEP_DISPLAY - 1) {
                                                            mMainImageView.setImageBitmap(mOperatingBitmap);
                                                            imageButtonBackward.setClickable(true);
                                                            if (NOW_STEP_DISPLAY > 0) {
                                                                NOW_STEP_DISPLAY--;
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onAdjustingCancelled() {

                                                    }
                                                });
                                break;
                            case BLUR:
                                final int finalI = i;
                                BlurAsyncTask task = new BlurAsyncTask() {
                                    @Override
                                    protected void onPreExecute() {
                                        super.onPreExecute();
                                        BlurSeekBar.setProgress(modifitation.getBlurProgress());
                                    }

                                    @Override
                                    protected void onPostExecute(Bitmap bitmap) {
                                        mOperatingBitmap = bitmap;
                                        if (finalI == NOW_STEP_DISPLAY - 1) {
                                            mMainImageView.setImageBitmap(mOperatingBitmap);
                                            imageButtonBackward.setClickable(true);
                                            if (NOW_STEP_DISPLAY > 0) {
                                                NOW_STEP_DISPLAY--;
                                                L.e("NOW:" + NOW_STEP_DISPLAY + ";" + "SUM:" + SUM_STEP_SAVE);
                                            }
                                        }
                                    }
                                };
                                task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, mOperatingBitmap);
                                break;
                            case WORD:
                                break;
                            default:
                        }
                    }
                }
            }
        });
    }

    private void initMain() {
        mainLayout = (RelativeLayout) findViewById(R.id.main_image);
        mainLayout.post(new Runnable() {
            @Override
            public void run() {
                mainLayout.postInvalidate();
            }
        });
    }

    private void setWrapSlidingMenu() {
        initMenuSelf();

        setButtonGroup();

        setWordDetail();

        setQuseDetailButton();
        setBlurDetail();
        setAdjustDetail();
        setBianjiDetail();
    }

    private void setWordDetail() {
        //TODO-forward and backward no wrote   WORD
        setWordCtrlLayout();

        editTextEnsureButton();

        setColorDetailButton();
        setFontDetailButton();
        setTextSizeDetail();
        setTextAlpha();
        setShadowDetail();
        setWordRotateDetail();

        setAlignHorOrVerDetailButton();

        setMoveDetail();
    }

    private void setWordRotateDetail() {
        final MoFaSeekBar rotateSeekBar = ((MoFaSeekBar) findViewById(R.id.seekBar_word_rotate));
        ImageButton minus = (ImageButton) findViewById(R.id.text_rotate_counterclockwise);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateSeekBar.getProgress() < 10) {
                    rotateSeekBar.setProgress(0);
                }else{
                    rotateSeekBar.setProgress(rotateSeekBar.getProgress() - 10);
                }
            }
        });

        ImageButton plus = (ImageButton) findViewById(R.id.text_rotate_clockwise);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateSeekBar.getProgress() > 244) {
                    rotateSeekBar.setProgress(254);
                }else{
                    rotateSeekBar.setProgress(rotateSeekBar.getProgress() + 10);
                }
            }
        });
        rotateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (textView == null) {
                    return;
                }
                textView.setRotate((progress - 127) / 127f * 180);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setMoveDetail() {
        findViewById(R.id.imageButton_move_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView == null) {
                    return;
                }
                textView.setTranslation(-7, 0);
            }
        });

        findViewById(R.id.imageButton_move_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView == null) {
                    return;
                }
                textView.setTranslation(7, 0);
            }
        });
        findViewById(R.id.imageButton_move_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView == null) {
                    return;
                }
                textView.setTranslation(0, -7);
            }
        });

        findViewById(R.id.imageButton_move_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView == null) {
                    return;
                }
                textView.setTranslation(0, 7);
            }
        });
    }

    private void setShadowDetail() {
        MoFaSeekBar SeekBarX = (MoFaSeekBar) findViewById(R.id.seekbar_word_shadow_X);
        MoFaSeekBar SeekBarY = (MoFaSeekBar) findViewById(R.id.seekbar_word_shadow_Y);
        MoFaSeekBar SeekBarSize = (MoFaSeekBar) findViewById(R.id.seekbar_word_shadow_Size);
        SeekBarX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mShadowX = progress / 127f * 10;
                setShadow();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        SeekBarY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mShadowY = progress / 127f * 10;
                setShadow();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        SeekBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mShadowRadius = progress / 127f * 9.99f + 0.01f;
                setShadow();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setShadow() {
        if (textView == null) {
            return;
        }
        textView.setShadowLayer(mShadowRadius, mShadowX, mShadowY, 0x5f000000);

    }

    private void editTextEnsureButton() {
        mEditTextInWord = (EditText) findViewById(R.id.adding_word_edit_text);

        findViewById(R.id.button_word_ensure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO-button_word_ensure_onClick
                final String s = mEditTextInWord.getText().toString();
                if (textView == null) {
                    textView = new MoFaTextView(HandleImageActivity.this);
                    textView.setOnFocusedListener(mMoFaTextViewOnFocusedListener);
                    textView.setAlpha(0);
                    mainLayout.addView(textView);
                    textView.setDismissWhenFocusOnTouchOutside();
                }
                textView.setMoFaText(s);
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.SelfCenter();
                    }
                });
            }
        });

        findViewById(R.id.button_ensure_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO- ensure the word
                //TODO- ListView to save reference of MoFaTextView
                //TODO- bug- TextView can't center itself
                // The logic is hard really
                final String s = mEditTextInWord.getText().toString();
                if (textView == null) {
                    textView = new MoFaTextView(HandleImageActivity.this);
                    textView.setOnFocusedListener(mMoFaTextViewOnFocusedListener);
                    textView.setAlpha(0);
                    mainLayout.addView(textView);
                    textView.setDismissWhenFocusOnTouchOutside();
                    textView.setMoFaText(s);
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.SelfCenter();
                        }
                    });
                } else {
                    textView = textView.copy();
                    mainLayout.addView(textView);
                    textView.setDismissWhenFocusOnTouchOutside();
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.CopyAnim();
                        }
                    });
                }
            }
        });
    }

    private void setAdjustDetail() {
        setAdjustCtrlLayout();
        adjustseekbarChangeListener = new AdjustSeekBarChangeListener();
        setTiaoseSeekBar();
        setRGBSeekBar();
        setDetailSeekBar();
    }

    private void setBianjiDetail() {
        cropImageView = (CropImageView) findViewById(R.id.crop_image_view);

        setBianjiCtrlLayout();

        setBianjiEnsureButton();
        setCropDetail();
        setRotateDetail();
    }

    private void setBianjiEnsureButton() {
        findViewById(R.id.bianji_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO-bug - bianji ensure logic
                mOperatingBitmap = cropImageView.getCroppedImage();
                mMainImageView.setImageBitmap(mOperatingBitmap);
                wrapSlidingDrawer.toggle();
                undoFCropperTPhotoView();
            }
        });
        findViewById(R.id.bianji_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allTopLayoutGONE();
                undoFCropperTPhotoView();

            }
        });
    }



    private void setRotateDetail() {
        findViewById(R.id.button_bianji_left_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRotate(-90);
            }
        });

        findViewById(R.id.button_bianji_right_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRotate(90);
            }
        });

        findViewById(R.id.button_bianji_hor_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.setRotationY(mRotateYDegree += 180);
            }
        });

        findViewById(R.id.button_bianji_ver_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropImageView.setRotationX(mRotateXDegree += 180);
            }
        });
    }

    private void setRotate(float delta) {
        cropImageView.rotateImage((int) delta);
    }

    private void setCropDetail() {
        CropDetailOnClickListener listener = new CropDetailOnClickListener();
        findViewById(R.id.Button_free).setOnClickListener(listener);
        findViewById(R.id.Button_1_to_1).setOnClickListener(listener);
        findViewById(R.id.Button_3_to_4).setOnClickListener(listener);
        findViewById(R.id.Button_4_to_6).setOnClickListener(listener);
        findViewById(R.id.Button_5_to_7).setOnClickListener(listener);
        findViewById(R.id.Button_8_to_10).setOnClickListener(listener);
        findViewById(R.id.Button_9_to_16).setOnClickListener(listener);
    }

    private class CropDetailOnClickListener implements View.OnClickListener {
        int ratioX, ratioY;

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.Button_free:
                    cropImageView.setFixedAspectRatio(false);
                    return;
                case R.id.Button_1_to_1:
                    ratioX = 1;
                    ratioY = 1;
                    break;
                case R.id.Button_3_to_4:
                    ratioX = 3;
                    ratioY = 4;
                    break;
                case R.id.Button_4_to_6:
                    ratioX = 4;
                    ratioY = 6;
                    break;
                case R.id.Button_5_to_7:
                    ratioX = 5;
                    ratioY = 7;
                    break;
                case R.id.Button_8_to_10:
                    ratioX = 8;
                    ratioY = 10;
                    break;
                case R.id.Button_9_to_16:
                    ratioX = 9;
                    ratioY = 16;
                    break;
                default:
            }
            cropImageView.setAspectRatio(ratioX, ratioY);
            cropImageView.setFixedAspectRatio(true);
        }
    }

    private void setDetailSeekBar() {
//        RuihuaSeekBar = (MoFaSeekBar) findViewById(R.id.ruihua_seekbar);
//        shadowSeekBar = (MoFaSeekBar) findViewById(R.id.shadow_seekbar);
//        wenheduSeekbar = (MoFaSeekBar) findViewById(R.id.wenhedu_seekbar);
//        RuihuaSeekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
//        shadowSeekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
//        wenheduSeekbar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
    }

    private void setTiaoseSeekBar() {
        BrightnessseekBar = (MoFaSeekBar) findViewById(R.id.brightness_seekbar);
        ContrastseekBar = (MoFaSeekBar) findViewById(R.id.contrast_seekbar);
        SaturationseekBar = (MoFaSeekBar) findViewById(R.id.Saturation_seekbar);
        SaturationseekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
        ContrastseekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
        BrightnessseekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
    }

    private void setRGBSeekBar() {
        RedSeekBar = (MoFaSeekBar) findViewById(R.id.R_seekbar);
        BlueSeekBar = (MoFaSeekBar) findViewById(R.id.B_seekbar);
        GreenSeekBar = (MoFaSeekBar) findViewById(R.id.G_seekbar);
        RedSeekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
        BlueSeekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
        GreenSeekBar.setOnSeekBarChangeListener(adjustseekbarChangeListener);
    }

    private void AdjustSeekBarHoming() {
        if (RedSeekBar != null) {
            RedSeekBar.setProgress(100);
            BlueSeekBar.setProgress(100);
            GreenSeekBar.setProgress(100);
            BrightnessseekBar.setProgress(80);
            ContrastseekBar.setProgress(70);
            SaturationseekBar.setProgress(100);
//            RuihuaSeekBar.setProgress(180);
        }
    }

    private void setWordCtrlLayout() {
        WordClickListener listener = new WordClickListener();

        word_imageButton_color = (ImageButton) findViewById(R.id.word_imageButton_color);
        word_imageButton_color.setOnClickListener(listener);
        word_imageButton_font = (ImageButton) findViewById(R.id.word_imageButton_font);
        word_imageButton_font.setOnClickListener(listener);
        word_imageButton_shadow = (ImageButton) findViewById(R.id.word_imageButton_shadow);
        word_imageButton_shadow.setOnClickListener(listener);
        word_imageButton_textsize = (ImageButton) findViewById(R.id.word_imageButton_textsize);
        word_imageButton_textsize.setOnClickListener(listener);
        word_imageButton_tmd = (ImageButton) findViewById(R.id.word_imageButton_tmd);
        word_imageButton_tmd.setOnClickListener(listener);
        word_imageButton_hor_ver = (ImageButton)
                findViewById(R.id.word_imageButton_hor_ver);
        word_imageButton_hor_ver.setOnClickListener(listener);
        word_imageButton_move = (ImageButton) findViewById(R.id.word_imageButton_move);
        word_imageButton_move.setOnClickListener(listener);
        word_imageButton_rotate = (ImageButton) findViewById(R.id.word_imageButton_rotate);
        word_imageButton_rotate.setOnClickListener(listener);
    }

    private void setBianjiCtrlLayout() {
        scrollViewCrop = (HorizontalScrollView) findViewById(R.id.crop_scroll_view);
        scrollViewRotate = (HorizontalScrollView) findViewById(R.id.rotate_scroll_view);
        findViewById(R.id.bianji_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollViewCrop.setVisibility(View.VISIBLE);
                scrollViewRotate.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.bianji_rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollViewCrop.setVisibility(View.GONE);
//                undoFCropperTPhotoView();
                scrollViewRotate.setVisibility(View.VISIBLE);
            }
        });
    }

    private void changeTImageCropper() {
        Bitmap b = getDrawingCache();
        addCropView(b);
        mMainImageView.setVisibility(View.GONE);
        removeAllText();
    }

    private void addCropView(Bitmap b) {
        if (b.isRecycled()) {
            Toast.makeText(this, "出错了。。。", Toast.LENGTH_SHORT).show();
            return;
        }
        cropImageView.setImageBitmap(b);
        cropImageView.setVisibility(View.VISIBLE);
        mCropping =true;

        //TODO-CropView shrink
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (mCropping) {
                    allTopLayoutGONE();
                    undoFCropperTPhotoView();
                    return true;
                }

            case KeyEvent.KEYCODE_HOME:
                //pretend to click abandon button(the X button on the top on the screen)
                abandon.performClick();
                return true;
            default:
        }
        return super.onKeyDown(keyCode,event);
    }

    /**
     * getDrawingCache of MainLayout. (the operating image)
     *
     * @return Bitmap
     */
    private Bitmap getDrawingCache() {
        mainLayout.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(mainLayout.getDrawingCache());
        mainLayout.setDrawingCacheEnabled(false);
        return b;
    }

    private Bitmap getCuttedDrawingCache() {
        Bitmap b = getDrawingCache();
        int bitmapHeight, bitmapWidth;
        bitmapHeight = mOperatingBitmap.getHeight();
        bitmapWidth = mOperatingBitmap.getWidth();
        float bitmapRadio = ((float) bitmapHeight) / bitmapWidth;

        int displayHeight, displayWidth;
        displayHeight = b.getHeight();
        displayWidth  = b.getWidth();
        int newBitmapHeight, newBitmapWidth;

        if (bitmapHeight > displayHeight || bitmapWidth > displayWidth) {
            float displayRadio = (float) displayHeight / displayWidth;
            float scaleRadio;
            if (bitmapRadio > displayRadio) {
                scaleRadio = ((float) displayHeight) / bitmapHeight;
                newBitmapHeight = displayHeight;
                newBitmapWidth = (int) (scaleRadio * bitmapWidth);
            } else {
                scaleRadio = ((float) displayWidth) / bitmapWidth;
                newBitmapWidth = displayWidth;
                newBitmapHeight = (int) (scaleRadio * bitmapHeight);
            }
        } else {
            newBitmapHeight = bitmapHeight;
            newBitmapWidth = bitmapWidth;
        }
        //TODO-BUG-here resolve the bug of white line
        Bitmap result = Bitmap.createBitmap(b,
                (b.getWidth() - newBitmapWidth) / 2, (b.getHeight() - newBitmapHeight) / 2+1,
                newBitmapWidth, newBitmapHeight-1
        );
        b.recycle();
        return result;
    }

    private void removeAllText() {
        int count = mainLayout.getChildCount();
        if (count > 3) {
            for (int i = count - 1; i > 2; i--) {
                L.e(mainLayout.getChildAt(i) + "");
                TextList.add((MoFaTextView) mainLayout.getChildAt(i));
                mainLayout.removeViewAt(i);
            }
        }
    }

    private void undoFCropperTPhotoView() {
        cropImageView.setVisibility(View.GONE);
        mCropping =false;
        mMainImageView.setVisibility(View.VISIBLE);
        attacher.update();
    }

    private void setAdjustCtrlLayout() {
//        TiaoseButton = (ImageButton) findViewById(R.id.adjust_tiaose);
//        RGBButton = (ImageButton) findViewById(R.id.adjust_RGB);
//        DetailButton = (ImageButton) findViewById(R.id.adjust_detail);

        ButtonBrightness = (MoFaTextButton) findViewById(R.id.adjust_brightness);
        ButtonContrast = (MoFaTextButton) findViewById(R.id.adjust_contrast);
        ButtonSaturation = (MoFaTextButton) findViewById(R.id.adjust_saturation);
        ButtonRGB = (MoFaTextButton) findViewById(R.id.adjust_rgb);


//        mAdjustDetailTiaoseLayout = (LinearLayout) findViewById(R.id.adjust_tiaose_detail_layout);
        mAdjustDetailContrastLayout = (RelativeLayout) findViewById(R.id.adjust_layout_detail_contrast);
        mAdjustDetailBrightnessLayout = (RelativeLayout) findViewById(R.id.adjust_layout_detail_brightness);
        mAdjustDetailSaturationLayout = (RelativeLayout) findViewById(R.id.adjust_layout_detail_saturation);
        mAdjustDetailRGBLayout = (LinearLayout) findViewById(R.id.adjust_RGB_detail_layout);
//        mAdjustDetailDetailLayout = (LinearLayout) findViewById(R.id.adjust_detail_detail_layout);


        AdjustClickListener listener = new AdjustClickListener();
//        TiaoseButton.setOnClickListener(listener);
//        RGBButton.setOnClickListener(listener);
//        DetailButton.setOnClickListener(listener);
        ButtonBrightness.setOnClickListener(listener);
        ButtonContrast.setOnClickListener(listener);
        ButtonSaturation.setOnClickListener(listener);
        ButtonRGB.setOnClickListener(listener);
        (findViewById(R.id.adjust_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageAdjuster.getInstance().destroy();
                AdjustSeekBarHoming();
                cancelStep();
            }
        });
        (findViewById(R.id.adjust_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorAdjuster bean = new ColorAdjuster();
                ImageAdjuster.getInstance().save(bean);
                mModifitation.set(Modifitation.TYPE.ADJUST, bean);
                mStepMemory.add(mModifitation);
                ensureStep();

                AdjustSeekBarHoming();
            }
        });
    }

    private void setTextAlpha() {
        final SeekBar tmdSeekBar = (SeekBar) findViewById(R.id.seekBartmd);
        tmdSeekBar.setMax(255);
        tmdSeekBar.setProgress(255);
        ImageButton buttonJianTouMingDu = (ImageButton) findViewById(R.id.jiantmd);
        ImageButton buttonJiaTouMingDu = (ImageButton) findViewById(R.id.jiatmd);
        buttonJianTouMingDu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tmdSeekBar.getProgress() == 0) {
                    //do nothing
                } else {
                    tmdSeekBar.setProgress(tmdSeekBar.getProgress() - 10);
                }
            }
        });
        buttonJiaTouMingDu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tmdSeekBar.getProgress() == 255) {
                    //do nothing
                } else {
                    tmdSeekBar.setProgress(tmdSeekBar.getProgress() + 10);
                }
            }
        });
        tmdSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //TODO-引用
                if (textView == null) {
                    return;
                }
                textView.setMoFaAlpha(progress / 255f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setTextSizeDetail() {
        final SeekBar textSeekBar = (SeekBar) findViewById(R.id.seekBar_text);
        textSeekBar.setMax(255);
        textSeekBar.setProgress(40);

        ImageButton textJian = (ImageButton) findViewById(R.id.text_jian);
        ImageButton textJia = (ImageButton) findViewById(R.id.text_jia);
        textJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSeekBar.getProgress() == 0) {
                    //do nothing
                } else {
                    textSeekBar.setProgress(textSeekBar.getProgress() - 10);
                }
            }
        });

        textJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textSeekBar.getProgress() == 255) {
                    //do nothing
                } else {
                    textSeekBar.setProgress(textSeekBar.getProgress() + 10);
                }
            }
        });

        textSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //TODO-textView-引用
                if (textView == null) {
                    return;
                }
                textView.setTextSize(progress / 255f * 190 + 10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void setBlurDetail() {

        BlurSeekBar = (MoFaSeekBar) findViewById(R.id.seekBar_blur);
        BlurSeekBar.setMax(24);

        BlurSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                BlurAsyncTask task = new BlurAsyncTask();
                task.execute(mOperatingBitmap,seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress_blur = progress;
            }
        });


        ImageButton MinusBlur = (ImageButton) findViewById(R.id.jian);
        ImageButton AddBlur = (ImageButton) findViewById(R.id.jia);

        MinusBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BlurSeekBar.getProgress() == 0) {
                    //do nothing
                } else {
                    BlurSeekBar.setProgressAndDisplay(BlurSeekBar.getProgress() - 1);
                }
            }
        });

        AddBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BlurSeekBar.getProgress() == 24) {
                    //do nothing.
                } else {
                    BlurSeekBar.setProgressAndDisplay(BlurSeekBar.getProgress() + 1);
                }
            }
        });

        ((ImageButton) findViewById(R.id.blur_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int progress = BlurSeekBar.getProgress();
                mModifitation.set(Modifitation.TYPE.BLUR, progress);
                mStepMemory.add(mModifitation);

                ensureStep();
                BlurSeekBarHoming();
            }
        });

        ((ImageButton) findViewById(R.id.blur_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlurSeekBarHoming();
                cancelStep();
            }
        });
    }

    private void BlurSeekBarHoming() {
        if (BlurSeekBar == null) {
            return;
        }
        BlurSeekBar.setProgress(0);
    }

    private void ensureStep() {
        mOperatingBitmap = bitmapStepNow;
        Toast.makeText(HandleImageActivity.this, "Save Success", Toast.LENGTH_SHORT).show();
        wrapSlidingDrawer.toggle();
        mModifitation = new Modifitation();

        SUM_STEP_SAVE++;
        NOW_STEP_DISPLAY++;
    }

    private void cancelStep() {
        mMainImageView.setImageBitmap(mOperatingBitmap);
        mModifitation.clear();
    }

    private void setQuseDetailButton() {
        SwitchButton QuseDetailSwitchButton;

        QuseDetailSwitchButton = (SwitchButton) findViewById(R.id.switch_quse);
        QuseDetailSwitchButton.setStatus(SwitchButton.STATUS.ON);
        QuseDetailSwitchButton.setOnStatusChangeListener(new SwitchButton.OnStatusChangeListener() {
            @Override
            public void onChange(SwitchButton.STATUS status) {
                if (gray) {
                    quseThread.cancel(false);
                    mMainImageView.setImageBitmap(mSrcBitmap);
                    gray = false;
                } else {
                    quseThread = new AsyncTask<Bitmap, Void, Bitmap>() {

                        @Override
                        protected Bitmap doInBackground(Bitmap... params) {
                            return toGrayscale(params[0]);
                        }

                        @Override
                        protected void onPostExecute(Bitmap bitmap) {

                            super.onPostExecute(bitmap);
                            if (!isCancelled()) {
                                mMainImageView.setImageBitmap(bitmap);
                            }
                        }
                    };
                    quseThread.execute(mSrcBitmap);
                    gray = true;
                }
            }
        });
    }

    private void findGroup() {
        rEditTextLayout = (RelativeLayout) findViewById(R.id.adding_word_layout);
        rBlurDetailLayout = (RelativeLayout) findViewById(R.id.rlayout_gauss);
        rFilterDetailLayout = (RelativeLayout) findViewById(R.id.relativeLayout_quse);
        rAdjustDetailLayout = (RelativeLayout) findViewById(R.id.relativeLayoutAdjust);
        rXuhuaDetailLayout = (RelativeLayout) findViewById(R.id.xuhuaLayout);
        rBianjiDetailLayout = (RelativeLayout) findViewById(R.id.bianjiLayout);

        rFontDetailLayout = (RelativeLayout) findViewById(R.id.relativeLayout_font);
        rTextSizeDetailLayout = (RelativeLayout) findViewById(R.id.relativeLayout_textsize);
        rTouMingDetailLayout = (RelativeLayout) findViewById(R.id.relativeLayout_tmd);
        rColorDetailLayout = (HorizontalScrollView) findViewById(R.id.relativeLayout_color);
        rShadowDetailLayout = (LinearLayout) findViewById(R.id.linearLayout_shadow);

        rEnglishFontTypeLayout = (HorizontalScrollView) findViewById(R.id.scrollView_font_English);
        rChineseFontTypeLayout = (HorizontalScrollView) findViewById(R.id.scrollView_font_Chinese);
    }

    private void setGroupTop() {
        GaussButton = (ImageButton) findViewById(R.id.gauss_layout_controller);
        wenziButton = (ImageButton) findViewById(R.id.adding_word_layout_controller);
//        filterButton = (ImageButton) findViewById(R.id.image_filter_layout_controller);
        AdjustButton = (ImageButton) findViewById(R.id.adjust_layout_controller);
//        xuhuaButton = (ImageButton) findViewById(R.id.xuhua);
        editButton = (ImageButton) findViewById(R.id.bianji);


//        filterButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                quseVISIBLE();
//            }
//        });
        wenziButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wenziVISIBLE();
            }
        });
        GaussButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mohuVISIBLE();
            }
        });
        AdjustButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustVISIBLE();
            }
        });
//        xuhuaButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                xuhuaVISIBLE();
//            }
//        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bianjiVISIBLE();
            }
        });
    }

    private void allTopLayoutGONE() {
        rEditTextLayout.setVisibility(View.GONE);

        rBlurDetailLayout.setVisibility(View.GONE);
        BlurSeekBarHoming();

        rFilterDetailLayout.setVisibility(View.GONE);

        rAdjustDetailLayout.setVisibility(View.GONE);
        AdjustSeekBarHoming();

        rXuhuaDetailLayout.setVisibility(View.GONE);
        rBianjiDetailLayout.setVisibility(View.GONE);

        undoFCropperTPhotoView();
        setUpButtonOnChoosenAndUnclickable(false);
//        allMainCtrlButtonClickable();
    }

    public void setButtonOnChoosenAndUnclickable(View button, boolean enable) {
        button.setClickable(!enable);
        button.setPressed(enable);
    }

    private void setUpButtonOnChoosenAndUnclickable(boolean enable) {
        imageButtonBackward.setPressed(enable);
        imageButtonForward.setPressed(enable);
        save.setPressed(enable);
        abandon.setPressed(enable);

        imageButtonBackward.setClickable(!enable);
        imageButtonForward.setClickable(!enable);
        save.setClickable(!enable);
        abandon.setClickable(!enable);
    }

    private void bianjiVISIBLE() {
        allTopLayoutGONE();
        setUpButtonOnChoosenAndUnclickable(true);
        changeTImageCropper();
        rBianjiDetailLayout.setVisibility(View.VISIBLE);
//        setButtonOnChoosenAndUnclickable(editButton,true);

    }

    private void allMainCtrlButtonClickable() {
        setButtonOnChoosenAndUnclickable(editButton, true);
        setButtonOnChoosenAndUnclickable(AdjustButton, true);
        setButtonOnChoosenAndUnclickable(GaussButton, true);
        setButtonOnChoosenAndUnclickable(wenziButton, true);
    }

    private void xuhuaVISIBLE() {
        allTopLayoutGONE();
        rXuhuaDetailLayout.setVisibility(View.VISIBLE);
    }

    private void adjustVISIBLE() {
        allTopLayoutGONE();
        rAdjustDetailLayout.setVisibility(View.VISIBLE);
//        setButtonOnChoosenAndUnclickable(AdjustButton, true);
    }

    private void mohuVISIBLE() {
        allTopLayoutGONE();
        rBlurDetailLayout.setVisibility(View.VISIBLE);
//        setButtonOnChoosenAndUnclickable(GaussButton, true);
    }

    private void wenziVISIBLE() {
        allTopLayoutGONE();
        rEditTextLayout.setVisibility(View.VISIBLE);
//        setButtonOnChoosenAndUnclickable(wenziButton,true);
    }

    private void quseVISIBLE() {
        allTopLayoutGONE();
        rFilterDetailLayout.setVisibility(View.VISIBLE);
    }

    private void setButtonGroup() {
        findGroup();
        setGroupTop();
    }

    /**
     * ctrl the handle image upward or downward.
     */
    private void initMenuSelf() {
        final ImageButton handleSelector = (ImageButton) findViewById(R.id.handle_selector);
        wrapSlidingDrawer = (MoFaSlidingDrawer) findViewById(R.id.slidingDrawer1);
        wrapSlidingDrawer.animateOpen();
        wrapSlidingDrawer.setOnDrawerOpenListener(new MoFaSlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                handleSelector.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.mofa_down_down));
            }
        });
        wrapSlidingDrawer.setOnDrawerCloseListener(new MoFaSlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                handleSelector.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.mofa_up_down));
            }
        });

    }

    private MoFaTextViewOnFocusedListener mMoFaTextViewOnFocusedListener;

    private void initTextView() {
        mMoFaTextViewOnFocusedListener = new MoFaTextViewOnFocusedListener();
        TextList = new ArrayList<MoFaTextView>();
        textView = new MoFaTextView(this);
        textView.setOnFocusedListener(mMoFaTextViewOnFocusedListener);
        textView.setAlpha(0);
        mainLayout.addView(textView);
        textView.setDismissWhenFocusOnTouchOutside();
    }


    private class MoFaTextViewOnFocusedListener implements MoFaTextView.OnFocusedListener {
        @Override
        public void onFocused(View view) {
            textView = (MoFaTextView) view;
            mEditTextInWord.setText(textView.getText());
        }
    }

    /**
     * set Color
     */
    private void setColorDetailButton() {
        Button color1Button;
        Button color2Button;
        Button color3Button;
        Button color4Button;
        Button color5Button;
        Button color6Button;
        Button color7Button;
        Button color8Button;
        Button color9Button;
        Button color10Button;
        Button color11Button;
        Button color12Button;
        Button color13Button;
        Button color14Button;
        Button color15Button;
        Button color16Button;
        Button color17Button;
        Button color18Button;
        Button color19Button;
        Button color20Button;
        Button color21Button;
        Button color22Button;
        Button color23Button;
        Button color24Button;
        Button color25Button;
        Button color26Button;
        Button color27Button;
        Button color28Button;
        Button color29Button;
        Button color30Button;
        Button color31Button;
        Button color32Button;
        Button color33Button;
        Button color34Button;
        Button color35Button;

        Button secondcolor1Button;
        Button secondcolor2Button;
        Button secondcolor3Button;
        Button secondcolor4Button;
        Button secondcolor5Button;
        Button secondcolor6Button;
        Button secondcolor7Button;
        Button secondcolor8Button;
        Button secondcolor9Button;
        Button secondcolor10Button;
        Button secondcolor11Button;
        Button secondcolor12Button;
        Button secondcolor13Button;
        Button secondcolor14Button;
        Button secondcolor15Button;
        Button secondcolor16Button;
        Button secondcolor17Button;
        Button secondcolor18Button;
        Button secondcolor19Button;
        Button secondcolor20Button;
        Button secondcolor21Button;
        Button secondcolor22Button;
        Button secondcolor23Button;
        Button secondcolor24Button;
        Button secondcolor25Button;
        Button secondcolor26Button;
        Button secondcolor27Button;
        Button secondcolor28Button;
        Button secondcolor29Button;
        Button secondcolor30Button;
        Button secondcolor31Button;
        Button secondcolor32Button;
        Button secondcolor33Button;
        Button secondcolor34Button;
        Button secondcolor35Button;

        color1Button = (Button) findViewById(R.id.imageButton_color1);
        color2Button = (Button) findViewById(R.id.imageButton_color2);
        color3Button = (Button) findViewById(R.id.imageButton_color3);
        color4Button = (Button) findViewById(R.id.imageButton_color4);
        color5Button = (Button) findViewById(R.id.imageButton_color5);
        color6Button = (Button) findViewById(R.id.imageButton_color6);
        color7Button = (Button) findViewById(R.id.imageButton_color7);
        color8Button = (Button) findViewById(R.id.imageButton_color8);
        color9Button = (Button) findViewById(R.id.imageButton_color9);
        color10Button = (Button) findViewById(R.id.imageButton_color10);
        color11Button = (Button) findViewById(R.id.imageButton_color11);
        color12Button = (Button) findViewById(R.id.imageButton_color12);
        color13Button = (Button) findViewById(R.id.imageButton_color13);
        color14Button = (Button) findViewById(R.id.imageButton_color14);
        color15Button = (Button) findViewById(R.id.imageButton_color15);
        color16Button = (Button) findViewById(R.id.imageButton_color16);
        color17Button = (Button) findViewById(R.id.imageButton_color17);
        color18Button = (Button) findViewById(R.id.imageButton_color18);
        color19Button = (Button) findViewById(R.id.imageButton_color19);
        color20Button = (Button) findViewById(R.id.imageButton_color20);
        color21Button = (Button) findViewById(R.id.imageButton_color21);
        color22Button = (Button) findViewById(R.id.imageButton_color22);
        color23Button = (Button) findViewById(R.id.imageButton_color23);
        color24Button = (Button) findViewById(R.id.imageButton_color24);
        color25Button = (Button) findViewById(R.id.imageButton_color25);
        color26Button = (Button) findViewById(R.id.imageButton_color26);
        color27Button = (Button) findViewById(R.id.imageButton_color27);
        color28Button = (Button) findViewById(R.id.imageButton_color28);
        color29Button = (Button) findViewById(R.id.imageButton_color29);
        color30Button = (Button) findViewById(R.id.imageButton_color30);
        color31Button = (Button) findViewById(R.id.imageButton_color31);
        color32Button = (Button) findViewById(R.id.imageButton_color32);
        color33Button = (Button) findViewById(R.id.imageButton_color33);
        color34Button = (Button) findViewById(R.id.imageButton_color34);
        color35Button = (Button) findViewById(R.id.imageButton_color35);

        //Lei make it
        secondcolor1Button = (Button) findViewById(R.id.imageButton_second_color1);
        secondcolor2Button = (Button) findViewById(R.id.imageButton_second_color2);
        secondcolor3Button = (Button) findViewById(R.id.imageButton_second_color3);
        secondcolor4Button = (Button) findViewById(R.id.imageButton_second_color4);
        secondcolor5Button = (Button) findViewById(R.id.imageButton_second_color5);
        secondcolor6Button = (Button) findViewById(R.id.imageButton_second_color6);
        secondcolor7Button = (Button) findViewById(R.id.imageButton_second_color7);
        secondcolor8Button = (Button) findViewById(R.id.imageButton_second_color8);
        secondcolor9Button = (Button) findViewById(R.id.imageButton_second_color9);
        secondcolor10Button = (Button) findViewById(R.id.imageButton_second_color10);
        secondcolor11Button = (Button) findViewById(R.id.imageButton_second_color11);
        secondcolor12Button = (Button) findViewById(R.id.imageButton_second_color12);
        secondcolor13Button = (Button) findViewById(R.id.imageButton_second_color13);
        secondcolor14Button = (Button) findViewById(R.id.imageButton_second_color14);
        secondcolor15Button = (Button) findViewById(R.id.imageButton_second_color15);
        secondcolor16Button = (Button) findViewById(R.id.imageButton_second_color16);
        secondcolor17Button = (Button) findViewById(R.id.imageButton_second_color17);
        secondcolor18Button = (Button) findViewById(R.id.imageButton_second_color18);
        secondcolor19Button = (Button) findViewById(R.id.imageButton_second_color19);
        secondcolor20Button = (Button) findViewById(R.id.imageButton_second_color20);
        secondcolor21Button = (Button) findViewById(R.id.imageButton_second_color21);
        secondcolor22Button = (Button) findViewById(R.id.imageButton_second_color22);
        secondcolor23Button = (Button) findViewById(R.id.imageButton_second_color23);
        secondcolor24Button = (Button) findViewById(R.id.imageButton_second_color24);
        secondcolor25Button = (Button) findViewById(R.id.imageButton_second_color25);
        secondcolor26Button = (Button) findViewById(R.id.imageButton_second_color26);
        secondcolor27Button = (Button) findViewById(R.id.imageButton_second_color27);
        secondcolor28Button = (Button) findViewById(R.id.imageButton_second_color28);
        secondcolor29Button = (Button) findViewById(R.id.imageButton_second_color29);
        secondcolor30Button = (Button) findViewById(R.id.imageButton_second_color30);
        secondcolor31Button = (Button) findViewById(R.id.imageButton_second_color31);
        secondcolor32Button = (Button) findViewById(R.id.imageButton_second_color32);
        secondcolor33Button = (Button) findViewById(R.id.imageButton_second_color33);
        secondcolor34Button = (Button) findViewById(R.id.imageButton_second_color34);
        secondcolor35Button = (Button) findViewById(R.id.imageButton_second_color35);


        ImageButtonColorOnClickListener lisner = new ImageButtonColorOnClickListener();
        color1Button.setOnClickListener(lisner);
        color2Button.setOnClickListener(lisner);
        color3Button.setOnClickListener(lisner);
        color4Button.setOnClickListener(lisner);
        color5Button.setOnClickListener(lisner);
        color6Button.setOnClickListener(lisner);
        color7Button.setOnClickListener(lisner);
        color8Button.setOnClickListener(lisner);
        color9Button.setOnClickListener(lisner);
        color10Button.setOnClickListener(lisner);
        color11Button.setOnClickListener(lisner);
        color12Button.setOnClickListener(lisner);
        color13Button.setOnClickListener(lisner);
        color14Button.setOnClickListener(lisner);
        color15Button.setOnClickListener(lisner);
        color16Button.setOnClickListener(lisner);
        color17Button.setOnClickListener(lisner);
        color18Button.setOnClickListener(lisner);
        color19Button.setOnClickListener(lisner);
        color20Button.setOnClickListener(lisner);
        color21Button.setOnClickListener(lisner);
        color22Button.setOnClickListener(lisner);
        color23Button.setOnClickListener(lisner);
        color24Button.setOnClickListener(lisner);
        color25Button.setOnClickListener(lisner);
        color26Button.setOnClickListener(lisner);
        color27Button.setOnClickListener(lisner);
        color28Button.setOnClickListener(lisner);
        color29Button.setOnClickListener(lisner);
        color30Button.setOnClickListener(lisner);
        color31Button.setOnClickListener(lisner);
        color32Button.setOnClickListener(lisner);
        color33Button.setOnClickListener(lisner);
        color34Button.setOnClickListener(lisner);
        color35Button.setOnClickListener(lisner);

        secondcolor1Button.setOnClickListener(lisner);
        secondcolor2Button.setOnClickListener(lisner);
        secondcolor3Button.setOnClickListener(lisner);
        secondcolor4Button.setOnClickListener(lisner);
        secondcolor5Button.setOnClickListener(lisner);
        secondcolor6Button.setOnClickListener(lisner);
        secondcolor7Button.setOnClickListener(lisner);
        secondcolor8Button.setOnClickListener(lisner);
        secondcolor9Button.setOnClickListener(lisner);
        secondcolor10Button.setOnClickListener(lisner);
        secondcolor11Button.setOnClickListener(lisner);
        secondcolor12Button.setOnClickListener(lisner);
        secondcolor13Button.setOnClickListener(lisner);
        secondcolor14Button.setOnClickListener(lisner);
        secondcolor15Button.setOnClickListener(lisner);
        secondcolor16Button.setOnClickListener(lisner);
        secondcolor17Button.setOnClickListener(lisner);
        secondcolor18Button.setOnClickListener(lisner);
        secondcolor19Button.setOnClickListener(lisner);
        secondcolor20Button.setOnClickListener(lisner);
        secondcolor21Button.setOnClickListener(lisner);
        secondcolor22Button.setOnClickListener(lisner);
        secondcolor23Button.setOnClickListener(lisner);
        secondcolor24Button.setOnClickListener(lisner);
        secondcolor25Button.setOnClickListener(lisner);
        secondcolor26Button.setOnClickListener(lisner);
        secondcolor27Button.setOnClickListener(lisner);
        secondcolor28Button.setOnClickListener(lisner);
        secondcolor29Button.setOnClickListener(lisner);
        secondcolor30Button.setOnClickListener(lisner);
        secondcolor31Button.setOnClickListener(lisner);
        secondcolor32Button.setOnClickListener(lisner);
        secondcolor33Button.setOnClickListener(lisner);
        secondcolor34Button.setOnClickListener(lisner);
        secondcolor35Button.setOnClickListener(lisner);
    }

    private void setAlignHorOrVerDetailButton() {
        findViewById(R.id.imageButton_word_align_hor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView == null) {
                    return;
                }
                textView.setHorizontal();
            }
        });

        findViewById(R.id.imageButton_word_align_ver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView == null) {
                    return;
                }

                textView.setVertical();
            }
        });
    }

    private void setFontDetailButton() {
        final SwitchButton switch_font;

        switch_font = (SwitchButton) findViewById(R.id.switch_font);
        switch_font.setStatus(SwitchButton.STATUS.OFF);
        switch_font.setOnStatusChangeListener(new SwitchButton.OnStatusChangeListener() {
            @Override
            public void onChange(SwitchButton.STATUS status) {
                if (status == SwitchButton.STATUS.ON) {
                    switch_font.setCursorDrawable(getResources().getDrawable(R.drawable.mofa__0019_font_ch));
                    rEnglishFontTypeLayout.setVisibility(View.GONE);
                    rChineseFontTypeLayout.setVisibility(View.VISIBLE);
                } else {
                    switch_font.setCursorDrawable(getResources().getDrawable(R.drawable.mofa__0018_font_en));
                    rChineseFontTypeLayout.setVisibility(View.GONE);
                    rEnglishFontTypeLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        font01Button = (ImageButton) findViewById(R.id.imageButton_font01);
        font02Button = (ImageButton) findViewById(R.id.imageButton_font02);
        font03Button = (ImageButton) findViewById(R.id.imageButton_font03);
        font04Button = (ImageButton) findViewById(R.id.imageButton_font04);
        font05Button = (ImageButton) findViewById(R.id.imageButton_font05);
        font06Button = (ImageButton) findViewById(R.id.imageButton_font06);
        font07Button = (ImageButton) findViewById(R.id.imageButton_font07);
        font08Button = (ImageButton) findViewById(R.id.imageButton_font08);
        font09Button = (ImageButton) findViewById(R.id.imageButton_font09);
        font10Button = (ImageButton) findViewById(R.id.imageButton_font10);
        font11Button = (ImageButton) findViewById(R.id.imageButton_font11);
        font12Button = (ImageButton) findViewById(R.id.imageButton_font12);
        font13Button = (ImageButton) findViewById(R.id.imageButton_font13);
        font14Button = (ImageButton) findViewById(R.id.imageButton_font14);
        font15Button = (ImageButton) findViewById(R.id.imageButton_font15);
        font16Button = (ImageButton) findViewById(R.id.imageButton_font16);
        font17Button = (ImageButton) findViewById(R.id.imageButton_font17);
        font18Button = (ImageButton) findViewById(R.id.imageButton_font18);
        font19Button = (ImageButton) findViewById(R.id.imageButton_font19);
        font20Button = (ImageButton) findViewById(R.id.imageButton_font20);
        font21Button = (ImageButton) findViewById(R.id.imageButton_font21);
        font22Button = (ImageButton) findViewById(R.id.imageButton_font22);
        font23Button = (ImageButton) findViewById(R.id.imageButton_font23);
        font24Button = (ImageButton) findViewById(R.id.imageButton_font24);
        font25Button = (ImageButton) findViewById(R.id.imageButton_font25);
        font26Button = (ImageButton) findViewById(R.id.imageButton_font26);
        font27Button = (ImageButton) findViewById(R.id.imageButton_font27);
        font28Button = (ImageButton) findViewById(R.id.imageButton_font28);
        font29Button = (ImageButton) findViewById(R.id.imageButton_font29);
        font30Button = (ImageButton) findViewById(R.id.imageButton_font30);


        morenButton = (RelativeLayout) findViewById(R.id.imageButton_moren);
        miaowuButton = (RelativeLayout) findViewById(R.id.imageButton_miaowu);
        daofengButton = (RelativeLayout) findViewById(R.id.imageButton_daofeng);
        shangheiButton = (RelativeLayout) findViewById(R.id.imageButton_shanghei);
        yueheiButton = (RelativeLayout) findViewById(R.id.imageButton_yuehei);


        font01Button.setOnClickListener(new FontClickListener("font/f1.ttf"));
        font02Button.setOnClickListener(new FontClickListener("font/f2.ttf"));
        font03Button.setOnClickListener(new FontClickListener("font/f3.ttf"));
        font04Button.setOnClickListener(new FontClickListener("font/f4.ttf"));
        font05Button.setOnClickListener(new FontClickListener("font/f5.ttf"));
        font06Button.setOnClickListener(new FontClickListener("font/f6.ttf"));
        font07Button.setOnClickListener(new FontClickListener("font/f7.ttf"));
        font08Button.setOnClickListener(new FontClickListener("font/f8.ttf"));
        font09Button.setOnClickListener(new FontClickListener("font/f9.ttf"));
        font10Button.setOnClickListener(new FontClickListener("font/f10.ttf"));
        font11Button.setOnClickListener(new FontClickListener("font/f11.ttf"));
        font12Button.setOnClickListener(new FontClickListener("font/f12.ttf"));
        font13Button.setOnClickListener(new FontClickListener("font/f13.ttf"));
        font14Button.setOnClickListener(new FontClickListener("font/f14.ttf"));
        font15Button.setOnClickListener(new FontClickListener("font/f15.ttf"));
        font16Button.setOnClickListener(new FontClickListener("font/f16.ttf"));
        font17Button.setOnClickListener(new FontClickListener("font/f17.ttf"));
        font18Button.setOnClickListener(new FontClickListener("font/f18.ttf"));
        font19Button.setOnClickListener(new FontClickListener("font/f19.ttf"));
        font20Button.setOnClickListener(new FontClickListener("font/f20.ttf"));
        font21Button.setOnClickListener(new FontClickListener("font/f21.ttf"));
        font22Button.setOnClickListener(new FontClickListener("font/f22.ttf"));
        font23Button.setOnClickListener(new FontClickListener("font/f23.ttf"));
        font24Button.setOnClickListener(new FontClickListener("font/f24.ttf"));
        font25Button.setOnClickListener(new FontClickListener("font/f25.ttf"));
        font26Button.setOnClickListener(new FontClickListener("font/f26.ttf"));
        font27Button.setOnClickListener(new FontClickListener("font/f27.ttf"));
        font28Button.setOnClickListener(new FontClickListener("font/f28.ttf"));
        font29Button.setOnClickListener(new FontClickListener("font/f29.ttf"));
        font30Button.setOnClickListener(new FontClickListener("font/f30.ttf"));



        RelativeLayout yingbiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_yingbi);
        RelativeLayout fanyuanButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_fanyuan);
        RelativeLayout gutiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_guti);
        RelativeLayout hupoButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_hupo);
        RelativeLayout kaitiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_kaiti);
        RelativeLayout ruixianButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_ruixian);
        RelativeLayout songtiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_songti);
        RelativeLayout tianniuButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_tianniu);
        RelativeLayout xiyuanButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_xiyuan);
        RelativeLayout xindiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_xindi);
        RelativeLayout yaotiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_yaoti);
        RelativeLayout youyuanButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_youyuan);
        RelativeLayout changmeiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_changmei);
        RelativeLayout zhiyiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_zhiyi);
//      TODO-Image line gap
        RelativeLayout zhongsongButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_zhongsong);
        RelativeLayout zhongyuanButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_zhongyuan);
        RelativeLayout zhunyuanButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_zhunyuan);
        RelativeLayout zongyiButton = (RelativeLayout) findViewById(R.id.RelativeLayout_font_download_zongyi);

        sharedPreferences = getSharedPreferences(Config.PREFERENCE_NAME_FONT, Context.MODE_PRIVATE);

//        miaowuButton.setOnClickListener(new FontClickListener("font/miaowu.ttf"));
//        daofengButton.setOnClickListener(new FontClickListener("font/daofeng.ttf"));
//        shangheiButton.setOnClickListener(new FontClickListener("font/shanghei.ttf"));
//        yueheiButton.setOnClickListener(new FontClickListener("font/yuehei.ttf"));
        boolean miaowuEnable = sharedPreferences.getBoolean(Config.miaowu,false);
        boolean daofengEnable = sharedPreferences.getBoolean(Config.daofeng,false);
        boolean shangeheiEnable = sharedPreferences.getBoolean(Config.shanghei,false);
        boolean yueheiEnable = sharedPreferences.getBoolean(Config.yuehei,false);


        boolean yingbiEnable = sharedPreferences.getBoolean(Config.yingbi, false);
        boolean fanyuanEnable = sharedPreferences.getBoolean(Config.fanyuan, false);
        boolean gutiEnable = sharedPreferences.getBoolean(Config.guti, false);
        boolean hupoEnable = sharedPreferences.getBoolean(Config.hupo, false);
        boolean kaitiEnable = sharedPreferences.getBoolean(Config.kaiti, false);
        boolean ruixianEnable = sharedPreferences.getBoolean(Config.ruixian, false);
        boolean songtiEnable = sharedPreferences.getBoolean(Config.songti, false);
        boolean tianniuEnable = sharedPreferences.getBoolean(Config.tianniu, false);
        boolean xiyuanEnable = sharedPreferences.getBoolean(Config.xiyuan, false);
        boolean xindiEnable = sharedPreferences.getBoolean(Config.xindi, false);
        boolean yaotiEnable = sharedPreferences.getBoolean(Config.yaoti, false);
        boolean youyuanEnable = sharedPreferences.getBoolean(Config.youyuan, false);
        boolean changmeiEnable = sharedPreferences.getBoolean(Config.changmei, false);
        boolean zhiyiEnable = sharedPreferences.getBoolean(Config.zhiyi, false);
        boolean zhongsongEnable = sharedPreferences.getBoolean(Config.zhongsong, false);
        boolean zhongyuanEnable = sharedPreferences.getBoolean(Config.zhongyuan, false);
//        boolean zhunyuanEnable = sharedPreferences.getBoolean(Config.zhunyuan, false);
        boolean zongyiEnable = sharedPreferences.getBoolean(Config.zongyi, false);

        morenButton.setOnClickListener(new FontClickListener());
        zhunyuanButton.setOnClickListener(new FontClickListener("font/zhunyuan.ttf"));

        FontCtrlView(miaowuEnable, miaowuButton, Config.miaowu);
        FontCtrlView(yueheiEnable,yueheiButton,Config.yuehei);
        FontCtrlView(shangeheiEnable,shangheiButton,Config.shanghei);
        FontCtrlView(daofengEnable,daofengButton,Config.daofeng);
        FontCtrlView(yingbiEnable, yingbiButton, Config.yingbi);
        FontCtrlView(fanyuanEnable, fanyuanButton, Config.fanyuan);
        FontCtrlView(gutiEnable, gutiButton, Config.guti);
        FontCtrlView(hupoEnable, hupoButton, Config.hupo);
        FontCtrlView(kaitiEnable, kaitiButton, Config.kaiti);
        FontCtrlView(ruixianEnable, ruixianButton, Config.ruixian);
        FontCtrlView(songtiEnable, songtiButton, Config.songti);
        FontCtrlView(tianniuEnable, tianniuButton, Config.tianniu);
        FontCtrlView(xiyuanEnable, xiyuanButton, Config.xiyuan);
        FontCtrlView(xindiEnable, xindiButton, Config.xindi);
        FontCtrlView(yaotiEnable, yaotiButton, Config.yaoti);
        FontCtrlView(youyuanEnable, youyuanButton, Config.youyuan);
        FontCtrlView(changmeiEnable, changmeiButton, Config.changmei);
        FontCtrlView(zhiyiEnable, zhiyiButton, Config.zhiyi);
        FontCtrlView(zhongsongEnable, zhongsongButton, Config.zhongsong);
        FontCtrlView(zhongyuanEnable, zhongyuanButton, Config.zhongyuan);
//        FontCtrlView(zhunyuanEnable, zhunyuanButton, Config.zhunyuan);
        FontCtrlView(zongyiEnable, zongyiButton, Config.zongyi);
    }

    private void FontCtrlView(boolean bool, RelativeLayout layout, String name) {

        if (!bool) {
            layout.setOnClickListener(new FontDownLoadClickListener(name
                    , (ProgressBar) layout.getChildAt(2)));
        } else {
            layout.setClickable(true);

            File file = getFileStreamPath(name);
            if (!file.exists()) {
                layout.setOnClickListener(new FontDownLoadClickListener(name
                        , (ProgressBar) layout.getChildAt(2)));
                return;
            }
            try {
                layout.setOnClickListener(
                        new FontClickListener(
                                Typeface.createFromFile(
                                        file
                                )
                        )
                );
                layout.removeViews(1, 2);
            } catch (Exception e) {
                e.printStackTrace();
                sharedPreferences = getSharedPreferences(Config.PREFERENCE_NAME_FONT, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(name, false);
                editor.apply();
                layout.setOnClickListener(new FontDownLoadClickListener(name
                        , (ProgressBar) layout.getChildAt(2)));
            }


        }
    }

    private void DownLoadCompleteToast(Exception e) {
        if (e != null) {
            Toast.makeText(HandleImageActivity.this, "下载失败了哎~", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(HandleImageActivity.this, "下载成功了呐~", Toast.LENGTH_SHORT).show();
    }

    private class FontDownLoadClickListener implements View.OnClickListener {
        String mName;
        ProgressBar mProgressbar;

        public FontDownLoadClickListener(String name, ProgressBar progressBar) {
            mName = name;
            mProgressbar = progressBar;
        }

        @Override
        public void onClick(final View v) {
            v.setClickable(false);
            Ion.with(HandleImageActivity.this)
                    .load(Config.url + mName)
                    .progressBar(mProgressbar)
                    .write(getBaseContext().getFileStreamPath(mName))
                    .setCallback(new FutureCallback<File>() {
                        @Override
                        public void onCompleted(Exception e, File result) {
                            Typeface face = null;
                            try{
                                face = Typeface.createFromFile(result);
                                v.setOnClickListener(new FontClickListener(face));
                                sharedPreferences = getSharedPreferences(Config.PREFERENCE_NAME_FONT, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(mName, true);
                                editor.apply();
                                ((RelativeLayout) v).removeViews(1, 2);
                                DownLoadCompleteToast(e);
                            }catch (Exception fileError){
                                fileError.printStackTrace();
                                DownLoadCompleteToast(new Exception());
                                ((ProgressBar)((RelativeLayout) v).getChildAt(2)).setProgress(0);
                                v.setOnClickListener(FontDownLoadClickListener.this);
                            }
                            v.setClickable(true);

                        }
                    });
        }
    }

    // Ensure the bitmap created and displayed on the screen. When it has created,mSrcMap = bitmap
    private void CreateSrcBitmap() {
        final DisplayImageOptions options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .imageScaleType(ImageScaleType.NONE)
                .build();
        //Listener to ensure the bitmap created and displayed on the screen. When it has created,mSrcMap = bitmap
        ImageLoadinglistener = new SimpleImageLoadingListener() {
            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                L.e(failReason.getType() + "");
                if (failReason.getType().equals(FailReason.FailType.OUT_OF_MEMORY)) {
                    ImageLoader.getInstance().displayImage(imageUri, (ImageView) view, options, ImageLoadinglistener);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mSrcBitmap = loadedImage;
                mOperatingBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight());
                mBitmapBlur = BlurMainImageForBackground();
                mMainImageView.setImageBitmap(mOperatingBitmap);

                attacher = new PhotoViewAttacher(mMainImageView);
                attacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                attacher.setZoomable(true);
                mDrawMatrix = attacher.getDrawMatrix();
            }
        };
        mMainImageView = (ImageView) findViewById(R.id.handle_imageView);
        Intent intent = getIntent();

        String path = intent.getStringExtra(ImageSelectedActivity.INTENT_EXTRA_NAME_IMAGE_SELECTED);

        boolean isNetwork = intent.getBooleanExtra("network", false);
        boolean isFromCam = intent.getBooleanExtra("Cam", false);
        //TODO-progressbar in ImageLoader

        //if here use options maybe RGB_565 or any more makes.
        // the RenderScript shut down.
        // with the Error  Unsupported element type
        if (isFromCam) {
            String PicturePath = intent.getStringExtra("tem_pic");


            ImageLoader.getInstance().displayImage("file://" + PicturePath
                    , mMainImageView
                    , options
                    , ImageLoadinglistener);

        } else {
            String s;
            if (isNetwork) {
                s = "file://";

                path = "/sdcard/mofa/" + path;

                ImageLoader.getInstance().displayImage(s + path
                        , mMainImageView
                        , options
                        , ImageLoadinglistener);

            } else {
                s = "file://";
                ImageLoader.getInstance().displayImage(s + path
                        , mMainImageView
                        , options
                        , ImageLoadinglistener);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (attacher != null) {
            attacher.cleanup();
        }
    }

    //Use SrcBitmap from CreateSrcBitmap and Blur it for actionbar_background.
    //There is something I don't know.
    private Bitmap BlurMainImageForBackground() {
        Matrix matrix = new Matrix();
        matrix.postScale(0.15f, 0.15f); // 长和宽放大缩小的比例

        Bitmap resizeBmp = Bitmap.createBitmap(mSrcBitmap, 0, 0,
                mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), matrix, true);

        Bitmap result0 = rsBlur(resizeBmp, this, 25);
        Bitmap result1 = rsBlur(result0, this, 25);
        Bitmap result2 = rsBlur(result1, this, 25);
        Bitmap result = rsBlur(result2, this, 25);

        background = (ImageView) findViewById(R.id.background_blur);
        background.setImageBitmap(result);

        resizeBmp.recycle();
        result0.recycle();
        result1.recycle();
        result2.recycle();
        return result;
    }

    //\/ and x is two image button on up of the window, which is save and abandon.
    private void SaveAndAbandonClickListener() {
        save = (ImageButton) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                RelativeLayout layout = (RelativeLayout) inflater
                        .inflate(R.layout.layout_dialog, null);
                DefaultFontInflator.applyRecursive(HandleImageActivity.this,layout);
                final Dialog dialog = new AlertDialog.Builder(HandleImageActivity.this).create();
                dialog.show();
                dialog.getWindow().setContentView(layout);
                ((TextView) layout.findViewById(R.id.dialog_text)).setText("确认保存吗？");
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
                        Intent intent = new Intent(HandleImageActivity.this, OkActivity.class);
                        //TODO-word add has error!!!!!!!
                        sBitmapMain = getCuttedDrawingCache();
                        sBitmapBlur = mBitmapBlur;
                        HandleImageActivity.this.startActivity(intent);
                        dialog.dismiss();
                        finish();
                    }
                });

            }
        });

        abandon = (ImageButton) findViewById(R.id.abandon);
        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                RelativeLayout layout = (RelativeLayout) inflater
                        .inflate(R.layout.layout_dialog, null);
                DefaultFontInflator.applyRecursive(HandleImageActivity.this,layout);
                final Dialog dialog = new AlertDialog.Builder(HandleImageActivity.this).create();
                dialog.show();
                dialog.getWindow().setContentView(layout);
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
                        if (mSrcBitmap != null) {
                            mSrcBitmap.recycle();
                        }
                        finish();
                        overridePendingTransition(R.anim.ani_static,R.anim.out_to_right);
                    }
                });


            }
        });
    }

    //Blur alg.
    private Bitmap rsBlur(Bitmap raw, Context context, int radius) {
        if (raw.getConfig().equals(Bitmap.Config.RGB_565)) {
            raw = this.RGB565toARGB888(raw);
        }
        RenderScript rs = RenderScript.create(context);
        Allocation alloc = Allocation.createFromBitmap(rs, raw);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs
                , alloc.getElement()
        );
        blur.setRadius(radius);
        blur.setInput(alloc);

        Bitmap result = Bitmap.createBitmap(raw.getWidth(), raw.getHeight(),
                raw.getConfig());
        Allocation outAlloc = Allocation.createFromBitmap(rs, result);
        blur.forEach(outAlloc);
        outAlloc.copyTo(result);
        rs.destroy();
        alloc.destroy();
        outAlloc.destroy();
        // raw.recycle();
        return result;
    }

    private Bitmap RGB565toARGB888(Bitmap img) {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    // 去色 alg.
    private Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayScale = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayScale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayScale;
    }

    private class AdjustSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private ImageAdjuster.ImageAdjustingListener listener;

        public AdjustSeekBarChangeListener() {
            listener = new ImageAdjuster.ImageAdjustingListener() {
                @Override
                public void onAdjustingStarted() {

                }

                @Override
                public void onAdjustingFailed() {
                }

                @Override
                public void onAdjustingComplete(View view, Bitmap result) {
                    bitmapStepNow = result;
                }

                @Override
                public void onAdjustingCancelled() {

                }
            };
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.brightness_seekbar:
                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, (int)((seekBar.getProgress() - 80)*0.5), ADJUSTER_TYPE.BRIGHTNESS, listener);
                    break;
                case R.id.contrast_seekbar:
                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, (int)((seekBar.getProgress() - 70)*0.5), ADJUSTER_TYPE.CONTRAST, listener);
                    break;
                case R.id.Saturation_seekbar:
                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, (int)((seekBar.getProgress() - 100)*0.5), ADJUSTER_TYPE.SATURATION, listener);
                    break;
                case R.id.R_seekbar:
                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, seekBar.getProgress() - 100, ADJUSTER_TYPE.RED_OFFSET, listener);
                    break;
                case R.id.G_seekbar:
                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, seekBar.getProgress() - 100, ADJUSTER_TYPE.GREEN_OFFSET, listener);
                    break;
                case R.id.B_seekbar:
                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, seekBar.getProgress() - 100, ADJUSTER_TYPE.BLUE_OFFSET, listener);
                    break;
//                case R.id.ruihua_seekbar:
//                    ImageAdjuster.getInstance().displayImageAdjusted(mOperatingBitmap, mMainImageView, seekBar.getProgress() - 180, ADJUSTER_TYPE.SATURATION, listener);
//                    break;
                default:
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class AdjustClickListener implements View.OnClickListener {
        private void allAdjustLayoutGONE(){
//            mAdjustDetailDetailLayout.setVisibility(View.GONE);
            mAdjustDetailRGBLayout.setVisibility(View.GONE);
            mAdjustDetailBrightnessLayout.setVisibility(View.GONE);
            mAdjustDetailContrastLayout.setVisibility(View.GONE);
            mAdjustDetailSaturationLayout.setVisibility(View.GONE);
//            mAdjustDetailTiaoseLayout.setVisibility(View.GONE);
            ButtonColorReset();
        }

        private void ButtonColorReset(){
            ButtonBrightness.colorReset();
            ButtonRGB.colorReset();
            ButtonContrast.colorReset();
            ButtonSaturation.colorReset();
        }

        @Override
        public void onClick(View v) {
            allAdjustLayoutGONE();
            switch (v.getId()) {
                case R.id.adjust_brightness:
                    mAdjustDetailBrightnessLayout.setVisibility(View.VISIBLE);
                    ButtonBrightness.colorPressed();
//                    mAdjustDetailDetailLayout.setVisibility(View.GONE);
//                    mAdjustDetailRGBLayout.setVisibility(View.GONE);
//                    mAdjustDetailTiaoseLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.adjust_contrast:
                    mAdjustDetailContrastLayout.setVisibility(View.VISIBLE);
                    ButtonContrast.colorPressed();
//                    mAdjustDetailDetailLayout.setVisibility(View.GONE);
//                    mAdjustDetailRGBLayout.setVisibility(View.VISIBLE);
//                    mAdjustDetailTiaoseLayout.setVisibility(View.GONE);
                    break;
                case R.id.adjust_saturation:
                    mAdjustDetailSaturationLayout.setVisibility(View.VISIBLE);
                    ButtonSaturation.colorPressed();
//                    mAdjustDetailDetailLayout.setVisibility(View.VISIBLE);
//                    mAdjustDetailRGBLayout.setVisibility(View.GONE);
//                    mAdjustDetailTiaoseLayout.setVisibility(View.GONE);
                    break;
                case R.id.adjust_rgb:
                    mAdjustDetailRGBLayout.setVisibility(View.VISIBLE);
                    ButtonRGB.colorPressed();
                    break;
                default:
            }
        }

    }

    private class WordClickListener implements View.OnClickListener {
        private void wordLayoutAllGONE() {
            rFontDetailLayout.setVisibility(View.GONE);
            rTouMingDetailLayout.setVisibility(View.GONE);
            rColorDetailLayout.setVisibility(View.GONE);
            rShadowDetailLayout.setVisibility(View.GONE);
            rTextSizeDetailLayout.setVisibility(View.GONE);
            findViewById(R.id.relativeLayout_HorizontalOrVertical).setVisibility(View.GONE);
            findViewById(R.id.linearlayout_move).setVisibility(View.GONE);
            findViewById(R.id.relativeLayout_word_rotate).setVisibility(View.GONE);
//            allButtonChoosenAndUnclickable();
        }

        private void allButtonChoosenAndUnclickable() {
            setButtonOnChoosenAndUnclickable(word_imageButton_color, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_font, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_hor_ver, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_tmd, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_shadow, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_rotate, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_font, false);
            setButtonOnChoosenAndUnclickable(word_imageButton_move, false);
        }

        @Override
        public void onClick(View v) {
            wordLayoutAllGONE();
            switch (v.getId()) {
                case R.id.word_imageButton_color:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    rColorDetailLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_shadow:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    rShadowDetailLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_tmd:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    rTouMingDetailLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_font:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    rFontDetailLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_textsize:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    rTextSizeDetailLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_hor_ver:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    findViewById(R.id.relativeLayout_HorizontalOrVertical).setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_move:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    findViewById(R.id.linearlayout_move).setVisibility(View.VISIBLE);
                    break;
                case R.id.word_imageButton_rotate:
//                    setButtonOnChoosenAndUnclickable(v,true);
                    findViewById(R.id.relativeLayout_word_rotate).setVisibility(View.VISIBLE);
                default:
            }
        }
    }

    private class BlurAsyncTask extends AsyncTask<Object, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... params) {
            int progress_blur = (Integer)params[1];
            if (progress_blur == 0) {
                return (Bitmap)params[0];
            }
            Matrix matrix = new Matrix();
            Matrix matrixEnlarge = new Matrix();
            Bitmap bitmap = (Bitmap)params[0];
            Bitmap bitmap_blurred;
            Log.e(TAG,""+progress_blur);

            if (progress_blur < 5) {
                matrix.postScale(0.5f, 0.5f); // 长和宽放大缩小的比例
                Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                Bitmap result = rsBlur(resizeBmp, HandleImageActivity.this, progress_blur * 3 + 3);
                matrixEnlarge.postScale(1 / 0.5f, 1 / 0.5f);
                Bitmap resizeBmp0 = Bitmap.createBitmap(result, 0, 0,
                        result.getWidth(), result.getHeight(), matrixEnlarge, true);

                bitmap_blurred = resizeBmp0;
                resizeBmp.recycle();
                result.recycle();
            } else if (progress_blur < 13) {
                matrix.postScale(0.4f, 0.4f); // 长和宽放大缩小的比例
                Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                Bitmap result0 = rsBlur(resizeBmp, HandleImageActivity.this, progress_blur * 2);
                Bitmap result = rsBlur(result0, HandleImageActivity.this, progress_blur + 8);
                matrixEnlarge.postScale(1 / 0.4f, 1 / 0.4f);
                Bitmap resizeBmp0 = Bitmap.createBitmap(result, 0, 0,
                        result.getWidth(), result.getHeight(), matrixEnlarge, true);

                bitmap_blurred = resizeBmp0;

                resizeBmp.recycle();
                result0.recycle();
                result.recycle();

            } else if (progress_blur < 23) {
                Log.e(TAG,""+progress_blur);
                matrix.postScale(0.25f, 0.25f); // 长和宽放大缩小的比例
                Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                Bitmap result0 = rsBlur(resizeBmp, HandleImageActivity.this, progress_blur + 3);
                Bitmap result1 = rsBlur(result0, HandleImageActivity.this, progress_blur + 3);
                Bitmap result = rsBlur(result1, HandleImageActivity.this, progress_blur + 3);
                matrixEnlarge.postScale(1 / 0.25f, 1 / 0.25f);
                Bitmap resizeBmp0 = Bitmap.createBitmap(result, 0, 0,
                        result.getWidth(), result.getHeight(), matrixEnlarge, true);
                bitmap_blurred = resizeBmp0;

                resizeBmp.recycle();
                result0.recycle();
                result1.recycle();
                result.recycle();

            } else if (progress_blur == 23) {

                matrix.postScale(0.2f, 0.2f); // 长和宽放大缩小的比例
                Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                Bitmap result0 = rsBlur(resizeBmp, HandleImageActivity.this, 25);
                Bitmap result1 = rsBlur(result0, HandleImageActivity.this, 25);
                Bitmap result2 = rsBlur(result1, HandleImageActivity.this, 25);
                Bitmap result = rsBlur(result2, HandleImageActivity.this, 25);
                matrixEnlarge.postScale(1 / 0.2f, 1 / 0.2f);
                Bitmap resizeBmp0 = Bitmap.createBitmap(result, 0, 0,
                        result.getWidth(), result.getHeight(), matrixEnlarge, true);

                bitmap_blurred = resizeBmp0;
                resizeBmp.recycle();
                result0.recycle();
                result1.recycle();
                result.recycle();
            } else {
                matrix.postScale(0.17f, 0.17f); // 长和宽放大缩小的比例
                Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                Bitmap result0 = rsBlur(resizeBmp, HandleImageActivity.this, 25);
                Bitmap result1 = rsBlur(result0, HandleImageActivity.this, 25);
                Bitmap result2 = rsBlur(result1, HandleImageActivity.this, 25);
                Bitmap result3 = rsBlur(result2, HandleImageActivity.this, 25);
                Bitmap result = rsBlur(result3, HandleImageActivity.this, 25);

                matrixEnlarge.postScale(1 / 0.17f, 1 / 0.17f);
                Bitmap resizeBmp0 = Bitmap.createBitmap(result, 0, 0,
                        result.getWidth(), result.getHeight(), matrixEnlarge, true);
                bitmap_blurred = resizeBmp0;

                resizeBmp.recycle();
                result0.recycle();
                result1.recycle();
                result2.recycle();
                result3.recycle();
                result.recycle();
            }

            return bitmap_blurred;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bitmapStepNow = bitmap;
//            mOperatingBitmap = bitmap;
            mMainImageView.setImageBitmap(bitmapStepNow);
        }
    }

    class ImageButtonColorOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_color1:
                    textView.setTextColor(getResources().getColor(R.color.color1));
                    break;
                case R.id.imageButton_color2:
                    textView.setTextColor(getResources().getColor(R.color.color2));
                    break;
                case R.id.imageButton_color3:
                    textView.setTextColor(getResources().getColor(R.color.color3));
                    break;
                case R.id.imageButton_color4:
                    textView.setTextColor(getResources().getColor(R.color.color4));
                    break;
                case R.id.imageButton_color5:
                    textView.setTextColor(getResources().getColor(R.color.color5));
                    break;
                case R.id.imageButton_color6:
                    textView.setTextColor(getResources().getColor(R.color.color6));
                    break;
                case R.id.imageButton_color7:
                    textView.setTextColor(getResources().getColor(R.color.color7));
                    break;
                case R.id.imageButton_color8:
                    textView.setTextColor(getResources().getColor(R.color.color8));
                    break;
                case R.id.imageButton_color9:
                    textView.setTextColor(getResources().getColor(R.color.color9));
                    break;
                case R.id.imageButton_color10:
                    textView.setTextColor(getResources().getColor(R.color.color10));
                    break;
                case R.id.imageButton_color11:
                    textView.setTextColor(getResources().getColor(R.color.color11));
                    break;
                case R.id.imageButton_color12:
                    textView.setTextColor(getResources().getColor(R.color.color12));
                    break;
                case R.id.imageButton_color13:
                    textView.setTextColor(getResources().getColor(R.color.color13));
                    break;
                case R.id.imageButton_color14:
                    textView.setTextColor(getResources().getColor(R.color.color14));
                    break;
                case R.id.imageButton_color15:
                    textView.setTextColor(getResources().getColor(R.color.color15));
                    break;
                case R.id.imageButton_color16:
                    textView.setTextColor(getResources().getColor(R.color.color16));
                    break;
                case R.id.imageButton_color17:
                    textView.setTextColor(getResources().getColor(R.color.color17));
                    break;
                case R.id.imageButton_color18:
                    textView.setTextColor(getResources().getColor(R.color.color18));
                    break;
                case R.id.imageButton_color19:
                    textView.setTextColor(getResources().getColor(R.color.color19));
                    break;
                case R.id.imageButton_color20:
                    textView.setTextColor(getResources().getColor(R.color.color20));
                    break;
                case R.id.imageButton_color21:
                    textView.setTextColor(getResources().getColor(R.color.color21));
                    break;
                case R.id.imageButton_color22:
                    textView.setTextColor(getResources().getColor(R.color.color22));
                    break;
                case R.id.imageButton_color23:
                    textView.setTextColor(getResources().getColor(R.color.color23));
                    break;
                case R.id.imageButton_color24:
                    textView.setTextColor(getResources().getColor(R.color.color24));
                    break;
                case R.id.imageButton_color25:
                    textView.setTextColor(getResources().getColor(R.color.color25));
                    break;
                case R.id.imageButton_color26:
                    textView.setTextColor(getResources().getColor(R.color.color26));
                    break;
                case R.id.imageButton_color27:
                    textView.setTextColor(getResources().getColor(R.color.color27));
                    break;
                case R.id.imageButton_color28:
                    textView.setTextColor(getResources().getColor(R.color.color28));
                    break;
                case R.id.imageButton_color29:
                    textView.setTextColor(getResources().getColor(R.color.color29));
                    break;
                case R.id.imageButton_color30:
                    textView.setTextColor(getResources().getColor(R.color.color30));
                    break;
                case R.id.imageButton_color31:
                    textView.setTextColor(getResources().getColor(R.color.color31));
                    break;
                case R.id.imageButton_color32:
                    textView.setTextColor(getResources().getColor(R.color.color32));
                    break;
                case R.id.imageButton_color33:
                    textView.setTextColor(getResources().getColor(R.color.color33));
                    break;
                case R.id.imageButton_color34:
                    textView.setTextColor(getResources().getColor(R.color.color34));
                    break;
                case R.id.imageButton_color35:
                    textView.setTextColor(getResources().getColor(R.color.color35));
                    break;

                case R.id.imageButton_second_color1:
                    textView.setTextColor(getResources().getColor(R.color.seccolor1));
                    break;
                case R.id.imageButton_second_color2:
                    textView.setTextColor(getResources().getColor(R.color.seccolor2));
                    break;
                case R.id.imageButton_second_color3:
                    textView.setTextColor(getResources().getColor(R.color.seccolor3));
                    break;
                case R.id.imageButton_second_color4:
                    textView.setTextColor(getResources().getColor(R.color.seccolor4));
                    break;
                case R.id.imageButton_second_color5:
                    textView.setTextColor(getResources().getColor(R.color.seccolor5));
                    break;
                case R.id.imageButton_second_color6:
                    textView.setTextColor(getResources().getColor(R.color.seccolor6));
                    break;
                case R.id.imageButton_second_color7:
                    textView.setTextColor(getResources().getColor(R.color.seccolor7));
                    break;
                case R.id.imageButton_second_color8:
                    textView.setTextColor(getResources().getColor(R.color.seccolor8));
                    break;
                case R.id.imageButton_second_color9:
                    textView.setTextColor(getResources().getColor(R.color.seccolor9));
                    break;
                case R.id.imageButton_second_color10:
                    textView.setTextColor(getResources().getColor(R.color.seccolor10));
                    break;
                case R.id.imageButton_second_color11:
                    textView.setTextColor(getResources().getColor(R.color.seccolor11));
                    break;
                case R.id.imageButton_second_color12:
                    textView.setTextColor(getResources().getColor(R.color.seccolor12));
                    break;
                case R.id.imageButton_second_color13:
                    textView.setTextColor(getResources().getColor(R.color.seccolor13));
                    break;
                case R.id.imageButton_second_color14:
                    textView.setTextColor(getResources().getColor(R.color.seccolor14));
                    break;
                case R.id.imageButton_second_color15:
                    textView.setTextColor(getResources().getColor(R.color.seccolor15));
                    break;
                case R.id.imageButton_second_color16:
                    textView.setTextColor(getResources().getColor(R.color.seccolor16));
                    break;
                case R.id.imageButton_second_color17:
                    textView.setTextColor(getResources().getColor(R.color.seccolor17));
                    break;
                case R.id.imageButton_second_color18:
                    textView.setTextColor(getResources().getColor(R.color.seccolor18));
                    break;
                case R.id.imageButton_second_color19:
                    textView.setTextColor(getResources().getColor(R.color.seccolor19));
                    break;
                case R.id.imageButton_second_color20:
                    textView.setTextColor(getResources().getColor(R.color.seccolor20));
                    break;
                case R.id.imageButton_second_color21:
                    textView.setTextColor(getResources().getColor(R.color.seccolor21));
                    break;
                case R.id.imageButton_second_color22:
                    textView.setTextColor(getResources().getColor(R.color.seccolor22));
                    break;
                case R.id.imageButton_second_color23:
                    textView.setTextColor(getResources().getColor(R.color.seccolor23));
                    break;
                case R.id.imageButton_second_color24:
                    textView.setTextColor(getResources().getColor(R.color.seccolor24));
                    break;
                case R.id.imageButton_second_color25:
                    textView.setTextColor(getResources().getColor(R.color.seccolor25));
                    break;
                case R.id.imageButton_second_color26:
                    textView.setTextColor(getResources().getColor(R.color.seccolor26));
                    break;
                case R.id.imageButton_second_color27:
                    textView.setTextColor(getResources().getColor(R.color.seccolor27));
                    break;
                case R.id.imageButton_second_color28:
                    textView.setTextColor(getResources().getColor(R.color.seccolor28));
                    break;
                case R.id.imageButton_second_color29:
                    textView.setTextColor(getResources().getColor(R.color.seccolor29));
                    break;
                case R.id.imageButton_second_color30:
                    textView.setTextColor(getResources().getColor(R.color.seccolor30));
                    break;
                case R.id.imageButton_second_color31:
                    textView.setTextColor(getResources().getColor(R.color.seccolor31));
                    break;
                case R.id.imageButton_second_color32:
                    textView.setTextColor(getResources().getColor(R.color.seccolor32));
                    break;
                case R.id.imageButton_second_color33:
                    textView.setTextColor(getResources().getColor(R.color.seccolor33));
                    break;
                case R.id.imageButton_second_color34:
                    textView.setTextColor(getResources().getColor(R.color.seccolor34));
                    break;
                case R.id.imageButton_second_color35:
                    textView.setTextColor(getResources().getColor(R.color.seccolor35));
                    break;
                default:
                    Log.e("Lei", "second_color_imagebutton switch click");
            }
        }
    }

    private class FontClickListener implements View.OnClickListener {
        private String fontString;
        private Typeface typeFace;

        protected FontClickListener(String fontString) {
            this.fontString = fontString;
        }

        protected FontClickListener(Typeface tf) {
            typeFace = tf;
        }

        protected FontClickListener() {
            this.fontString = null;
        }

        @Override
        public void onClick(View v) {
            if (textView == null) {
                textView = new MoFaTextView(HandleImageActivity.this);
                textView.setOnFocusedListener(mMoFaTextViewOnFocusedListener);
                mainLayout.addView(textView);
                textView.setDismissWhenFocusOnTouchOutside();

            }
            if (fontString != null) {
                textView.setTypeface(fontString);
            } else {
                if (typeFace == null) {
                    textView.setTypeface(Typeface.DEFAULT);
                } else {
                    textView.setTypeface(typeFace);
                }
            }
        }
    }
}