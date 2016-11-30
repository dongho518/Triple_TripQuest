package com.example.kim.triple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class Place_MissionInfo extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__mission_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("place_image");
        Bitmap image;
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        ImageView detail_ImageView = (ImageView)findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
       // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(getIntent().getStringExtra("place_name"));
    }
}
