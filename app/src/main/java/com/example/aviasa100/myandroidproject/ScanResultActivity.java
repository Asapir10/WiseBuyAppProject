package com.example.aviasa100.myandroidproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.aviasa100.myandroidproject.utils.ImageLoadingTask;
import com.example.aviasa100.myandroidproject.utils.HttpRequest;
import com.example.aviasa100.myandroidproject.utils.Prefs;
import com.example.aviasa100.myandroidproject.utils.ProductDetails;
import com.example.aviasa100.myandroidproject.utils.getJson;
import com.google.android.gms.common.images.ImageManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.net.HttpURLConnection;

import static android.app.PendingIntent.getActivity;

public class ScanResultActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CODE = "code";

    ScanResultActivity self = this;
    private String codeResult,  selectedProduct;
    public ImageView productV, loadList, myShopCart;
    private TextView counterTxt, productName, weight, price;
    public Button addBtn, reduceBtn, applyBtn;
    int counter;
    long priceOfProduct;
    public long totalAmount = 0;
    private static final String CHOSEN_AMOUNT_FILE = "com.aviasa100.CHOSEN_AMOUNT";
    private static final String TOTAL_PRICE_FILE = "com.aviasa100.TOTAL_PRICE";
    private static final String CHOSEN_AMOUNT_KEY = "chosenAmount";
    private static final String TOTAL_PRICE_KEY = "totalPrice";
    public SharedPreferences sharedPrefs,totalPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        sharedPrefs = getSharedPreferences(CHOSEN_AMOUNT_FILE, MODE_PRIVATE);
        totalPrefs = getSharedPreferences(TOTAL_PRICE_FILE, MODE_PRIVATE);
        counter = 1;
        //loadList = (ImageView)findViewById(R.id.myShoppingCart) ;
        productV = (ImageView)findViewById(R.id.imageView) ;
        counterTxt = (TextView)findViewById(R.id.chosenAmount);
        addBtn = (Button)findViewById(R.id.addBtn);
        reduceBtn = (Button)findViewById(R.id.reduceBtn);
        applyBtn = (Button)findViewById(R.id.applyBtn);
        myShopCart = findViewById(R.id.myShoppingCart);
        productName = findViewById(R.id.productName);
        weight = findViewById(R.id.weightResult);
        price = findViewById(R.id.priceResult);

        myShopCart.setOnClickListener(self);
        //loadList.setOnClickListener(self);
        addBtn.setOnClickListener(self);
        reduceBtn.setOnClickListener(self);
        counterTxt.setOnClickListener(self);
        applyBtn.setOnClickListener(self);
        price.setOnClickListener(self);
    }

    @Override
    protected void onStart() {
        super.onStart();

        codeResult = getIntent().getStringExtra(CODE);
        showDetails();

    }

    public static void loadImage(final ImageView img, final String url){
        new Thread(){//background thread
            public void run() {
                try {
                    final Bitmap bm = BitmapFactory.decodeStream(new URL(url).openStream());
                    img.post(new Runnable() {//UI Main thread
                        @Override
                        public void run() {
                            img.setImageBitmap(bm);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addBtn:
                showCounter(1);
                break;
            case R.id.reduceBtn:
                showCounter(-1);
                break;
            case R.id.applyBtn:
                continueShopping();
                break;
            case R.id.myShoppingCart:
                goToCart();
                break;
            default:

        }

    }
    private void showCounter(int delta){
        if(counter + delta >= 0){//if after change - isn't lower than zero
            counter += delta;
            counterTxt.setText(" " + counter);
            if(selectedProduct != null){
                Prefs.setProductCount(self, selectedProduct, counter);
            }
        }
    }

    private void showPrice(long delta1){
        if(priceOfProduct + delta1 >= 0){//if after change - isn't lower than zero
            priceOfProduct += delta1;
            price.setText(" " + priceOfProduct);
            if(selectedProduct != null){
                Prefs.setProductPrice(self, selectedProduct, priceOfProduct);
                addPrice(delta1);
                totalAmount += priceOfProduct;

            }
        }
    }

    private void addPrice(long delta1) {
        if(priceOfProduct + delta1 >= 0) {
            totalAmount += priceOfProduct; //add the price to total amount of the shopping
        }

    }

    private void continueShopping() {
        sharedPrefs.edit().putInt(CHOSEN_AMOUNT_KEY, counter).apply();
        //totalPrefs.edit().putLong(TOTAL_PRICE_KEY, priceOfProduct).apply();
        Intent applyIntent = new Intent(self , BarcodScannerActivity.class);
        startActivity(applyIntent);
        finish();
    }

    private void goToCart() {

        Intent listIntent = new Intent(self, MyShoppingCartListActivity.class);

        startActivity(listIntent);

        finish();


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void setSelectedProduct(String pN){
        if(pN != null){
            selectedProduct = pN;
            Prefs.addProductName(self, pN);


        }
    }

    public void showDetails(){

        if(codeResult != null){

            JSONObject json = getJson.getDataByCode(codeResult);
            if(json != null) {
                String name = json.optString("name", " ");
                System.out.println(name);

                setSelectedProduct(name);
                productName.setText(name == null ? "no name" : selectedProduct);

                weight.setText(json.optString("weight", "0"));
                price.setText(json.optString("price", "0"));
                loadImage(productV,json.optString("image"));

            }
            else
                throw new NullPointerException("dugma2 Type cannot be null");
            //ImageLoadingTask.loadImage(json.optString("image",null),productV);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPrefs.edit();
        //  SharedPreferences.Editor editor2 = totalPrefs.edit();

        editor.putInt(CHOSEN_AMOUNT_KEY,counter);
        //  editor2.putLong(TOTAL_PRICE_KEY,priceOfProduct); //save the product price and send it to the cart list

        editor.apply();
    }

}
