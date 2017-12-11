package com.muzhi.camerasdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.muzhi.camerasdk.activity.PhotoPickActivityCamera;
import com.muzhi.camerasdk.activity.PreviewActivityCamera;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.muzhi.camerasdk.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW;


public class PyxCamera implements LuBanSDK.LuBanImageFace, SelectCameraDialog.SelectCameraDialogFace {

    private File mTmpFile;
    private Activity activity;
    private SelectCameraDialog selectCameraDialog;
    private CameraSdkParameterInfo cameraSdkParameterInfo;
    private CameraImageCallBack cameraImageCallBack;


    public PyxCamera(Activity activity, CameraSdkParameterInfo cameraSdkParameterInfo, CameraImageCallBack cameraImageCallBack) {
        this.activity = activity;
        this.cameraSdkParameterInfo = cameraSdkParameterInfo;
        this.cameraImageCallBack = cameraImageCallBack;
        this.selectCameraDialog = new SelectCameraDialog(activity);
        this.selectCameraDialog.setDialogFace(this);
    }

    public void openCameraSdk() {
        if (cameraSdkParameterInfo.isSingle_mode()) {
            selectCameraDialog.showDialog();
        } else {
            openPhotoPick();
        }
    }

    @Override
    public void cameraBtn() {
        showCameraAction();
    }

    @Override
    public void photoBtn() {
        openPhotoPick();
    }

    private void openPhotoPick() {
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent(activity, PhotoPickActivityCamera.class);
        intent.putExtras(b);
        activity.startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
    }


    private void showCameraAction() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            mTmpFile = FileUtils.createTmpFile(activity);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            activity.startActivityForResult(cameraIntent, TAKE_PICTURE_PREVIEW);
        } else {
            Toast.makeText(activity, R.string.camerasdk_msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }


    public void openImagePreview(Activity activity, int position) {
        cameraSdkParameterInfo.setPosition(position);
        Bundle b = new Bundle();
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent(activity.getApplication(), PreviewActivityCamera.class);
        intent.putExtras(b);
        activity.startActivityForResult(intent, TAKE_PICTURE_PREVIEW);
    }


    public void onActivityCameraResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY:
                setMultiMode(data);
                break;
            case TAKE_PICTURE_PREVIEW:
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

    public void setMultiMode(Intent data) {
        if (data == null) {
            return;
        }
        ArrayList<String> list = getImageList(data.getExtras());
        LuBanSDK.getInstance().compressImage(activity, list);
        LuBanSDK.getInstance().setLuBanImageFace(this);
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
