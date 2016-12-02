package com.example.kim.triple;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.model.Mission;

import org.w3c.dom.Text;

public class MissionPlay extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbar;
    private int UserId = 1010;
    private IMissionAidlInterface binder;
    private boolean runningFlag = true;
    private TextView missionInfoTextView;
    private TextView stepTextView;
    private TextView barometerTextView;
    private myToggle mytoggle;
    int missionId;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = IMissionAidlInterface.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_play);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        missionId = intent.getIntExtra("mission_id",1);

        MissionDao missionDao = new MissionDao(this);
        Mission mission = missionDao.selectFromId(missionId);
        Bitmap image = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(mission.getImageUrl(), "drawable","com.example.kim.triple"));

        ImageView detail_ImageView = (ImageView) findViewById(R.id.backdrop);
        detail_ImageView.setImageBitmap(image);
        // detail_ImageView.setImageResource(R.drawable.res1);

        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctl.setTitle(mission.getName());
        final TextView tag = (TextView) findViewById(R.id.detailText1);
        TextView phone = (TextView) findViewById(R.id.detailText2);
        TextView address = (TextView) findViewById(R.id.detailText3);
        missionInfoTextView = (TextView) findViewById(R.id.missionInfo_textview);
        stepTextView = (TextView) findViewById(R.id.step_textview);
        barometerTextView = (TextView) findViewById(R.id.barometer_textview);

        missionInfoTextView.setVisibility(View.INVISIBLE);
        barometerTextView.setVisibility(View.INVISIBLE);
        stepTextView.setVisibility(View.INVISIBLE);

        if(missionId == 1) {
            missionInfoTextView.setVisibility(View.VISIBLE);
        } else if(missionId == 2)
        {
            barometerTextView.setVisibility(View.VISIBLE);
        }else if(missionId ==3){

        }else{
            stepTextView.setVisibility(View.VISIBLE);
        }

        tag.setText("미션대기");
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatActionBtn);
        mytoggle = new myToggle();
        mytoggle.setToggle(true);
        final Intent serviceintent = new Intent(this, BackgroundSensorService.class);

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mytoggle.getToggle()) {
/*
                    Intent myintent = new Intent(getApplicationContext(), BedgePopupActivity.class);
                    //myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(myintent);
*/
                    tag.setText("미션시작");
                    serviceintent.putExtra("mission_id",missionId);
                    bindService(serviceintent, connection, BIND_AUTO_CREATE);

                    mytoggle.setToggle(false);
                    Thread startSpeed = new Thread(new GetSpeedThread());
                    startSpeed.start();
                } else {
                    tag.setText("미션대기");
                    mytoggle.setToggle(true);
                    //unbindService(connection);
                }
            }
        });

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mytoggle.getToggle()==false){
            unbindService(connection);
        }

    }

    private class myToggle{
        private boolean myflag;
        myToggle(){}
        public boolean getToggle(){
            return myflag;
        }
        public void setToggle(boolean arg){
            myflag = arg;
        }
    }

    class GetSpeedThread implements Runnable{
        private Handler handler = new Handler();

        public void run(){
            while(runningFlag){
                if(binder == null){
                    continue;
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(missionId == 1) {
                                missionInfoTextView.setVisibility(View.VISIBLE);
                                missionInfoTextView.setText(String.valueOf(binder.get_speed())+"m/s");

                            } else if(missionId == 2)
                            {
                                barometerTextView.setText(String.valueOf(binder.getBarometer())+"hpa");
                            }else if(missionId ==3){

                            }else{
                                stepTextView.setText(String.valueOf(binder.getStep())+"걸음");
                            }

                        }catch (RemoteException e){
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }


    }

}
