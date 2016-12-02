package com.example.kim.triple;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.dao.TripLocationDao;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.TripLocation;

import java.util.List;

import broadcast.ProximityIntentReceiver;

public class PlaceMissionActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbar;
    private LocationManager locationManager;
    private double latitude = 35.894487;
    private double longitude = 128.610536;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__mission_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int tripId = intent.getIntExtra("place_id", 2);

        Log.i("tripId", "" + tripId);
        TripLocationDao tripLocationDao = new TripLocationDao(this);
        TripLocation tripLocation = tripLocationDao.selectFromId(tripId);
        Log.i("getTrip", tripLocation.getName());


        Bitmap image = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(tripLocation.getPicture(), "drawable", "com.example.kim.triple"));
        ImageView detail_ImageView = (ImageView) findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
        // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(tripLocation.getName());

        TextView tag = (TextView) findViewById(R.id.detailText1);
        TextView phone = (TextView) findViewById(R.id.detailText2);
        TextView address = (TextView) findViewById(R.id.detailText3);

        tag.setText("Tag : " + tripLocation.getTag());
        address.setText("주소 : " + tripLocation.getAddress());
        phone.setText("전화번호 : " + tripLocation.getPhoneNumber());


       /* ArrayList<String> arrName = new ArrayList<String>();
        ArrayAdapter<String> adapName = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.name,arrName);*/
        MissionViewAdapter adapter = new MissionViewAdapter();
        ListView listview = (ListView) findViewById(R.id.MissionList);
        listview.setAdapter(adapter);
        //버전 체크필요
        listview.setNestedScrollingEnabled(true);

        MissionDao missionDao = new MissionDao(this);
        List<Mission> missionList = missionDao.selectFromTripId(tripId);

        for (Mission elem : missionList) {
            adapter.addItem(elem.getId(), BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(elem.getImageUrl(), "drawable", "com.example.kim.triple")),
                    elem.getName(), elem.getExplan(), elem.getExplan());
        }

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                MissionViewItem item = (MissionViewItem) parent.getItemAtPosition(position);

                int missionId = item.getId();

                Intent intent = new Intent(PlaceMissionActivity.this, MissionPreviewActivity.class);

                // SINGLE_TOP : 이미 만들어진게 있으면 그걸 쓰고, 없으면 만들어서 써라
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                intent.putExtra("mission_id", missionId);
                // Toast.makeText(getApplicationContext(),place_name+" 미션이 선택 되었습니다.", Toast.LENGTH_SHORT).show();
                // setupLocation();
                // TODO : use item data.
                startActivity(intent);
            }
        });

    }
}


