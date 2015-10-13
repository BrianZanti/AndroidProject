package com.cs4720.ms1;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Brian on 9/29/2015.
 */
public class TrackCoords extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    //private Handler mHandler = new Handler();
    public static long UPDATE_INTERVAL = 5000;
    protected GoogleApiClient googleApiClient;
    boolean connected = false;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public TrackCoords() {
        super("Tack Coordinates");
    }

    public void alert(String alert) {
        Toast.makeText(this, alert, Toast.LENGTH_LONG).show();
    }

    protected void onHandleIntent(Intent intent) {
        Log.i("Handling Intent", "Handling Intent");
        try {
            Thread.sleep((long) 5000);
        }
        catch(Exception e){}
        Log.i("Waking up", "Waking Up");
        buildGoogleApiClient();
        googleApiClient.connect();
        while(!connected);
        long endTime = System.currentTimeMillis()+60000;
        while(System.currentTimeMillis() - endTime < 0)
        {
            Log.i("While Loop","While Loop");
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if( location != null) {
                Log.i("Recording Location", "(" + location.getLatitude() + "," + location.getLongitude() + ")");
                String s =  "(" + location.getLatitude() + "," + location.getLongitude() + ")";
                String FILENAME = "gps_storage.txt";
                try {
                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
                    fos.write(s.getBytes());
                    fos.close();
                } catch (FileNotFoundException e ){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            try {
                Thread.sleep(5000);
            }
            catch(Exception e){}
        }
        Log.i("Ending Thread","Ending Thread");
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        connected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("Connection Suspended","Connection Suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("Connection Failed","Connection Failed");
        googleApiClient.connect();
    }

    /*private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Toast.makeText(TrackCoords.this, "Current Time: " + System.currentTimeMillis(), Toast.LENGTH_LONG).show();

            mHandler.postDelayed(this, UPDATE_INTERVAL);
        }
    };*/
}
