package com.muzhi.camerasdk.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.muzhi.camerasdk.R;
import com.muzhi.camerasdk.library.utils.PhotoUtils;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.muzhi.camerasdk.model.Constants;
import com.muzhi.camerasdk.view.CropImageView;


public class CutActivityCamera extends CameraBaseActivity {

    private CropImageView mCropView;
    private TextView btn_done;

    private ProgressDialog progressDialog;
    private CameraSdkParameterInfo parameterInfo;
    private RadioGroup layout_crop, layout_tab;
    private LinearLayout layout_rotation;
    private Bitmap sourceMap;

    // Handle机制
    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            String path = PhotoUtils.saveAsBitmap(mContext, mCropView.getCroppedBitmap());
            PhotoPickActivityCamera.instance.getForResultComplate(path);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camerasdk_activity_cut);
        showLeftIcon();
        setActionBarTitle("裁剪");
        findViews();
        Bundle b = getIntent().getExtras();
        try {
            parameterInfo = (CameraSdkParameterInfo) b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
        } catch (Exception e) {
        }
        if (parameterInfo != null) {
            sourceMap = PhotoUtils.getBitmap(parameterInfo.getImage_list().get(0));
            mCropView.setImageBitmap(sourceMap);
        } else {
            sourceMap = Constants.bitmap;
            mCropView.setImageBitmap(sourceMap);
        }
    }

    private void findViews() {
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        btn_done = (TextView) findViewById(R.id.camerasdk_title_txv_right_text);
        btn_done.setVisibility(View.VISIBLE);
        btn_done.setText("确定");

        layout_crop = (RadioGroup) findViewById(R.id.layout_crop);
        layout_tab = (RadioGroup) findViewById(R.id.layout_tab);
        layout_rotation = (LinearLayout) findViewById(R.id.layout_rotation);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("加载中···");
        progressDialog.setCancelable(false);
        initEvent();

    }

    private void initEvent() {
        findViewById(R.id.button1_1).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_1_1);
            }
        });
        findViewById(R.id.button3_4).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
            }
        });
        findViewById(R.id.button4_3).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
            }
        });
        findViewById(R.id.button9_16).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
            }
        });
        findViewById(R.id.button16_9).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
            }
        });
        btn_done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                done();
            }
        });

        layout_tab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                if (arg1 == R.id.button_crop) {
                    layout_crop.setVisibility(View.VISIBLE);
                    layout_rotation.setVisibility(View.GONE);
                } else {
                    layout_crop.setVisibility(View.GONE);
                    layout_rotation.setVisibility(View.VISIBLE);
                }
            }
        });

        findViewById(R.id.ratation_left).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.rotateImage(sourceMap, -90);
                mCropView.setImageBitmap(sourceMap);
            }
        });
        findViewById(R.id.ratation_right).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.rotateImage(sourceMap, 90);
                mCropView.setImageBitmap(sourceMap);
            }
        });
        findViewById(R.id.ratation_vertical).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.reverseImage(sourceMap, -1, 1);
                mCropView.setImageBitmap(sourceMap);
            }
        });
        findViewById(R.id.ratation_updown).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceMap = PhotoUtils.reverseImage(sourceMap, 1, -1);
                mCropView.setImageBitmap(sourceMap);
            }
        });


    }

    private void done() {
        if (parameterInfo != null) {
            new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.show();
                            mHandler.sendEmptyMessage(0x111);
                        }
                    });

                }
            }.start();

        } else {
            Constants.bitmap = mCropView.getCroppedBitmap();
            setResult(Constants.RequestCode_Croper);
            finish();
        }
    }


}
