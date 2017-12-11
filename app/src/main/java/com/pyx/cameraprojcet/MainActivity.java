package com.pyx.cameraprojcet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.muzhi.camerasdk.PyxCamera;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PyxCamera.CameraImageCallBack {

    private ImageView imageView;
    private Button button;

    private PyxCamera pyxCamera;
    private CameraSdkParameterInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        button = (Button) findViewById(R.id.image_btn);
        initCamera();
        setBtnOnClick();
        setImageOnClick();
    }

    //一、实例化对象设置info对象设置SDK参数及UI
    private void initCamera() {
        info = new CameraSdkParameterInfo();
        info.setSingleMode(true);
        info.setCutoutImage(true);
        pyxCamera = new PyxCamera(this, info, this);
    }


    //二、重写回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pyxCamera.onActivityCameraResult(requestCode, resultCode, data);
    }

    //三、打开自定义相册，选择图片
    private void setBtnOnClick() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pyxCamera.openCameraSdk();
            }
        });
    }

    //四、图片浏览(可选择性使用)
    private void setImageOnClick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pyxCamera.openImagePreview(MainActivity.this, 0);
            }
        });
    }


    @Override
    public void returnImageList(ArrayList<String> list) {
        Glide.with(this).load(list.get(0)).centerCrop().into(imageView);
    }

    @Override
    public void onImageReturnError(String error) {

    }

    @Override
    public void deleteItem(int position) {

    }
}
