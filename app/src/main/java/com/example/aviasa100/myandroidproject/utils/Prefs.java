package com.example.aviasa100.myandroidproject.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.example.aviasa100.myandroidproject.MyShoppingCartListActivity;
import com.example.aviasa100.myandroidproject.ScanResultActivity;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Prefs {

    public static final String FILE_NAME = "shoppingCart";
    public static final String NAMES = "productsNames";



    public static Set<String> getProductNames(Context context){

               return getPrefs(context).getStringSet(NAMES, new HashSet<String>());

    }


    public static  void addProductName(Context context, String productName){

        if(productName != null){
            Set<String> names = new HashSet(getProductNames(context));

            names.add(productName);//added only if not exists
            Intent sendIntent = new Intent(context,MyShoppingCartListActivity.class);
            sendIntent.putExtra(productName,0);

            System.out.println("*************"+names + "**************"); // it returns the array with the names
            getPrefs(context).edit().putStringSet(NAMES, names).apply();//AVIA - save back to prefs...
        }
     }


    public static int getProductCount(Context context, String productName){
        return getPrefs(context).getInt(productName, 0);
    }


    public static long getProductPrice(Context context, String productName){
        return getPrefs(context).getInt(productName, 0);
    }







    public static void setProductCount(Context context, String productName, int counter){
        if(counter >= 0 && !productName.trim().isEmpty()){//validations
            getPrefs(context).edit().putInt(productName, counter).apply();
        }
    }


    public static void setProductPrice(Context context, String productName, long priceOfProduct){
        if(priceOfProduct >= 0 && !productName.trim().isEmpty()){//validations
            getPrefs(context).edit().putLong(productName,priceOfProduct).apply();
            //totalAmount += priceOfProduct;
        }
    }


    private static SharedPreferences getPrefs(Context context){
       // context.getApplicationContext();
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);


    }



}
