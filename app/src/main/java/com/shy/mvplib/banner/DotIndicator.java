package com.shy.mvplib.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by zhangli on 2019/7/19.
 */

public class DotIndicator extends View {
    private Drawable drawable;

    public DotIndicator(Context context) {
        super(context);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DotIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (drawable != null) {

            Bitmap bitmap = getBitmap(drawable);
            //画圆形
            Bitmap roundBitmap = getRoundBitmap(bitmap);
            canvas.drawBitmap(roundBitmap, 0, 0, null);
        }
    }

    /**
     * 绘制圆形bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap getRoundBitmap(Bitmap bitmap) {
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getRadius(), mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        return circleBitmap;
    }

    private int getRadius() {
        return getMeasuredHeight() / 2 > getMeasuredWidth() / 2 ? getMeasuredWidth() / 2 : getMeasuredHeight() / 2;
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //创建bitmap
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建画布
        Canvas canvas = new Canvas(outBitmap);
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }

    public void setDrable(Drawable drable) {
        this.drawable = drable;
        invalidate();
    }
}
