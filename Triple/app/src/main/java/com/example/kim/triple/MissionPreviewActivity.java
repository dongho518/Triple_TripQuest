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

import android.provider.ContactsContract;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kim.triple.data.dao.MissionCartDao;
import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;

import broadcast.ProximityIntentReceiver;

public class MissionPreviewActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    private int UserId =1010;
    private boolean aBoolean;
    private LocationManager locationManager;
    private double latitude=35.894487;
    private double longitude=128.610536;
    private int missionId;
    private PendingStack pendingStack = new PendingStack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        aBoolean = false;

        Intent intent = getIntent();
        missionId = intent.getIntExtra("mission_id",1);

        MissionDao missionDao = new MissionDao(this);
        Mission mission = missionDao.selectFromId(missionId);

        final String missionlatitude = mission.getLatitude();
        final String missionlongitude = mission.getLongitude();

        Bitmap image = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mission.getImageUrl(), "drawable","com.example.kim.triple"));

        ImageView detail_ImageView = (ImageView) findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
        // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(mission.getName());

        TextView enrolltext = (TextView) findViewById(R.id.enrolltextView1);
        enrolltext.setText("미션등록");

        TextView detailtext = (TextView) findViewById(R.id.detailtextView1);
        detailtext.setText(mission.getExplan());

        ImageView map = (ImageView) findViewById(R.id.mapImageView);
        map.setImageResource(R.drawable.sample_map);

        final MissionCartDao missionCartDao = new MissionCartDao(this);

        final FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatActionBtn);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean == false) {
                    MissionCart missionCart = new MissionCart();
                    missionCart.setUserId(UserId);
                    missionCart.setMissionId(missionId);
                    missionCart.setMissionResult(0);
                    missionCartDao.insert(missionCart);
                    Log.i("mission add", "" + missionId + UserId);
                    myFab.setImageResource(R.drawable.ic_close);
                    Toast.makeText(getApplicationContext(), "미션이 선택 되었습니다.", Toast.LENGTH_SHORT).show();
                    aBoolean = true;

                    checkPermission();
                    setupLocation(missionlatitude,missionlongitude);
                }
            }
        });


    }

    private void setupLocation(String arglat, String arglng){
        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            Intent missionIntent = new Intent(this, ProximityIntentReceiver.class);
            missionIntent.putExtra("mission_id", missionId);
            Log.i("mission_id_send",""+missionId);
            PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, missionIntent, 0);
            pendingStack.setPendigIntent(missionId, proximityIntent);
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
        if(ContextCompat.checkSelfPermission( MissionPreviewActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MissionPreviewActivity.this, permission)) {
                ActivityCompat.requestPermissions(MissionPreviewActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(MissionPreviewActivity.this, new String[]{permission}, requestCode);
            }
        }
    }
}
