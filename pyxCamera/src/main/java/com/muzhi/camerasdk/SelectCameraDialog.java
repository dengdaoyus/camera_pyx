package com.muzhi.camerasdk;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class SelectCameraDialog extends Dialog {

    private View layoutView;
    private TextView cameraBtn;
    private TextView photoBtn;
    private SelectCameraDialogFace dialogFace;

    public void setDialogFace(SelectCameraDialogFace dialogFace) {
        this.dialogFace = dialogFace;
    }

    public SelectCameraDialog(@NonNull Context context) {
        super(context, R.style.MyNewAlertDialog);
        initCameraDialog(context);
    }

    private void initCameraDialog(Context mContext) {
        layoutView = LayoutInflater.from(mContext).inflate(R.layout.dialog_select_camera, null);
        cameraBtn = (TextView) layoutView.findViewById(R.id.dialog_camera);
        photoBtn = (TextView) layoutView.findViewById(R.id.dialog_photo);
        setContentView(layoutView);
        initDialogWindow();
        setOnClickListener();
    }

    private void initDialogWindow() {
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void showDialog() {
        show();
    }


    private void setOnClickListener() {
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogFace != null) {
                    dialogFace.cameraBtn();
                    dismiss();
                }
            }
        });

        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogFace != null) {
                    dialogFace.photoBtn();
                    dismiss();
                }
            }
        });
    }

    public interface SelectCameraDialogFace {
        void cameraBtn();

        void photoBtn();
    }
}
