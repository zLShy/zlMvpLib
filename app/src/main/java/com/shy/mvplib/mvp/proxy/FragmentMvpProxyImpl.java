package com.shy.mvplib.mvp.proxy;


import com.shy.mvplib.mvp.base.BaseView;

public class FragmentMvpProxyImpl<V extends BaseView> extends MvpProxyImpl<V> implements FragmentMvpProxy{

    public FragmentMvpProxyImpl(V view) {
        super(view);
    }
    // 不同对待，一般可能不会


}
