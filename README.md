# camera_pyx
图片多选、单选、框架，使用简单，UI 可以定制

![image](https://github.com/Mypyx/camera_pyx/raw/master/Gif/device-2017-12-11-142814_0-515.gif)   
![image](https://github.com/Mypyx/camera_pyx/raw/master/Gif/device-2017-12-11-143346_0-756.gif)   

```java

====================================================依赖添加=======================================================	
	allprojects {
    		repositories {
     		   jcenter()
       		    maven { url "https://jitpack.io" }
                 }
              }
	      
	      
	 //添加依赖
	       compile 'com.github.Mypyx:camera_pyx:pyx_1.0.1'
	      

====================================================Parameter======================================================

    //最大图片选择数，int类型，默认9
    private int maxImage = 9;
    //图片选择模式，默认多选
    private boolean singleMode = false;
    //正方形的裁剪图片必须与单张相结合
    private boolean cutoutImage = false;
    //是否打开dialog选中相机、相册对话框
    private boolean isOpenDialog = true;
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
    //设置标题字体大小
    private int textSize;
    //设置完成按钮的背景颜色
    private int completeColor;
    //设置完成按钮的文本
    private String completeText;
    //设置完成按钮的文本大小
    private int completeTextSize;
    //设置取消的文本
    private String cancelText;
    //设置取消的 字体颜色
    private int cancelColor;
    //设置取消的 字体大小
    private int cancelTextSize;


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
