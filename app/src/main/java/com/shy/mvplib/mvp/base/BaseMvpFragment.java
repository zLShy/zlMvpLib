package com.shy.mvplib.mvp.base;

import android.content.Context;

import com.shy.mvplib.mvp.proxy.FragmentMvpProxy;
import com.shy.mvplib.mvp.proxy.FragmentMvpProxyImpl;

import androidx.fragment.app.Fragment;

/**
 * Created by ZhangL on 2020-06-03.
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends Fragment implements BaseView {

    private FragmentMvpProxy mMvpProxy;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mMvpProxy = createMvpProxy();
    }


    /**
     * 创建 Mvp 的代理  自己去写 Fragment
     *
     * @return
     */
    private FragmentMvpProxy createMvpProxy() {
        if (mMvpProxy == null) {
            mMvpProxy = new FragmentMvpProxyImpl<>(this);
        }
        return mMvpProxy;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mMvpProxy.unbindPresenter();
    }
}
