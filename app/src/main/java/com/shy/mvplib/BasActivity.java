package com.shy.mvplib;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;

/**
 * Created by ZhangL on 2020-04-07.
 */
public abstract class BasActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName().toString();
    public static Context mAppContext;

    /**
     * 为我们的 activity 的状态栏设置颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 直接调用系统提供的方法 setStatusBarColor
            activity.getWindow().setStatusBarColor(color);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 首先把他弄成全屏（），在状态栏的部分加一个布局
            // activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 电量 时间 网络状态 都还在
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 在状态栏的部分加一个布局 setContentView 源码分析，自己加一个布局 (高度是状态栏的高度)
            View view = new View(activity);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);

            //  android:fitsSystemWindows="true" 每个布局都要写
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            // 获取activity中setContentView布局的根布局
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            contentView.setPadding(0, getStatusBarHeight(activity), 0, 0);
            // View activityView = contentView.getChildAt(0);
            // activityView.setFitsSystemWindows(true);
            // activityView.setPadding(0,getStatusBarHeight(activity),0,0);
        }

    }

    /**
     * 获取状态栏的高度
     *
     * @param activity
     * @return
     */
    private static int getStatusBarHeight(Activity activity) {
        // 插件式换肤：怎么获取资源的，先获取资源id，根据id获取资源
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        Log.e("TAG", statusBarHeightId + " -> " + resources.getDimensionPixelOffset(statusBarHeightId));
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }

    /**
     * 设置activity全屏
     *
     * @param activity
     */
    public static void setActivityTranslucent(Activity activity) {
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 这个怎么写有没有思路？看源码  29次
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAppContext = this;

        AppManager.getInstance().addActivity(this);
    }



    public void startActivity(Class<? extends BasActivity> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    public void startActivityForParams(Class<? extends BasActivity> clz, Map<String, String> map) {
        Intent intent = new Intent(this, clz);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            intent.putExtra(mapKey, mapValue);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * [沉浸状态栏]
     */
    public void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        }
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void copyTxt(String txt) {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", txt);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        toast("复制成功");
    }

    public void callPhone(final String phoneNo) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
            @SuppressLint("MissingPermission")
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + phoneNo);
                    intent.setData(data);
                    startActivity(intent);
                }
            }
        });
    }
}
