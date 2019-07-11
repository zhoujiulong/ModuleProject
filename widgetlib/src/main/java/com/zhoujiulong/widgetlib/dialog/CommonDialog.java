package com.zhoujiulong.widgetlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.zhoujiulong.widgetlib.R;

/**
 * @author zhoujiulong
 * @createtime 2019/3/7 14:03
 */
public class CommonDialog extends Dialog implements View.OnClickListener {

    private TextView mTvMsg;
    private TextView mTvLeftBt;
    private TextView mTvRightBt;
    private View mViewLine;

    private Context mContext;
    private Listener mListener;
    private boolean mIsAutoDismiss = true;

    public static CommonDialog build(Context context, String msg) {
        CommonDialog commonDialog = new CommonDialog(context);
        commonDialog.setMsg(msg);
        return commonDialog;
    }

    private CommonDialog(@NonNull Context context) {
        this(context, R.style.CommonDialog);
    }

    private CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_common);
        mContext = context;

        mTvMsg = findViewById(R.id.tv_msg);
        mTvLeftBt = findViewById(R.id.tv_left);
        mTvRightBt = findViewById(R.id.tv_right);
        mViewLine = findViewById(R.id.view_center_line);

        mTvLeftBt.setOnClickListener(this);
        mTvRightBt.setOnClickListener(this);
    }

    public CommonDialog setMsg(CharSequence msg) {
        mTvMsg.setText(msg);
        return this;
    }

    public CommonDialog setLeftBtStr(String leftBtStr) {
        mTvLeftBt.setText(leftBtStr);
        if (TextUtils.isEmpty(leftBtStr)) {
            mTvLeftBt.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
        } else {
            mTvLeftBt.setVisibility(View.VISIBLE);
            mViewLine.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public CommonDialog setRightBtStr(String rightBtStr) {
        mTvRightBt.setText(rightBtStr);
        if (TextUtils.isEmpty(rightBtStr)) {
            mTvRightBt.setVisibility(View.GONE);
            mViewLine.setVisibility(View.GONE);
        } else {
            mTvRightBt.setVisibility(View.VISIBLE);
            mViewLine.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public CommonDialog setListener(Listener listener) {
        mListener = listener;
        return this;
    }

    public CommonDialog setMsgTextColorRes(@ColorRes int colorRes) {
        return setMsgTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public CommonDialog setMsgTextColor(@ColorInt int colorRes) {
        mTvMsg.setTextColor(colorRes);
        return this;
    }

    public CommonDialog setLeftBtTextColorRes(@ColorRes int colorRes) {
        return setLeftBtTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public CommonDialog setLeftBtTextColor(@ColorInt int colorRes) {
        mTvLeftBt.setTextColor(colorRes);
        return this;
    }

    public CommonDialog setRightBtTextColorRes(@ColorRes int colorRes) {
        return setRightBtTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    public CommonDialog setRightBtTextColor(@ColorInt int colorRes) {
        mTvRightBt.setTextColor(colorRes);
        return this;
    }

    public CommonDialog setAutoDimiss(boolean autoDimiss) {
        mIsAutoDismiss = autoDimiss;
        return this;
    }

    @Override
    public void onClick(View view) {
        if (mIsAutoDismiss) dismiss();
        int id = view.getId();
        if (id == R.id.tv_left) {
            if (mListener != null) mListener.onLeftClick();
        } else if (id == R.id.tv_right) {
            if (mListener != null) mListener.onRightClick();
        }
    }

    public static abstract class Listener {
        public abstract void onRightClick();

        public void onLeftClick() {
        }
    }

}















