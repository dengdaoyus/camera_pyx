package com.muzhi.camerasdk;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2017\11\18 0018.
 */

public class LuBanSDK {

    public static LuBanSDK instance;
    private static ArrayList<String> images;

    public static synchronized LuBanSDK getInstance() {
        if (instance == null) {
            instance = new LuBanSDK();
            images = new ArrayList<>();
        }
        return instance;
    }

    private LuBanImageFace luBanImageFace;

    public void setLuBanImageFace(LuBanImageFace luBanImageFace) {
        this.luBanImageFace = luBanImageFace;
    }

    public void compressImage(Context context, final ArrayList<String> files) {
        this.images.clear();
        Luban.with(context)
                .load(files)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        if (luBanImageFace != null) {
                            luBanImageFace.onStartImages();
                        }
                    }

                    @Override
                    public void onSuccess(File file) {
                        images.add(file.getAbsolutePath());
                        if (luBanImageFace != null) {
                            if (files.size() == images.size()) {
                                luBanImageFace.returnImageList(images);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (luBanImageFace != null) {
                            luBanImageFace.onError(e.getMessage());
                        }
                    }
                }).launch();
    }

    public interface LuBanImageFace {

        void returnImageList(ArrayList<String> file);

        void onStartImages();

        void onError(String error);

    }
}
