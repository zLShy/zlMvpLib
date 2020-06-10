package com.shy.mvplib.mvp.base;

import android.os.Bundle;


import com.shy.mvplib.BasActivity;
import com.shy.mvplib.BaseActivity;
import com.shy.mvplib.mvp.proxy.ActivityMvpProxy;
import com.shy.mvplib.mvp.proxy.ActivityMvpProxyImpl;

import androidx.annotation.Nullable;


public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {
    private ActivityMvpProxy mMvpProxy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
        setContentView();
        // 创建 P，创建只能交给 子类，每个 Activity 都不一样
        mMvpProxy = createMvpProxy();

        initData();
        initViews();
    }

    protected abstract void setContentView();

    protected abstract void initViews();

    protected abstract void initData();

    /**
     * 创建 Mvp 的代理  自己去写 Fragment
     *
     * @return
     */
    private ActivityMvpProxy createMvpProxy() {
        if (mMvpProxy == null) {
            mMvpProxy = new ActivityMvpProxyImpl<>(this);
        }
        return mMvpProxy;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mMvpProxy.unbindPresenter();

    }
}
