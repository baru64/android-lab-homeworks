package com.example.homework3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private List<String> answers;
    private SensorManager sensorManager;
    private Sensor sensor;
    private double last, sum;
    private boolean shaking;
    ImageView image;
    TextView answerText;
    FlingAnimation flingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answers = Arrays.asList(getResources().getStringArray(R.array.answers));
        shaking = false;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION) {
            last =event.values[0] + event.values[1];
            sum += last;
        }
        if (Math.abs(last) > 3) {
            shaking=true;
            animateShaking(last);
        } else if(shaking) {
            shaking=false;
            image = findViewById(R.id.ball);
            image.setImageResource(R.drawable.hw3ball_empty);
            answerText = findViewById(R.id.Answer);
            image.setTranslationX(0);
            Random randGen = new Random((long)sum*100000);
            int idx = randGen.nextInt(20);
            answerText.setText(answers.get(idx));
            sum =0.0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume()
    {
        super.onResume();

        if (sensor != null) {
            sensorManager.registerListener(this,sensor,100000);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (sensor!=null) {
            sensorManager.unregisterListener(this);
        }
    }

    private void animateShaking(double velocity)
    {
        image = findViewById(R.id.ball);
        image.setImageResource(R.drawable.hw3ball_front);
        answerText = findViewById(R.id.Answer);
        answerText.setText("");
        flingAnimation = new FlingAnimation(image, DynamicAnimation.X);
        flingAnimation.setStartVelocity((float) (-50*velocity)).setMinValue(-50).setMaxValue(100).setFriction(0.1f);
        flingAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE);
        flingAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean b, float v, float v1) {
                image.setTranslationX(0);
            }
        });
        flingAnimation.start();
    }

}
