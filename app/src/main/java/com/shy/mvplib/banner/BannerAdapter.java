package com.shy.mvplib.banner;

import android.view.View;

/**
 * Created by zhangli on 2019/7/15.
 */

public abstract class BannerAdapter {
    /**
     * 根据位置获取viewpager子view
     *
     * @param position
     * @return
     */
    public abstract View getView(int position);

    /**
     * 获取banner长度
     *
     * @return
     */

    public abstract int getCount();

    /**
     * 广告位描述
     *
     * @param mCurrentPage
     * @return
     */
    public String getBannerDesc(int mCurrentPage) {
        return "";
    }
}
