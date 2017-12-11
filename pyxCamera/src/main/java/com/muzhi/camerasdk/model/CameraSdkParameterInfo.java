package com.muzhi.camerasdk.model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 参数
 */
public class CameraSdkParameterInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String EXTRA_PARAMETER = "extra_camerasdk_parameter";
    public static final int TAKE_PICTURE_FROM_CAMERA = 100;
    public static final int TAKE_PICTURE_FROM_GALLERY = 200;
    public static final int TAKE_PICTURE_PREVIEW = 300;
    public static final int TAKE_PICTURE_SINGLE_CAMERA = 400;


    //====================================================Parameter==================================================


    //最大图片选择数，int类型，默认9
    private int maxImage = 9;
    //图片选择模式，默认多选
    private boolean singleMode = false;
    //正方形的裁剪图片必须与单张相结合
    private boolean cutoutImage = false;
    //是否显示相机，默认显示
    private boolean showCamera = true;
    //在预览时带的下标，也可作为标识使用
    private int position = 0;
    //图片的路径集合
    private ArrayList<String> imageList;


    //=====================================================================UI======================================================

    //设置标题文本内容
    private String titleName;
    //设置标题颜色
    private int titleColor;









    public int getMaxImage() {
        return maxImage;
    }

    public void setMaxImage(int maxImage) {
        this.maxImage = maxImage;
    }

    public boolean isSingleMode() {
        return singleMode;
    }

    public void setSingleMode(boolean singleMode) {
        this.singleMode = singleMode;
    }

    public boolean isCutoutImage() {
        return cutoutImage;
    }

    public void setCutoutImage(boolean cutoutImage) {
        this.cutoutImage = cutoutImage;
    }

    public boolean isShowCamera() {
        return showCamera;
    }

    public void setShowCamera(boolean showCamera) {
        this.showCamera = showCamera;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<String> getImageList() {
        if (imageList == null) {
            imageList = new ArrayList<>();
        }
        return imageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
}
