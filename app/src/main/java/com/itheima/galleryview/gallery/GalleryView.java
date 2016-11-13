package com.itheima.galleryview.gallery;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class GalleryView extends Gallery {
    /**
     * 最大缩放比例
     */
    private float mMaxScale = 0.15f;
    /**
     * GalleryView的中心点
     */
    private int mCoveflowCenter;

    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setStaticTransformationsEnabled(true);
//        this.setSpacing(20);
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int keyCode;
        if (isScrollingLeft(e1, e2)) {
            keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode, null);
        return true;
    }

    /**
     * 安卓15以上默认开启了硬件加速，需特殊处理
     */
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (android.os.Build.VERSION.SDK_INT > 15) {
            transformView(child);
        }
        return super.drawChild(canvas, child, drawingTime);
    }

    /**
     * 获取Gallery的中心x
     */
    private int getCenterOfCoverflow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    /**
     * 获取View的中心x
     */
    private int getCenterOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCoveflowCenter = getCenterOfCoverflow();   // size改变后重新获取中心
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation trans) {
        if (android.os.Build.VERSION.SDK_INT < 16)
            transformView(child);
        return true;
    }

    @SuppressLint("NewApi")
    private void transformView(View child) {
        int childCenter = getCenterOfView(child);
        float scale = (((float) (mCoveflowCenter - childCenter)) / (float) child.getWidth()) * mMaxScale;
        float sxy = 1f - Math.abs(scale);
        child.setPivotY(child.getHeight() / 2);
        if (childCenter > mCoveflowCenter) {
            child.setPivotX(0);
        } else {
            child.setPivotX(child.getWidth());
        }
        child.setScaleX(sxy);
        child.setScaleY(sxy);
    }

//    @Override
//    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
//        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
//        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
//
//        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
//                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin
//                        + widthUsed, lp.width);
//        int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
//                getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin
//                        + heightUsed, lp.height);
//
//        childHeightMeasureSpec *= 0.85;
//        childWidthMeasureSpec *= 0.85;
//        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//    }

}