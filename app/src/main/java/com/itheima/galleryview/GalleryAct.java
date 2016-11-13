package com.itheima.galleryview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itheima.galleryview.gallery.GalleryAdapter;
import com.itheima.galleryview.gallery.GalleryView;

public class GalleryAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GalleryView gallery = (GalleryView) findViewById(R.id.gallery);

//        assert gallery != null;
        gallery.setAdapter(new GalleryAdapter());
    }
}
