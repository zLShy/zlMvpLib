package com.shy.mvplib.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by zhangli on 2019/7/15.
 */

public class BannerScroller extends Scroller {
    private int mScrollerDuration = 1000;

    /**
     * 设置翻页时间
     * @param duration
     */
    public void setScrollerDuration(int duration) {
        this.mScrollerDuration = duration;
    }
    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
