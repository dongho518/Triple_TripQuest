package com.example.kim.triple;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kim.triple.data.dao.MissionCartDao;
import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;

public class MissionPreviewActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    private int UserId =1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        final int missionId = intent.getIntExtra("mission_id",1);

        MissionDao missionDao = new MissionDao(this);
        Mission mission = missionDao.selectFromId(missionId);
        Bitmap image = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mission.getImageUrl(), "drawable","com.example.kim.triple"));

        ImageView detail_ImageView = (ImageView) findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
        // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(mission.getName());

        TextView centerText = (TextView) findViewById(R.id.detailText2);
        centerText.setText("미션등록");

        final MissionCartDao missionCartDao = new MissionCartDao(this);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatActionBtn);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MissionCart missionCart = new MissionCart();
                missionCart.setUserId(UserId);
                missionCart.setMissionId(missionId);
                missionCart.setMissionResult(0);
                missionCartDao.insert(missionCart);
                Log.i("mission add",""+missionId+UserId);
            }
        });


    }
}
