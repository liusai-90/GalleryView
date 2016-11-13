package com.itheima.galleryview.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by Sens on 2015/4/8 0008.
 */
public class RoundDrawable extends Drawable {
    private RectF rectF;
    private float radio;
    private Paint paint;
    private Bitmap bitmap;
    private int width;
    private Paint outsideBorderPaint;
    private Paint insideBorderPaint;
    private int borderThickness;
    private int bitWidth, bitHeight;
    private static Matrix matrix;
    private int lineWidth = 0;

    public RoundDrawable(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitWidth = this.bitmap.getWidth();
        this.bitHeight = this.bitmap.getHeight();
        if (matrix == null) {
            matrix = new Matrix();
        }
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setShader(new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR));//设置渲染器
    }

    /**
     * 圆环厚度
     */
    public void setBorderThickness(int borderThickness) {
        if (borderThickness != 0) {
            this.borderThickness = borderThickness;
        }
    }

    /**
     * 设置圆环颜色
     */
    public void setInsideBorderColor(int color) {
        if (color != 0) {
            if (this.insideBorderPaint == null) {
                this.insideBorderPaint = new Paint();
                this.insideBorderPaint.setAntiAlias(true);
            }
            this.insideBorderPaint.setColor(color);
        }
    }

    /**
     * 设置外边框颜色
     */
    public void setOutsideBorderColor(int color) {
        if (color != 0) {
            if (this.outsideBorderPaint == null) {
                this.outsideBorderPaint = new Paint();
                this.outsideBorderPaint.setAntiAlias(true);
            }
            this.outsideBorderPaint.setColor(color);
            lineWidth = 1;//TODO
        }
    }

    /**
     * 获取图像
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * 设置圆角
     */
    public void setRadio(float radio) {
        this.radio = radio;
    }

    private int viewWidth;
    private int viewHeight;

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        viewWidth = right - left;
        viewHeight = bottom - top;
        if ((float) this.bitWidth / (float) this.bitHeight > (float) viewWidth / (float) viewHeight)
            paint.getShader().setLocalMatrix(zoomMatrix(viewHeight * this.bitWidth / this.bitHeight, viewHeight));
        else
            paint.getShader().setLocalMatrix(zoomMatrix(viewWidth, viewWidth * this.bitHeight / this.bitWidth));
        this.width = Math.min(viewWidth, viewHeight);
        this.rectF = new RectF(left, top, right, bottom);
    }

    /**
     * 修改渲染器大小
     */
    private Matrix zoomMatrix(double newWidth, double newHeight) {
        //重置操作图片用的matrix对象
        matrix.set(null);
        // 计算宽高缩放率
        float scaleWidth = (float) newWidth / (float) bitWidth;
        float scaleHeight = (float) newHeight / (float) bitHeight;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        float transX = (float) (viewWidth - borderThickness - lineWidth - newWidth) / 2;
        float transY = (float) (viewHeight - borderThickness - lineWidth - newHeight) / 2;
        matrix.postTranslate(transX, transY);
        return matrix;
    }

    private Bitmap mainBitmap;

    @Override
    public void draw(Canvas canvas) {
        mainBitmap = mainBitmap == null ? creatMainBitmap() : mainBitmap;
        if (mainBitmap != null) {
            canvas.drawBitmap(mainBitmap, 0, 0, null);
        }
    }

    private Bitmap creatMainBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (radio == 0) {
            if (borderThickness != 0) {
                if (lineWidth != 0)
                    canvas.drawCircle(width / 2, width / 2, width / 2, outsideBorderPaint);
                canvas.drawCircle(width / 2, width / 2, width / 2 - lineWidth, insideBorderPaint);
            }
            canvas.drawCircle(width / 2, width / 2, width / 2 - borderThickness, paint);
        } else {
            RectF mRectF;
            if (borderThickness != 0) {
                if (lineWidth != 0)
                    canvas.drawRoundRect(rectF, radio, radio, outsideBorderPaint);
                mRectF = new RectF(rectF.left + lineWidth, rectF.top + lineWidth, rectF.right - lineWidth, rectF.bottom - lineWidth);
                canvas.drawRoundRect(mRectF, radio - lineWidth, radio - lineWidth, insideBorderPaint);
            }
            mRectF = new RectF(rectF.left + borderThickness, rectF.top + borderThickness, rectF.right - borderThickness, rectF.bottom - borderThickness);
            canvas.drawRoundRect(mRectF, radio - borderThickness, radio - borderThickness, paint);
        }
        return bitmap;
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
