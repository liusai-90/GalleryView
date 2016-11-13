package com.itheima.galleryview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gallery(View v) {
        startActivity(new Intent(this, GalleryAct.class));
    }

    public void viewPager(View v) {
        startActivity(new Intent(this, ViewPagerAct.class));
    }

}
