package com.cs4720.ms1;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Date;

public class MyService extends Service implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Handler mHandler = new Handler();
    private static long UPDATE_INTERVAL = 5000;
    protected GoogleApiClient googleApiClient;
    protected ArrayList<Location> coords;
    protected long start;
    protected long end;
    protected String name;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        googleApiClient.connect();
        Bundle bundle = intent.getBundleExtra("bundle");
        start = bundle.getLong("start");
        end = bundle.getLong("end");
        name = bundle.getString("name");
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onCreate() {
        Log.i("====================","============================================");

        buildGoogleApiClient();
        coords = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
        Log.i("Intent Example", "Service onDestroy");
        _shutdownService();

    }

    private void _shutdownService() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        Log.i("Intent Example", "Timer stopped...");
    }

    private void doServiceWork() {
        if(end < System.currentTimeMillis())
        {
            mHandler.removeCallbacks(mUpdateTimeTask);
            stopSelf();
        }
        if(start < System.currentTimeMillis()) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            coords.add(lastLocation);
            Log.i("=======================","Lat: "+Double.toString(lastLocation.getLatitude())+" Long: "+
                    Double.toString(lastLocation.getLongitude()));
        }
        else{
            Log.i("=======================","Service Not Ready");
        }

    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            doServiceWork();
            mHandler.postDelayed(this, UPDATE_INTERVAL);
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("====================","============================================");
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, UPDATE_INTERVAL);
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        googleApiClient.connect();
    }
}

