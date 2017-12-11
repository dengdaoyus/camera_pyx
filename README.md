# camera_pyx
(仿微信选择图片带压缩)一款基于cameraSDK上添加了鲁班算法的图片框架
```java

====================================================依赖添加=======================================================	
	allprojects {
    		repositories {
     		   jcenter()
       		    maven { url "https://jitpack.io" }
                 }
              }
	      
	      
	 //添加依赖
	 compile 'com.github.Mypyx:camera_pyx:pyx_1.0.0'
	      

====================================================Parameter======================================================

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

   
=======================================================UI================================================

    //设置标题文本内容
    private String titleName;
    //设置标题颜色
    private int titleColor;


=====================================================使用步骤===========================================================
    //一、实例化对象设置info对象设置SDK参数及UI
    private void initCamera() {
        info = new CameraSdkParameterInfo();
        //设置单选模式
        info.setSingleMode(true);
        //打开图片剪裁
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
