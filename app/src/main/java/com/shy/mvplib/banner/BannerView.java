package com.shy.mvplib.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.shy.mvplib.R;

import androidx.viewpager.widget.ViewPager;


/**
 * Created by zhangli on 2019/7/19.
 */

public class BannerView extends RelativeLayout {
    private BannerViewPager mBannerVp;
    private TextView mBannerDescTv;
    private LinearLayout mBannerContainer;
    private Drawable mBannerFocusDrable;
    private Drawable mBannerNormalDrable;
    private RelativeLayout mBottomContainer;
    private Drawable mCustomSelectDrawable;
    private Drawable mCustomNormalDrawable;
    private BannerAdapter mAdapter;

    private int mCurrentPage = 0;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.ui_banner, this);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {

        this.mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        this.mBannerDescTv = (TextView) findViewById(R.id.banner_desc_tv);
        this.mBannerContainer = (LinearLayout) findViewById(R.id.banner_container);
        mBannerFocusDrable = new ColorDrawable(Color.RED);
        mBannerNormalDrable = new ColorDrawable(Color.WHITE);
        mBottomContainer = findViewById(R.id.bottom_container);
    }

    public BannerView setSelectDrawble(Drawable drawble) {
        mCustomSelectDrawable = drawble;
        return this;
    }

    public BannerView setNormalDrawble(Drawable drawble) {
        mCustomNormalDrawable = drawble;
        return this;
    }

    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        this.mBannerVp.setAdapter(adapter);
        initDots();

        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageSelect(position);
            }
        });

        String firstDesc = mAdapter.getBannerDesc(mCurrentPage);
        if (!TextUtils.isEmpty(firstDesc)) {
            mBannerDescTv.setText(firstDesc);
        }

    }

    /**
     * 设置指示器图片
     *
     * @param position
     */

    private void pageSelect(int position) {
        //当前点的 上一个点置默认图标
        DotIndicator dotIndicator = (DotIndicator) mBannerContainer.getChildAt(mCurrentPage);
        dotIndicator.setDrable(mBannerNormalDrable);
        //当前点置选中图标
        mCurrentPage = position % mAdapter.getCount();
        DotIndicator selectDot = (DotIndicator) mBannerContainer.getChildAt(mCurrentPage);
        if (mCustomSelectDrawable != null && mCustomNormalDrawable != null) {
            selectDot.setDrable(mCustomSelectDrawable);
        }else {
            selectDot.setDrable(mBannerFocusDrable);
        }


        String desc = mAdapter.getBannerDesc(mCurrentPage);
        if (!TextUtils.isEmpty(desc)) {
            mBannerDescTv.setText(desc);
        }

    }

    /**
     * 设置指示器
     */
    private void initDots() {

        int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            DotIndicator dotIndicator = new DotIndicator(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(8), dip2px(8));
            params.leftMargin = params.rightMargin = dip2px(2);
            dotIndicator.setLayoutParams(params);
            if (i == 0) {
                if (mCustomSelectDrawable != null && mCustomNormalDrawable != null) {
                    dotIndicator.setDrable(mCustomSelectDrawable);
                }else {
                    dotIndicator.setDrable(mBannerFocusDrable);
                }

            } else {
                if (mCustomSelectDrawable != null && mCustomNormalDrawable != null) {
                    dotIndicator.setDrable(mCustomNormalDrawable);
                }else {
                    dotIndicator.setDrable(mBannerNormalDrable);
                }
            }

            mBannerContainer.addView(dotIndicator);
        }
    }

    private int dip2px(int defValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, defValue, getResources().getDisplayMetrics());
    }

    public void setBottomVisibity(boolean visiable) {
        if (mBottomContainer!= null) {
            if (visiable) {
                mBottomContainer.setVisibility(VISIBLE);
            }else {
                mBottomContainer.setVisibility(GONE);
            }
        }

    }

    public void startRoll() {
        mBannerVp.startRoll();
    }

    public void setDurtion(int durtion) {
        mBannerVp.setScrollerDuration(durtion);
    }

    public void setNextPage() {
        mBannerVp.setNextPage();
    }
}
