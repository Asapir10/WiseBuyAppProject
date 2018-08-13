package com.example.aviasa100.myandroidproject;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import com.example.aviasa100.myandroidproject.utils.LoadingCart;

public class BarcodScannerActivity extends AppCompatActivity  {

    private static final int CAMERA = 0;
    private JSONArray products;
    private TextView txtQr;

    JSONObject obj = new JSONObject();
    ImageView imageView;
    AnimationDrawable cartAnimation;
    private static int SPLASH_TIME_OUT = 4800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcod_scanner);
        getSupportActionBar().hide();
        txtQr = (TextView) findViewById(R.id.txtQr);
        JSONObject obj = new JSONObject();

    }

    @Override
    protected void onStart() {
        super.onStart();

        askCameraPermission();

    }


    @Override
    protected void onResume() {
        super.onResume();

        tryToScan();
    }

    private void askCameraPermission() {
        PermissionManager.check(this, Manifest.permission.CAMERA, CAMERA);
    }



    //Called when QR scan is needed
    private void tryToScan() {
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).setRequestedPreviewSize(256, 256).build();
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                    final SparseArray<Barcode> codes = detections.getDetectedItems();
                if (codes.size() > 0) {//if allowed to scan & codes detected
                    txtQr.post(new Runnable() {
                        @Override
                        public void run() {
                            String code = codes.valueAt(0).displayValue;
                            txtQr.setText(code);
                            Intent intent = new Intent(BarcodScannerActivity.this,LoadingCart.class);
                            setContentView(R.layout.activity_loading_page);
                            imageView = (ImageView)findViewById(R.id.image);
                            imageView.setBackgroundResource(R.drawable.animation);
                            cartAnimation =(AnimationDrawable)imageView.getBackground();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onStart();

                                }
                            },SPLASH_TIME_OUT);
                            cartAnimation.start();
                            Intent i = new Intent(BarcodScannerActivity.this, ScanResultActivity.class);
                            i.putExtra(ScanResultActivity.CODE, code);
                            startActivity(i);
                        }
                    });
                    // now go with the barcode number to check if exist in the JSON file

                }
            }
        });
        //Adds SurfaceHolder.Callback to SurfaceHolder of SurfaceView by id R.id.scanQr (valid id)
        ((SurfaceView) findViewById(R.id.scanQr)).getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                try {
                    //Sometimes starts successfully but sometimes stays black
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    askCameraPermission();
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

    }




}


