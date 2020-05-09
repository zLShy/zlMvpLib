package com.shy.mvplib.navigationbottom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BottomTabItem {


    private int mLayoutId;
    private Context mContext;
    private View mTabView;

    public BottomTabItem(int layoutId, Context context) {
        this.mLayoutId = layoutId;
        this.mContext = context;
    }

    public View getTabView() {

        if (mTabView == null) {
            mTabView = LayoutInflater.from(mContext).inflate(mLayoutId,null);
            initLayout();
        }
        return mTabView;
    }

    protected abstract void initLayout();

    protected <T> T findViewById(int id) {
        return (T) mTabView.findViewById(id);
    }

    protected abstract void setSelected(Boolean selected);


}
