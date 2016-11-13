package com.itheima.galleryview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.itheima.galleryview.R;

/**
 */
public class RoundImageView extends ImageView {
	private int mBorderThickness = 0;
	private int mBorderOutsideColor = 0;
	private int mBorderInsideColor = 0;
	private float mRound = 0;

	private RoundDrawable roundDrawable;

	public RoundImageView(Context context) {
		this(context, null);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.roundedimageview, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.roundedimageview_border_thickness:
					mBorderThickness = a.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
					break;
				case R.styleable.roundedimageview_border_outside_color:
					mBorderOutsideColor = a.getColor(R.styleable.roundedimageview_border_outside_color, 0);
					break;
				case R.styleable.roundedimageview_border_inside_color:
					mBorderInsideColor = a.getColor(R.styleable.roundedimageview_border_inside_color, 0);
					break;
				case R.styleable.roundedimageview_round:
					mRound = a.getDimensionPixelSize(R.styleable.roundedimageview_round, 0);
					break;
			}
		}
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setImageDrawable(getDrawable());
	}

	public void setRoundRadius(float radius) {
		this.mRound = radius;
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		if (drawable == null) return;
		if (drawable instanceof RoundDrawable)
			roundDrawable = (RoundDrawable) drawable;
		else
			roundDrawable = new RoundDrawable(drawableToBitamp(drawable));
		roundDrawable.setBorderThickness(mBorderThickness);
		roundDrawable.setInsideBorderColor(mBorderInsideColor);
		roundDrawable.setOutsideBorderColor(mBorderOutsideColor);
		roundDrawable.setRadio(mRound);
		roundDrawable.invalidateSelf();
		super.setImageDrawable(roundDrawable);
	}

	private Bitmap drawableToBitamp(Drawable drawable) {
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap.Config config = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	@Override
	public void setImageResource(int resId) {
		this.setImageDrawable(getContext().getResources().getDrawable(resId));
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		this.setImageDrawable(new BitmapDrawable(getResources(), bm));
	}

}