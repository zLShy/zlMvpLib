package com.shy.mvplib.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by zhangli on 2019/8/26.
 * 100.62
 */

class DialogViewHelper {

    private View mContentView;
    private SparseArray<WeakReference<View>> mView;

    public DialogViewHelper(int layoutResId, Context context) {

        this();
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public DialogViewHelper() {

        mView = new SparseArray<>();
    }

    public void setContentView(View contentView) {
        this.mContentView = contentView;
    }

    public View getContentView() {

        return mContentView;
    }

    public void setText(int viewId, CharSequence text) {

        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }

    }

    public void setonClikListener(int viewId, View.OnClickListener listener) {

        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }

    }

    public <T extends View> T getView(int viewId) {

        WeakReference<View> weakReference = mView.get(viewId);
        View view = null;
        if (weakReference != null) {
            view = weakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mView.put(viewId, new WeakReference<View>(view));
        }
        return (T) view;
    }
}
