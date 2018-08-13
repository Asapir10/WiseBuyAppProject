package com.example.aviasa100.myandroidproject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class LoadingPageActivity extends AppCompatActivity {

    public static final String CODE = "code";
    AnimationDrawable cartAnimation;
    private static int SPLASH_TIME_OUT = 3500;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setBackgroundResource(R.drawable.animation);
        cartAnimation =(AnimationDrawable)imageView.getBackground();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadingPageActivity.this, ScanResultActivity.class);
                onStart();

                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);





        }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        cartAnimation.start();
    }
}

