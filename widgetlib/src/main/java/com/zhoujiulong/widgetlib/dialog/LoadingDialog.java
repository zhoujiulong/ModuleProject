package com.zhoujiulong.widgetlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.zhoujiulong.widgetlib.R;


/**
 * @author zhoujiulong
 * @createtime 2019/2/26 11:43
 * LoadingDialog
 */
public class LoadingDialog extends Dialog {

    private ImageView mViewLoading;
    private AnimationDrawable mLoadingAni;

    public static LoadingDialog build(Context context) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(true);
        return loadingDialog;
    }

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.LoadingDialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void show() {
        super.show();
        if (mViewLoading == null) {
            mViewLoading = findViewById(R.id.iv_loading);
            mViewLoading.setImageResource(R.drawable.anim_loading);
        }
        if (mLoadingAni == null) {
            mLoadingAni = (AnimationDrawable) mViewLoading.getDrawable();
        }
        mLoadingAni.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mLoadingAni.stop();
    }

    @Override
    public void cancel() {
        super.cancel();
        mLoadingAni.stop();
    }
}





















