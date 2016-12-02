package com.example.kim.triple;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;



/**
 * Created by khwbori on 2016. 12. 1..
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.kim.triple.data.dao.MissionDao;
import com.example.kim.triple.data.model.Mission;
import com.example.kim.triple.data.model.MissionCart;

/**
 * Created by khwbori on 2016. 12. 1..
 */

public class BackgroundSensorService extends Service {
    private Step step;
    private Barometer barometer;
    private Speedmeter speedmeter;
    double mySpeed, maxSpeed;
    private  Mission mission;

    IMissionAidlInterface.Stub binder = new IMissionAidlInterface.Stub() {
        @Override
        public double get_speed() throws RemoteException {
            return mySpeed;
        }
        public int getStep() throws RemoteException {
            return step.getStep();
        }
        public float getBarometer() throws RemoteException{
            return barometer.getBarometer();
        }
    };

    /*
    @Override
    protected void onHandleIntent(Intent intent){
        step = new Step();
        barometer = new Barometer();


        step.stepCountStart();
        barometer.barometerCountStart();
        try{
            Thread.sleep(10000);//이것으로 언제 서버에 값을 넘겨줄지 결정
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }*/

    public IBinder onBind(Intent intent){
        int missionId = intent.getIntExtra("mission_id",0);
        Log.i("background Id",""+missionId);
        MissionDao missionDao = new MissionDao(getApplicationContext());
        mission =missionDao.selectFromId(missionId);


        if(missionId == 1){ //마라톤
            speedmeter = new Speedmeter();
            speedmeter.setTime(mission.getEndTime());
            Thread speedThread = new Thread(new Speedmeter());
            speedThread.start();


        }else if(missionId==2){ //페러글라이딩
            Log.i("background 2 start","start");

            barometer = new Barometer();
            barometer.setTime(mission.getEndTime());
            Thread barometerThread = new Thread(barometer);
            barometerThread.start();

        }else if(missionId==3){ //온도

        }else if(missionId==4){ //만보기
            step = new Step();
            step.setTime(mission.getEndTime());
            Thread stepThread = new Thread(step);
            stepThread.start();
        }

        return binder;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        /*
        Intent myintent = new Intent(this, MainActivity.class);
        myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(myintent);
        Log.i("Service Destroy","");*/
        /*
        int mystep =  step.getStep();
        float barvalue = barometer.getBarometer();
        step.destroyStep();
        barometer.destroyBarometer();
         Log.i("myStep", ""+mystep);
        Log.i("mybarometer", ""+barvalue);
*/

    }
    @Override
    public void onCreate() {
        // TODO 서비스 생성시에 호출
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO 서비스 시작시에 호출

        return super.onStartCommand(intent, flags, startId);
    }



    class Step implements Runnable{
        private SensorManager sensorManager;
        private float previousY, currentY;
        private int steps;
        private int threshold;
        private int time;

        private SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                currentY = y;
                if(Math.abs(currentY -previousY) > threshold) {
                    steps++;
                    Log.i("currentStep",""+steps);
                }
                previousY = y;
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }


        };
        public Step(){
            time = 5;
            threshold = 10;
            previousY = currentY = steps = 0;
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(stepDetector,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        };
        public void setTime(int arg){
            time = arg;
        }
        public void run(){
            for(int i = 0; i<time; i++){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            try {
                destroyStep();
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
        public int getStep(){
            return steps;
        };

        public void destroyStep(){

            sensorManager.unregisterListener(stepDetector);
            Intent myintent = new Intent(BackgroundSensorService.this, BedgePopupActivity.class);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(steps > 100){
                myintent.putExtra("result",1);
            }else{
                myintent.putExtra("result",0);
            }
            myintent.putExtra("missionId",mission.getId());
            myintent.putExtra("missionName",mission.getName());
            myintent.putExtra("missionBedge",mission.getClassification());
            startActivity(myintent);
        }
    };

    class Barometer implements Runnable {
        //SensorManager lets you access the device's sensors
        //declare Variables
        private SensorManager sensorManager;
        private float barvalue;
        private int time;
        private SensorEventListener barometerDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    float[] values = event.values;
                    barvalue = values[0];
                    Log.i("Barometer", ""+values[0]);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };


        public Barometer(){
            time=5;
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(barometerDetector,
                    sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                    SensorManager.SENSOR_DELAY_NORMAL);
        };
        public void setTime(int arg){
            time = arg;
        }
        public void run(){
            for(int i = 0; i<time; i++){
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            try {
                destroyBarometer();
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }

        public float getBarometer(){
            return barvalue;
        };

        public void destroyBarometer(){
            sensorManager.unregisterListener(barometerDetector);
            Intent myintent = new Intent(BackgroundSensorService.this, BedgePopupActivity.class);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            myintent.putExtra("result",1);
            myintent.putExtra("missionId",mission.getId());
            myintent.putExtra("missionName",mission.getName());
            myintent.putExtra("missionBedge",mission.getClassification());
            startActivity(myintent);
           // startActivity(new Intent(getApplicationContext(), BedgePopupActivity.class));
        }

    };

    class Speedmeter implements Runnable{

        private LocationManager lm;
        private LocationListener ll;
        private Handler handler;
        private int time;
        private int sum_speed=0;

        public Speedmeter()
        {
            maxSpeed = mySpeed = 0;

            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            ll = new SpeedoActionListener();

            try {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,ll);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        public void setTime(int arg){
            time = arg;
        }
        public void run(){
            for(int i = 0; i<time; i++){

                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            try {
                lm.removeUpdates(ll);
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }

        private class SpeedoActionListener implements LocationListener {

            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    sum_speed+=mySpeed;
                    mySpeed = location.getSpeed();
                    if (mySpeed > maxSpeed) {
                        maxSpeed = mySpeed * (float)3.6;

                    }
                }
            }
            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub
            }
        }
        public void Destroyspeed(){
            Intent myintent = new Intent(BackgroundSensorService.this, BedgePopupActivity.class);
            myintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(sum_speed/time > 4  ){
                myintent.putExtra("result",1);
            }else{
                myintent.putExtra("result",0);
            }
            myintent.putExtra("missionId",mission.getId());
            myintent.putExtra("missionName",mission.getName());
            myintent.putExtra("missionBedge",mission.getClassification());
            startActivity(myintent);
        }
    }



}