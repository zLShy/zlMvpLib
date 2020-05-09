package com.shy.mvplib.navigationtitle;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ZhangL on 2020-04-01.
 */
public class AbsNavigationBar implements INavigation {


    private Builder mBuilder;
    private View mNavigationView;

    protected AbsNavigationBar(Builder builder) {
        this.mBuilder = builder;

        createNavigationView();
    }

    /**
     * 创建导航栏
     */
    @Override
    public void createNavigationView() {

        mNavigationView = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.mLayoutId, mBuilder.mParent, false);


        attachParent(mNavigationView, mBuilder.mParent);

        attachNavigationParams();
    }


    /**
     * 绑定NavagationView到跟布局
     *
     * @param navigationView
     * @param parent
     */
    @Override
    public void attachParent(View navigationView, ViewGroup parent) {
        parent.addView(navigationView, 0);
    }

    @Override
    public void attachNavigationParams() {


        for (int i = 0; i < mBuilder.mTextList.size(); i++) {
            int viedId = mBuilder.mTextList.keyAt(i);
            TextView textView = findViewById(viedId);
            textView.setText((CharSequence) mBuilder.mTextList.get(viedId));
        }

        for (int i=0;i<mBuilder.mListenerList.size();i++) {
            int viewId = mBuilder.mListenerList.keyAt(i);
            View view = findViewById(viewId);
            view.setOnClickListener((View.OnClickListener) mBuilder.mListenerList.get(viewId));
        }
    }

    public <T> T findViewById(int viedId) {
        return (T) mNavigationView.findViewById(viedId);
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public static abstract class Builder<T extends Builder> {

        Context mContext;
        int mLayoutId;
        ViewGroup mParent;
        SparseArray<CharSequence> mTextList;
        SparseArray<View.OnClickListener> mListenerList;

        public Builder(Context context, int layoutId, ViewGroup parent) {
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mParent = parent;

            mTextList = new SparseArray<>();
            mListenerList = new SparseArray<>();
        }

        public abstract AbsNavigationBar create();


        public T setText(int id, String txt) {
            mTextList.put(id, txt);
            return (T) this;
        }

        public T setOnclickListener(int id, View.OnClickListener listener) {
            mListenerList.put(id, listener);
            return (T) this;
        }
    }
}
