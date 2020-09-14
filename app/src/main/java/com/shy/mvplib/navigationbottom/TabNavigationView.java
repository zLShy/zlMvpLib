package com.shy.mvplib.navigationbottom;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class TabNavigationView extends LinearLayout {
    private List<BottomTabItem> mTabViews;
    private int mCurrentIndex = -1;
    private selectChange mCallback;

    public TabNavigationView(Context context) {
        this(context, null);
    }

    public TabNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOrientation(HORIZONTAL);
        setElevation(3.0F);
        mTabViews = new ArrayList<>();

    }


    public void addTabItem(List<BottomTabItem> mTabItems) {

        mTabViews.clear();
        mTabViews.addAll(mTabItems);
        for (int i = 0; i < mTabItems.size(); i++) {
            View view = mTabItems.get(i).getTabView();
            addView(view);

            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.CENTER;
            params.weight = 1;
            view.setLayoutParams(params);
            setItemClickListener(view, i);
        }

        mTabViews.get(0).setSelected(true);
        mCurrentIndex = 0;

    }

    private void setItemClickListener(View bottomTabItem, final int position) {

        bottomTabItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex != position) {

                    mTabViews.get(mCurrentIndex).setSelected(false);
                    mCurrentIndex = position;
                    mTabViews.get(mCurrentIndex).setSelected(true);
                    if (mCallback != null) {
                        mCallback.chaged(mCurrentIndex);
                    }
                }
            }
        });

    }

    public void setCurrentIndex(int index) {
        if (index >= mTabViews.size()) {
            throw new RuntimeException("角标越界....");
        }
        if (index != mCurrentIndex) {
            mTabViews.get(mCurrentIndex).setSelected(false);
            this.mCurrentIndex = index;
            mTabViews.get(mCurrentIndex).setSelected(true);
        }
    }

    public void setCallback(selectChange callback) {
        this.mCallback = callback;
    }

    public interface selectChange {
        void chaged(int index);
    }
}
