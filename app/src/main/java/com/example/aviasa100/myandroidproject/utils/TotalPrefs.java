package com.example.aviasa100.myandroidproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class TotalPrefs {

    private static final String FILE_NAME = "total";
    private static final String TOTAL = "totalPrice";


    public static Set<String> getTotalPrice(Context context){
        return getPrefs(context).getStringSet(TOTAL, new HashSet<String>());
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

}
