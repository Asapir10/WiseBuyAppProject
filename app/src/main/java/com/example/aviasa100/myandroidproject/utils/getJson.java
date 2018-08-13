package com.example.aviasa100.myandroidproject.utils;

import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.example.aviasa100.myandroidproject.ScanResultActivity;
import com.example.aviasa100.myandroidproject.utils.HttpRequest;
import android.os.StrictMode;

public class getJson  {


    public static  JSONObject getDataByCode(String code){
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);


        }

        final String BaseUrl = "https://f8e92d86-4273-418f-9df9-66cc168584ee-bluemix.cloudant.com/products_details/884676cc54ac65956ab5c69ea6a5118e";

        try {
            JSONObject res = new HttpRequest(BaseUrl).withHeaders("Accept:application/json").prepare().sendAndReadJSON();
            System.out.println(res);
            return res.optJSONObject("data").optJSONObject(code);


        }
         catch (IOException | JSONException e)
         {
             e.printStackTrace();
             return null;
         }


    }

}
