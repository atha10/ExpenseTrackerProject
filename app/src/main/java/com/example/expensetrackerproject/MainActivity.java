package com.example.expensetrackerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {


    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView transitionimage = findViewById(R.id.imageView1);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        transitionimage.startAnimation(AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.zoom_in
        ));
       CountDownTimer countDownTimer =
                new CountDownTimer(1000, 100) {
                    public void onTick(long millisUntilFinished) {
                        spinner.setProgress(Math.abs((int) millisUntilFinished / 100 - 100));

                    }

                    @Override
                    public void onFinish() {
                        transitionimage.startAnimation(AnimationUtils.loadAnimation(
                                getApplicationContext(),
                                R.anim.zoom_out
                        ));
                        newac();
                    }
                };
        countDownTimer.start();

    }
    public void newac(){
        startActivity(new Intent(this,Login_activity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

}
