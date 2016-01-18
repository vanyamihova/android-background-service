package com.fos.trackingapplication.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.fos.trackingapplication.controller.PreferenceService;
import com.fos.trackingapplication.listener.CustomLocationListener;

/**
 * Created by Vanya Mihova on 11/16/2015.
 */
public class TrackingService extends Service {

    public final static String RESULT = "com.fos.trackingapplication.service.TrackingService.REQUEST_PROCESSED";

    private Handler mHandler;
    private Location mLocation;
    private PreferenceService mPreferenceService;
    private LocalBroadcastManager mLocalBroadcastManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Start", Toast.LENGTH_LONG).show();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new CustomLocationListener(this);

        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        String providerInfo = LocationManager.GPS_PROVIDER;
        if(isNetworkEnabled)
            providerInfo = LocationManager.NETWORK_PROVIDER;

        locationManager.requestLocationUpdates(providerInfo, 10000, 0, locationListener);

        if(locationManager != null)
            mLocation = locationManager.getLastKnownLocation(providerInfo);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mPreferenceService = PreferenceService.getInstance(this);
        mHandler = new Handler();

        saveDataInPrefs();

        return START_STICKY;
    }


//    public void setPreferenceService(PreferenceService preferenceService) {
//        this.mPreferenceService = preferenceService;
//    }



    private void saveDataInPrefs() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(mHandler != null && mLocation != null && mPreferenceService != null) {
                    int counter = mPreferenceService.getCount();

                    mPreferenceService.setLngString(counter, String.valueOf(mLocation.getLongitude()));
                    mPreferenceService.setLtdString(counter, String.valueOf(mLocation.getLatitude()));

                    counter++;
                    mPreferenceService.setCount(counter);

                    // notify UI for changes
                    sendNotifyToUI();

                    mHandler.postDelayed(this, 10000);
                }

            }
        });
    }


    public void sendNotifyToUI() {
        mLocalBroadcastManager.sendBroadcast(new Intent(RESULT));
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler = null;
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


}
