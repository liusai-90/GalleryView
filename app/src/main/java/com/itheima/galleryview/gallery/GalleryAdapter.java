package com.itheima.galleryview.gallery;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.itheima.galleryview.R;

/**
 * Created by liusai on 2016/11/12.
 */
public class GalleryAdapter extends BaseAdapter {

    private DisplayMetrics displayMetrics;

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_gallery_view, null ,false);
            convertView.setLayoutParams(new GalleryView.LayoutParams(getWidth(parent.getContext()),getHeight(parent.getContext())));
        }

        ImageView cardView = (ImageView) convertView.findViewById(R.id.headRIV);
        cardView.setImageResource(position % 2 == 0 ? R.mipmap.a1 : R.mipmap.head);
        return convertView;
    }

    public int getWidth(Context context) {
        if(displayMetrics == null)
        displayMetrics = context.getResources().getDisplayMetrics();

        return displayMetrics.widthPixels * 520 / 640;
    }

    public int getHeight(Context context) {
//        if(displayMetrics == null)
//            displayMetrics = context.getResources().getDisplayMetrics();

        return getWidth(context) * 800 / 520;
    }
}
