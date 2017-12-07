package com.muzhi.camerasdk.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * 相机管理类
 * jazzy
 */
public class CameraManager {

    private static CameraManager mInstance;
    private Stack<Activity> cameras = new Stack<Activity>();

    public static CameraManager getInst() {
        if (mInstance == null) {
            synchronized (CameraManager.class) {
                if (mInstance == null)
                    mInstance = new CameraManager();
            }
        }
        return mInstance;
    }

    public void addActivity(Activity act) {
        cameras.add(act);
    }

}
