package com.muzhi.camerasdk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.muzhi.camerasdk.activity.PhotoPickActivityCamera;
import com.muzhi.camerasdk.activity.PreviewActivityCamera;
import com.muzhi.camerasdk.library.utils.PhotoUtils;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;

import java.util.ArrayList;


public class PyxCamera implements LuBanSDK.LuBanImageFace {

    private Activity activity;
    private CameraSdkParameterInfo cameraSdkParameterInfo;
    private CameraImageCallBack cameraImageCallBack;


    /**
     *
     * @param activity
     * @param cameraSdkParameterInfo 选择图片参数
     * @param cameraImageCallBack 图片回调
     */
    public PyxCamera(Activity activity, CameraSdkParameterInfo cameraSdkParameterInfo, CameraImageCallBack cameraImageCallBack) {
        this.activity = activity;
        this.cameraSdkParameterInfo = cameraSdkParameterInfo;
        this.cameraImageCallBack = cameraImageCallBack;
    }


    //打开相册
    public void openPhotoPick(Activity activity) {
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, cameraSdkParameterInfo);

        Intent intent = new Intent(activity, PhotoPickActivityCamera.class);
        intent.putExtras(b);
        activity.startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
    }


    /**
     *
     * @param activity
     * @param position 展示第几个图片
     */
    public void openImagePreview(Activity activity, int position) {
        cameraSdkParameterInfo.setPosition(position);
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, cameraSdkParameterInfo);

        Intent intent = new Intent(activity.getApplication(), PreviewActivityCamera.class);
        intent.putExtras(b);
        activity.startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW);
    }


    public void onActivityCameraResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY:
                if (data == null) {
                    return;
                }
                ArrayList<String> list = getImageList(data.getExtras());
                LuBanSDK.getInstance().compressImage(activity, list);
                LuBanSDK.getInstance().setLuBanImageFace(this);
                break;
            case CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW:
                if (data == null) {
                    return;
                }
                int position = data.getIntExtra("position", -1);
                cameraImageCallBack.deleteItem(position);
                cameraSdkParameterInfo.getImage_list().remove(position);
                break;
        }
    }


    private ArrayList<String> getImageList(Bundle bundle) {
        if (bundle != null) {
            cameraSdkParameterInfo = (CameraSdkParameterInfo) bundle.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER);
            ArrayList<String> list = cameraSdkParameterInfo.getImage_list();
            return list;
        } else {
            return null;
        }
    }

    @Override
    public void returnImageList(ArrayList<String> file) {
        cameraImageCallBack.returnImageList(file, file.get(0));
    }

    @Override
    public void onStartImages() {

    }

    @Override
    public void onError(String error) {
        cameraImageCallBack.onImageReturnError(error);
    }


    public interface CameraImageCallBack {
        void returnImageList(ArrayList<String> list, String oneImage);

        void onImageReturnError(String error);

        void deleteItem(int position);
    }
}
