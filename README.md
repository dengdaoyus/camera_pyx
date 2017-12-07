# camera_pyx
一款基于cameraSDK上添加了鲁班算法的图片框架
```java

    /**
     * 
     * @param activity  
     * @param cameraSdkParameterInfo 选择图片参数对象
     * @param cameraImageCallBack 图片回调
     */
     //第一步
     PyxCamera pyxCamera = new PyxCamera(this, info, this);
     
     //二、重写回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pyxCamera.onActivityCameraResult(requestCode, resultCode, data);
    }
   
    //三、打开自定义相册，选择图片
    pyxCamera.openPhotoPick(MainActivity.this);
    
     /**
     * 
     * @param activity
     * @param position 展示第几个图片
     */
    //四、图片浏览(选择性使用，可以用自己写的)
    pyxCamera.openImagePreview(MainActivity.this, 0);

