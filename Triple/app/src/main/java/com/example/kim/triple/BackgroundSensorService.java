package com.example.kim.triple;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;



/**
 * Created by khwbori on 2016. 12. 1..
 */

public class BackgroundSensorService extends IntentService {
    private Step step;
    private Barometer barometer;

    public BackgroundSensorService(){
        super("SensorBackgroundHere");
    }
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

    }
    @Override
    public void onDestroy(){
        int mystep =  step.getStep();
        float barvalue = barometer.getBarometer();
        step.destroyStep();
        barometer.destroyBarometer();
        Log.i("myStep", ""+mystep);
        Log.i("mybarometer", ""+barvalue);
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



    class Step{
        private SensorManager sensorManager;
        private float previousY, currentY;
        private int steps;
        private int threshold;

        Step(){};
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
        public void stepCountStart(){
            threshold = 10;
            previousY = currentY = steps = 0;
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(stepDetector,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        };

        public int getStep(){
            return steps;
        };

        public void destroyStep(){
            sensorManager.unregisterListener(stepDetector);
        }
    };

    class Barometer {
        //SensorManager lets you access the device's sensors
        //declare Variables
        private SensorManager sensorManager;
        private float barvalue;

        Barometer(){};

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


        public void barometerCountStart(){

            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(barometerDetector,
                    sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
                    SensorManager.SENSOR_DELAY_NORMAL);
        };

        public float getBarometer(){
            return barvalue;
        };

        public void destroyBarometer(){
            sensorManager.unregisterListener(barometerDetector);
        }

    };
}
