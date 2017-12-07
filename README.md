# camera_pyx
(仿微信选择图片带压缩)一款基于cameraSDK上添加了鲁班算法的图片框架
```java


    	//设置参数
  	CameraSdkParameterInfo info;
	
	public boolean is_net_path=false;		//是否来自网络默认为否
	private int ret_type=0;			        //返回类型(0:返回生成的图片路径 1:返回生成的Bitmap)    默认为0
	private int max_image = 9;			//最大图片选择数，int类型，默认9
	private boolean single_mode = false;	        //图片选择模式，默认多选
	private boolean croper_image=false;		//正方形的裁剪图片必须与单张相结合
	private boolean show_camera = true;		//是否显示相机，默认显示
	private int position = 0;			//在预览时带的下标，也可作为标识使用

//=======================================使用步骤===========================================================
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
     button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pyxCamera.openPhotoPick(MainActivity.this);
            }
        });
        
    
    
     /**
     * 
     * @param activity
     * @param position 展示第几个图片
     */
     
    //四、图片浏览(选择性使用，可以用自己写的)
    imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //四、图片浏览
                pyxCamera.openImagePreview(MainActivity.this, 0);
            }
        });

