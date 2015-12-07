package com.cs4720.ms1;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.Parse;
import com.parse.ParseObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Brian on 9/29/2015.
 */
public class TrackCoords extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    public static long UPDATE_INTERVAL = 5000;
    protected GoogleApiClient googleApiClient;
    boolean connected = false;

    public TrackCoords() {

        super("TrackCoords");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        DBHelper db = new DBHelper(this);
        ArrayList<Coord> coords = new ArrayList<>();
        String name = intent.getStringExtra("name");
        EventTracker e = db.getEvent(name);
        long start = e.getStartTimeInMillis();
        long end = e.getEndTimeInMillis();
        buildGoogleApiClient();
        googleApiClient.connect();
        while(!connected);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Izpp6si7XyILb60PlFhPa4dms9DjtGcrT6cTTphK", "UFquFrrczNxyrVGZWdbiPs4M3197Sh6zBgtqQVlL");
        while(System.currentTimeMillis() - end < 0)
        {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if( location != null) {
                Log.i("Recording Location", "(" + location.getLatitude() + "," + location.getLongitude() + ")");
                Coord c = new Coord(location.getLatitude(),location.getLongitude(), System.currentTimeMillis());

                ParseObject locationObject = new ParseObject("locationObject");
                locationObject.put("latitude", location.getLatitude());
                locationObject.put("longitude",location.getLongitude());
                locationObject.saveInBackground();
                String FILENAME = "gps_storage.txt";
            }
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
}
