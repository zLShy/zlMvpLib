package com.shy.mvplib;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by ZhangL on 2020-04-03.
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected boolean isVisiable = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(setContentView(),container,false);
        }
        initView(mRootView);

        Log.e("TAG","onCreate==");
        return mRootView;

    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.e("TAG",isVisibleToUser+"---->visiable");
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    protected abstract void initView(View mRootView);

    protected abstract int setContentView();
}
