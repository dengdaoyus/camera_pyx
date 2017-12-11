package com.muzhi.camerasdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.muzhi.camerasdk.activity.CutActivityCamera;
import com.muzhi.camerasdk.activity.PhotoPickActivityCamera;
import com.muzhi.camerasdk.activity.PreviewActivityCamera;
import com.muzhi.camerasdk.model.CameraSdkParameterInfo;
import com.muzhi.camerasdk.model.CutCameraResult;
import com.muzhi.camerasdk.utils.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;

import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.EXTRA_PARAMETER;
import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.TAKE_PICTURE_PREVIEW;
import static com.muzhi.camerasdk.model.CameraSdkParameterInfo.TAKE_PICTURE_SINGLE_CAMERA;


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
        EventBus.getDefault().register(this);
    }

    public void openCameraSdk() {
        if (cameraSdkParameterInfo.isSingle_mode() && cameraSdkParameterInfo.isCroper_image()) {
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
        b.putSerializable(EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent(activity, PhotoPickActivityCamera.class);
        intent.putExtras(b);
        activity.startActivityForResult(intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY);
    }


    private void showCameraAction() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            mTmpFile = FileUtils.createTmpFile(activity);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            activity.startActivityForResult(cameraIntent, TAKE_PICTURE_SINGLE_CAMERA);
        } else {
            Toast.makeText(activity, R.string.camerasdk_msg_no_camera, Toast.LENGTH_SHORT).show();
        }
    }


    public void openImagePreview(Activity activity, int position) {
        cameraSdkParameterInfo.setPosition(position);
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent(activity.getApplication(), PreviewActivityCamera.class);
        intent.putExtras(b);
        activity.startActivityForResult(intent, TAKE_PICTURE_PREVIEW);
    }


    public void onActivityCameraResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY:
                setImageListData(data);
                break;
            case TAKE_PICTURE_PREVIEW:
                setImageDeResult(data);
                break;
            case TAKE_PICTURE_SINGLE_CAMERA:
                setSingleModeResult();
                break;
        }
    }

    private void setImageDeResult(Intent data) {
        if (data == null) {
            return;
        }
        int position = data.getIntExtra("position", -1);
        cameraImageCallBack.deleteItem(position);
        cameraSdkParameterInfo.getImage_list().remove(position);
    }


    private void setSingleModeResult() {
        if (cameraSdkParameterInfo.isSingle_mode()) {
            cameraSdkParameterInfo.getImage_list().clear();
            cameraSdkParameterInfo.getImage_list().add(mTmpFile.getPath());
            stateCutActivity();
        }
    }

    private void stateCutActivity() {
        Bundle b = new Bundle();
        b.putSerializable(EXTRA_PARAMETER, cameraSdkParameterInfo);
        Intent intent = new Intent();
        intent.putExtras(b);
        intent = new Intent(activity, CutActivityCamera.class);
        intent.putExtras(b);
        activity.startActivity(intent);
    }

    //单选模式剪裁成功回调
    @Subscribe
    public void getCutCameraResult(CutCameraResult cutCameraResult) {
        cameraSdkParameterInfo.getImage_list().clear();
        cameraSdkParameterInfo.getImage_list().add(cutCameraResult.getPath());
        LuBanSDK.getInstance().compressImage(activity, cameraSdkParameterInfo.getImage_list());
        LuBanSDK.getInstance().setLuBanImageFace(this);
    }


    private ArrayList<String> getImageList(Bundle bundle) {
        if (bundle != null) {
            cameraSdkParameterInfo = (CameraSdkParameterInfo) bundle.getSerializable(EXTRA_PARAMETER);
            ArrayList<String> list = cameraSdkParameterInfo.getImage_list();
            return list;
        } else {
            return null;
        }
    }


    private void setImageListData(Intent data) {
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
