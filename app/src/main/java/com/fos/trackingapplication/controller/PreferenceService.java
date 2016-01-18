package com.fos.trackingapplication.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.fos.trackingapplication.R;

/**
 * Created by Vanya Mihova on 11/19/2015.
 */
public class PreferenceService {

    private static final String SHARED_PREFERENCES_COUNT = "SharedPreferencesCount";
    private static PreferenceService Instance;
    private SharedPreferences mSharedPreferences;

    public static PreferenceService getInstance(Context context) {
        if(Instance == null) {
            Instance = new PreferenceService(context);
        }
        return Instance;
    }


    public PreferenceService(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }


    public int getCount() {
        return mSharedPreferences.getInt(SHARED_PREFERENCES_COUNT, 0);
    }


    public void setCount(int count) {
        mSharedPreferences.edit().putInt(SHARED_PREFERENCES_COUNT, count).commit();
    }


    public void setLngString(int key, String value) {
        mSharedPreferences.edit().putString("lng" + key, value).commit();
    }


    public String getLngString(int key) {
        return mSharedPreferences.getString("lng" + key, null);
    }





    public void setLtdString(int key, String value) {
        mSharedPreferences.edit().putString("ltd" + key, value).commit();
    }


    public String getLtdString(int key) {
        return mSharedPreferences.getString("ltd" + key, null);
    }

}
