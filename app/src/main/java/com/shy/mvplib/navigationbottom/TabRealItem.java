package com.shy.mvplib.navigationbottom;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.shy.mvplib.R;


/**
 * Created by ZhangL on 2020-03-31.
 */
public class TabRealItem extends BottomTabItem {

    private String mItemTxt;
    private int mResIcon;

    private TabRealItem(Context context) {
        super(R.layout.bottom_tab_item, context);
    }

    public TabRealItem(Builder builder) {
        this(builder.context);
        this.mItemTxt = builder.text;
        this.mResIcon = builder.iconId;
    }

    @Override
    protected void initLayout() {
        ImageView imageView = findViewById(R.id.bottom_iv);
        TextView textView = findViewById(R.id.bottom_tv);
        imageView.setImageResource(mResIcon);
        textView.setText(mItemTxt);
    }

    @Override
    protected void setSelected(Boolean selected) {
        ImageView imageView = findViewById(R.id.bottom_iv);
        TextView textView = findViewById(R.id.bottom_tv);
        imageView.setSelected(selected);
        textView.setSelected(selected);
    }

    public static class Builder {
        Context context;
        int iconId;
        String text;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder text(String text) {

            this.text = text;
            return this;
        }

        public Builder resIcon(int iconId) {

            this.iconId = iconId;
            return this;
        }

        public TabRealItem create() {
            return new TabRealItem(this);
        }
    }
}
