package com.shy.mvplib.mvp.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;


import com.shy.mvplib.BasActivity;
import com.shy.mvplib.BaseActivity;
import com.shy.mvplib.mvp.proxy.ActivityMvpProxy;
import com.shy.mvplib.mvp.proxy.ActivityMvpProxyImpl;

import androidx.annotation.Nullable;


public abstract class BaseMvpActivity<P extends BasePresenter> extends BasActivity implements BaseView {
    private ActivityMvpProxy mMvpProxy;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1;
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
        setContentView(setLayoutId());
        // 创建 P，创建只能交给 子类，每个 Activity 都不一样
        mMvpProxy = createMvpProxy();
        initData();
        initViews();

    }

    protected abstract int setLayoutId();

    protected abstract void initViews();

    protected void initData() {
    };

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1;
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

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
