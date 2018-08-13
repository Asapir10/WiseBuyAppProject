package com.example.aviasa100.myandroidproject.utils;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.aviasa100.myandroidproject.BarcodScannerActivity;
import com.example.aviasa100.myandroidproject.LoadingPageActivity;
import com.example.aviasa100.myandroidproject.MainActivity;
import com.example.aviasa100.myandroidproject.R;
import com.example.aviasa100.myandroidproject.ScanResultActivity;

public class LoadingCart extends BarcodScannerActivity {

    //public static final String CODE = "code";
    AnimationDrawable cartAnimation;
    private static int SPLASH_TIME_OUT = 3500;

    public LoadingCart(int activity_loading_page) {
        super();
    }

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

        DisplayMetrics lp = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(lp);

        int width = lp.widthPixels;
        int height = lp.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // Intent homeIntent = new Intent(BarcodScannerActivity.this, ScanResultActivity.class);
               onStart();

                //startActivity(homeIntent);
               // finish();
            }
        },SPLASH_TIME_OUT);





    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        cartAnimation.start();
    }
}


