package com.itheima.galleryview;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itheima.galleryview.view.ViewPagerTransform;

public class ViewPagerAct extends AppCompatActivity {

    private ViewPagerTransform viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        viewPager = (ViewPagerTransform) findViewById(R.id.viewpager);

        // 设置页面间距
        viewPager.setPageMargin(20);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

//            @Override
//            public float getPageWidth(int position) {
//                return 0.8f;
//            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(container.getContext(),R.layout.widget_gallery_view, null);

//                view.setLayoutParams(getPageLayoutParams());
                ImageView iv = (ImageView) view.findViewById(R.id.headRIV);
                iv.setImageResource(position % 2 == 0 ? R.mipmap.a1 : R.mipmap.head);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    private ViewPager.LayoutParams getPageLayoutParams() {
        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = (int) (viewPager.getMeasuredWidth() * 0.85);
        layoutParams.height = viewPager.getMeasuredHeight();
        return layoutParams;
    }

}
