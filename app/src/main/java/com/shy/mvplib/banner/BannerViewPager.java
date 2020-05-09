package com.shy.mvplib.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by zhangli on 2019/7/15.
 */

public class BannerViewPager extends ViewPager {
    private BannerAdapter mAdapter;
    private final int SCROLL_MSG = 0x001;
    private final int NEXT_MSG = 0x002;
    private int mCutDownTime = 3500;
    private BannerScroller mScroller;
    private int mCurrentPage = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SCROLL_MSG) {
                setCurrentItem(getCurrentItem() + 1);
                startRoll();
            } else if (msg.what == NEXT_MSG) {
                setCurrentItem(getCurrentItem() + 1);
            }

        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            //第一个参数 当前属性类 第二个参数设置的值
            mScroller = new BannerScroller(context);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2销毁Handler 内存泄漏解决
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;

    }

    public void setScrollerDuration(int duration) {
        mScroller.setScrollerDuration(duration);
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        setAdapter(new BannerViewAdapter());
    }

    public void startRoll() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    public void setNextPage() {
        mHandler.removeMessages(NEXT_MSG);
        mHandler.sendEmptyMessage(NEXT_MSG);
    }


    private class BannerViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount());
            container.addView(bannerItemView);
            return bannerItemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }


}
