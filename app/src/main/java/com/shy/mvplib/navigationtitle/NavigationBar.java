package com.shy.mvplib.navigationtitle;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by Zhangl on 2020-04-01.
 */
public class NavigationBar extends AbsNavigationBar {

    protected NavigationBar(Builder builder) {
        super(builder);


    }

    public static class Builder extends AbsNavigationBar.Builder<NavigationBar.Builder> {

        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
