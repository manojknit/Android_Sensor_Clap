package com.cloudjibe.android_sensor_clap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;

    float currentproximityValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float pro = event.values[0];
        if(currentproximityValue != pro) {
            int maxVolume = 10;
            //Change volume value as per proximity. It should be in log
            final float volume = (float) (1 - (Math.log(maxVolume - pro) / Math.log(maxVolume)));
            MediaPlayer mMediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.applause);
            mMediaPlayer.setVolume(volume, volume);
            mMediaPlayer.start();
            currentproximityValue = pro;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //AccuracyChanged
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
