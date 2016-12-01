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
import com.example.kim.triple.data.model.Mission;

import java.util.List;

import broadcast.ProximityIntentReceiver;

public class PlaceMissionActivity extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbar;
    private LocationManager locationManager;
    private double latitude=35.894487;
    private double longitude=128.610536;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__mission_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        byte[] arr = getIntent().getByteArrayExtra("place_image");
        Bitmap image;
        image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        ImageView detail_ImageView = (ImageView)findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
        // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(intent.getStringExtra("place_name"));

        TextView tag = (TextView) findViewById(R.id.detailText1) ;
        TextView phone = (TextView) findViewById(R.id.detailText2);
        TextView address = (TextView) findViewById(R.id.detailText3) ;

        tag.setText("Tag : "+intent.getStringExtra("place_tag"));
        address.setText("주소 : "+intent.getStringExtra("place_address"));
        phone.setText("전화번호 : "+"010 - 7272 - 3768");

       /* ArrayList<String> arrName = new ArrayList<String>();
        ArrayAdapter<String> adapName = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.name,arrName);*/
        MissionViewAdapter adapter = new MissionViewAdapter();
        ListView listview = (ListView) findViewById(R.id.MissionList);
        listview.setAdapter(adapter);
        //버전 체크필요
        listview.setNestedScrollingEnabled(true);

        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place1),
                "대유랜드","[레포츠] 제주도서귀포시","제주특별자치도 서귀포시 상예로 381(상예동)");
        adapter.addItem( BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place2),
                "퍼시픽랜드","[관광지] 퍼시픽랜드","제주특별자치도 서귀포시 중문관광로 154-17(색달동)" );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place3),
                "한라산 트레킹","[레포츠] 제주도제주시","제주특별자치도 제주시 1100로 2070-61(해안동)"  );
        adapter.addItem(BitmapFactory.decodeResource(getResources(),R.drawable.jeju_place4),
                "한림공원 국화축제2016","[관광지] 제주도 서귀포시","제주특별자치도 제주시 한림읍 한림로 300(한림읍)" );

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                MissionViewItem item = (MissionViewItem) parent.getItemAtPosition(position) ;

                String place_name = item.getTitle();
                String place_info1 = item.getDesc();
                String place_info2 = item.getDesc2();
                Bitmap place_bitmap = item.getIcon() ;

                Toast.makeText(getApplicationContext(),place_name+" 미션이 선택 되었습니다.", Toast.LENGTH_SHORT).show();
                setupLocation();
                // TODO : use item data.
            }
        }) ;

        checkPermission();
    }


    private void setupLocation()
    {
        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            Intent missionIntent = new Intent(this, ProximityIntentReceiver.class);
            PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, missionIntent, 0);
            locationManager.addProximityAlert(latitude, longitude, 10f, -1, proximityIntent);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };

            if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                        locationListener);
            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                        locationListener);
        } catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void checkPermission(){
        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, APP_PERMISSION);
        if (ActivityCompat. checkSelfPermission (this,
                Manifest.permission. ACCESS_FINE_LOCATION ) != PackageManager. PERMISSION_GRANTED
                && ActivityCompat. checkSelfPermission (this,
                Manifest.permission. ACCESS_COARSE_LOCATION ) != PackageManager. PERMISSION_GRANTED )
            return;
    }

    static final Integer APP_PERMISSION = 1;
    private void askForPermission(String permission, Integer requestCode) {
        if(ContextCompat.checkSelfPermission(PlaceMissionActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PlaceMissionActivity.this, permission)) {
                ActivityCompat.requestPermissions(PlaceMissionActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(PlaceMissionActivity.this, new String[]{permission}, requestCode);
            }
        }
    }
}
