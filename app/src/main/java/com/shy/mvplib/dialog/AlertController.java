package com.shy.mvplib.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zhangli on 2019/8/26.
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;


    public AlertController(AlertDialog dialog, Window window) {

        this.mDialog = dialog;
        this.mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public <T extends View> T getView(int viewId) {

        return mViewHelper.getView(viewId);
    }


    public static class AlertParams {


        public Context mContext;
        public int mTheme;
        //是否取消监听
        public boolean mCancelable = false;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局view
        public View mView;
        //布局id
        public int mViewLayoutResId;
        public SparseArray<CharSequence> mTextSparry = new SparseArray<>();
        public SparseArray<View.OnClickListener> mListenerSparry = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mAnim = 0;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public AlertParams(Context context, int themeResId) {

            this.mContext = context;
            this.mTheme = themeResId;
        }

        public void apply(AlertController mAlert) throws RuntimeException {


            DialogViewHelper viewHelper = null;
            if (mViewLayoutResId != 0) {

                viewHelper = new DialogViewHelper(mViewLayoutResId, mContext);

            }

            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new RuntimeException("view或LayoutResId不能为null");
            }

            mAlert.getDialog().setContentView(viewHelper.getContentView());

            //设置文本信息
            int mTextSparrySize = mTextSparry.size();
            Log.e("TGA", mTextSparrySize + "===========size");
            for (int i = 0; i < mTextSparrySize; i++) {
                viewHelper.setText(mTextSparry.keyAt(i), mTextSparry.valueAt(i));
            }

            int mClickArraySzie = mListenerSparry.size();
            //设置点击事件
            for (int i = 0; i < mClickArraySzie; i++) {
                viewHelper.setonClikListener(mListenerSparry.keyAt(i), mListenerSparry.valueAt(i));
            }

            Window mWindow = mAlert.getWindow();

            mWindow.setGravity(mGravity);

            mWindow.setWindowAnimations(mAnim);

            WindowManager.LayoutParams mParams = mWindow.getAttributes();
            mParams.width = mWidth;
            mParams.height = mHeight;

            mWindow.setAttributes(mParams);

        }
    }
}
