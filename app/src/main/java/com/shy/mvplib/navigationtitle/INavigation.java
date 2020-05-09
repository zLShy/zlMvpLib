package com.shy.mvplib.navigationtitle;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZhangL on 2020-04-01.
 */
public interface INavigation {

    void createNavigationView();

    void attachParent(View navigationView, ViewGroup parent);

    void attachNavigationParams();
}
