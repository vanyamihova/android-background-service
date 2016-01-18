package com.fos.trackingapplication.listener;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Vanya Mihova on 11/17/2015.
 */
public class CustomLocationListener implements LocationListener {

    private Context mContext;

    public CustomLocationListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
