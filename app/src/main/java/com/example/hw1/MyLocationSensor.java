/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/20/19 9:22 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/20/19 9:21 AM
 *
 */

package com.example.hw1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyLocationSensor {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MySharedPref msp;

    public MyLocationSensor(Activity activity) {
        //Location Settings
        msp = new MySharedPref(activity);
        resetLatLong();
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                msp.putFloat("playerLongitude", (float) longitude);
                msp.putFloat("playerLatitude", (float) latitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }


    }

    /**
     * Clear the LatLong from previous player
     */
    private void resetLatLong(){
        msp.putFloat("playerLongitude", (float) 0);
        msp.putFloat("playerLatitude", (float) 0);
    }
}
